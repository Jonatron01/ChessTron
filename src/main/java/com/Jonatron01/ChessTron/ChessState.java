package com.Jonatron01.ChessTron;

import java.util.Stack;

public class ChessState {
    public Stack<int[][]> chessStack;
    public int[][] chessBoard;

    public ChessState() {
        chessStack = new Stack<>();
        chessBoard = ChessRules.ChessUtil.getNewBoard();
    }


    
}
