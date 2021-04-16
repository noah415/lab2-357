abstract class Instruction
{
  int opcode;

  public Instruction(int opcode)
  {
    this.opcode = opcode;
  }

  abstract public void printBinary();

}
