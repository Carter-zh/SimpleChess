/**
 *
 */
package chess.pieces;

import chess.Board;
import chess.Move;

import java.util.ArrayList;

public class Pawn extends Piece {

    final private int WHITE_FIRST_ROW = 1;
    final private int BLACK_FIRST_ROW = 6;

    /**
     *
     */


    public Pawn(boolean color) {
        super(color);
        value = 1;
        if (color == Piece.WHITE) {
            FilePath = "wpawn.png";
        } else {
            FilePath = "bpawn.png";
        }
    }

    public Pawn(int col, int row, boolean color) {
        super(color);
        x = col;
        y = row;
        if (color == Piece.WHITE) {
            FilePath = "wpawn.png";
        } else {
            FilePath = "bpawn.png";
        }
        value = 1;
    }

    public Pawn clone() {
        return new Pawn(color);
    }

    public String toString() {
        if (color == Piece.WHITE)
            return "p";
        else
            return "P";
    }

    public ArrayList<Move> getMoves(Board b, int x, int y) {
        ArrayList<Move> moves = new ArrayList<Move>();

        if (color == Piece.WHITE) {
            // forward
            if (valid(x, y + 1) && !b.getTile(x, y + 1).isOccupied())
                moves.add(new Move(x, y, x, y + 1));

            // kill diagonally
            if (valid(x + 1, y + 1) && b.getTile(x + 1, y + 1).isOccupied() && b.getTile(x + 1, y + 1).getPiece().getColor() != color)
                moves.add(new Move(x, y, x + 1, y + 1));

            // kill diagonally
            if (valid(x - 1, y + 1) && b.getTile(x - 1, y + 1).isOccupied() && b.getTile(x - 1, y + 1).getPiece().getColor() != color)
                moves.add(new Move(x, y, x - 1, y + 1));

            // Advanced forward
            if (getY() == WHITE_FIRST_ROW) {
                if (valid(x, y + 2) && !b.getTile(x, y + 2).isOccupied()) {
                    moves.add(new Move(x, y, x, y + 2));
                }
            }
        } else {
            // forward
            if (valid(x, y - 1) && !b.getTile(x, y - 1).isOccupied())
                moves.add(new Move(x, y, x, y - 1));

            // kill diagonally
            if (valid(x + 1, y - 1) && b.getTile(x + 1, y - 1).isOccupied() && b.getTile(x + 1, y - 1).getPiece().getColor() != color)
                moves.add(new Move(x, y, x + 1, y - 1));

            // kill diagonally
            if (valid(x - 1, y - 1) && b.getTile(x - 1, y - 1).isOccupied() && b.getTile(x - 1, y - 1).getPiece().getColor() != color)
                moves.add(new Move(x, y, x - 1, y - 1));

            // Advanced forward
            if (getY() == BLACK_FIRST_ROW) {
                if (valid(x, y - 2) && !b.getTile(x, y - 2).isOccupied()) {
                    moves.add(new Move(x, y, x, y - 2));
                }
            }
        }

        return moves;
    }
    //Todo: if a pawn encounter an advanced move
}

