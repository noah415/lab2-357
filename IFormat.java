class IFormat extends Instruction{
    private int rs;
    private int rt;
    private int immediate;

    public IFormat(int opcode, int rs, int rt, int immediate){
        super(opcode);
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate
    }

}