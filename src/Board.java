import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel implements MouseListener
{
    GameHelper game;
    Move[] moves;
    int playerTurn, selectedX, selectedY;
    boolean gameActive;

    AlphaBeta aiPlayer = new AlphaBeta();

    Board()
    {
        addMouseListener(this);
        game = new GameHelper();
        playerTurn = GameHelper.RED;
        moves = game.getMoves(GameHelper.RED);
        gameActive = true;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        int col = (evt.getX() - 2) / 50;
        int row = (evt.getY() - 2) / 50;
        if (col >= 0 && col < 8 && row >= 0 && row < 8)
        {
            selectPiece(row, col);
        }
    }
    @Override
    public void mouseReleased(MouseEvent evt) { }
    @Override
    public void mouseClicked(MouseEvent evt) { }
    @Override
    public void mouseEntered(MouseEvent evt) { }
    @Override
    public void mouseExited(MouseEvent evt) { }

    void selectPiece(int x, int y)
    {
        for (Move move : moves)
        {
            if (move.xCurrent == x && move.yCurrent == y)
            {
                selectedX = x;
                selectedY = y;
                repaint();
                return;
            }
            if (move.xCurrent == selectedX && move.yCurrent == selectedY && move.xNext == x && move.yNext == y)
            {
                executeTurn(move);
                return;
            }
        }
    }

    void executeTurn(Move move)
    {
        //player turn
        if (playerTurn == GameHelper.RED)
        {
            game.executeMove(move);
            if (move.isJump())
            {
                this.moves = game.getMoves(playerTurn, move.xNext, move.yNext);
                if (this.moves != null)
                {
                    selectedX = move.xNext;
                    selectedY = move.yNext;
                    repaint();
                    return;
                }
            }
            //swap turns
                playerTurn = GameHelper.BLACK;
        }

        if (playerTurn == GameHelper.BLACK)
        {
            //exit on no ai moves
            this.moves = game.getMoves(playerTurn);
            if (this.moves == null || this.moves.length == 0) {
                System.exit(0);
            }

            //ai turn
            Move aiMove = aiPlayer.AlphaBeta(game, playerTurn);
            if (aiMove != null)
            {
                game.executeMove(aiMove);
            }
            if (aiMove.isJump())
            {
                this.moves = game.getMoves(playerTurn, aiMove.xNext, aiMove.yNext);
                if (this.moves != null)
                {
                    selectedX = aiMove.xNext;
                    selectedY = aiMove.yNext;
                    repaint();
                    return;
                }
            }
            repaint();
            //reset environment, exit on no player moves
            playerTurn = GameHelper.RED;
            selectedX = -1;
            selectedY = -1;
            this.moves = game.getMoves(playerTurn);
            if (this.moves == null)
            {
                System.exit(0);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        Graphics2D g2d = (Graphics2D) graphics.create();

        //draw board and pieces
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ( i % 2 == j % 2 )
                {
                    g2d.setColor(Color.lightGray);
                }
                else
                {
                    g2d.setColor(Color.gray);
                }
                g2d.fillRect(j * 50, i * 50, 50, 50);
                if (game.gameBoard[i][j] == GameHelper.RED || game.gameBoard[i][j] == GameHelper.RED_DOUBLE)
                {
                    g2d.setColor(Color.red);
                    g2d.fillOval(j * 50 + 4, i * 50 + 4, 39, 39);
                    if (game.gameBoard[i][j] == GameHelper.RED_DOUBLE) {
                        g2d.setColor(Color.pink);
                        g2d.fillOval(j * 50 + 8, i * 50 + 8, 31, 31);
                    }
                }
                if (game.gameBoard[i][j] == GameHelper.BLACK || game.gameBoard[i][j] == GameHelper.BLACK_DOUBLE)
                {
                    g2d.setColor(Color.black);
                    g2d.fillOval(j * 50 + 4, i * 50 + 4, 39, 39);
                    if (game.gameBoard[i][j] == GameHelper.BLACK_DOUBLE)
                    {
                        g2d.setColor(Color.darkGray);
                        g2d.fillOval(j * 50 + 8, i * 50 + 8, 31, 31);
                    }
                }
            }
        }
        //movable pieces
        g2d.setColor(Color.blue);
        g2d.setStroke(new BasicStroke(3.0f));
        for (Move move : moves)
        {
            g2d.drawRect(1 + move.yCurrent * 50, 1 + move.xCurrent * 50, 47, 47);
        }
        //selected piece
        if (selectedX >= 0)
        {
            g2d.setColor(Color.cyan);
            g2d.drawRect(1 + selectedY * 50, 1 + selectedX * 50, 47, 47);
            //moves available for piece
            for (Move move : moves)
            {
                g2d.setColor(Color.green);
                if (move.yCurrent == selectedY && move.xCurrent == selectedX)
                {
                    g2d.fillRect(move.yNext * 50, move.xNext * 50, 50, 50);
                }
            }
        }
    }
}