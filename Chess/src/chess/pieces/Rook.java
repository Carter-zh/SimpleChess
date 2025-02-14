/**
 *
 */
package chess.pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Move;

public class Rook extends Piece {

    public Rook(boolean color) {
        super(color);
        value = 5;
        if (color == Piece.WHITE) {
            FilePath = "wrook.png";
        } else {
            FilePath = "brook.png";
        }
    }

    public Rook(int col, int row, boolean color) {
        super(color);
        x = col;
        y = row;
        value = 0;
        if (color == Piece.WHITE) {
            FilePath = "wrook.png";
        } else {
            FilePath = "brook.png";
        }
    }

    public String toString() {
        if (color == Piece.WHITE)
            return "r";
        else
            return "R";
    }

    public Rook clone() {
        return new Rook(color);
    }


    public ArrayList<Move> getMoves(Board b, int x, int y) {
        ArrayList<Move> moves = new ArrayList<Move>();

        // up
        for (int i = 1; i < 8; i++) {
            if (valid(x, y + i)) {
                if (b.getTile(x, y + i).isOccupied()) {
                    if (b.getTile(x, y + i).getPiece().color != color)
                        moves.add(new Move(x, y, x, y + i));

                    break;
                } else
                    moves.add(new Move(x, y, x, y + i));
            }
        }

        // down
        for (int i = 1; i < 8; i++) {
            if (valid(x, y - i)) {
                if (b.getTile(x, y - i).isOccupied()) {
                    if (b.getTile(x, y - i).getPiece().color != color)
                        moves.add(new Move(x, y, x, y - i));

                    break;
                } else
                    moves.add(new Move(x, y, x, y - i));
            }
        }

        // left
        for (int i = 1; i < 8; i++) {
            if (valid(x - i, y)) {
                if (b.getTile(x - i, y).isOccupied()) {
                    if (b.getTile(x - i, y).getPiece().color != color)
                        moves.add(new Move(x, y, x - i, y));

                    break;
                } else
                    moves.add(new Move(x, y, x - i, y));
            }
        }

        // right
        for (int i = 1; i < 8; i++) {
            if (valid(x + i, y)) {
                if (b.getTile(x + i, y).isOccupied()) {
                    if (b.getTile(x + i, y).getPiece().color != color)
                        moves.add(new Move(x, y, x + i, y));

                    break;
                } else
                    moves.add(new Move(x, y, x + i, y));
            }
        }


        return moves;
    }
}
