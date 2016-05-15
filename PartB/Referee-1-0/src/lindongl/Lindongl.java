package lindongl;

import aiproj.hexifence.Move;
import aiproj.hexifence.Piece;
import aiproj.hexifence.Player;

import java.io.PrintStream;
import java.io.StringReader;

public class Lindongl implements Player, Piece {
    public int piece;
    public Board board;
    public boolean invalid = false;

    public int init(int n, int p){
        if(n == 2 || n == 3) {
            if(p == 1 || p == 2) {
                piece = p;
                board = new Board(n);
                board.print();
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public Move makeMove(){
        return new Move();
    }

    public int opponentMove(Move m){
        String coordinate = String.valueOf(m.Row) + String.valueOf(m.Col);
        if(board.edgesMap.containsKey(coordinate)) {
            Edge edge = board.edgesMap.get(coordinate);
            if(edge.capturedBy == 0) {
                edge.capturedBy = m.P;
                for (int i = 0; i < edge.relativeHexagons.size(); i++) {
                    edge.relativeHexagons.get(i).oneEdgeCaptured(m.P);
                }
                for (int i = 0; i < edge.relativeHexagons.size(); i++) {
                    if(edge.relativeHexagons.get(i).availableEdgeNum == 0){
                        return 1;
                    }
                }
                return 0;
            }
        }
        invalid = true;
        return -1;
    }

    public int getWinner() {
        if (invalid) {
            return -1;
        } else {
            if(Board.availableHexagonNum == 0) {
                if(Board.hexagonsCapturedByBlue > Board.hexagonsCapturedByRed) {
                    return 1;
                } else if (Board.hexagonsCapturedByBlue < Board.hexagonsCapturedByRed) {
                    return 2;
                } else {
                    return 3;
                }
            }
        }
        return 0;

    }

    public void printBoard(PrintStream output) {
        for (int i = 0; i < board.configuration.length; i++) {
            for(int j = 0; j < board.configuration[1].length; j++) {
                output.print(board.configuration[i][j]);
                if(j != board.configuration[1].length - 1) {
                    output.print(" ");
                }
            }
            output.println();
        }
    }

    public static void main(String args[]) {
        Lindongl lindongl = new Lindongl();
        lindongl.init(3, 1);
    }

    public static void pln(Object object) {
        System.out.println(object);
    }
}