abstract class Instruction
{
  public int opcode;

  public Instruction(int opcode)
  {
    this.opcode = opcode;
  }

  protected void printSect(int arr[])
  {
    for (int bit : arr)
    {
      System.out.print(bit);
    }
    System.out.print();
  }

  abstract public void printBinary();

}
