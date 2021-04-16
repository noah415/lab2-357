class jFormat extends Instruction
{
    private int address;

    public jFormat(int opcode, int address){
        super(opcode);
        this.address = address;
    }

}