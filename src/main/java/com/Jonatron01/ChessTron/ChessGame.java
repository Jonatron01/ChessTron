package com.Jonatron01.ChessTron;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class ChessGame {
    boolean wHuman;
    boolean bHuman;
    Stack<int[][]> chessStack;
    int[][] currBoard;
    boolean turn = true;
    public ChessGame(boolean wHuman, boolean bHuman) {
        this.wHuman = wHuman;
        this.bHuman = bHuman;
        initChessGame();
    }
    public ChessGame(boolean wHuman, boolean bHuman, int[][] chessBoard) {
        this.wHuman = wHuman;
        this.bHuman = bHuman;
        initChessGame(chessBoard);
    }
    public ChessGame(boolean wHuman, boolean bHuman, Stack<int[][]> chessStack) {
        this.wHuman = wHuman;
        this.bHuman = bHuman;
        initChessGame(chessStack);
    }

    public void initChessGame() {
        this.chessStack = new Stack<>();
        this.currBoard = ChessRules.ChessUtil.getNewBoard();
        chessStack.add(currBoard);
    }
    public void initChessGame(int[][] chessBoard) {
        this.chessStack = new Stack<>();
        this.currBoard = chessBoard;
        chessStack.add(currBoard);
    }
    public void initChessGame(Stack<int[][]> chessStack) {
        this.chessStack = chessStack;
        this.currBoard = chessStack.peek();
        chessStack.add(currBoard);
    }

    public int runGame() throws Exception {
        while (ChessRules.ChessUtil.getGameState(turn, chessStack) == 0) {
            printBoard();
            if (turn) {
                Move m;
                if (wHuman) {
                    m = getHumanMove(true);
                }
                else {
                    m = getComputerMove(true);
                }
                System.out.println("\nWhite move: " + m + "\n");
                addMove(m);
                turn = false;
            }
            else {
                Move m;
                if (bHuman) {
                    m = getHumanMove(false);
                }
                else {
                    m = getComputerMove(false);
                }
                System.out.println("\nBlack move: " + m + "\n");
                addMove(m);
                turn = true;
            }
        }
        return ChessRules.ChessUtil.getGameState(turn, chessStack);
        
        
    }

    public Move getComputerMove(boolean white) throws Exception {
        int gamephase = ChessRules.ChessUtil.getGamephase(currBoard);
        int depth;
        int depthDelta;
        if (gamephase == 0) {
            depth = 10;
            depthDelta = 0;
        }
        else if (gamephase < 2) {
            depth = 8;
            depthDelta = 0;
        }
        else if (gamephase < 4) {
            depth = 8;
            depthDelta = 0;
        }
        else {
            depth = 6;
            depthDelta = 0;
        }
        Instant start = Instant.now();
        ScoredMove sm = ChessRules.ChessUtil.depthEvaluator(white, depthDelta, depth, -1500, 1500, chessStack);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("total load time: " + duration.toMillis());
        System.out.println("static evaluator time: " + ChessRules.ChessUtil.evaluationTime);
        System.out.println("move generation time:: " + ChessRules.ChessUtil.validMovesTime);
        System.out.println("nodes visited: " + ChessRules.ChessUtil.nodesCount);
        System.out.println("total count: " + ChessRules.ChessUtil.totalCount);
        System.out.println("prune count:: " + ChessRules.ChessUtil.pruneCount);
        ChessRules.ChessUtil.evaluationTime = 0;
        ChessRules.ChessUtil.validMovesTime = 0;
        ChessRules.ChessUtil.nodesCount = 0;
        ChessRules.ChessUtil.totalCount = 0;
        ChessRules.ChessUtil.pruneCount = 0;
        System.out.println(sm.getScore());
        if (sm.getHasMate()) {
            System.out.println("mate in " + sm.getMate());
        }
        if (sm.getMove() == null) {
            System.out.println("game end");
            if (white) {
                if (sm.getScore() < -950) {
                    System.out.println("the bot has lost :'(");
                }
                else {
                    System.out.println("a draw....");
                }
            }
            else {
                if (sm.getScore() > 950) {
                    System.out.println("the bot has lost :'(");
                }
                else {
                    System.out.println("a draw....");
                }
            }
            
        }
        return sm.getMove();
    }
    public Move getHumanMove(boolean white) throws Exception {
        ArrayList<Move> playerMoves = ChessRules.ChessUtil.getValidMoves(white, currBoard);
        String moveList = "";
        int i = 0;
        if (playerMoves.isEmpty()) {
            if (ChessRules.ChessUtil.isWhiteKingSafe(currBoard)) {
                System.out.println("it's a draw....");
            }
            else {
                System.out.println("the bot won :O");
            }
        }
        for (Move m: playerMoves) {
            moveList = moveList + i + ". " + m + "\n";
            i++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println(moveList);
        System.out.print("Select your move: move# = ");
        boolean validInput = false;
        int number = -1;
        Move chosenMove = null;
        while (!validInput) {
            try {
                System.out.print("Enter an integer: ");
                number = scanner.nextInt();  // Attempt to read an integer
                if (number == -1) {
                    undo();
                    System.out.println("Undoing last 2 moves");
                    printBoard();
                    return getHumanMove(white);
                }
                else {
                    chosenMove = playerMoves.get(number);
                    if (chosenMove != null) {
                        validInput = true;  // If successful, set flag to exit loop
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                scanner.next();  // Clear the invalid input from the scanner
            }
        }
        return chosenMove;
    }

    public void addMove (Move m) {
        chessStack.add(ChessRules.ChessUtil.moveExecutor(turn, currBoard, m));
        currBoard = chessStack.peek();
    }

    public void addMoveNo (Move m) {
        chessStack.add(ChessRules.ChessUtil.moveExecutor(turn, currBoard, m));
        currBoard = chessStack.peek();
    }

    public void printBoard() {
        System.out.println(ChessRules.ChessUtil.boardToString(currBoard));
    }

    public void undo() {
        if (chessStack.size() > 2) {
            chessStack.pop();
            chessStack.pop();
            currBoard = chessStack.peek();
        }
    }

    public boolean getTurn () {
        return turn;
    }

    public ArrayList<WebMove> getWebMoves () {
        ArrayList<Move> playerMoves = ChessRules.ChessUtil.getValidMoves(turn, currBoard);
        WebMoveArray wma = new WebMoveArray(playerMoves);
        return wma.getWebMoves();
    }

    public ArrayList<Move> getMoves() {
        return ChessRules.ChessUtil.getValidMoves(turn, currBoard);
    }

    public int[][] getBoard() {
        return currBoard;
    }

    public void changeTurn() {
        System.out.println("turns changed = " + turn + " -> " + !turn);
        turn = !turn;
    }
}
