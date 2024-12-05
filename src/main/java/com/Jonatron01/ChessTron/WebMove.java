package com.Jonatron01.ChessTron;

public class WebMove {
    int index;
    int piece;
    int afterPiece;
    int[] start;
    int[] end;
    boolean promotion;
    public WebMove (Move m, int index, boolean promotion) {
        this.index = index;
        Square startSquare = m.getStartSquare();
        Square endSquare = m.getEndSquare();
        piece = startSquare.getPiece();
        afterPiece = endSquare.getPiece();
        int[] start = {startSquare.getRow(), startSquare.getColumn()};
        int[] end = {endSquare.getRow(), endSquare.getColumn()};
        this.start = start;
        this.end = end;
        this.promotion = promotion;
    }
    public int getIndex() {
        return index;
    }
    public int getPiece() {
        return piece;
    }
    public int getAfterPiece() {
        return piece;
    }
    public int[] getStart() {
        return start;
    }
    public int[] getEnd() {
        return end;
    }
    public boolean getPromotion() {
        return promotion;
    }
}
