package com.Jonatron01.ChessTron;

import java.util.ArrayList;

public class WebMoveArray {
    ArrayList<WebMove> wm = new ArrayList<>();
    public WebMoveArray (ArrayList<Move> moves) {
        int i = 0;
        for (Move m: moves) {
            wm.add(new WebMove(m, i, m.getPromotion()));
            i++;
        }
    }

    public ArrayList<WebMove> getWebMoves() {
        return wm;
    }
}
