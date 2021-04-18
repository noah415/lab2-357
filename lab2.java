import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.*;

class lab2
{
  static HashMap<String, Integer[]> opcodes = new HashMap<String, Integer[]>();
  static HashMap<String, Integer> labels = new HashMap<String, Integer>();

  private void initMap()
  {
    this.opcodes.put("and", new Integer[] {0,36});
    this.opcodes.put("or", new Integer[] {0,37});
    this.opcodes.put("add", new Integer[] {0,32});
    this.opcodes.put("addi", new Integer[] {8,0});
    this.opcodes.put("sll", new Integer[] {0,0});
    this.opcodes.put("sub", new Integer[] {0,34});
    this.opcodes.put("slt", new Integer[] {0,42});
    this.opcodes.put("beq", new Integer[] {4,0});
    this.opcodes.put("bne", new Integer[] {5,0});
    this.opcodes.put("lw", new Integer[] {35,0});
    this.opcodes.put("sw", new Integer[] {43,0});
    this.opcodes.put("j", new Integer[] {2,0});
    this.opcodes.put("jr", new Integer[] {0,8});
    this.opcodes.put("jal", new Integer[] {3,0});
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
      // read lines in file
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
