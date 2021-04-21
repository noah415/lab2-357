class JFormat extends Instruction
{
    private int address;

    public JFormat(int opcode, int address){
        super(opcode);
        this.address = address;
    }

    // TODO: insert logic for calculating the address of label

    // TODO: insert logic for printing in binary
    public void printBinary()
    {
      String op = Instruction.bTS(opcode, 6);
      String sadd = Instruction.bTS(address, 5);
      System.out.println(op + " " + sadd);
    }

}
