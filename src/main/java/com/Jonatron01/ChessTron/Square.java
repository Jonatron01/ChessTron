package com.Jonatron01.ChessTron;

public class Square {
    final private int piece;
    final private int r;
    final private int c;
    public Square(int piece, int r, int c) {
        this.piece = piece;
        this.r = r;
        this.c = c;
    }

    public int getPiece() {
        return piece;
    }

    public int getRow() {
        return r;
    }
    
    public int getColumn() {
        return c;
    }

    @Override
    public String toString() { 
        return "" + piece + " at " + ChessRules.ChessUtil.getSquareName(r, c) + "\n";
     } 
}
