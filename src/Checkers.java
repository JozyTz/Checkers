import java.awt.*;
import javax.swing.*;

public class Checkers extends JPanel
{

    public static void main(String[] args)
    {
        Checkers game = new Checkers();
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(game);
        window.setVisible(true);
        window.pack();
    }

    public Checkers()
    {
        setLayout(null);
        setPreferredSize(new Dimension(700,600) );
        Board board = new Board();
        add(board);
        board.setBounds(200,100,400,400);
    }
}
