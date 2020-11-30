/**
 *
 */
package chess.pieces;

import java.util.ArrayList;

import chess.*;


public class Bishop extends Piece {

    public Bishop(boolean color) {
        super(color);
        value = 3;
        if (color == Piece.WHITE) {
            FilePath = "wbishop.png";
        } else {
            FilePath = "bbishop.png";
        }
    }

    public Bishop(int col, int row, boolean color) {
        super(color);
        x = col;
        y = row;
        value = 3;
        if (color == Piece.WHITE) {
            FilePath = "wbishop.png";
        } else {
            FilePath = "bbishop.png";
        }
    }

    public String toString() {
        if (color == Piece.WHITE)
            return "b";
        else
            return "B";
    }

    public Bishop clone() {
        return new Bishop(color);
    }

    public ArrayList<Move> getMoves(Board b, int x, int y) {
        ArrayList<Move> moves = new ArrayList<Move>();


        // NE
        for (int i = 1; i < 8; i++) {
            if (valid(x + i, y - i)) {
                if (b.getTile(x + i, y - i).isOccupied()) {
                    if (b.getTile(x + i, y - i).getPiece().color != color) {
                        moves.add(new Move(x, y, x + i, y - i));
                    }
                    break;
                } else {
                    moves.add(new Move(x, y, x + i, y - i));
                }
            }
        }

        // NW
        for (int i = 1; i < 8; i++) {
            if (valid(x - i, y - i)) {
                if (b.getTile(x - i, y - i).isOccupied()) {
                    if (b.getTile(x - i, y - i).getPiece().color != color)
                        moves.add(new Move(x, y, x - i, y - i));

                    break;
                } else
                    moves.add(new Move(x, y, x - i, y - i));
            }
        }

        // SE
        for (int i = 1; i < 8; i++) {
            if (valid(x + i, y + i)) {
                if (b.getTile(x + i, y + i).isOccupied()) {
                    if (b.getTile(x + i, y + i).getPiece().color != color)
                        moves.add(new Move(x, y, x + i, y + i));

                    break;
                } else
                    moves.add(new Move(x, y, x + i, y + i));
            }
        }

        // SW
        for (int i = 1; i < 8; i++) {
            if (valid(x - i, y + i)) {
                if (b.getTile(x - i, y + i).isOccupied()) {
                    if (b.getTile(x - i, y + i).getPiece().color != color)
                        moves.add(new Move(x, y, x - i, y + i));

                    break;
                } else
                    moves.add(new Move(x, y, x - i, y + i));
            }
        }

        return moves;
    }
}
