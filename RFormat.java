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
      this.rs = dest;
      this.rt = op1;
      this.rd = 0;
      this.shamt = op2;
    }
    else
    {
      this.rs = dest;
      this.rt = op1;
      this.rd = op2;
      this.shamt = 0;
    }
  }

  public void printBinary()
  {
    int instO[6];
    int instRS[5];
    int instRT[5];
    int instRD[5];
    int instS[5];
    int instF[6];

    instO = ConvertToBinary.decToBin(6, this.opcode);
    printSect(instO);
    instRS = ConvertToBinary.decToBin(5, this.rs);
    printSect(instRS);
    instRT = ConvertToBinary.decToBin(5, this.rt);
    printSect(instRT);
    instRD = ConvertToBinary.decToBin(5, this.rd);
    printSect(instRD);
    instS = ConvertToBinary.decToBin(5, this.shamt);
    printSect(instS);
    instF = ConvertToBinary.decToBin(6, this.funct);
    printSect(instF);
  }

}
