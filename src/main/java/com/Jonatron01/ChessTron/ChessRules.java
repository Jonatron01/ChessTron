package com.Jonatron01.ChessTron;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

public class ChessRules {







    public static class ChessUtil {
        final static int W_PAWN = 1;
        final static int W_QUEEN = 2;
        final static int W_BISHOP = 3;
        final static int W_KNIGHT = 4;
        final static int W_ROOK_C = 5;
        final static int W_ROOK = 6;
        final static int W_PAWN_EP = 7;
        final static int W_KING_C = 8;
        final static int W_KING = 9;
        final static int B_PAWN = -1;
        final static int B_QUEEN = -2;
        final static int B_BISHOP = -3;
        final static int B_KNIGHT = -4;
        final static int B_ROOK_C = -5;
        final static int B_ROOK = -6;
        final static int B_PAWN_EP = -7;
        final static int B_KING_C = -8;
        final static int B_KING = -9;
        final static int[] mg_value = { 82, 337, 365, 477, 1025,  0};
        final static int[] eg_value = { 94, 281, 297, 512,  936,  0};
        static double evaluationTime;
        static double validMovesTime;
        static int nodesCount;
        static int totalCount;
        static int pruneCount;

        final static int[] mg_pawn_table = {
            0,   0,   0,   0,   0,   0,  0,   0,
            98, 134,  61,  95,  68, 126, 34, -11,
            -6,   7,  26,  31,  65,  56, 25, -20,
            -14,  13,   6,  21,  23,  12, 17, -23,
            -27,  -2,  -5,  12,  17,   6, 10, -25,
            -26,  -4,  -4, -10,   3,   3, 33, -12,
            -35,  -1, -20, -23, -15,  24, 38, -22,
            0,   0,   0,   0,   0,   0,  0,   0,
        };
        
        final static int[] eg_pawn_table = {
            0,   0,   0,   0,   0,   0,   0,   0,
            178, 173, 158, 134, 147, 132, 165, 187,
            94, 100,  85,  67,  56,  53,  82,  84,
            32,  24,  13,   5,  -2,   4,  17,  17,
            13,   9,  -3,  -7,  -7,  -8,   3,  -1,
            4,   7,  -6,   1,   0,  -5,  -1,  -8,
            13,   8,   8,  10,  13,   0,   2,  -7,
            0,   0,   0,   0,   0,   0,   0,   0,
        };
        
        final static int[] mg_knight_table = {
            -167, -89, -34, -49,  61, -97, -15, -107,
            -73, -41,  72,  36,  23,  62,   7,  -17,
            -47,  60,  37,  65,  84, 129,  73,   44,
            -9,  17,  19,  53,  37,  69,  18,   22,
            -13,   4,  16,  13,  28,  19,  21,   -8,
            -23,  -9,  12,  10,  19,  17,  25,  -16,
            -29, -53, -12,  -3,  -1,  18, -14,  -19,
            -105, -21, -58, -33, -17, -28, -19,  -23,
        };
        
        final static int[] eg_knight_table = {
            -58, -38, -13, -28, -31, -27, -63, -99,
            -25,  -8, -25,  -2,  -9, -25, -24, -52,
            -24, -20,  10,   9,  -1,  -9, -19, -41,
            -17,   3,  22,  22,  22,  11,   8, -18,
            -18,  -6,  16,  25,  16,  17,   4, -18,
            -23,  -3,  -1,  15,  10,  -3, -20, -22,
            -42, -20, -10,  -5,  -2, -20, -23, -44,
            -29, -51, -23, -15, -22, -18, -50, -64,
        };
        
        final static int[] mg_bishop_table = {
            -29,   4, -82, -37, -25, -42,   7,  -8,
            -26,  16, -18, -13,  30,  59,  18, -47,
            -16,  37,  43,  40,  35,  50,  37,  -2,
            -4,   5,  19,  50,  37,  37,   7,  -2,
            -6,  13,  13,  26,  34,  12,  10,   4,
            0,  15,  15,  15,  14,  27,  18,  10,
            4,  15,  16,   0,   7,  21,  33,   1,
            -33,  -3, -14, -21, -13, -12, -39, -21,
        };
        
        final static int[] eg_bishop_table = {
            -14, -21, -11,  -8, -7,  -9, -17, -24,
            -8,  -4,   7, -12, -3, -13,  -4, -14,
            2,  -8,   0,  -1, -2,   6,   0,   4,
            -3,   9,  12,   9, 14,  10,   3,   2,
            -6,   3,  13,  19,  7,  10,  -3,  -9,
            -12,  -3,   8,  10, 13,   3,  -7, -15,
            -14, -18,  -7,  -1,  4,  -9, -15, -27,
            -23,  -9, -23,  -5, -9, -16,  -5, -17,
        };
        
        final static int[] mg_rook_table = {
            32,  42,  32,  51, 63,  9,  31,  43,
            27,  32,  58,  62, 80, 67,  26,  44,
            -5,  19,  26,  36, 17, 45,  61,  16,
            -24, -11,   7,  26, 24, 35,  -8, -20,
            -36, -26, -12,  -1,  9, -7,   6, -23,
            -45, -25, -16, -17,  3,  0,  -5, -33,
            -44, -16, -20,  -9, -1, 11,  -6, -71,
            -19, -13,   1,  17, 16,  7, -37, -26,
        };
        
        final static int[] eg_rook_table = {
            13, 10, 18, 15, 12,  12,   8,   5,
            11, 13, 13, 11, -3,   3,   8,   3,
            7,  7,  7,  5,  4,  -3,  -5,  -3,
            4,  3, 13,  1,  2,   1,  -1,   2,
            3,  5,  8,  4, -5,  -6,  -8, -11,
            -4,  0, -5, -1, -7, -12,  -8, -16,
            -6, -6,  0,  2, -9,  -9, -11,  -3,
            -9,  2,  3, -1, -5, -13,   4, -20,
        };
        
        final static int[] mg_queen_table = {
            -28,   0,  29,  12,  59,  44,  43,  45,
            -24, -39,  -5,   1, -16,  57,  28,  54,
            -13, -17,   7,   8,  29,  56,  47,  57,
            -27, -27, -16, -16,  -1,  17,  -2,   1,
            -9, -26,  -9, -10,  -2,  -4,   3,  -3,
            -14,   2, -11,  -2,  -5,   2,  14,   5,
            -35,  -8,  11,   2,   8,  15,  -3,   1,
            -1, -18,  -9,  10, -15, -25, -31, -50,
        };
        
        final static int[] eg_queen_table = {
            -9,  22,  22,  27,  27,  19,  10,  20,
            -17,  20,  32,  41,  58,  25,  30,   0,
            -20,   6,   9,  49,  47,  35,  19,   9,
            3,  22,  24,  45,  57,  40,  57,  36,
            -18,  28,  19,  47,  31,  34,  39,  23,
            -16, -27,  15,   6,   9,  17,  10,   5,
            -22, -23, -30, -16, -16, -23, -36, -32,
            -33, -28, -22, -43,  -5, -32, -20, -41,
        };
        
        final static int[] mg_king_table = {
            -65,  23,  16, -15, -56, -34,   2,  13,
            29,  -1, -20,  -7,  -8,  -4, -38, -29,
            -9,  24,   2, -16, -20,   6,  22, -22,
            -17, -20, -12, -27, -30, -25, -14, -36,
            -49,  -1, -27, -39, -46, -44, -33, -51,
            -14, -14, -22, -46, -44, -30, -15, -27,
            1,   7,  -8, -64, -43, -16,   9,   8,
            -15,  36,  12, -54,   8, -28,  24,  14,
        };
        
        final static int[] eg_king_table = {
            -74, -35, -18, -18, -11,  15,   4, -17,
            -12,  17,  14,  17,  17,  38,  23,  11,
            10,  17,  23,  15,  20,  45,  44,  13,
            -8,  22,  24,  27,  26,  33,  26,   3,
            -18,  -4,  21,  24,  27,  23,   9, -11,
            -19,  -3,  11,  21,  23,  16,   7,  -9,
            -27, -11,   4,  13,  14,   4,  -5, -17,
            -53, -34, -21, -11, -28, -14, -24, -43
        };
        
        final static int[][] mg_pesto_table =
        {
            mg_pawn_table,
            mg_knight_table,
            mg_bishop_table,
            mg_rook_table,
            mg_queen_table,
            mg_king_table
        };
        
