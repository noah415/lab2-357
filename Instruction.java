abstract class Instruction
{
  public int opcode;

  public Instruction(int opcode)
  {
    this.opcode = opcode;
  }

  abstract public void printBinary();

}
