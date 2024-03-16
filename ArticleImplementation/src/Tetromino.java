public class Tetromino {
    private int[][] shape;

    public Tetromino(int[][] shape) {
        this.shape = shape;
    }

    public int[][] getShape() {
        return shape;
    }

    public void rotate() {
        int[][] rotatedShape = new int[shape[0].length][shape.length];

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotatedShape[j][shape.length - 1 - i] = shape[i][j];
            }
        }

        shape = rotatedShape;
    }

    // 静态工厂方法创建不同形状
    public static Tetromino createShape(int type) {
        switch (type) {
            case 1:
                return new Tetromino(new int[][] { {1}, {1}, {1}, {1} }); // I 形状
            case 2:
                return new Tetromino(new int[][] { {1, 0}, {1, 0}, {1, 1} }); // L 形状
            case 3:
                return new Tetromino(new int[][] { {1, 1}, {1, 1} }); // O 形状
            case 4:
                return new Tetromino(new int[][] { {0, 1, 1}, {1, 1, 0} }); // S 形状
            case 5:
                return new Tetromino(new int[][] { {0, 1, 0}, {1, 1, 1} }); // T 形状
            case 6:
                return new Tetromino(new int[][] { {1, 1, 0}, {0, 1, 1} }); // Z 形状
            default:
                return null;
        }
    }
}
