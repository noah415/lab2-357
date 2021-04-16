import java.util.*;

class lab2
{
  HashMap<String, int[2]> opcodes = new HashMap<String int[2]>();

  private void initMap()
  {
    this.opcodes.put("and", [0,36])
    this.opcodes.put("or", [0,37])
    this.opcodes.put("add", [0,32])
    this.opcodes.put("addi", [8,0])
    this.opcodes.put("sll", [0,0])
    this.opcodes.put("sub", [0,34])
    this.opcodes.put("slt", [0,42])
    this.opcodes.put("beq", [4,0])
    this.opcodes.put("bne", [5,0])
    this.opcodes.put("lw", [35,0])
    this.opcodes.put("sw", [43,0])
    this.opcodes.put("j", [2,0])
    this.opcodes.put("jr", [0,8])
    this.opcodes.put("jal", [3,0])
  }

  public static void main(String[] args)
  {
    initMap();
  }
}

// superclass: instruction
// superclass: opcode, print method, constructor
// addi: opcode, rs, rt, immediate
// reference dictionary in every each of the 3 unique classes
// R: dictions: arr[]: [opcode, function]
// registers can be hard coded into main
// 32 bit number: instruction = opcode | rs | rt | immediate
