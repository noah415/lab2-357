import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.*;

class lab2
{
  final int initAddress = 0; // this is the address of the first instruction in MIPS
  static HashMap<String, Integer[]> rcodes = new HashMap<String, Integer[]>();
  static HashMap<String, Integer[]> icodes = new HashMap<String, Integer[]>();
  static HashMap<String, Integer[]> jcodes = new HashMap<String, Integer[]>();
  static HashMap<String, Integer> labels = new HashMap<String, Integer>();
  static HashMap<String, Integer> registers = new HashMap<String, Integer>();

  static void putValues(char prefix, int start, int end, int offset, HashMap<String, Integer> map){
    for(int i = start; i <= end; i++)
      map.put(prefix + String.valueOf(i), i + offset);
  }
  private static void initMap()
  {
    // value = array {opcode, funct, FORMATCODE} (decimal)
    //FORMATCODE
    //1 = R
    //2 = I
    //3 = J
   rcodes.put("and", new Integer[] {0,36,1});
   rcodes.put("or", new Integer[] {0,37,1});
   rcodes.put("add", new Integer[] {0,32,1});
   rcodes.put("sll", new Integer[] {0,0,1});
   rcodes.put("sub", new Integer[] {0,34,1});
   rcodes.put("slt", new Integer[] {0,42,1});
   rcodes.put("jr", new Integer[] {0,8,1});

   icodes.put("addi", new Integer[] {8,0,2});
   icodes.put("beq", new Integer[] {4,0,2});
   icodes.put("bne", new Integer[] {5,0,2});
   icodes.put("lw", new Integer[] {35,0,2});
   icodes.put("sw", new Integer[] {43,0,2});

   jcodes.put("j", new Integer[] {2,0,3});
   jcodes.put("jal", new Integer[] {3,0,3});

   registers.put("zero", 0);
   registers.put("0", 0);
   registers.put("v0", 2);
   registers.put("v1", 3);
   registers.put("a0", 4);
   registers.put("a1", 5);
   registers.put("a2", 6);
   registers.put("a3", 7);
   registers.put("t0", 8);
   registers.put("t1", 9);
   putValues('t', 0, 7, 8, registers);
   putValues('s', 0, 7, 16, registers);
   registers.put("t8", 24);
   registers.put("t9", 25);
   registers.put("sp", 29);
   registers.put("ra", 31);

  }

  private static boolean validLine(String line, int offset){
    // goes through the line from offset + 1 to end.
    // if no alphanumerica chars are found to the left of a "#", then
    // we have a "blank" line with a label, so we return false
    int len = line.length();
    char c = 0;
    for (int i = offset + 1; i < len; i++){
      c = line.charAt(i);
      if(c == '#')
        return false;
      // if there is a command on line, return True
      if(Character.isLetterOrDigit(c))
        return true;
    }
    // if we reach the end without finding a '#' or alphanumeric
    // line is empty (whitespace)
    return false;
  }

