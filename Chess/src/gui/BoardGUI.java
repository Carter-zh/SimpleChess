package gui;

import chess.Chess;
import chess.Move;
import chess.pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class BoardGUI extends JComponent{

    public int turnCounter = 0;
    private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

    private final int Square_Width = 65;
    public ArrayList<Piece> White_Pieces;
    public ArrayList<Piece> Black_Pieces;


    public ArrayList<DrawingShape> Static_Shapes;
    public ArrayList<DrawingShape> Piece_Graphics;

    public Piece Active_Piece;
    public Piece inCheck;

    private final int rows = 8;
    private final int cols = 8;
    private Integer[][] BoardGrid;
    private String board_file_path = "images" + File.separator + "board.png";
    private String active_square_file_path = "images" + File.separator + "active_square.png";

    public void initGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                BoardGrid[i][j] = 0;
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Chess.board.getTile(i, j).isOccupied()) {
                    if (Chess.board.getTile(i, j).getPiece().getColor() == Piece.WHITE) {
                        White_Pieces.add(Chess.board.getTile(i, j).getPiece());
                    } else {
                        Black_Pieces.add(Chess.board.getTile(i, j).getPiece());
                    }
                }
            }
        }

    }

    public BoardGUI() {

        BoardGrid = new Integer[rows][cols];
        Static_Shapes = new ArrayList();
        Piece_Graphics = new ArrayList();
        White_Pieces = new ArrayList();
        Black_Pieces = new ArrayList();

        initGrid();

        this.setBackground(new Color(227, 159, 98));
        this.setPreferredSize(new Dimension(520, 520));
//        this.setMinimumSize(new Dimension(100, 100));
//        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);
        this.addComponentListener(componentAdapter);
        this.addKeyListener(keyAdapter);


        this.setVisible(true);
        this.requestFocus();
        drawBoard();
    }


    private void drawBoard() {
        Piece_Graphics.clear();
        Static_Shapes.clear();
        //initGrid();

        Image board = loadImage(board_file_path);
        Static_Shapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
        if (Active_Piece != null) {
            Image active_square = loadImage("images" + File.separator + "active_square.png");
            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width * Active_Piece.getX(), Square_Width * Active_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
        }
        if (inCheck != null) {
            Image in_check = loadImage("images" + File.separator + "check_square.png");
            Static_Shapes.add(new DrawingImage(in_check, new Rectangle2D.Double(Square_Width * inCheck.getX(), Square_Width * inCheck.getY(), in_check.getWidth(null), in_check.getHeight(null))));
        }
        for (int i = 0; i < White_Pieces.size(); i++) {
            int COL = White_Pieces.get(i).getX();
            int ROW = White_Pieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "white_pieces" + File.separator + White_Pieces.get(i).getFilePath());
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width * COL, Square_Width * ROW, piece.getWidth(null), piece.getHeight(null))));
        }
        for (int i = 0; i < Black_Pieces.size(); i++) {
            int COL = Black_Pieces.get(i).getX();
            int ROW = Black_Pieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + Black_Pieces.get(i).getFilePath());
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width * COL, Square_Width * ROW, piece.getWidth(null), piece.getHeight(null))));
        }
        this.repaint();
    }


    public Piece getPiece(int x, int y) {
        for (Piece p : White_Pieces) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        for (Piece p : Black_Pieces) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }

    private final MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mousePressed(MouseEvent e) {
            int d_X = e.getX();
            int d_Y = e.getY();
            int Clicked_Row = d_Y / Square_Width;
            int Clicked_Column = d_X / Square_Width;
            boolean is_whites_turn = true;

            if (turnCounter % 2 == 1) {
                is_whites_turn = false;
            }

            Piece clicked_piece = getPiece(Clicked_Column, Clicked_Row);

            if (Active_Piece == null && clicked_piece != null && ((is_whites_turn && clicked_piece.isWhite()) || (!is_whites_turn && clicked_piece.isBlack()))) {
                Active_Piece = clicked_piece;
                drawBoard();
            } else if (Active_Piece != null && Active_Piece.getX() == Clicked_Column && Active_Piece.getY() == Clicked_Row) {
                Active_Piece = null;
                drawBoard();
            } else {
                try {
                    if (Active_Piece == null || ((!is_whites_turn || !Active_Piece.isWhite()) && (is_whites_turn || !Active_Piece.isBlack()))) {
                        drawBoard();
                    } else if (Chess.board.checkMove(Active_Piece, Clicked_Column, Clicked_Row)) {
                        // update the actual board, judge if the move is castling
                        Move thisMove;
                        if (Active_Piece instanceof King) {
                            // judge if the king is castling.
                            if (Active_Piece.getX() == 3 && Active_Piece.getY() == 0 && (Clicked_Column == 1 || Clicked_Column == 5) && Clicked_Row == 0
                                    || Active_Piece.getX() == 3 && Active_Piece.getY() == 7 && (Clicked_Column == 1 || Clicked_Column == 5) && Clicked_Row == 7) {
                                thisMove = new Move(Active_Piece.getX(), Active_Piece.getY(), Clicked_Column, Clicked_Row, true);
                            } else {
                                thisMove = new Move(Active_Piece.getX(), Active_Piece.getY(), Clicked_Column, Clicked_Row, false);
                            }
                        } else {
                            thisMove = new Move(Active_Piece.getX(), Active_Piece.getY(), Clicked_Column, Clicked_Row);
                        }
                        //save the return value and update the actual board
                        int n = Chess.board.makeMove(thisMove);

                        // remove the occupied piece
                        if (clicked_piece != null) {
                            if (clicked_piece.isWhite()) {
                                White_Pieces.remove(clicked_piece);
                            } else {
                                Black_Pieces.remove(clicked_piece);
                            }
                        }

                        // do move
                        Active_Piece.setX(Clicked_Column);
                        Active_Piece.setY(Clicked_Row);

                        // for special moves
                        switch (n) {
                            // Promotion case:
                            case 1:
                                if (is_whites_turn) {
                                    for (int i = 0; i < White_Pieces.size(); i++) {
                                        if (White_Pieces.get(i) instanceof Pawn) {
                                            if (White_Pieces.get(i).getY() == 7) {
                                                int c = White_Pieces.get(i).getX();
                                                int r = White_Pieces.get(i).getY();
                                                White_Pieces.remove(i);
                                                White_Pieces.add(new Queen(c, r, Piece.WHITE));
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < Black_Pieces.size(); i++) {
                                        if (Black_Pieces.get(i) instanceof Pawn) {
                                            if (Black_Pieces.get(i).getY() == 0) {
                                                int c = Black_Pieces.get(i).getX();
                                                int r = Black_Pieces.get(i).getY();
                                                Black_Pieces.remove(i);
                                                Black_Pieces.add(new Queen(c, r, Piece.BLACK));
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            // castling case:
                            case 21:
                                for (int i = 0; i < White_Pieces.size(); i++) {
                                    if (White_Pieces.get(i).getPiece() instanceof Rook) {
                                        if (White_Pieces.get(i).getX() == 0) {
                                            White_Pieces.get(i).setX(2);
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 22:
                                for (int i = 0; i < White_Pieces.size(); i++) {
                                    if (White_Pieces.get(i).getPiece() instanceof Rook) {
                                        if (White_Pieces.get(i).getX() == 7) {
                                            White_Pieces.get(i).setX(4);
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 23:
                                for (int i = 0; i < Black_Pieces.size(); i++) {
                                    if (Black_Pieces.get(i).getPiece() instanceof Rook) {
                                        if (Black_Pieces.get(i).getX() == 0) {
                                            Black_Pieces.get(i).setX(2);
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 24:
                                for (int i = 0; i < Black_Pieces.size(); i++) {
                                    if (Black_Pieces.get(i).getPiece() instanceof Rook) {
                                        if (Black_Pieces.get(i).getX() == 7) {
                                            Black_Pieces.get(i).setX(4);
                                            break;
                                        }
                                    }
                                }
                                break;
                        }

                        // check if the move lead to check
                        if (Chess.board.isCheck(!is_whites_turn)) {
                            if (is_whites_turn == true) {
                                int i;
                                for (i = 0; i < Black_Pieces.size(); i++) {
                                    if (Black_Pieces.get(i) instanceof King) {
                                        inCheck = Black_Pieces.get(i);
                                        break;
                                    }
                                }
                            } else {
                                int i;
                                for (i = 0; i < White_Pieces.size(); i++) {
                                    if (White_Pieces.get(i) instanceof King) {
                                        inCheck = White_Pieces.get(i);
                                        break;
                                    }
                                }
                            }
                        } else {
                            inCheck = null;
                        }

                        Active_Piece = null;
                        drawBoard();
                        // check if the game has a result
                        int stat = Chess.board.chessStatus();
                        if(stat == 1)
                        {
                            White_Win dialog = new White_Win();
                            dialog.pack();
                            dialog.setVisible(true);
                        }
                        if(stat == -1)
                        {
                            Black_Win dialog = new Black_Win();
                            dialog.pack();
                            dialog.setVisible(true);
                        }
                        if(stat == 2)
                        {
                            Game_Draw dialog = new Game_Draw();
                            dialog.pack();
                            dialog.setVisible(true);
                        }
                        turnCounter++;

                    }
                } catch (NullPointerException nullPointerException) {
                    System.out.println("Invalid click expected.");
                    drawBoard();
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }


    };

    private void adjustShapePositions(double dx, double dy) {

        Static_Shapes.get(0).adjustPosition(dx, dy);
        this.repaint();

    }


    private Image loadImage(String imageFile) {
        try {
            return ImageIO.read(new File(imageFile));
        } catch (IOException e) {
            return NULL_IMAGE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        drawShapes(g2);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
    }


    private void drawShapes(Graphics2D g2) {
        for (DrawingShape shape : Static_Shapes) {
            shape.draw(g2);
        }
        for (DrawingShape shape : Piece_Graphics) {
            shape.draw(g2);
        }
    }

    private ComponentAdapter componentAdapter = new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {

        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentResized(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }
    };

    private KeyAdapter keyAdapter = new KeyAdapter() {

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    };

}


interface DrawingShape {
    boolean contains(Graphics2D g2, double x, double y);

    void adjustPosition(double dx, double dy);

    void draw(Graphics2D g2);
}


class DrawingImage extends ChessGUI implements DrawingShape {

    public Image image;
    public Rectangle2D rect;

    public DrawingImage(Image image, Rectangle2D rect) {
        this.image = image;
        this.rect = rect;
    }

    @Override
    public boolean contains(Graphics2D g2, double x, double y) {
        return rect.contains(x, y);
    }

    @Override
    public void adjustPosition(double dx, double dy) {
        rect.setRect(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());
    }

    @Override
    public void draw(Graphics2D g2) {
        Rectangle2D bounds = rect.getBounds2D();
        g2.drawImage(image, (int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getMaxX(), (int) bounds.getMaxY(),
                0, 0, image.getWidth(null), image.getHeight(null), null);
    }
}
