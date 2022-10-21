import java.util.ArrayList;

public class GameHelper
{
    int[][] gameBoard;
    static final int EMPTY = 0, RED = 1, RED_DOUBLE = 2, BLACK = 3, BLACK_DOUBLE = 4;

    GameHelper()
    {
        //init board from scratch with empty squares, red squares, and black squares
        gameBoard = new int[8][8];
        for (int row = 0; row < gameBoard.length; row++)
        {
            for (int col = 0; col < gameBoard[0].length; col++)
            {
                gameBoard[row][col] = EMPTY;
            }
        }
        for (int row =0; row < 3; row++) {
            for (int col = 0; col < gameBoard[0].length; col++)
            {
                if (row % 2 == col % 2)
                {
                    gameBoard[row][col] = BLACK;
                }
            }
        }
        for (int row = gameBoard.length-1; row > gameBoard.length-4; row--)
        {
            for (int col = 0; col < gameBoard[0].length; col++)
            {
                if (row % 2 == col % 2) {
                    gameBoard[row][col] = RED;
                }
            }
        }
    }

    GameHelper(int[][] initBoard)
    {
        //init a gameboard using custom data
        gameBoard = new int[8][8];
        for (int i = 0; i < initBoard.length; i++)
        {
            for (int j = 0; j < initBoard[0].length; j++)
            {
                gameBoard[i][j] = initBoard[i][j];
            }
        }
    }

    boolean executeMove(Move move)
    {
        int xCurrent = move.xCurrent;
        int yCurrent = move.yCurrent;
        int xNext = move.xNext;
        int yNext = move.yNext;

        if (move.isJump())
        {
            if (xNext - xCurrent < 0)
            {
                if (yNext - yCurrent < 0)
                {
                    gameBoard[xCurrent - 1][yCurrent - 1] = EMPTY;
                }
                else
                {
                    gameBoard[xCurrent - 1][yCurrent + 1] = EMPTY;
                }
            }
            if (xNext - xCurrent > 0)
            {
                if (yNext - yCurrent > 0)
                {
                    gameBoard[xCurrent + 1][yCurrent + 1]= EMPTY;
                }
                else
                {
                    gameBoard[xCurrent + 1][yCurrent - 1]= EMPTY;
                }
            }
        }
        if (gameBoard[xCurrent][yCurrent] == RED && xNext == 0)
        {
            gameBoard[xNext][yNext] = RED_DOUBLE;
            gameBoard[xCurrent][yCurrent] = EMPTY;
            return true;
        }
        else if (gameBoard[xCurrent][yCurrent] == BLACK && xNext == gameBoard.length - 1)
        {
            gameBoard[xNext][yNext] = BLACK_DOUBLE;
            gameBoard[xCurrent][yCurrent] = EMPTY;
            return true;
        }
        else
        {
            gameBoard[xNext][yNext] = gameBoard[xCurrent][yCurrent];
            gameBoard[xCurrent][yCurrent] = EMPTY;
            return false;
        }
    }

    boolean isRed(int x, int y)
    {
        if (gameBoard[x][y] == RED || gameBoard[x][y] == RED_DOUBLE)
        {
            return true;
        }
        return false;
    }

    boolean isBlack(int x, int y)
    {
        if (gameBoard[x][y] == BLACK || gameBoard[x][y] == BLACK_DOUBLE)
        {
            return true;
        }
        return false;
    }

