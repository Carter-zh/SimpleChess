import chess.Board;
import chess.Tile;
import chess.pieces.*;

public class TestChessBoard extends Board {
    public static final int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
    private Tile[][] testTile;

    /**
     * 1	. . . . . . k .
     * 2	. . P . . p p p 	white
     * 3	. . . . . . . .
     * 4	. . . . . . . .
     * 5	. . . . . . . .
     * 6	. . . . . . . .
     * 7	P P P P P P P P 	black
     * 8    R N B K Q B N R
     *
     *      a b c d e f g h
     *      1 2 3 4 5 6 7 8
     */

    public TestChessBoard(Tile[][] testTile) {
        this.testTile = testTile;
    }

    public TestChessBoard() {
        // initialize board
        boolean co = Piece.WHITE;
        testTile = new Tile[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                testTile[j][i] = new Tile();
            }
        }
        testTile[g][0] = new Tile(new King(6, 0, co));

        for (int i = 0; i < 3; i++) {
            testTile[i+5][1] = new Tile(new Pawn(i+5, 1, co));
        }

        co = Piece.BLACK;
        testTile[a][8 - 1] = new Tile(new Rook(0, 7, co));
        testTile[b][8 - 1] = new Tile(new Knight(1, 7, co));
        testTile[c][8 - 1] = new Tile(new Bishop(2, 7, co));
        testTile[d][8 - 1] = new Tile(new King(3, 7, co));
        testTile[e][8 - 1] = new Tile(new Queen(4, 7, co));
        testTile[f][8 - 1] = new Tile(new Bishop(5, 7, co));
        testTile[g][8 - 1] = new Tile(new Knight(6, 7, co));
        testTile[h][8 - 1] = new Tile(new Rook(7, 7, co));
        testTile[c][1] = new Tile(new Pawn(2, 1, co));

        for (int i = 0; i < 8; i++) {
            testTile[i][7 - 1] = new Tile(new Pawn(i, 6, co));
        }
    }

    public static void main(String[] args) {
        TestChessBoard testBoard = new TestChessBoard();
        System.out.println(testBoard);
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < 8; i++) {
            str += (i + 1) + "  ";
            for (int j = 0; j < 8; j++) {
                str += testTile[j][i] + " ";
            }
            str += "\n";
        }

        str += "\n   a b c d e f g h";

        return str;
    }

}
