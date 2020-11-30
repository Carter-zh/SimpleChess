/**
 *
 */
package chess.pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Move;

public class King extends Piece {
    boolean hasMoved = false;

    /**
     *
     */
    public King(boolean color) {
        super(color);
        value = 0;
        if (color == Piece.WHITE) {
            FilePath = "wking.png";
        } else {
            FilePath = "bking.png";
        }
    }

    public King(boolean color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
        value = 0;
        if (color == Piece.WHITE) {
            FilePath = "wking.png";
        } else {
            FilePath = "bking.png";
        }
    }

    public King(int col, int row, boolean color) {
        super(color);
        x = col;
        y = row;
        value = 0;
        if (color == Piece.WHITE) {
            FilePath = "wking.png";
        } else {
            FilePath = "bking.png";
        }
    }

    public King clone() {
        return new King(color, hasMoved);
    }

    public String toString() {
        if (color == Piece.WHITE)
            return "k";
        else
            return "K";
    }

    public ArrayList<Move> getMoves(Board b, int x, int y) {
        ArrayList<Move> moves = new ArrayList<Move>();

        // N
        if (valid(x, y - 1) &&
                (!b.getTile(x, y - 1).isOccupied() ||
                        (b.getTile(x, y - 1).isOccupied() && b.getTile(x, y - 1).getPiece().getColor() != color)))
            moves.add(new Move(x, y, x, y - 1));

        // NE
        if (valid(x + 1, y - 1) &&
                (!b.getTile(x + 1, y - 1).isOccupied() ||
                        (b.getTile(x + 1, y - 1).isOccupied() && b.getTile(x + 1, y - 1).getPiece().getColor() != color)))
            moves.add(new Move(x, y, x + 1, y - 1));

        // E
        if (valid(x + 1, y) &&
                (!b.getTile(x + 1, y).isOccupied() ||
                        (b.getTile(x + 1, y).isOccupied() && b.getTile(x + 1, y).getPiece().getColor() != color)))
            moves.add(new Move(x, y, x + 1, y));


        // SE
        if (valid(x + 1, y + 1) &&
                (!b.getTile(x + 1, y + 1).isOccupied() ||
                        (b.getTile(x + 1, y + 1).isOccupied() && b.getTile(x + 1, y + 1).getPiece().getColor() != color)))
            moves.add(new Move(x, y, x + 1, y + 1));


        // S
        if (valid(x, y + 1) &&
                (!b.getTile(x, y + 1).isOccupied() ||
                        (b.getTile(x, y + 1).isOccupied() && b.getTile(x, y + 1).getPiece().getColor() != color)))
            moves.add(new Move(x, y, x, y + 1));

        // SW
        if (valid(x - 1, y + 1) &&
                (!b.getTile(x - 1, y + 1).isOccupied() ||
                        (b.getTile(x - 1, y + 1).isOccupied() && b.getTile(x - 1, y + 1).getPiece().getColor() != color)))
            moves.add(new Move(x, y, x - 1, y + 1));

        // W
        if (valid(x - 1, y) &&
                (!b.getTile(x - 1, y).isOccupied() ||
                        (b.getTile(x - 1, y).isOccupied() && b.getTile(x - 1, y).getPiece().getColor() != color)))
            moves.add(new Move(x, y, x - 1, y));

        // NW
        if (valid(x - 1, y - 1) &&
                (!b.getTile(x - 1, y - 1).isOccupied() ||
                        (b.getTile(x - 1, y - 1).isOccupied() && b.getTile(x - 1, y - 1).getPiece().getColor() != color)))
            moves.add(new Move(x, y, x - 1, y - 1));

        // Castling
        if (color == Piece.WHITE) {
            if (!hasMoved && x == Board.d && y == 0) {
                if (!b.getTile(Board.c, 0).isOccupied() &&
                        !b.getTile(Board.b, 0).isOccupied() &&
                        b.getTile(Board.a, 0).isOccupied() &&
                        b.getTile(Board.a, 0).getPiece().toString().equals("r"))
                    moves.add(new Move(x, y, x - 2, y));
                if (!b.getTile(Board.e, 0).isOccupied() &&
                        !b.getTile(Board.f, 0).isOccupied() &&
                        !b.getTile(Board.g, 0).isOccupied() &&
                        b.getTile(Board.h, 0).isOccupied() &&
                        b.getTile(Board.h, 0).getPiece().toString().equals("r"))
                    moves.add(new Move(x, y, x + 2, y));
            } else {
                hasMoved = true;
            }
        } else { // color == Piece.BLACK
            if (!hasMoved && x == Board.d && y == 7) {
                if (!b.getTile(Board.c, 7).isOccupied() &&
                        !b.getTile(Board.b, 7).isOccupied() &&
                        b.getTile(Board.a, 7).isOccupied() &&
                        b.getTile(Board.a, 7).getPiece().toString().equals("R")) {
                    moves.add(new Move(x, y, x - 2, y));
                }
                if (!b.getTile(Board.e, 7).isOccupied() &&
                        !b.getTile(Board.f, 7).isOccupied() &&
                        !b.getTile(Board.g, 7).isOccupied() &&
                        b.getTile(Board.h, 7).isOccupied() &&
                        b.getTile(Board.h, 7).getPiece().toString().equals("R")) {
                    moves.add(new Move(x, y, x + 2, y));
                }
            } else {
                hasMoved = true;
            }
        }


        // TODO King cannot move into open fire


        return moves;
    }
}
