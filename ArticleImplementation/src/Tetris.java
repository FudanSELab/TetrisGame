import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {

    private JLabel scoreLabel;

    public Tetris() {
        initUI();
    }

    private void initUI() {
        scoreLabel = new JLabel("得分：0");
        add(scoreLabel, BorderLayout.SOUTH);
//        add(new TetrisBoard());
        add(new TetrisBoard());
        setTitle("俄罗斯方块");
        setSize(300, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中
    }

    public void updateScore(int score) {
        scoreLabel.setText("得分：" + score);
    }

    public static void main(String[] args) {
        Tetris game = new Tetris();
        game.setVisible(true);
    }
}
