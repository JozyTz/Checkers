import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AlphaBeta
{
    static final int EMPTY = 0, RED = 1, RED_DOUBLE = 2, BLACK = 3, BLACK_DOUBLE = 4;
    public int maxDepth;

    public Move AlphaBeta(GameHelper game, int playerTurn)
    {
        Move bestMove = null;
        List<Move> depthMoves = null;
        List<Move> moves = Arrays.asList(game.getMoves(playerTurn));
        if (moves.size() == 1)
        {
            return moves.get(0);
        }
        for (maxDepth = 1; maxDepth <= 7; maxDepth++)
        {
            depthMoves = new ArrayList<>();
            int bestValue = Integer.MIN_VALUE;
            for (Move move : moves)
            {
                GameHelper tmpBoard = new GameHelper(game.gameBoard);
                tmpBoard.executeMove(move);
                int min = minValue(tmpBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, playerTurn);
                if (min == bestValue)
                {
                    move.moveVal = min;
                    depthMoves.add(move);
                }
                if (min > bestValue)
                {
                    depthMoves.clear();
                    move.moveVal = min;
                    depthMoves.add(move);
                    bestValue = min;
                }
                if (bestValue == Integer.MAX_VALUE)
                {
                    break;
                }
            }
            bestMove = getBestMove(depthMoves);
        }
        //for debugging
        for (Move move: depthMoves) {
            System.out.println(move.xCurrent + " " + move.yCurrent + " " + move.xNext + " " + move.yNext + " val:" + move.moveVal);
        }
        return bestMove;
    }

    private Move getBestMove(List<Move> moves)
    {
        if (!moves.isEmpty())
        {
            Random random = new Random();
            int bestMoveIndex = random.nextInt(moves.size());
            return moves.get(bestMoveIndex);
        }
        return null;
    }

    public int getValueNeighbors(GameHelper game, int x, int y)
    {
        int i = x - 1; int j = y - 1; int iMax = x + 1; int jMax = y + 1;
        int valueNeighbors = 0;

        if (x == 0) i = x;
        if (x == 7) iMax = i;
        if (y == 0) j = y;
        if (y == 7) jMax = j;

        for (;i <= iMax; i++)
        {
            for (; j <= jMax; j++)
            {
                if (game.gameBoard[x][y] == BLACK || game.gameBoard[x][y] == BLACK_DOUBLE)
                {
                    if (game.gameBoard[i][j] == BLACK || game.gameBoard[i][j] == BLACK_DOUBLE)
                    {
                        valueNeighbors++;
                    }
                }
                if (game.gameBoard[x][y] == RED || game.gameBoard[x][y] == RED_DOUBLE)
                {
                    if (game.gameBoard[i][j] == RED || game.gameBoard[i][j] == RED_DOUBLE)
                    {
                        valueNeighbors++;
                    }
                }
            }
        }
        return valueNeighbors;
    }

    /**
     * Gets a best move value based on number of pieces, proximity of friendly pieces, and distance of a piece to the
     * opposite edge
     */
    public int moveEvaluator(GameHelper game)
    {
        int moveValue = 0;
        int blackPieceValues = 0;
        int redPieceValues = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                switch (game.gameBoard[i][j])
                {
                    case (RED):
                        redPieceValues += 3;
                        moveValue -= getValueNeighbors(game, i, j) + 7 - i;
                        break;
                    case (RED_DOUBLE):
                        redPieceValues += 6;
                        moveValue -= getValueNeighbors(game, i, j) + 7 - i;
                        break;
                    case (BLACK):
                        blackPieceValues += 3;
                        moveValue += getValueNeighbors(game, i, j) + (i);
                        break;
                    case (BLACK_DOUBLE):
                        blackPieceValues += 6;
                        moveValue += getValueNeighbors(game, i, j) + (i);
                        break;
                }
            }
        }
        moveValue = moveValue + blackPieceValues - redPieceValues;
        return moveValue;
    }

    /**
     * Gets a best move value based on number of pieces
     */
    public int moveEvaluatorBasic(GameHelper game)
    {
        int moveValue = 0;
        int blackPieceValues = 0;
        int redPieceValues = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                switch (game.gameBoard[i][j])
                {
                    case (RED):
                        redPieceValues += 3;
                        break;
                    case (RED_DOUBLE):
                        redPieceValues += 6;
                        break;
                    case (BLACK):
                        blackPieceValues += 3;
                        break;
                    case (BLACK_DOUBLE):
                        blackPieceValues += 6;
                        break;
                }
            }
        }
        moveValue = blackPieceValues - redPieceValues;
        return moveValue;
    }

    public int minValue(GameHelper game, int alpha, int beta, int depth, int playerTurn)
    {
        List<Move> moves = null;
        if (depth <= maxDepth)
        {
            moves = Arrays.asList(game.getMoves(playerTurn));
        }
        if (moves == null || moves.isEmpty() || depth == maxDepth)
        {
            return moveEvaluator(game);
        }
        int val = Integer.MAX_VALUE;
        for (Move move : moves)
        {
            GameHelper tmpBoard = new GameHelper(game.gameBoard);
            tmpBoard.executeMove(move);
            val = Math.min(val, maxValue(tmpBoard, alpha, beta, depth + 1, playerTurn));
            if (val <= alpha)
            {
                return val;
            }
            beta = Math.min(beta, val);
        }
        return val;
    }

    public int maxValue(GameHelper game, int alpha, int beta, int depth, int playerTurn)
    {
        List<Move> moves = null;
        if (depth <= maxDepth)
        {
            moves = Arrays.asList(game.getMoves(playerTurn));
        }
        if (moves == null || moves.isEmpty() || depth == maxDepth)
        {
            return moveEvaluator(game);
        }
        int val = Integer.MIN_VALUE;
        for (Move move : moves)
        {
            GameHelper tmpBoard = new GameHelper(game.gameBoard);
            tmpBoard.executeMove(move);
            val = Math.max(val, minValue(tmpBoard, alpha, beta, depth + 1, playerTurn));
            if (val >= beta)
            {
                return val;
            }
            alpha = Math.max(alpha, val);
        }
        return val;
    }
}