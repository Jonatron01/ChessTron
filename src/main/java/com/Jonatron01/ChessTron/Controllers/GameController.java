package com.Jonatron01.ChessTron.Controllers;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Jonatron01.ChessTron.ChessGame;
import com.Jonatron01.ChessTron.ChessRules;
import com.Jonatron01.ChessTron.Move;
import com.Jonatron01.ChessTron.WebMove;

import jakarta.servlet.http.HttpSession;

@RestController
public class GameController {
    @PostMapping("/webMove")
    public boolean webMove(HttpSession session, @RequestBody Map<String, Object> webMove) {
        ChessGame chessGame = (ChessGame) session.getAttribute("Chess_Game");
        if ((boolean)webMove.get("wTurn") != chessGame.getTurn()) {
            return false;
        }
        ArrayList<Move> moves = chessGame.getMoves();
        Move chosenMove = moves.get((int)webMove.get("webMove"));
        if (chosenMove == null) {
            return false;
        }
        chessGame.addMove(chosenMove);
        chessGame.changeTurn();
        return true;
    }

    @PostMapping("/game_init")
    public boolean game_init(HttpSession session, @RequestBody Map<String, Object> body) {
        System.out.println("oh nnice");
        session.setAttribute("Chess_Game", new ChessGame((boolean)body.get("wHuman"), (boolean)body.get("bHuman")));
        ChessGame chessGame = (ChessGame) session.getAttribute("Chess_Game");
        System.out.println(ChessRules.ChessUtil.boardToString(chessGame.getBoard()));
        return true;
    }

    @GetMapping("/getWebMoves")
    public ArrayList<WebMove> validMovesWeb(HttpSession session) {
        ChessGame chessGame = (ChessGame) session.getAttribute("Chess_Game");
        return chessGame.getWebMoves();
    }

    @GetMapping("/turn")
    public boolean validturn(HttpSession session) {
        ChessGame chessGame = (ChessGame) session.getAttribute("Chess_Game");
        System.out.println(chessGame.getTurn());
        return chessGame.getTurn();
    }

    @GetMapping("/getBoard")
    public int[][] getBoard(HttpSession session) {
        ChessGame chessGame = (ChessGame) session.getAttribute("Chess_Game");
        return chessGame.getBoard();
    }

    @GetMapping("/getComputerMove")
    public int[][] getComputerMove(HttpSession session) throws Exception {
        ChessGame chessGame = (ChessGame) session.getAttribute("Chess_Game");
        chessGame.addMove(chessGame.getComputerMove(chessGame.getTurn()));
        chessGame.changeTurn();
        return chessGame.getBoard();
    }
}