    /**
     * get all possible moves for player
     */
    Move[] getMoves(int player)
    {
        ArrayList<Move> jumps = new ArrayList<Move>();
        ArrayList<Move> moves = new ArrayList<Move>();

        if (player == RED)
        {
            for (int i = 0; i < gameBoard.length; i ++)
            {
                for (int j = 0; j < gameBoard[0].length; j++)
                {
                    if (isRed(i, j))
                    {
                        Move move;
                        //up-left
                        if(i > 0 && j > 0 && gameBoard[i - 1][j - 1] == EMPTY)
                        {
                            move = new Move(i, j, i - 1, j - 1);
                            moves.add(move);
                        }
                        //up-left-jump
                        else if (i > 1 && j > 1 && gameBoard[i - 2][j - 2] == EMPTY && isBlack(i - 1, j - 1))
                        {
                            move = new Move(i, j, i - 2, j - 2);
                            jumps.add(move);
                        }
                        //up-right
                        if (i > 0 && j < gameBoard[0].length - 1 && gameBoard[i - 1][j + 1] == EMPTY)
                        {
                            move = new Move(i, j, i - 1, j + 1);
                            moves.add(move);
                        }
                        //up-right-jump
                        else if (i > 1 && j < gameBoard[0].length - 2 && gameBoard[i - 2][j + 2] == EMPTY && isBlack(i - 1, j + 1))
                        {
                            move = new Move(i, j, i - 2, j + 2);
                            jumps.add(move);
                        }
                    }
                    if (gameBoard[i][j]== RED_DOUBLE)
                    {
                        Move move;
                        //down-left
                        if (i < gameBoard.length - 1 && j > 0 && gameBoard[i + 1][j - 1] == EMPTY)
                        {
                            move = new Move(i, j, i + 1, j - 1);
                            moves.add(move);
                        }
                        //down-left-jump
                        else if (i < gameBoard.length - 2 && j > 1 && gameBoard[i + 2][j - 2] == EMPTY && isBlack(i + 1, j - 1))
                        {
                            move = new Move(i, j, i + 2, j - 2);
                            jumps.add(move);
                        }
                        //down-right
                        if (i < gameBoard.length - 1  && j < gameBoard[0].length - 1 && gameBoard[i + 1][j + 1] == EMPTY)
                        {
                            move = new Move(i, j, i + 1, j + 1);
                            moves.add(move);
                        }
                        //down-right-jump
                        else if (i < gameBoard.length - 2 && j < gameBoard[0].length - 2 && gameBoard[i + 2][j + 2] == EMPTY && isBlack(i + 1, j + 1))
                        {
                            move = new Move(i, j, i + 2, j + 2);
                            jumps.add(move);
                        }
                    }
                }
            }
        }

        if (player == BLACK)
        {
            for (int i = 0; i < gameBoard.length; i ++)
            {
                for (int j = 0; j < gameBoard[0].length; j++)
                {
                    if (isBlack(i, j))
                    {
                        Move move;
                        //down-left
                        if (i < gameBoard.length - 1 && j > 0 && gameBoard[i + 1][j - 1] == EMPTY)
                        {
                            move = new Move(i, j, i + 1, j - 1);
                            moves.add(move);
                        }
                        //down-left-jump
                        else if (i < gameBoard.length - 2 && j > 1 && gameBoard[i + 2][j - 2] == EMPTY && isRed(i + 1, j - 1))
                        {
                            move = new Move(i, j, i + 2, j - 2);
                            jumps.add(move);
                        }
                        //down-right
                        if (i < gameBoard.length - 1  && j < gameBoard[0].length - 1 && gameBoard[i + 1][j + 1] == EMPTY)
                        {
                            move = new Move(i, j, i + 1, j + 1);
                            moves.add(move);
                        }
                        //down-right-jump
                        else if (i < gameBoard.length - 2 && j < gameBoard[0].length - 2 && gameBoard[i + 2][j + 2] == EMPTY && isRed(i + 1, j + 1))
                        {
                            move = new Move(i, j, i + 2, j + 2);
                            jumps.add(move);
                        }
                    }
                    if (gameBoard[i][j]== BLACK_DOUBLE)
                    {
                        Move move;
                        //up-left
                        if(i > 0 && j > 0 && gameBoard[i - 1][j - 1] == EMPTY)
                        {
                            move = new Move(i, j, i - 1, j - 1);
                            moves.add(move);
                        }
                        //up-left-jump
                        else if (i > 1 && j > 1 && gameBoard[i - 2][j - 2] == EMPTY && isRed(i - 1, j - 1))
                        {
                            move = new Move(i, j, i - 2, j - 2);
                            jumps.add(move);
                        }
                        //up-right
                        if (i > 0 && j < gameBoard[0].length - 1 && gameBoard[i - 1][j + 1] == EMPTY)
                        {
                            move = new Move(i, j, i - 1, j + 1);
                            moves.add(move);
                        }
                        //up-right-jump
                        else if (i > 1 && j < gameBoard[0].length - 2 && gameBoard[i - 2][j + 2] == EMPTY && isRed(i - 1, j + 1))
                        {
                            move = new Move(i, j, i - 2, j + 2);
                            jumps.add(move);
                        }
                    }
                }
            }
        }
        if (jumps.size() > 0)
        {
            Move[] jumpMoveList = new Move[jumps.size()];
            jumpMoveList = jumps.toArray(jumpMoveList);
            return jumpMoveList;
        }
        else if (moves.size() > 0)
        {
            Move[] moveList = new Move[moves.size()];
            moveList = moves.toArray(moveList);
            return moveList;
        }
        else
        {
            return null;
        }
    }

