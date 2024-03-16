import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.awt.event.ActionListener;
import java.util.Random;

public class TetrisBoard extends JPanel implements ActionListener {

    private Tetromino currentPiece; // 当前的Tetromino
    private int x = 0; // 方块的初始x坐标
    private int y = 0; // 方块的初始y坐标
    private final Timer timer;

    private int score = 0;

    private static final int BOARD_WIDTH = 14; // 游戏区域宽度，单位：方块数量
    private static final int BOARD_HEIGHT = 20; // 游戏区域高度，单位：方块数量
    private boolean[][] board = new boolean[BOARD_HEIGHT][BOARD_WIDTH];

    public TetrisBoard() {
        initBoard();
        timer = new Timer(500, this); // 每500毫秒触发一次
        timer.start();
    }

    private void initBoard() {
        // 初始化游戏板
        setFocusable(true); // 使面板能够接收键盘事件
        Random rand = new Random();
        int shapeType = rand.nextInt(6) + 1; // 生成1到6的随机数
        currentPiece = Tetromino.createShape(shapeType);
        x = BOARD_WIDTH / 2 - 2; // 根据形状调整初始x位置
        y = 0; // 重置y位置
        x = 0;
        y = 0;
        addKeyListener(new TAdapter());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        moveDown();
    }
    private void checkCompleteLines() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            boolean lineComplete = true;

            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (!board[row][col]) {
                    lineComplete = false;
                    break;
                }
            }

            if (lineComplete) {
                removeLine(row);
                row--; // 由于行被移除，索引回退一行以再次检查新移下来的行
            }
        }
    }

    private void removeLine(int line) {
        for (int row = line; row > 0; row--) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = board[row - 1][col];
            }
        }
        Arrays.fill(board[0], false);

        score += 100; // 假设每消除一行增加100分
    }



    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moveLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                moveRight();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                rotatePiece();
            }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                moveDown();
            }
        }
    }

    private void moveDown() {
        if (checkMove(currentPiece, x, y + 20)) {
            y += 20;
        } else {
            placePieceOnBoard();
            checkCompleteLines();
            Random rand = new Random();
            int shapeType = rand.nextInt(6) + 1; // 生成1到6的随机数
            currentPiece = Tetromino.createShape(shapeType);
            x = BOARD_WIDTH / 2 - 2; // 根据形状调整初始x位置
            y = 0; // 重置y位置
            x = 0;
            y = 0;

            if (isGameOver()) {
                timer.stop();
                // 游戏结束处理
            }
        }
        repaint();
    }


    private void rotatePiece() {
        Tetromino rotated = new Tetromino(currentPiece.getShape().clone());
        rotated.rotate();

        if (checkMove(rotated, x, y)) {
            currentPiece = rotated;
        }

        repaint();
    }

    private void moveLeft() {
        if (checkMove(currentPiece, x - 20, y)) {
            x -= 20;
        }
        repaint();
    }

    private void moveRight() {
        if (checkMove(currentPiece, x + 20, y)) {
            x += 20;
        }
        repaint();
    }

    private boolean checkMove(Tetromino piece, int newX, int newY) {
        for (int i = 0; i < piece.getShape().length; i++) {
            for (int j = 0; j < piece.getShape()[i].length; j++) {
                if (piece.getShape()[i][j] != 0) {
                    int boardX = (newX + j * 20) / 20;
                    int boardY = (newY + i * 20) / 20;

                    if (boardX < 0 || boardX >= BOARD_WIDTH || boardY < 0 || boardY >= BOARD_HEIGHT) {
                        return false; // 触及边界
                    }

                    if (board[boardY][boardX]) {
                        return false; // 触及其他方块
                    }
                }
            }
        }
        return true;
    }

    private void placePieceOnBoard() {
        for (int i = 0; i < currentPiece.getShape().length; i++) {
            for (int j = 0; j < currentPiece.getShape()[i].length; j++) {
                if (currentPiece.getShape()[i][j] != 0) {
                    board[(y + i * 20) / 20][(x + j * 20) / 20] = true;
                }
            }
        }

        checkCompleteLines();
        ((Tetris) SwingUtilities.getWindowAncestor(this)).updateScore(score);


        Random rand = new Random();
        int shapeType = rand.nextInt(6) + 1; // 生成1到6的随机数
        currentPiece = Tetromino.createShape(shapeType);
//        x = BOARD_WIDTH / 2 - 2; // 根据形状调整初始x位置
//        y = 0; // 重置y位置
        x = 0;
        y = 0;

        if (isGameOver()) {
            // 游戏结束处理，例如显示游戏结束消息
            JOptionPane.showMessageDialog(this, "游戏结束", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // 或者提供重新开始游戏的选项
        } else {
            // 游戏未结束，创建新的Tetromino
            currentPiece = Tetromino.createShape(shapeType);
//            x = BOARD_WIDTH / 2 - 2; // 根据形状调整初始x位置
//            y = 0; // 重置y位置
            x = 0;
            y = 0;
        }
        // 这里可以添加代码来检查是否有完整的行应该被消除
    }

    private boolean isGameOver() {
        for (int col = 0; col < BOARD_WIDTH; col++) {
            if (board[0][col]) { // 检查顶部行是否被占用
                return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g); // 绘制游戏板上的沉积方块
        drawTetromino(g); // 绘制当前活动的Tetromino
        drawBorder(g); // 绘制边框
    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.BLACK); // 设置边框颜色
        g.drawRect(0, 0, BOARD_WIDTH * 20, BOARD_HEIGHT * 20); // 绘制边框
    }
    private void drawBoard(Graphics g) {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col]) {
                    g.fillRect(col * 20, row * 20, 20, 20); // 以20x20像素的方块绘制
                }
            }
        }
    }

    private void drawTetromino(Graphics g) {
        for (int i = 0; i < currentPiece.getShape().length; i++) {
            for (int j = 0; j < currentPiece.getShape()[i].length; j++) {
                if (currentPiece.getShape()[i][j] == 1) {
                    g.fillRect(x + j * 20, y + i * 20, 20, 20);
                }
            }
        }
    }

}