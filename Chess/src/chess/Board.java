package chess;

import chess.pieces.*;

import java.util.ArrayList;

public class Board{
    public static final int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
    private int turnCounter = 0;
    private final boolean WHITE_TURN = true;
    private final boolean BLACK_TURN = true;
    private Tile[][] tiles;

    /**
     * 1	r n b k q b n r
     * 2	p p p p p p p p 	white
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

    public Board(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Board() {
        // initialize board
        boolean co = Piece.WHITE;
        tiles = new Tile[8][8];
        tiles[a][0] = new Tile(new Rook(0, 0, co));
        tiles[b][0] = new Tile(new Knight(1, 0, co));
        tiles[c][0] = new Tile(new Bishop(2, 0, co));
        tiles[d][0] = new Tile(new King(3, 0, co));
        tiles[e][0] = new Tile(new Queen(4, 0, co));
        tiles[f][0] = new Tile(new Bishop(5, 0, co));
        tiles[g][0] = new Tile(new Knight(6, 0, co));
        tiles[h][0] = new Tile(new Rook(7, 0, co));

        for (int i = 0; i < 8; i++) {
            tiles[i][2 - 1] = new Tile(new Pawn(i, 1, co));
        }

        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[j][i] = new Tile();
            }
        }

        co = Piece.BLACK;
        tiles[a][8 - 1] = new Tile(new Rook(0, 7, co));
        tiles[b][8 - 1] = new Tile(new Knight(1, 7, co));
        tiles[c][8 - 1] = new Tile(new Bishop(2, 7, co));
        tiles[d][8 - 1] = new Tile(new King(3, 7, co));
        tiles[e][8 - 1] = new Tile(new Queen(4, 7, co));
        tiles[f][8 - 1] = new Tile(new Bishop(5, 7, co));
        tiles[g][8 - 1] = new Tile(new Knight(6, 7, co));
        tiles[h][8 - 1] = new Tile(new Rook(7, 7, co));

        for (int i = 0; i < 8; i++) {
            tiles[i][7 - 1] = new Tile(new Pawn(i, 6, co));
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < 8; i++) {
            str += (i + 1) + "  ";
            for (int j = 0; j < 8; j++) {
                str += tiles[j][i] + " ";
            }
            str += "\n";
        }

        str += "\n   a b c d e f g h";

        return str;
    }

    /**
     * Checks if player color is under check
     *
     * @param color
     * @return
     */
    public boolean isCheck(boolean color) {
        int x = -1, y = -1;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (tiles[i][j].isOccupied() &&
                        tiles[i][j].getPiece().getColor() == color &&
                        tiles[i][j].getPiece().toString().equalsIgnoreCase("K")) {
                    x = i;
                    y = j;
                }
            }

        // check a move if after making this move the king can be killed (moving into check)
        ArrayList<Move> opponentMoves = getMoves(!color, false);

        // check all opponent moves if they kill king (opponent moves in next round)
        for (int j = 0; j < opponentMoves.size(); j++) {
            if (opponentMoves.get(j).getX2() == x && opponentMoves.get(j).getY2() == y)
                return true;
        }

