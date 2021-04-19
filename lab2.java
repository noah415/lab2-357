import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.*;

class lab2
{
  final int initAddress = 0; // this is the address of the first instruction in MIPS
  static HashMap<String, Integer[]> opcodes = new HashMap<String, Integer[]>();
  static HashMap<String, Integer> labels = new HashMap<String, Integer>();

  private void initMap()
  {
    // value = array {opcode, funct, FORMATCODE} (decimal)
    //FORMATCODE
    //1 = R
    //2 = I
    //3 = J
    this.opcodes.put("and", new int[] {0,36,1});
    this.opcodes.put("or", new int[] {0,37,1});
    this.opcodes.put("add", new int[] {0,32,1});
    this.opcodes.put("addi", new int[] {8,0,2});
    this.opcodes.put("sll", new int[] {0,0,1});
    this.opcodes.put("sub", new int[] {0,34,1});
    this.opcodes.put("slt", new int[] {0,42,1});
    this.opcodes.put("beq", new int[] {4,0,2});
    this.opcodes.put("bne", new int[] {5,0,2});
    this.opcodes.put("lw", new int[] {35,0,2});
    this.opcodes.put("sw", new int[] {43,0,2});
    this.opcodes.put("j", new int[] {2,0,3});
    this.opcodes.put("jr", new int[] {0,8,1});
    this.opcodes.put("jal", new int[] {3,0,3});
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


      // read lines in file (second pass)
/* ---------------------------------------------------------------------------*/


      Scanner scanner = new Scanner(new File(args[0]));
      count = 0;
      int hashIndex;
      int[] opcode;
      List<String> instParts;
      Instruction inst;

      while (scanner.hasNextLine()) {
        hashIndex = line.indexOf('#');

        // remove all whitespace
        line = scanner.nextLine().trim();

        if (hashIndex != -1)
          line = line.substring(0, hashIndex);

        if (line.trim().length() == 0) // removes all remaining whitespace and checks the length
          continue;

        labelIndex = line.indexOf(':');

        if (labelIndex != -1)
          line = line.substring(labelIndex, line.length());

        if (line.trim().length() == 0) // removes all remaining whitespace and checks the length
          continue;

        instParts = Arrays.asList(line.split("\\s*,\\s*")); //splits line by whitespace and commas

        opcode = opcodes.get(instParts[0]);

        if (opcode == null)
        {
          System.out.println("Invalid opcode");
          exit()
        }

        if (opcode[2] == 1)
        {
          if (instParts.length() < 3) // for the jal case
            inst = new RFormat(opcode[0], instParts[0], 0, 0, opcode[1]);
          else
            inst = new RFormat(opcode[0], instParts[0], instParts[1], instParts[2], opcode[1]);
        }
        else if (opcode[2] == 2)
          inst = new IFormat(instParts[0], instParts[1], instParts[2]);
        else if (opcode[2] == 3)
          inst = new JFormat(instParts[0], instParts[1], instParts[2]);


        inst.printBinary();

        count += 4; // should we use += 4 to make it easier????
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
