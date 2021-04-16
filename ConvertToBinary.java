

class ConvertToBinary
{
  public static int[] decToBin(int length, int val)
  {
    int binary[length];
    int index = 0;

    while (val > 0)
    {
      binary[--length] = val % 2;
      val = val / 2;
    }

    return binary;
  }
}
