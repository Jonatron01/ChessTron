package com.Jonatron01.ChessTron;

public class ScoredMove {
    Move move;
    double score;
    boolean draw = false;
    boolean haveMate = false;
    int mate = 0;

    public ScoredMove(Move move, double score) {
        this.move = move;
        this.score = score;
    }
    public ScoredMove(Move move, double score, boolean draw) {
        this.move = move;
        this.score = score;
        this.draw = draw;
    }
    public ScoredMove(boolean mate, Move move, double score) {
        this.move = move;
        this.score = score;
        this.haveMate = mate;
    }

    public Move getMove() {
        return move;
    }

    public double getScore() {
        return score;
    }

    public boolean isDraw() {
        return draw;
    }

    public void countMate() {
        mate++;
    }

    public void decrementMate() {
        mate--;
    }

    public int getMate() {
        return mate;
    }

    public void setHasMate(boolean b) {
        haveMate = b;
    }

    public boolean getHasMate() {
        return haveMate;
    }
    public void setMate(int m) {
        mate = m;
    }

    @Override
    public String toString() {
        return move.toString();
     } 
}
