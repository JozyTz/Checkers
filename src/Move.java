public class Move
{
    int xCurrent, yCurrent, xNext, yNext, moveVal;

    Move(int xCurrent, int yCurrent, int xNext, int yNext)
    {
        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
        this.xNext = xNext;
        this.yNext = yNext;
        this.moveVal = Integer.MIN_VALUE;
    }

    boolean isJump()
    {
        return (xCurrent - xNext == 2 || xCurrent - xNext == -2);
    }
}