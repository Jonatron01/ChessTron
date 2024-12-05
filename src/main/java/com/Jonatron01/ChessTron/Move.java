package com.Jonatron01.ChessTron;

public class Move {
    final private Square start;
    final private Square end;
    private boolean promotion;
    final private int castle;
    final private boolean enPassant;
    private double staticScore = 0;
    final private boolean white;

    public Move(int piece, int r, int c, Square end, int castle, boolean enPassant) {
        promotion = ((piece == 1 && end.getRow() == 7) || (piece == -1 && end.getRow() == 0));
        this.castle = castle;
        this.enPassant = enPassant;
        this.start = new Square(piece, r, c);
        this.end = end;
        if (piece > 0) {
            white = true;
        }
        else {
            white = false;
        }
    }
    public Move(int piece, int r, int c, Square end) {
        this(piece, r, c, end, 0, false);
    }
    public Move(int piece, int r, int c, Square end, boolean promotion) {
        this(piece, r, c, end, 0, false);
        this.promotion = promotion;
    }
    /*public Move(Square start, Square end) {
        this.start = start;
        this.end = end;
    }*/

    public Square getStartSquare() {
        return start;
    }

    public Square getEndSquare() {
        return end;
    }

    public boolean getEP() {
        return enPassant;
    }

    public int getCastle() {
        return castle;
    }

    public boolean getPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public void initStaticScore(int[][] chessBoard) {
        staticScore = ChessRules.ChessUtil.staticEvaluator(white, ChessRules.ChessUtil.moveExecutor(white, chessBoard, this));
    }
    public double getStaticScore() {
        return staticScore;
    }


    @Override
    public String toString() {
        String moveName;
        int piece = start.getPiece();
        if (piece == 1 || piece == 7 || piece == -1 || piece == -7) {
            moveName = "Pawn ";
        }
        else if (piece == 2 || piece == -2) {
            moveName = "Queen ";
        }
        else if (piece == 3 || piece == -3) {
            moveName = "Bishop ";
        }
        else if (piece == 4 || piece == -4) {
            moveName = "Knight ";
        }
        else if (piece == 5 || piece == 6 || piece == -5 || piece == -6) {
            moveName = "Rook ";
        }
        else {
            moveName = "King ";
        }

        moveName = moveName + ChessRules.ChessUtil.getSquareName(start.getRow(), start.getColumn()) + " to " + ChessRules.ChessUtil.getSquareName(end.getRow(), end.getColumn());
        if (enPassant) {
            moveName = moveName + " E.P.";
        }
        else if (castle == 1) {
            moveName = moveName + " 0-0";
        }
        else if (castle == 2) {
            moveName = moveName + " 0-0-0";
        }
        moveName = moveName + "";
        return moveName;
        //return "" + ChessRules.ChessUtil.getMoveName(start.getRow(), start.getColumn()) + " to " + ChessRules.ChessUtil.getMoveName(end.getRow(), end.getColumn()) + "\n";
     } 
}