        final static int[][] eg_pesto_table =
        {
            eg_pawn_table,
            eg_knight_table,
            eg_bishop_table,
            eg_rook_table,
            eg_queen_table,
            eg_king_table
        };

        public static int getGameState(boolean white, Stack<int[][]> chessStack) {
            // 0 = business as usual
            // 2 = draw;
            //-1 = black victory
            // 1 = white victory
            if (isThreefold(chessStack)) {
                System.out.println("why did it do this");
                Iterator<int[][]> iter = chessStack.iterator();
                while (iter.hasNext()) {
                    System.out.println(boardToString(iter.next()));
                }
                return 2;
            }
            if (!hasValidMoves(white, chessStack.peek())) {
                if (white) {
                    if (!isWhiteKingSafe(chessStack.peek())) {
                        return -1;
                    }
                    else {
                        return 2;
                    }
                }
                else {
                    if (!isBlackKingSafe(chessStack.peek())) {
                        return 1;
                    }
                    else {
                        return 2;
                    }
                }
            }
            return 0;
        }

        public static ArrayList<Move> getValidMoves(boolean white, int[][] chessBoard) {
            Instant start = Instant.now();
            ArrayList<Move> moves = new ArrayList<>();
            ArrayList<Move> pawns = new ArrayList<>();
            ArrayList<Move> knights = new ArrayList<>();
            ArrayList<Move> bishops = new ArrayList<>();
            ArrayList<Move> rooks = new ArrayList<>();
            ArrayList<Move> queens = new ArrayList<>();
            ArrayList<Move> kings = new ArrayList<>();
            if (white) {
                for (int r = 7 ; r >= 0 ; r--) {
                    for (int c = 0 ; c < 8 ; c ++) {
                        if (chessBoard[r][c] == W_PAWN || chessBoard[r][c] == W_PAWN_EP) {
                            pawns.addAll(getValidMovesWhitePiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == W_KNIGHT) {
                            knights.addAll(getValidMovesWhitePiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == W_BISHOP) {
                            bishops.addAll(getValidMovesWhitePiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == W_ROOK || chessBoard[r][c] == W_ROOK_C) {
                            rooks.addAll(getValidMovesWhitePiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == W_QUEEN) {
                            queens.addAll(getValidMovesWhitePiece(chessBoard, r, c));
                        }
                        else if(chessBoard[r][c] == W_KING || chessBoard[r][c] == W_KING_C) {
                            kings.addAll(getValidMovesWhitePiece(chessBoard, r, c));
                        }
                    }
                }
                
                moves.addAll(pawns);
                moves.addAll(kings);
                moves.addAll(knights);
                moves.addAll(bishops);
                moves.addAll(queens);
                moves.addAll(rooks);
                //moves = moveSorter(true, moves, chessBoard);
                /* // troll zone start
                System.out.println(boardToString(chessBoard));
                while (!moves.isEmpty()) {
                    System.out.println(boardToString(moveExecutor(true, chessBoard, moves.getFirst())));
                    System.out.println(moves.getFirst());
                    moves.removeFirst();
                }
                //System.out.println(boardToString(chessBoard));
                // troll zone end */
                ArrayList<Move> priorityMoves = new ArrayList<>();
                Iterator<Move> iter = moves.iterator();
                while (iter.hasNext()) {
                    Move m = iter.next();
                    if (m.getEndSquare().getPiece() != 0) {
                        priorityMoves.add(m);
                        iter.remove();
                    }
                    else {
                        int[][] tcb = moveExecutor(true, chessBoard, m);
                        if (!isBlackKingSafe(tcb)) {
                            priorityMoves.add(m);
                            iter.remove();
                        }/* 
                        else {
                            boolean isAttack;
                            
                            
                        
                            if (!isBlackKingSafe(moveExecutor(true, chessBoard, m))) {
                                priorityMoves.add(m);
                                iter.remove();
                            }
                        }*/
                    }
                    

                }
                for (Move m: priorityMoves) {
                    moves.addFirst(m);
                }
                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                validMovesTime += duration.toMillis();
                return moves;
            }
            else {
                for (int r = 0 ; r < 8 ; r++) {
                    for (int c = 0 ; c < 8 ; c++) {
                        if (chessBoard[r][c] == B_PAWN || chessBoard[r][c] == B_PAWN_EP) {
                            pawns.addAll(getValidMovesBlackPiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == B_KNIGHT) {
                            knights.addAll(getValidMovesBlackPiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == B_BISHOP) {
                            bishops.addAll(getValidMovesBlackPiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == B_ROOK || chessBoard[r][c] == B_ROOK_C) {
                            rooks.addAll(getValidMovesBlackPiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == B_QUEEN) {
                            queens.addAll(getValidMovesBlackPiece(chessBoard, r, c));
                        }
                        else if (chessBoard[r][c] == B_KING || chessBoard[r][c] == B_KING_C) {
                            kings.addAll(getValidMovesBlackPiece(chessBoard, r, c));
                        }
                    }
                }
                moves.addAll(pawns);
                moves.addAll(kings);
                moves.addAll(knights);
                moves.addAll(bishops);
                moves.addAll(queens);
                moves.addAll(rooks);
                //moves = moveSorter(false, moves, chessBoard);
                /* // troll zone start
                while (!moves.isEmpty()) {
                    System.out.println(boardToString(moveExecutor(false, chessBoard, moves.getFirst())));
                    System.out.println(moves.getFirst());
                    moves.removeFirst();
                }
                // troll zone end */
                ArrayList<Move> priorityMoves = new ArrayList<>();
                Iterator<Move> iter = moves.iterator();
                while (iter.hasNext()) {
                    Move m = iter.next();
                    if (m.getEndSquare().getPiece() != 0) {
                        priorityMoves.add(m);
                        iter.remove();
                    }
                    else if (!isWhiteKingSafe(moveExecutor(false, chessBoard, m))) {
                        priorityMoves.add(m);
                        iter.remove();
                    }

                }
                for (Move m: priorityMoves) {
                    moves.addFirst(m);
                }
                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                validMovesTime += duration.toMillis();
                return moves;
            }
        }

        public static boolean hasValidMoves(boolean white, int[][] chessBoard) {
            ArrayList<Move> moves;
            if (white) {
                for (int r = 7 ; r >= 0 ; r--) {
                    for (int c = 0 ; c < 8 ; c++) {
                        if (chessBoard[r][c] > 0) {
                            moves = getValidMovesWhitePiece(chessBoard, r, c);
                        
                            if (!moves.isEmpty()) {
                                Iterator<Move> iter = moves.iterator();
                                while (iter.hasNext()) {
                                    Move m = iter.next();
                                    if (isWhiteKingSafe(moveExecutor(true, chessBoard, m))) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
            else {
                for (int r = 0 ; r < 8 ; r++) {
                    for (int c = 0 ; c < 8 ; c++) {
                        if (chessBoard[r][c] < 0) {
                            moves = getValidMovesBlackPiece(chessBoard, r, c);
                        
                            if (!moves.isEmpty()) {
                                Iterator<Move> iter = moves.iterator();
                                while (iter.hasNext()) {
                                    Move m = iter.next();
                                    if (isBlackKingSafe(moveExecutor(false, chessBoard, m))) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
        }

        public static ArrayList<Move> getValidMovesWhitePiece(int[][] chessBoard, int r, int c) {
            ArrayList<Move> moves = new ArrayList<>();
            int piece = chessBoard[r][c];
            if (piece > 0) {
                if (piece == W_PAWN) {
                    moves.addAll(getPawnMoves(true, chessBoard, r, c));
                }
                if (piece == W_BISHOP) {
                    moves.addAll(getBishopMoves(true, chessBoard, r, c));
                }
                if (piece == W_KNIGHT) {
                    moves.addAll(getKnightMoves(true, chessBoard, r, c));
                }
                if (piece == W_ROOK_C || piece == W_ROOK) {
                    moves.addAll(getRookMoves(true, chessBoard, r, c));
                }
                if (piece == W_QUEEN) {
                    moves.addAll(getQueenMoves(true, chessBoard, r, c));
                }
                if (piece >= W_KING_C) {
                    moves.addAll(getKingMoves(true, chessBoard, r, c));
                }
            }
            // perform king check -> expand out pawn promotion moves
            ArrayList<Move> finalMoves = new ArrayList<>();
            Iterator<Move> iter = moves.iterator();
            while (iter.hasNext()) { 
                Move m = iter.next();
                if (isWhiteKingSafe(moveExecutor(true, chessBoard, m))) {
                    if (m.getPromotion()) {
                        iter.remove();
                        Square sq = m.getStartSquare();
                        finalMoves.add(new Move(W_QUEEN, sq.getRow(), sq.getColumn(), m.getEndSquare(), true));
                        finalMoves.add(new Move(W_ROOK, sq.getRow(), sq.getColumn(), m.getEndSquare(), true));
                        finalMoves.add(new Move(W_BISHOP, sq.getRow(), sq.getColumn(), m.getEndSquare(), true));
                        finalMoves.add(new Move(W_KNIGHT, sq.getRow(), sq.getColumn(), m.getEndSquare(), true));
                    }
                    else {
                        finalMoves.add(m);
                    }
                }
            }

            return finalMoves;
        }

        public static ArrayList<Move> getValidMovesBlackPiece(int[][] chessBoard, int r, int c) {
            ArrayList<Move> moves = new ArrayList<>();
            int piece = chessBoard[r][c];
            if (piece < 0) {
                if (piece == B_PAWN) {
                    moves.addAll(getPawnMoves(false, chessBoard, r, c));
                }
                if (piece == B_BISHOP) {
                    moves.addAll(getBishopMoves(false, chessBoard, r, c));
                }
                if (piece == B_KNIGHT) {
                    moves.addAll(getKnightMoves(false, chessBoard, r, c));
                }
                if (piece == B_ROOK_C || piece == B_ROOK) {
                    moves.addAll(getRookMoves(false, chessBoard, r, c));
                }
                if (piece == B_QUEEN) {
                    moves.addAll(getQueenMoves(false, chessBoard, r, c));
                }
                if (piece <= B_KING_C) {
                    moves.addAll(getKingMoves(false, chessBoard, r, c));
                }
            }
            ArrayList<Move> finalMoves = new ArrayList<>();
            Iterator<Move> iter = moves.iterator();
            while (iter.hasNext()) { 
                Move m = iter.next();
                if (isBlackKingSafe(moveExecutor(false, chessBoard, m))) {
                    if (m.getPromotion()) {
                        iter.remove();
                        Square sq = m.getStartSquare();
                        finalMoves.add(new Move(B_QUEEN, sq.getRow(), sq.getColumn(), m.getEndSquare(), true));
                        finalMoves.add(new Move(B_ROOK, sq.getRow(), sq.getColumn(), m.getEndSquare(), true));
                        finalMoves.add(new Move(B_BISHOP, sq.getRow(), sq.getColumn(), m.getEndSquare(), true));
                        finalMoves.add(new Move(B_KNIGHT, sq.getRow(), sq.getColumn(), m.getEndSquare(), true));
                    }
                    else {
                        finalMoves.add(m);
                    }
                }
            }

            return finalMoves;
        }

        public static ArrayList<Move> getPawnMoves(boolean white, int[][] chessBoard, int r, int c) {
            ArrayList<Move> moves = new ArrayList<>();
            if (white) {
                ArrayList<Square> s = getSquaresDirection(chessBoard, 0, r, c);
                if (!s.isEmpty()) {
                    if (s.getFirst().getPiece() == 0) {
                        moves.add(new Move(W_PAWN, r, c, s.getFirst()));
                    }
                    s.removeFirst();
    
                    if (r == 1 && !s.isEmpty()) {
                        if (s.getFirst().getPiece() == 0) {
                            moves.add(new Move(W_PAWN_EP, r, c, s.getFirst()));
                        }
                    }
                }
                
                s = getSquaresDirection(chessBoard, 1, r, c);
                if (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p < 0 && p > B_KING_C) {
                        moves.add(new Move(W_PAWN, r, c, s.getFirst()));
                    }
                }
    
                s = getSquaresDirection(chessBoard, 7, r, c);
                if (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p < 0 && p > B_KING_C) {
                        moves.add(new Move(W_PAWN, r, c, s.getFirst()));
                    }
                }

                // detect en passant
                s = getSquaresDirection(chessBoard, 2, r, c);
                if (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p == B_PAWN_EP) {
                        Square sq = new Square(0, s.getFirst().getRow() + 1, s.getFirst().getColumn());
                        moves.add(new Move(W_PAWN, r, c, sq, 0, true));

                        // MUST REMEMBER TO REMOVE THE SQUARE THE EN PASSANT PAWN IS ON
                        // ALSO THE DOUBLE PUSHED PAWN INDICATOR SHOULD BE REVERTED TO NORMAL BEFORE THE NEXT TURN
                    }
                }
                s = getSquaresDirection(chessBoard, 6, r, c);
                if (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p == B_PAWN_EP) {
                        Square sq = new Square(0, s.getFirst().getRow() + 1, s.getFirst().getColumn());
                        moves.add(new Move(W_PAWN, r, c, sq, 0, true));
                    }
                }
                

            }
            else {
                ArrayList<Square> s = getSquaresDirection(chessBoard, 4, r, c);
                if (!s.isEmpty()) {
                    if (s.getFirst().getPiece() == 0) {
                        moves.add(new Move(B_PAWN, r, c, s.getFirst()));
                    }
                    s.removeFirst();
    
                    if (r == 6 && !s.isEmpty()) {
                        if (s.getFirst().getPiece() == 0) {
                            moves.add(new Move(B_PAWN_EP, r, c, s.getFirst()));
                        }
                    }
                }
                
                s = getSquaresDirection(chessBoard, 5, r, c);
                if (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p > 0 && p < W_KING_C) {
                        moves.add(new Move(B_PAWN, r, c, s.getFirst()));
                    }
                }
    
                s = getSquaresDirection(chessBoard, 3, r, c);
                if (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p > 0 && p < W_KING_C) {
                        moves.add(new Move(B_PAWN, r, c, s.getFirst()));
                    }
                }

                // detect en passant
                s = getSquaresDirection(chessBoard, 2, r, c);
                if (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p == W_PAWN_EP) {
                        Square sq = new Square(0, s.getFirst().getRow() - 1, s.getFirst().getColumn());
                        moves.add(new Move(B_PAWN, r, c, sq, 0, true));

                        // MUST REMEMBER TO REMOVE THE SQUARE THE EN PASSANT PAWN IS ON
                    }
                }
                s = getSquaresDirection(chessBoard, 6, r, c);
                if (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p == W_PAWN_EP) {
                        Square sq = new Square(0, s.getFirst().getRow() - 1, s.getFirst().getColumn());
                        moves.add(new Move(B_PAWN, r, c, sq, 0, true));
                    }
                }
            }

            return moves;
            
        }

        public static ArrayList<Move> getBishopMoves(boolean white, int [][] chessBoard, int r, int c) {
            ArrayList<Move> moves = new ArrayList<>();
            if (white) {
                for (int i = 0 ; i < 4 ; i++) {
                    ArrayList<Square> s = getSquaresDirection(chessBoard, (i * 2) + 1, r, c);
                    while (!s.isEmpty()) {
                        int p = s.getFirst().getPiece();
                        if (p <= 0 && p > B_KING_C) {
                            moves.add(new Move(W_BISHOP, r, c, s.getFirst()));
                        }
                        s.removeFirst();
                    }
                }
            }
            else {
                for (int i = 0 ; i < 4 ; i++) {
                    ArrayList<Square> s = getSquaresDirection(chessBoard, (i * 2) + 1, r, c);
                    while (!s.isEmpty()) {
                        int p = s.getFirst().getPiece();
                        if (p >= 0 && p < W_KING_C) {
                            moves.add(new Move(B_BISHOP, r, c, s.getFirst()));
                        }
                        s.removeFirst();
                    }
                }
            }
            return moves;
        }
        
        public static ArrayList<Move> getKnightMoves(boolean white, int [][] chessBoard, int r, int c) {
            ArrayList<Move> moves = new ArrayList<>();
            if (white) {
                ArrayList<Square> s = getKnightSquares(chessBoard, r, c);
                while (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p <= 0 && p > B_KING_C) {
                        moves.add(new Move(W_KNIGHT, r, c, s.getFirst()));
                    }
                    s.removeFirst();
                }
            }
            else {
                ArrayList<Square> s = getKnightSquares(chessBoard, r, c);
                while (!s.isEmpty()) {
                    int p = s.getFirst().getPiece();
                    if (p >= 0 && p < W_KING_C) {
                        moves.add(new Move(B_KNIGHT, r, c, s.getFirst()));
                    }
                    s.removeFirst();
                }
            }
            
            return moves;
        }

        public static ArrayList<Move> getRookMoves(boolean white, int [][] chessBoard, int r, int c) {
            ArrayList<Move> moves = new ArrayList<>();
            if (white) {
                for (int i = 0 ; i < 4 ; i++) {
                    ArrayList<Square> s = getSquaresDirection(chessBoard, i * 2, r, c);
                    while (!s.isEmpty()) {
                        int p = s.getFirst().getPiece();
                        if (p <= 0 && p > B_KING_C) {
                            moves.add(new Move(W_ROOK, r, c, s.getFirst()));
                        }
                        s.removeFirst();
                    }
                }
            }
            else {
                for (int i = 0 ; i < 4 ; i++) {
                    ArrayList<Square> s = getSquaresDirection(chessBoard, i * 2, r, c);
                    while (!s.isEmpty()) {
                        int p = s.getFirst().getPiece();
                        if (p >= 0 && p < W_KING_C) {
                            moves.add(new Move(B_ROOK, r, c, s.getFirst()));
                        }
                        s.removeFirst();
                    }
                }
            }
            return moves;
        }

        public static ArrayList<Move> getQueenMoves(boolean white, int [][] chessBoard, int r, int c) {
            ArrayList<Move> moves = new ArrayList<>();
            if (white) {
                for (int i = 0 ; i < 8 ; i++) {
                    ArrayList<Square> s = getSquaresDirection(chessBoard, i, r, c);
                    while (!s.isEmpty()) {
                        int p = s.getFirst().getPiece();
                        if (p <= 0 && p > B_KING_C) {
                            moves.add(new Move(W_QUEEN, r, c, s.getFirst()));
                        }
                        s.removeFirst();
                    }
                }
            }
            else {
                for (int i = 0 ; i < 8 ; i++) {
                    ArrayList<Square> s = getSquaresDirection(chessBoard, i, r, c);
                    while (!s.isEmpty()) {
                        int p = s.getFirst().getPiece();
                        if (p >= 0 && p < W_KING_C) {
                            moves.add(new Move(B_QUEEN, r, c, s.getFirst()));
                        }
                        s.removeFirst();
                    }
                }
            }
            return moves;
        }

        public static ArrayList<Move> getKingMoves(boolean white, int [][] chessBoard, int r, int c) {
            ArrayList<Move> moves = new ArrayList<>();
            if (white) {
                int kingState = chessBoard[r][c];
                if (kingState == W_KING_C) {
                    if (chessBoard[0][7] == W_ROOK_C && 
                        chessBoard[0][6] == 0 &&
                        chessBoard[0][5] == 0 &&
                        isSquareSafe(true, chessBoard, 0, 4) && 
                        isSquareSafe(true, chessBoard, 0, 5)) {
                            moves.add(new Move(W_KING, 0, 4, new Square(0, 0, 6), 1, false));
                    }
                    if (chessBoard[0][0] == W_ROOK_C && 
                        chessBoard[0][1] == 0 &&
                        chessBoard[0][2] == 0 &&
                        chessBoard[0][3] == 0 &&
                        isSquareSafe(true, chessBoard, 0, 4) && 
                        isSquareSafe(true, chessBoard, 0, 3)) {
                            moves.add(new Move(W_KING, 0, 4, new Square(0, 0, 2), 2, false));
                    }
                }
                for (int i = 0 ; i < 8 ; i++) {
                    ArrayList<Square> s = getSquaresDirection(chessBoard, i, r, c);
                    if (!s.isEmpty()) {
                        int p = s.getFirst().getPiece();
                        if (p <= 0 && p > B_KING_C) {
                            moves.add(new Move(W_KING, r, c, s.getFirst()));
                        }
                    }
                }
                
            }
            else {
                int kingState = chessBoard[r][c];
                if (kingState == B_KING_C) {
                    if (chessBoard[7][7] == B_ROOK_C && 
                        chessBoard[7][6] == 0 &&
                        chessBoard[7][5] == 0 &&
                        isSquareSafe(false, chessBoard, 7, 4) && 
                        isSquareSafe(false, chessBoard, 7, 5)) {
                            moves.add(new Move(B_KING, 7, 4, new Square(0, 7, 6), 1, false));
                    }
                    if (chessBoard[7][0] == B_ROOK_C && 
                        chessBoard[7][1] == 0 &&
                        chessBoard[7][2] == 0 &&
                        chessBoard[7][3] == 0 &&
                        isSquareSafe(false, chessBoard, 7, 4) && 
                        isSquareSafe(false, chessBoard, 7, 3)) {
                            moves.add(new Move(B_KING, 7, 4, new Square(0, 7, 2), 2, false));
                    }
                }
                for (int i = 0 ; i < 8 ; i++) {
                    ArrayList<Square> s = getSquaresDirection(chessBoard, i, r, c);
                    if (!s.isEmpty()) {
                        int p = s.getFirst().getPiece();
                        if (p >= 0 && p < W_KING_C) {
                            moves.add(new Move(B_KING, r, c, s.getFirst()));
                        }
                    }
                }
            }
            
            return moves;
        }
        public static int[][] getNewBoard() {
            /*int[][] board = new int[][] {{0,  8,  0,  0,  6,  -3,  0,  -2},
                                        { 1, 0,  1,  0,  0,  1,  0,  1},
                                        { -3, 0,  1,  0,  0,  0,  0,  0},
                                        { 0,  0,  0,  0,   0,  0,  0,  0},
                                        { 0,  0,  0,  0,  0,  0,  3,  0},
                                        { 0,  0,  -4,  0,  2,  0,  0,  0},
                                        { -1, -1,  0,  0,  0,  0,  -1,  -1},
                                        { -6,  0,  0,  0,  0,  -8,  0,  -6}};
            /*int[][] board = new int[][] {{5, 4, 3, 2, 7, 3, 4, 5},
                                {1, 1, 1, 1, 1, 1, 1, 1},
                                {0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0},
                                {-1, -1, -1, -1, -1, -1, -1, -1},
                                {-5, -4, -3, -2, -7, -3, -4, -5}};*/
            int[][] board = new int[][] {{5,  4,  3,  2,  8,  3,  4,  5},
                                        { 1,  1,  1,  1,  1,  1,  1,  1},
                                        { 0,  0,  0,  0,  0,  0,  0,  0},
                                        { 0,  0,  0,  0,  0,  0,  0,  0},
                                        { 0,  0,  0,  0,  0,  0,  0,  0},
                                        { 0,  0,  0,  0,  0,  0,  0,  0},
                                        {-1, -1, -1, -1, -1, -1, -1, -1},
                                        {-5, -4, -3, -2, -8, -3, -4, -5}};
            /*int[][] board = new int[][] {{6,  0,  3,  0,  0,  9,  4,  -2},
                                        { 1,  1,  0,  0,  0,  0,  0,  1},
                                        { 0,  0,  0,  0,  0,  0,  0,  0},
                                        { 0,  0,  1,  0,  0,  0,  0,  0},
                                        { 0,  0,  0,  4,  0,  0,  0,  0},
                                        { 0,  0,  0,  0,  0,  6,  0,  0},
                                        {-1, -1, -1, -4, -1, -1, -1, -1},
                                        {-6, 0, 0, -6, -9, -3, 0, -6}};

            /*int[][] board = new int[][] {{9, 6, 0, 0, 0, 0, 0, 6},
                                        {1, 0, 1, 0, 0, 0, 0, 0},
                                        { 0,  0,  0,  0,  0,  0,  0,  -6},
                                        { 0,  0,  0,  -3,  0,  0,  0,  0},
                                        { 0,  0,  0,  0,  0,  0,  0,  0},
                                        { 0,  0,  0,  0,  0,  0,  0,  0},
                                        { -1,  0,  0,  0,  0,  0,  0,  0},
                                        {-9,  0,  0,  0,  0,  0,  0,  0},
                                        };
*/
            return board;
        }

        public static boolean isWhiteKingSafe(int[][] chessBoard) {
            int kingRow = -1;
            int kingColumn = -1;
            
            //find the king
            for (int r = 0 ; r < 8 ; r++) {
                for (int c = 0; c < 8; c++) {
                    if (chessBoard[r][c] >= W_KING_C) {
                        kingRow = r;
                        kingColumn = c;
                    }
                }
            }
            if (kingRow == -1 || kingColumn == -1) {
                throw new Error();
            }
            return isSquareSafe(true, chessBoard, kingRow, kingColumn);
        }

        public static boolean isBlackKingSafe(int[][] chessBoard) {
            int kingRow = -1;
            int kingColumn = -1;
            
            //find the king
            for (int r = 0 ; r < 8 ; r++) {
                for (int c = 0; c < 8; c++) {
                    if (chessBoard[r][c] <= B_KING_C) {
                        kingRow = r;
                        kingColumn = c;
                    }
                }
            }
            if (kingRow == -1 || kingColumn == -1) {
                throw new Error();
            }
            return isSquareSafe(false, chessBoard, kingRow, kingColumn);
        }

        public static String getSquareName(int r, int c) {
            String move = "" + (char)(c + 97) + (r + 1);
            return move;
        }

        public static boolean isSquareSafe(boolean white, int[][] chessBoard, int r, int c) {
            if (white) {
                return isSquareSafeWhite(chessBoard, r, c);
            }
            else {
                return isSquareSafeBlack(chessBoard, r, c);
            }
        }

        public static boolean isSquareSafeWhite(int[][] chessBoard, int r, int c) {
            ArrayList<Square> north = getSquaresDirection(chessBoard, 0, r, c);
            if (!north.isEmpty()) {
                int piece = north.getLast().getPiece();
                if (piece == B_QUEEN || piece == B_ROOK_C || piece == B_ROOK) {
                    return false;
                }
                if (north.size() == 1 && piece <= B_KING_C) {
                    return false;
                }
            }
            ArrayList<Square> east = getSquaresDirection(chessBoard, 2, r, c);
            if (!east.isEmpty()) {
                int piece = east.getLast().getPiece();
                if (piece == B_QUEEN || piece == B_ROOK_C || piece == B_ROOK) {
                    return false;
                }
                if (east.size() == 1 && piece <= B_KING_C) {
                    return false;
                }
            }
            ArrayList<Square> south = getSquaresDirection(chessBoard, 4, r, c);
            if (!south.isEmpty()) {
                int piece = south.getLast().getPiece();
                if (piece == B_QUEEN || piece == B_ROOK_C || piece == B_ROOK) {
                    return false;
                }
                if (south.size() == 1 && piece <= B_KING_C) {
                    return false;
                }
            }
            ArrayList<Square> west = getSquaresDirection(chessBoard, 6, r, c);
            if (!west.isEmpty()) {
                int piece = west.getLast().getPiece();
                if (piece == B_QUEEN || piece == B_ROOK_C || piece == B_ROOK) {
                    return false;
                }
                if (west.size() == 1 && piece <= B_KING_C) {
                    return false;
                }
            }
            ArrayList<Square> northEast = getSquaresDirection(chessBoard, 1, r, c);
            if (!northEast.isEmpty()) {
                int piece = northEast.getLast().getPiece();
                if (piece == B_QUEEN || piece == B_BISHOP) {
                    return false;
                }
                if (northEast.size() == 1 && (piece <= B_KING_C || piece == B_PAWN)) {
                    return false;
                }
            }
            ArrayList<Square> southEast = getSquaresDirection(chessBoard, 3, r, c);
            if (!southEast.isEmpty()) {
                int piece = southEast.getLast().getPiece();
                if (piece == B_QUEEN || piece == B_BISHOP) {
                    return false;
                }
                if (southEast.size() == 1 && (piece <= B_KING_C)) {
                    return false;
                }
            }
            ArrayList<Square> southWest = getSquaresDirection(chessBoard, 5, r, c);
            if (!southWest.isEmpty()) {
                int piece = southWest.getLast().getPiece();
                if (piece == B_QUEEN || piece == B_BISHOP) {
                    return false;
                }
                if (southWest.size() == 1 && (piece <= B_KING_C)) {
                    return false;
                }
            }
            ArrayList<Square> northWest = getSquaresDirection(chessBoard, 7, r, c);
            if (!northWest.isEmpty()) {
                int piece = northWest.getLast().getPiece();
                if (piece == B_QUEEN || piece == B_BISHOP) {
                    return false;
                }
                if (northWest.size() == 1 && (piece <= B_KING_C || piece == B_PAWN)) {
                    return false;
                }
            }
            ArrayList<Square> knights = getKnightSquares(chessBoard, r, c);
            while(!knights.isEmpty()) {
                int piece = knights.getFirst().getPiece();
                if (piece == -4) {
                    return false;
                }
                knights.removeFirst();
            }
            return true;
        }

        public static boolean isSquareSafeBlack(int[][] chessBoard, int r, int c) {
            ArrayList<Square> north = getSquaresDirection(chessBoard, 0, r, c);
            if (!north.isEmpty()) {
                int piece = north.getLast().getPiece();
                if (piece == W_QUEEN || piece == W_ROOK_C || piece == W_ROOK) {
                    return false;
                }
                if (north.size() == 1 && piece >= W_KING_C) {
                    return false;
                }
            }
            ArrayList<Square> east = getSquaresDirection(chessBoard, 2, r, c);
            if (!east.isEmpty()) {
                int piece = east.getLast().getPiece();
                if (piece == W_QUEEN || piece == W_ROOK_C || piece == W_ROOK) {
                    return false;
                }
                if (east.size() == 1 && piece >= W_KING_C) {
                    return false;
                }
            }
            ArrayList<Square> south = getSquaresDirection(chessBoard, 4, r, c);
            if (!south.isEmpty()) {
                int piece = south.getLast().getPiece();
                if (piece == W_QUEEN || piece == W_ROOK_C || piece == W_ROOK) {
                    return false;
                }
                if (south.size() == 1 && piece >= W_KING_C) {
                    return false;
                }
            }
            ArrayList<Square> west = getSquaresDirection(chessBoard, 6, r, c);
            if (!west.isEmpty()) {
                int piece = west.getLast().getPiece();
                if (piece == W_QUEEN || piece == W_ROOK_C || piece == W_ROOK) {
                    return false;
                }
                if (west.size() == 1 && piece >= W_KING_C) {
                    return false;
                }
            }
            ArrayList<Square> northEast = getSquaresDirection(chessBoard, 1, r, c);
            if (!northEast.isEmpty()) {
                int piece = northEast.getLast().getPiece();
                if (piece == W_QUEEN || piece == W_BISHOP) {
                    return false;
                }
                if (northEast.size() == 1 && (piece >= W_KING_C)) {
                    return false;
                }
            }
            ArrayList<Square> southEast = getSquaresDirection(chessBoard, 3, r, c);
            if (!southEast.isEmpty()) {
                int piece = southEast.getLast().getPiece();
                if (piece == W_QUEEN || piece == W_BISHOP) {
                    return false;
                }
                if (southEast.size() == 1 && (piece >= W_KING_C || piece == W_PAWN)) {
                    return false;
                }
            }
            ArrayList<Square> southWest = getSquaresDirection(chessBoard, 5, r, c);
            if (!southWest.isEmpty()) {
                int piece = southWest.getLast().getPiece();
                if (piece == W_QUEEN || piece == W_BISHOP) {
                    return false;
                }
                if (southWest.size() == 1 && (piece >= W_KING_C || piece == W_PAWN)) {
                    return false;
                }
            }
            ArrayList<Square> northWest = getSquaresDirection(chessBoard, 7, r, c);
            if (!northWest.isEmpty()) {
                int piece = northWest.getLast().getPiece();
                if (piece == W_QUEEN || piece == W_BISHOP) {
                    return false;
                }
                if (northWest.size() == 1 && (piece >= W_KING_C)) {
                    return false;
                }
            }
            ArrayList<Square> knights = getKnightSquares(chessBoard, r, c);
            while(!knights.isEmpty()) {
                int piece = knights.getFirst().getPiece();
                if (piece == 4) {
                    return false;
                }
                knights.removeFirst();
            }
            return true;
        }

        // SHOULD BE WORKING PROPERLY !!!!!!!!!!!!!!!!!
        // returns an array list of the squares in a direction until a piece is reached or the board ends
        public static ArrayList<Square> getSquaresDirection(int[][] chessBoard, int direction, int r, int c) {
            // directions:  (N is towards black side)
            // 0 = N
            // 1 = NE
            // 2 = E
            // 3 = SE
            // 4 = S
            // 5 = SW
            // 6 = W
            // 7 = NW

            ArrayList<Square> squareList = new ArrayList<>();

            if (direction == 0) {
                if (r < 7) {
                    for (int i = 1 ; i <= 7 - r ; i++) {
                        int piece = chessBoard[r + i][c];
                        Square square = new Square(piece, r + i, c);
                        squareList.add(square);
                        if (piece != 0) {
                            return squareList;
                        }
                    }
                }
            }
            if (direction == 1) {
                if (r < 7 && c < 7) {  // POTENTIALLY (it is) REDUNDANT (the if statement)
                    for (int i = 1 ; i <= 7 - r && i <= 7 - c ; i++) {
                        int piece = chessBoard[r + i][c + i];
                        Square square = new Square(piece, r + i, c + i);
                        squareList.add(square);
                        if (piece != 0) {
                            return squareList;
                        }
                    }
                }
            }
            if (direction == 2) {
                if (c < 7) {
                    for (int i = 1 ; i <= 7 - c ; i++) {
                        int piece = chessBoard[r][c + i];
                        Square square = new Square(piece, r, c + i);
                        squareList.add(square);
                        if (piece != 0) {
                            return squareList;
                        }
                    }
                }
            }
            if (direction == 3) {
                if (r > 0 && c < 7) {  // POTENTIALLY (it is) REDUNDANT (the if statement)
                    for (int i = 1 ; i <= r && i <= 7 - c ; i++) {
                        int piece = chessBoard[r - i][c + i];
                        Square square = new Square(piece, r - i, c + i);
                        squareList.add(square);
                        if (piece != 0) {
                            return squareList;
                        }
                    }
                }
            }
            if (direction == 4) {
                if (r > 0) {
                    for (int i = 1 ; i <= r ; i++) {
                        int piece = chessBoard[r - i][c];
                        Square square = new Square(piece, r - i, c);
                        squareList.add(square);
                        if (piece != 0) {
                            return squareList;
                        }
                    }
                }
            }
            if (direction == 5) {
                if (r > 0 && c > 0) {  // POTENTIALLY (it is) REDUNDANT (the if statement)
                    for (int i = 1 ; i <= r && i <= c ; i++) {
                        int piece = chessBoard[r - i][c - i];
                        Square square = new Square(piece, r - i, c - i);
                        squareList.add(square);
                        if (piece != 0) {
                            return squareList;
                        }
                    }
                }
            }
            if (direction == 6) {
                if (c > 0) {
                    for (int i = 1 ; i <= c ; i++) {
                        int piece = chessBoard[r][c - i];
                        Square square = new Square(piece, r, c - i);
                        squareList.add(square);
                        if (piece != 0) {
                            return squareList;
                        }
                    }
                }
            }
            if (direction == 7) {
                if (r < 7 && c > 0) {  // POTENTIALLY (it is) REDUNDANT (the if statement)
                    for (int i = 1 ; i <= 7 - r && i <= c ; i++) {
                        int piece = chessBoard[r + i][c - i];
                        Square square = new Square(piece, r + i, c - i);
                        squareList.add(square);
                        if (piece != 0) {
                            return squareList;
                        }
                    }
                }
            }
            return squareList;
            // PLUS MEANS POSITIVE DIRECTION ALPHA AND NUMERICAL SQUARES
        }

        public static ArrayList<Square> getKnightSquares(int[][] chessBoard, int r, int c) {
            ArrayList<Square> squaresList = new ArrayList<>();

            for (int i = 0 ; i < 2 ; i++) {
                for (int j = 0 ; j < 2 ; j++) {
                    if (((r - 2 + (i*4)) >= 0) && ((r - 2 + (i*4)) <= 7) && ((c - 1 + (j*2)) >= 0) && ((c - 1 + (j*2)) <= 7)) {
                        int x = r - 2 + (i*4);
                        int y = c - 1 + (j*2);
                        int piece = chessBoard[x][y];
                        Square square = new Square(piece, x, y);
                        squaresList.add(square);
                    }
                    if (((c - 2 + (i*4)) >= 0) && ((c - 2 + (i*4)) <= 7) && ((r - 1 + (j*2)) >= 0) && ((r - 1 + (j*2)) <= 7)) {
                        int x = r - 1 + (j*2);
                        int y = c - 2 + (i*4);
                        int piece = chessBoard[x][y];
                        Square square = new Square(piece, x, y);
                        squaresList.add(square);
                    }
                }
            }
            return squaresList;
        }

        public static int[][] moveExecutor(boolean white, int[][] chessBoard, Move move) {
            int[][] newChessBoard = Arrays.stream(chessBoard)
                .map(int[]::clone) // Clone each row
                .toArray(int[][]::new);
            if (white) {
                for (int i = 0 ; i < 8 ; i++) {
                    for (int j = 0 ; j < 8 ; j++) {
                        if (newChessBoard[i][j] == W_PAWN_EP) {
                            newChessBoard[i][j] = W_PAWN;
                        }
                        if (newChessBoard[i][j] == B_PAWN_EP) {
                            newChessBoard[i][j] = B_PAWN;
                        }
                    }
                }
                if (move.getEP() == true) {
                    Square start = move.getStartSquare();
                    Square end = move.getEndSquare();
                    newChessBoard[start.getRow()][start.getColumn()] = 0;
                    newChessBoard[end.getRow()][end.getColumn()] = start.getPiece();
                    newChessBoard[end.getRow() - 1][end.getColumn()] = 0;
                }
                else if (move.getCastle() == 1) {
                    newChessBoard[0][4] = 0;
                    newChessBoard[0][7] = 0;
                    newChessBoard[0][6] = W_KING;
                    newChessBoard[0][5] = W_ROOK;
                }
                else if (move.getCastle() == 2) {
                    newChessBoard[0][4] = 0;
                    newChessBoard[0][0] = 0;
                    newChessBoard[0][2] = W_KING;
                    newChessBoard[0][3] = W_ROOK;
                }
                else {
                    Square start = move.getStartSquare();
                    Square end = move.getEndSquare();
                    newChessBoard[start.getRow()][start.getColumn()] = 0;
                    newChessBoard[end.getRow()][end.getColumn()] = start.getPiece();
                }
            }
            else {
                for (int i = 0 ; i < 8 ; i++) {
                    for (int j = 0 ; j < 8 ; j++) {
                        if (newChessBoard[i][j] == W_PAWN_EP) {
                            newChessBoard[i][j] = W_PAWN;
                        }
                        if (newChessBoard[i][j] == B_PAWN_EP) {
                            newChessBoard[i][j] = B_PAWN;
                        }
                    }
                }
                if (move.getEP() == true) {
                    Square start = move.getStartSquare();
                    Square end = move.getEndSquare();
                    newChessBoard[start.getRow()][start.getColumn()] = 0;
                    newChessBoard[end.getRow()][end.getColumn()] = start.getPiece();
                    newChessBoard[end.getRow() + 1][end.getColumn()] = 0;
                }
                else if (move.getCastle() == 1) {
                    newChessBoard[7][4] = 0;
                    newChessBoard[7][7] = 0;
                    newChessBoard[7][6] = B_KING;
                    newChessBoard[7][5] = B_ROOK;
                }
                else if (move.getCastle() == 2) {
                    newChessBoard[7][4] = 0;
                    newChessBoard[7][0] = 0;
                    newChessBoard[7][2] = B_KING;
                    newChessBoard[7][3] = B_ROOK;
                }
                else {
                    Square start = move.getStartSquare();
                    Square end = move.getEndSquare();
                    newChessBoard[start.getRow()][start.getColumn()] = 0;
                    newChessBoard[end.getRow()][end.getColumn()] = start.getPiece();
                }
            }
            return newChessBoard;
        }

        public static String boardToString(int[][] chessBoard) {
            String board = "";
            for (int i = 7 ; i >= 0 ; i--) {
                for (int j = 0 ; j < 8 ; j++) {
                    int p = chessBoard[i][j];
                    String c = "  ";
                    if (p == 1 || p == 7) {
                        c = " P";
                    }
                    if (p == 2) {
                        c = " Q";
                    }
                    if (p == 3) {
                        c = " B";
                    }
                    if (p == 4) {
                        c = " N";
                    }
                    if (p == 5 || p == 6) {
                        c = " R";
                    }
                    if (p == 8 || p == 9) {
                        c = " K";
                    }
                    if (p == -1 || p == -7) {
                        c = "~p";
                    }
                    if (p == -2) {
                        c = "~q";
                    }
                    if (p == -3) {
                        c = "~b";
                    }
                    if (p == -4) {
                        c = "~n";
                    }
                    if (p == -5 || p == -6) {
                        c = "~r";
                    }
                    if (p == -8 || p == -9) {
                        c = "~k";
                    }
                    board += "[" + c + " ] ";
                }
                board += "\n";
            }
            return board;
        }
        public static double staticEvaluator(boolean white, int[][] chessBoard) {
            Instant start = Instant.now();
            if (!hasValidMoves(white, chessBoard)) {
                if (white) {
                    if (!isWhiteKingSafe(chessBoard)) {
                        return -1000.0;
                    }
                    else {
                        return 0.0;
                    }
                }
                else {
                    if (!isBlackKingSafe(chessBoard)) {
                        return 1000.0;
                    }
                    else {
                        return 0.0;
                    }
                }
            }
            /* 
            if (!isWhiteKingSafe(chessBoard)) {
                if (getValidMoves(true, chessBoard).isEmpty()) {
                    return -1000.0;
                }
            }
            if (!isBlackKingSafe(chessBoard)) {
                if (getValidMoves(false, chessBoard).isEmpty()) {
                    return 1000.0;
                }
            }*/
            
            int mScore = 0;
            int eScore = 0;
            int mScoreW = 0;
            int eScoreW = 0;
            int mScoreB = 0;
            int eScoreB = 0;
            int gamephase = 0;
            // make the score proportional to the remaining material to discourage trading when behind
            for (int i = 0 ; i < 8 ; i++) {
                for (int j = 0 ; j < 8 ; j++) {
                    if (chessBoard[i][j] == W_PAWN || chessBoard[i][j] == W_PAWN_EP) {
                        mScoreW += mg_value[0] + mg_pesto_table[0][(7-i)*8 + j];
                        eScoreW += eg_value[0] + eg_pesto_table[0][(7-i)*8 + j];
                    }
                    if (chessBoard[i][j] == W_KNIGHT) {
                        mScoreW += mg_value[1] + mg_pesto_table[1][(7-i)*8 + j];
                        eScoreW += eg_value[1] + eg_pesto_table[1][(7-i)*8 + j];
                        gamephase += 1;
                    }
                    if (chessBoard[i][j] == W_BISHOP) {
                        mScoreW += mg_value[2] + mg_pesto_table[2][(7-i)*8 + j];
                        eScoreW += eg_value[2] + eg_pesto_table[2][(7-i)*8 + j];
                        gamephase += 1;
                    }
                    if (chessBoard[i][j] == W_ROOK || chessBoard[i][j] == W_ROOK_C) {
                        mScoreW += mg_value[3] + mg_pesto_table[3][(7-i)*8 + j];
                        eScoreW += eg_value[3] + eg_pesto_table[3][(7-i)*8 + j];
                        gamephase += 2;
                    }
                    if (chessBoard[i][j] == W_QUEEN) {
                        mScoreW += mg_value[4] + mg_pesto_table[4][(7-i)*8 + j];
                        eScoreW += eg_value[4] + eg_pesto_table[4][(7-i)*8 + j];
                        gamephase += 4;
                    }
                    if (chessBoard[i][j] == W_KING_C || chessBoard[i][j] == W_KING) {
                        mScoreW += mg_pesto_table[5][(7-i)*8 + j];
                        eScoreW += eg_pesto_table[5][(7-i)*8 + j];
                    }
                    if (chessBoard[i][j] == B_PAWN || chessBoard[i][j] == B_PAWN_EP) {
                        mScoreB += mg_value[0] + mg_pesto_table[0][i*8 + j];
                        eScoreB += eg_value[0] + eg_pesto_table[0][i*8 + j];
                    }
                    if (chessBoard[i][j] == B_KNIGHT) {
                        mScoreB += mg_value[1] + mg_pesto_table[1][i*8 + j];
                        eScoreB += eg_value[1] + eg_pesto_table[1][i*8 + j];
                        gamephase += 1;
                    }
                    if (chessBoard[i][j] == B_BISHOP) {
                        mScoreB += mg_value[2] + mg_pesto_table[2][i*8 + j];
                        eScoreB += eg_value[2] + eg_pesto_table[2][i*8 + j];
                        gamephase += 1;
                    }
                    if (chessBoard[i][j] == B_ROOK || chessBoard[i][j] == B_ROOK_C) {
                        mScoreB += mg_value[3] + mg_pesto_table[3][i*8 + j];
                        eScoreB += eg_value[3] + eg_pesto_table[3][i*8 + j];
                        gamephase += 2;
                    }
                    if (chessBoard[i][j] == B_QUEEN) {
                        mScoreB += mg_value[4] + mg_pesto_table[4][i*8 + j];
                        eScoreB += eg_value[4] + eg_pesto_table[4][i*8 + j];
                        gamephase += 4;
                    }
                    if (chessBoard[i][j] == B_KING_C || chessBoard[i][j] == B_KING) {
                        mScoreB += mg_value[5] + mg_pesto_table[5][i*8 + j];
                        eScoreB += eg_value[5] + eg_pesto_table[5][i*8 + j];
                    }
                }
            }
            mScore = mScoreW - mScoreB;
            eScore = eScoreW - eScoreB;
            int mPhase = gamephase;
            if (mPhase > 24) mPhase = 24;
            int ePhase = 24 - mPhase;
            double score;
            if (mScore * mPhase + eScore * ePhase > 0) {
                score = (mScore * mPhase + eScore * ePhase) / ((mScoreB / 100) + 2400.0);
            }
            else {
                score = (mScore * mPhase + eScore * ePhase) / ((mScoreW / 100) + 2400.0);
            }
            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            evaluationTime += duration.toMillis();
            return score;
        }

        public static int getGamephase(int[][] chessBoard) {
            int gamephase = 0;

            for (int i = 0 ; i < 8 ; i++) {
                for (int j = 0 ; j < 8 ; j++) {
                    if (chessBoard[i][j] == W_KNIGHT) {
                        gamephase += 1;
                    }
                    if (chessBoard[i][j] == W_BISHOP) {
                        gamephase += 1;
                    }
                    if (chessBoard[i][j] == W_ROOK || chessBoard[i][j] == W_ROOK_C) {
                        gamephase += 2;
                    }
                    if (chessBoard[i][j] == W_QUEEN) {
                        gamephase += 4;
                    }
                    if (chessBoard[i][j] == B_KNIGHT) {
                        gamephase += 1;
                    }
                    if (chessBoard[i][j] == B_BISHOP) {
                        gamephase += 1;
                    }
                    if (chessBoard[i][j] == B_ROOK || chessBoard[i][j] == B_ROOK_C) {
                        gamephase += 2;
                    }
                    if (chessBoard[i][j] == B_QUEEN) {
                        gamephase += 4;
                    }
                }
            }
            return gamephase;
        }

        public static boolean isThreefold(Stack<int[][]> chessStack) {
            int[][] newBoard = chessStack.peek();
            Iterator<int[][]> iter;
            int count;/* 
            if (move.getStartSquare().getPiece() > 0) {
                newBoard = moveExecutor(true, chessStack.peek(), move);
            }
            else {
                newBoard = moveExecutor(false, chessStack.peek(), move);
            }
            */
            iter = chessStack.iterator();
            count = 0;
            while (iter.hasNext() && count < 3) {
                int[][] currBoard = iter.next();
                boolean seen = true;
                for (int i = 0 ; i < 8 && seen ; i++) {
                    for (int j = 0 ; j < 8 && seen ; j++) {
                        if (((currBoard[i][j] == W_PAWN_EP) || (currBoard[i][j] == W_PAWN)) ^ ((newBoard[i][j] == W_PAWN_EP) || (newBoard[i][j] == W_PAWN))) {
                            seen = false;
                        }
                        else if (((currBoard[i][j] == W_ROOK_C) || (currBoard[i][j] == W_ROOK)) ^ ((newBoard[i][j] == W_ROOK_C) || (newBoard[i][j] == W_ROOK))) {
                            seen = false;
                        }
                        else if (((currBoard[i][j] == W_KING_C) || (currBoard[i][j] == W_KING)) ^ ((newBoard[i][j] == W_KING_C) || (newBoard[i][j] == W_KING))) {
                            seen = false;
                        }
                        else if (((currBoard[i][j] == B_PAWN_EP) || (currBoard[i][j] == B_PAWN)) ^ ((newBoard[i][j] == B_PAWN_EP) || (newBoard[i][j] == B_PAWN))) {
                            seen = false;
                        }
                        else if (((currBoard[i][j] == B_ROOK_C) || (currBoard[i][j] == B_ROOK)) ^ ((newBoard[i][j] == B_ROOK_C) || (newBoard[i][j] == B_ROOK))) {
                            seen = false;
                        }
                        else if (((currBoard[i][j] == B_KING_C) || (currBoard[i][j] == B_KING)) ^ ((newBoard[i][j] == B_KING_C) || (newBoard[i][j] == B_KING))) {
                            seen = false;
                        }
                        else if (currBoard[i][j] != newBoard[i][j]) {
                            seen = false;
                        }
                        /*if (currBoard[i][j] == W_ROOK_C) {
                            if (W_ROOK != newBoard[i][j]) {
                                seen = false;
                            }
                        }
                        if (currBoard[i][j] == W_KING_C) {
                            if (W_KING != newBoard[i][j]) {
                                seen = false;
                            }
                        }
                        if (currBoard[i][j] == B_PAWN_EP) {
                            if (B_PAWN != newBoard[i][j]) {
                                seen = false;
                            }
                        }
                        if (currBoard[i][j] == B_ROOK_C) {
                            if (B_ROOK != newBoard[i][j]) {
                                seen = false;
                            }
                        }
                        if (currBoard[i][j] == B_KING_C) {
                            if (B_KING != newBoard[i][j]) {
                                seen = false;
                            }
                        }*/
                    }
                }
                if (seen) {
                    count++;
                }
            }
            if (count >= 3) {
                System.out.println("oh");
                return true;
            }
            return false;
        }

        public static ArrayList<Move> moveSorter(boolean white, ArrayList<Move> moves, int[][] chessBoard) {
            for (Move m: moves) {
                m.initStaticScore(chessBoard);
            }
            if (white) {
                moves.sort(Comparator.comparingDouble(Move::getStaticScore).reversed());
            }
            else {
                moves.sort(Comparator.comparingDouble(Move::getStaticScore));
            }
            
            return moves;

        }

        
        public static ScoredMove depthEvaluator(boolean white, int depthDelta, int depth, double alpha, double beta, Stack<int[][]> chessStack) throws Exception {
            nodesCount++;
            // call method - white's turn, 0 depth
            // if depth is odd, its blacks turn
            int[][] chessBoard = chessStack.peek();
            boolean extension = false;
            if (depth == 0) {
                if (depthDelta > 1 && (!isWhiteKingSafe(chessBoard) || !isBlackKingSafe(chessBoard))) {
                    //System.out.println("stalemate!");
                    //System.out.println(boardToString(chessBoard));
                    depth += 2;
                    depthDelta -= 2;
                    extension = true;
                }
                if (!extension) {
                    return new ScoredMove(null, staticEvaluator(white, chessBoard));
                }
            }
            if (isThreefold(chessStack)) {
                if (getGamephase(chessBoard) < 18) {
                    return new ScoredMove(null, 0.0, true);
                }
                else if (white) {
                    return new ScoredMove(null, 3.0, true);
                }
                else {
                    return new ScoredMove(null, -3.0, true);
                }
                
            }
            if (white) { // even number means get white moves?
                ArrayList<Move> moves = ChessRules.ChessUtil.getValidMoves(true, chessBoard);
                if (moves.isEmpty()) {
                    if (isWhiteKingSafe(chessBoard)) {
                        //System.out.println("stalemate!");
                        //System.out.println(boardToString(chessBoard));

                        return new ScoredMove(null, 0);
                    }
                    else {
                        //System.out.println("checkmate");
                        //System.out.println(boardToString(chessBoard));
                        return new ScoredMove(true, null, -1000);
                    }
                }/* 
                if (depth == 4) {
                    ScoredMove nm = new ScoredMove(null, depthEvaluator(!white, depthDelta, depth - 3, alpha, beta, chessStack).getScore());
                    if (nm.getScore() > beta + 0.50) {
                        depth -= 2;
                    }
                }*/
                ScoredMove best = null;
                for (Move m : moves) {
                    totalCount++;
                    
                    
                    if (alpha < beta) {
                        
                        ScoredMove sm;

                        chessStack.add(moveExecutor(white, chessBoard, m));
                        ScoredMove de = depthEvaluator(!white, depthDelta, depth - 1, alpha, beta, chessStack);
                        sm = new ScoredMove(m, de.getScore());
                        if (de.getHasMate() == true) {
                            sm.setHasMate(true);
                        }
                        if (de.getScore() > 999 || de.getScore() < -999) {
                            int mate = de.getMate();
                            sm.setMate(mate);
                            sm.countMate();
                        }
                        chessStack.pop();
                        
                        if (sm.getScore() > alpha) {
                            alpha = sm.getScore();
                        }
                        if (best == null) {
                            best = sm;
                        }
                        if (sm.getScore() > 999) {
                            if (best.getHasMate() == true) {
                                if (sm.getMate() <= best.getMate()) {
                                    best = sm;
                                }
                            }
                            else {
                                best = sm;
                                alpha = best.getScore();
                            }
                        }
                        else if (sm.getScore() > best.getScore()) {
                            best = sm;
                            alpha = best.getScore();
                        }
                        
                    }
                    else {
                        pruneCount++;
                        //System.out.println("prune");
                        break;
                    }
                }
                //System.out.println(best.getMove().toString() + depth);
                if (best == null) {
                    throw new Exception();
                }
                return best;
            }
            else {
                totalCount++;
                ArrayList<Move> moves = ChessRules.ChessUtil.getValidMoves(false, chessBoard);
                if (moves.isEmpty()) {
                    if (isBlackKingSafe(chessBoard)) {
                        //System.out.println("stalemate");
                        //System.out.println(boardToString(chessBoard));
                        return new ScoredMove(null, 0);
                    }
                    else {
                        //System.out.println("checkmate");
                        //System.out.println(boardToString(chessBoard));
                        return new ScoredMove(true, null, 1000);
                    }
                }
/* 
                if (depth == 4) {
                    ScoredMove nm = new ScoredMove(null, depthEvaluator(!white, depthDelta, depth - 3, alpha, beta, chessStack).getScore());
                    if (nm.getScore() < alpha - 0.50) {
                        depth -= 2;
                    }
                }*/
                ScoredMove best = null;
                for (Move m : moves) {
                    if (alpha < beta) {
                        
                        
                        
                        ScoredMove sm;
                    

                        chessStack.add(moveExecutor(white, chessBoard, m));
                        ScoredMove de = depthEvaluator(!white, depthDelta, depth - 1, alpha, beta, chessStack);
                        sm = new ScoredMove(m, de.getScore());
                        if (de.getHasMate() == true) {
                            sm.setHasMate(true);
                        }
                        if (de.getScore() > 999 || de.getScore() < -999) {
                            int mate = de.getMate();
                            sm.setMate(mate);
                            sm.countMate();
                        }
                        chessStack.pop();

                        
                        if (sm.getScore() < beta) {
                            beta = sm.getScore();
                        }
                        if (best == null) {
                            best = sm;
                        }
                        if (sm.getScore() < -999) {
                            if (best.getHasMate() == true) {
                                if (sm.getMate() <= best.getMate()) {
                                    best = sm;
                                }
                            }
                            else {
                                best = sm;
                                beta = best.getScore();
                            }
                        }
                        else if (sm.getScore() < best.getScore()) {
                            best = sm;
                            beta = best.getScore();
                        }
                    }
                    else {
                        pruneCount++;
                        //System.out.println(depth + "prune");
                        
                        break;
                    }
                }
                //System.out.println(best.getMove().toString() + depth);
                return best;
            }




            
        }
    }
}
