package chess.pieces;

import chess.Board;
import chess.Move;

import java.util.ArrayList;

public abstract class Piece {
    public static final boolean WHITE = true, BLACK = false;
    protected boolean color;
    protected int value;
    protected int x;
    protected int y;
    public String FilePath;


    public boolean getColor() {
        return color;
    }

    public Piece(boolean color) {
        this.color = color;
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public abstract Piece clone();

    public abstract ArrayList<Move> getMoves(Board b, int x, int y);

    static public boolean valid(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7)
            return false;
        else
            return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getFilePath() {
        return FilePath;
    }

    public boolean isWhite() {
        if (color == WHITE) {
            return true;
        } else {
            return false;
        }
    }

    public Piece getPiece() {
        return this;
    }

    public boolean isBlack() {
        if (color == BLACK) {
            return true;
        } else {
            return false;
        }
    }

    public void setX(int clicked_column) {
        x = clicked_column;
    }

    public void setY(int clicked_row) {
        y = clicked_row;
    }

}
