import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class TetrisGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 600);
        frame.add(new GamePanel());
        frame.setVisible(true);
    }
}