  public static void main(String[] args)
  {
    initMap();
    // line count
    int count = 0;
    // string to read in
    String line = null;
    String label = null;
    int index = 0;
    if(args.length != 1){
      System.out.println("Usage: lab2 <filename>");
      return;
    }
    try {
      Scanner scanner = new Scanner(new File(args[0]));
      // read lines in file (first pass)
      while (scanner.hasNextLine()) {
        // remove all whitespace
        line = scanner.nextLine().trim();
        // get length of line
        int len = line.length();
        // check if line is null or empty after trim
        if((!line.trim().isEmpty() && line != null) && line.charAt(0) == '#'){
          System.out.println("We found a comment!");
          continue;
        }
        // if the line has been trimmed and is still not empty:
        if(len > 0) {
          // check to see if there is a label somewhere to be parsed
          if((index = line.indexOf(':')) != -1) {
            // get the substring
            label = line.substring(0, index);
            System.out.println("Label " + label + " Found! Line " + count + " is: " + line + " Index is: " + index);
            // append to dictionary
            labels.put(label, count);
            // we found a label, check to see if the line is blank (ie just a comment)
            // if so, don't increment count
            if(!validLine(line, index + 1)) {
              System.out.println("Line continues on next one");
              continue;
            }
          }
          else
            System.out.println("Line " + count + " is: " + line);
          // only increment count if it is a valid line (non-comment, non-whitespace)
          count++;
        }
        // process the line
      }
      scanner.close();

      // read lines in file (second pass)
/* ---------------------------------------------------------------------------*/
      System.out.println("keys =" + Arrays.asList(labels.keySet()));

      Scanner scannerOne = new Scanner(new File(args[0]));
      count = 0;
      int hashIndex;
      String opcode;
      int labelIndex;
      List<String> instParts;
      Instruction inst;
      int ln;
      int dest;
      int r1;
      int r2;
      int immediate;
      int addr;

      while (scannerOne.hasNextLine()) {

        // remove all whitespace
        line = scannerOne.nextLine().trim();
        hashIndex = line.indexOf('#');
        if (hashIndex != -1)
          line = line.substring(0, hashIndex);

        if (line.length() == 0) // removes all remaining whitespace and checks the length
          continue;

        labelIndex = line.indexOf(':');

        if (labelIndex != -1) {
          line = line.substring(labelIndex + 1, line.length());
          line = line.trim();
        }

        if (line.length() == 0) // removes all remaining whitespace and checks the length
          continue;
        instParts = Arrays.asList(line.split("[:, $()]+")); //splits line by whitespace and commas
        opcode = instParts.get(0);
        opcode = opcode.trim();
        System.out.println("Opcode is: " + opcode + " count is: " + count);
        if(rcodes.containsKey(opcode)){
          System.out.println("Opcode is R-format");
          ln = instParts.size();
          if(ln == 2){
            // jr instruction detected
            dest = 0;
            r1 = registers.get(instParts.get(1));
            r2 = 0;
          }
          else {
            dest = registers.get(instParts.get(1));
            r1 = registers.get(instParts.get(2));
            if (registers.containsKey(instParts.get(3)))
              r2 = registers.get(instParts.get(3));
            else
              r2 = Integer.parseInt(instParts.get(3));
          }
          RFormat r = new RFormat((int)rcodes.get(opcode)[1], dest, r1, r2, (int)rcodes.get(opcode)[0]);
        }
        else if(icodes.containsKey(opcode)){
          System.out.println("Opcode is I-format");
          for(int j = 0; j < instParts.size(); j++){
            System.out.println("part is : " + instParts.get(j));
          }
          r1 = registers.get(instParts.get(1));
          if(!registers.containsKey(instParts.get(2)))
            Collections.swap(instParts, 2, 3);
          r2 = registers.get(instParts.get(2));
          if(labels.containsKey(instParts.get(3).trim()))
            immediate = labels.get(instParts.get(3).trim()) - count;
          else
            immediate = Integer.parseInt(instParts.get(3));
            IFormat i = new IFormat ((int)icodes.get(opcode)[1], r1, r2, immediate);
        }
        else if(jcodes.containsKey(opcode)){
          System.out.println("Opcode is J-format");
          if(labels.containsKey(instParts.get(1).trim()))
            addr = labels.get(instParts.get(1).trim());
          else
            addr = Integer.parseInt(instParts.get(1));
          JFormat j = new JFormat((int)jcodes.get(opcode)[1], addr);
        }
        else{
          System.out.println("Invalid opcode: -" + opcode + "-");
          return;
        }

        count ++; // 
      }
    }
    catch(FileNotFoundException e){
      System.out.println("File not found!");
      return;
    }
    System.out.println("Function has " + count + " lines!");
  }
}

// superclass: instruction
// superclass: opcode, print method, constructor
// addi: opcode, rs, rt, immediate
// reference dictionary in every each of the 3 unique classes
// R: dictions: arr[]: [opcode, function]
// registers can be hard coded into main
// 32 bit number: instruction = opcode | rs | rt | immediate
