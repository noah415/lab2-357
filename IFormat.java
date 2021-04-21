class IFormat extends Instruction{
    private int rs;
    private int rt;
    private int immediate;

    public IFormat(int opcode, int rs, int rt, int immediate){
        super(opcode);
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate;
    }

    // TODO: insert logic for calculating the address of label (if needed)

    // TODO: insert logic for printing in binary
    public void printBinary()
    {
      String op = Instruction.bTS(opcode, 6);
      String srs = Instruction.bTS(rs, 5);
      String srt = Instruction.bTS(rt, 5);
      String simm = Instruction.bTS(immediate, 16);
      System.out.println(op + " " + srs + " " + srt + " " + simm);
    }


}
