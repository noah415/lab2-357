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
        System.out.println("REEEEEE");
    }
    

}