        return false;
    }

    /**
     * Checks if player color is under check
     *
     * @param color
     * @param moves
     * @return
     */
    public boolean isCheckAfter(boolean color, ArrayList<Move> moves) {

        Tile[][] newTiles = getTilesAfter(moves);

        int x = -1, y = -1;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (newTiles[i][j].isOccupied() &&
                        newTiles[i][j].getPiece().getColor() == color &&
                        newTiles[i][j].getPiece().toString().equalsIgnoreCase("K")) {
                    x = i;
                    y = j;
                }
            }

        // check a move if after making this move the king can be killed (moving into check)
        ArrayList<Move> opponentMoves = getMovesAfter(!color, moves, false);

        // check all opponent moves if they kill king (opponent moves in next round)
        for (int j = 0; j < opponentMoves.size(); j++) {
            if (opponentMoves.get(j).getX2() == x && opponentMoves.get(j).getY2() == y)
                return true;
        }

        return false;
    }

    public ArrayList<Move> getMoves(boolean color, boolean checkCheck) {
        ArrayList<Move> moves = new ArrayList<>();

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (tiles[i][j].isOccupied() &&
                        tiles[i][j].getPiece().getColor() == color) {
                    moves.addAll(tiles[i][j].getPiece().getMoves(this, i, j));
                }
            }

        // check if move is valid (must not be check after move) and throw away erroneous moves
        if (checkCheck) {
            // find king (of correct color)
            int x = -1, y = -1;
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) {
                    if (tiles[i][j].isOccupied() &&
                            tiles[i][j].getPiece().getColor() == color &&
                            tiles[i][j].getPiece().toString().equalsIgnoreCase("K")) {
                        x = i;
                        y = j;
                    }
                }

            ArrayList<Move> removeThese = new ArrayList<Move>();
            for (int i = 0; i < moves.size(); i++) {
                // check a move if after making this move the king can be killed (moving into check)
                ArrayList<Move> checkThis = new ArrayList<Move>(moves.subList(i, i + 1));
                ArrayList<Move> opponentMoves = getMovesAfter(!color, checkThis, false);

                int xUpdated = x, yUpdated = y;
                if (checkThis.get(0).getX1() == x && checkThis.get(0).getY1() == y) { // get updated king position
                    xUpdated = checkThis.get(0).getX2();
                    yUpdated = checkThis.get(0).getY2();
                }

                // check all opponent moves if they kill king (opponent moves in next round)
                for (int j = 0; j < opponentMoves.size(); j++) {
                    if (opponentMoves.get(j).getX2() == xUpdated && opponentMoves.get(j).getY2() == yUpdated)
                    {
                        removeThese.add(checkThis.get(0));
                        break;
                    }
                }
            }

            moves.removeAll(removeThese); // remove invalid moves
        }

        return moves;
    }

    public ArrayList<Move> getMovesAfter(boolean color, ArrayList<Move> moves, boolean checkCheck) {

        Tile[][] temp = new Tile[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                temp[x][y] = new Tile(this.tiles[x][y]);
            }
        }

        Board b = new Board(temp);

        for (int i = 0; i < moves.size(); i++) {
            b.makeMove(moves.get(i));
        }


        ArrayList<Move> futureMoves = b.getMoves(color, checkCheck);

        return futureMoves;
    }

    public Tile[][] getTilesAfter(ArrayList<Move> moves) {

        Tile[][] temp = new Tile[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                temp[x][y] = new Tile(this.tiles[x][y]);
            }
        }

        Board b = new Board(temp);

        for (int i = 0; i < moves.size(); i++)
            b.makeMove(moves.get(i));

        Tile[][] temp2 = new Tile[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                temp2[x][y] = new Tile(b.getTile(x, y));
            }
        }

        return temp2;
    }

    /**
     * Make the move updated in the board.
     *
     * @param m
     * @return 0 if game continues.
     * 1 if promotion in valid.
     * 2? if castling in valid.
     */
    public int makeMove(Move m) {
        Tile oldTile = tiles[m.getX1()][m.getY1()];

        tiles[m.getX2()][m.getY2()] = tiles[m.getX1()][m.getY1()];
        tiles[m.getX1()][m.getY1()] = new Tile();

        if (m.isCastling() && !isCheck(true) && !isCheck(false)) {
            if (m.getX2() == b && m.getY2() == 0) {
                tiles[c][0] = tiles[a][0];
                tiles[a][0] = new Tile();
                return 21;
            }
            if (m.getX2() == f && m.getY2() == 0) {
                tiles[e][0] = tiles[h][0];
                tiles[h][0] = new Tile();
                return 22;
            }
            if (m.getX2() == b && m.getY2() == 7) {
                tiles[c][7] = tiles[a][0];
                tiles[a][7] = new Tile();
                return 23;
            }
            if (m.getX2() == f && m.getY2() == 7) {
                tiles[e][7] = tiles[h][7];
                tiles[h][7] = new Tile();
                return 24;
            }
        }

        // pawn at top?
        if (oldTile.isOccupied()) {
            if (oldTile.getPiece().toString().equals("P") && m.getY2() == 0) {
                tiles[m.getX2()][m.getY2()] = new Tile(new Queen(Piece.BLACK));
                return 1;
            }

            if (oldTile.getPiece().toString().equals("p") && m.getY2() == 7) {
                tiles[m.getX2()][m.getY2()] = new Tile(new Queen(Piece.WHITE));
                return 1;
            }
        }
        return 0;
    }

    /**
     * This method checks if the chess game ends.
     *
     * @return  1 if WHITE wins
     *          0 if draw
     *          -1 if BLACK wins
     */
    public int chessStatus()
    {
        turnCounter++;
        if (this.isCheck(Piece.WHITE) && turnChecker())
		{
			// check and can't move
            if(getMoves(Piece.WHITE,true).size() == 0)
            {
                return -1;
            }
		}
        if (this.isCheck(Piece.BLACK) && !turnChecker())
        {
            // check and can't move
            if(getMoves(Piece.BLACK,true).size() == 0)
            {
                return -1;
            }
        }
        // !check but can't move
        if(getMoves(Piece.WHITE,true).size() == 0 && turnChecker())
        {
            return 2;
        }
        if(getMoves(Piece.BLACK,true).size() == 0 && turnChecker())
        {
            return 2;
        }
        return 0;
    }

    private boolean turnChecker()
    {
        if (turnCounter % 2 == 1)
        {
            return BLACK_TURN;
        }
        else
        {
            return WHITE_TURN;
        }
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public Board getBoard() {
        return this;
    }

    public boolean checkMove(Piece oriPiece, int x, int y) {
        int i;
        ArrayList<Move> validMove = oriPiece.getMoves(Chess.board, oriPiece.getX(), oriPiece.getY());
        for (i = 0; i < validMove.size(); i++) {
            if (x == validMove.get(i).getX2() && y == validMove.get(i).getY2()) {
                return true;
            }
        }
        return false;
    }

}
