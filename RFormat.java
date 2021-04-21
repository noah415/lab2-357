import java.util.*;

class RFormat extends Instruction
{
  public int opcode;
  public int rs;
  public int rt;
  public int rd;
  public int shamt;
  public int funct;

  public RFormat(int opcode, int dest, int op1, int op2, int funct)
  {
    super(opcode);
    this.funct = funct;

    if (opcode == 0 && funct == 0)
    {
      this.rd = dest;
      this.rs = 0;
      this.rt = op1;
      this.shamt = op2;
    }
    else
    {
      this.rd = dest;
      this.rs = op1;
      this.rt = op2;
      this.shamt = 0;
    }
  }

  public void printBinary()
  {
    System.out.println("REEEEEE");
  }

}
