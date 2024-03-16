class Tetromino {
    private int x, y;
    boolean[][] shape;
    private int size;

    public Tetromino(int x, int y,boolean[][] shape) {
        this.x = x;
        this.y = y;
        this.shape = shape;
    }

    public void moveDown() {
        y++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAtBottom() {
        return y >= GamePanel.ROWS - 1;
    }

    public boolean canMoveDown(boolean[][] board) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    int boardRow = y + row + 1;
                    int boardCol = x + col;
                    if (boardRow >= GamePanel.ROWS || board[boardRow][boardCol]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveLeft(boolean[][] board) {
        if (canMove(-1, board)) {
            x--;
        }
    }

    public void moveRight(boolean[][] board) {
        if (canMove(1, board)) {
            x++;
        }
    }
    public void dropDown(boolean[][] board) {
        if (canMoveDown(board)) {
            y++;
        }
    }

    private boolean canMove(int deltaX, boolean[][] board) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    int newCol = x + col + deltaX;
                    if (newCol < 0 || newCol >= GamePanel.COLS || board[y + row][newCol]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void rotate(boolean[][] board) {
        boolean[][] rotatedShape = rotateShape();

        if (canPlace(rotatedShape, x, y, board)) {
            shape = rotatedShape;
        }
    }

    private boolean[][] rotateShape() {
        int numRows = shape.length;
        int numCols = shape[0].length;
        boolean[][] newShape = new boolean[numCols][numRows];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                newShape[c][numRows - 1 - r] = shape[r][c];
            }
        }

        return newShape;
    }

    private boolean canPlace(boolean[][] shape, int x, int y, boolean[][] board) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    int boardRow = y + row;
                    int boardCol = x + col;
                    if (boardRow < 0 || boardRow >= GamePanel.ROWS ||
                            boardCol < 0 || boardCol >= GamePanel.COLS ||
                            board[boardRow][boardCol]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}