    /**
     * get all possible moves from a specific tile
     */
    Move[] getMoves(int player, int x, int y)
    {
        ArrayList<Move> jumps = new ArrayList<Move>();

        if (player == RED)
        {
            if (isRed(x, y))
            {
                Move move;
                //up-left-jump
                if (x > 1 && y > 1 && gameBoard[x - 2][y - 2] == EMPTY && isBlack(x - 1, y - 1))
                {
                    move = new Move(x, y, x - 2, y - 2);
                    jumps.add(move);
                }
                //up-right-jump
                if (x > 1 && y < gameBoard[0].length - 2 && gameBoard[x - 2][y + 2] == EMPTY && isBlack(x - 1, y + 1))
                {
                    move = new Move(x, y, x - 2, y + 2);
                    jumps.add(move);
                }
            }
            if (gameBoard[x][y]== RED_DOUBLE)
            {
                Move move;
                //down-left-jump
                if (x < gameBoard.length - 2 && y > 1 && gameBoard[x + 2][y - 2] == EMPTY && isBlack(x + 1, y - 1))
                {
                    move = new Move(x, y, x + 2, y - 2);
                    jumps.add(move);
                }
                //down-right-jump
                if (x < gameBoard.length - 2 && y < gameBoard[0].length - 2 && gameBoard[x + 2][y + 2] == EMPTY && isBlack(x + 1, y + 1))
                {
                    move = new Move(x, y, x + 2, y + 2);
                    jumps.add(move);
                }
            }
        }

        if (player == BLACK)
        {
            if (isBlack(x, y))
            {
                Move move;
                //down-left-jump
                if (x < gameBoard.length - 2 && y > 1 && gameBoard[x + 2][y - 2] == EMPTY && isRed(x + 1, y - 1))
                {
                    move = new Move(x, y, x + 2, y - 2);
                    jumps.add(move);
                }
                //down-right-jump
                if (x < gameBoard.length - 2 && y < gameBoard[0].length - 2 && gameBoard[x + 2][y + 2] == EMPTY && isRed(x + 1, y + 1))
                {
                    move = new Move(x, y, x + 2, y + 2);
                    jumps.add(move);
                }
            }
            if (gameBoard[x][y]== BLACK_DOUBLE)
            {
                Move move;
                //up-left-jump
                if (x > 1 && y > 1 && gameBoard[x - 2][y - 2] == EMPTY && isRed(x - 1, y - 1))
                {
                    move = new Move(x, y, x - 2, y - 2);
                    jumps.add(move);
                }
                //up-right-jump
                if (x > 1 && y < gameBoard[0].length - 2 && gameBoard[x - 2][y + 2] == EMPTY && isRed(x - 1, y + 1))
                {
                    move = new Move(x, y, x - 2, y + 2);
                    jumps.add(move);
                }
            }
        }
        if (jumps.size() > 0)
        {
            Move[] jumpMoveList = new Move[jumps.size()];
            jumpMoveList = jumps.toArray(jumpMoveList);
            return jumpMoveList;
        }
        else
        {
            return null;
        }
    }
}