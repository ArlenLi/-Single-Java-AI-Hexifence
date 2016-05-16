package lindongl;

import aiproj.hexifence.Move;
import aiproj.hexifence.Piece;
import aiproj.hexifence.Player;

import java.io.PrintStream;
import java.io.StringReader;
import java.util.*;

public class Lindongl implements Player, Piece {
    public int piece;
    public Board board;
    public boolean invalid = false;
    public int opponentPiece;
    public int moveNum;
    public int firstStage;
    public int secondStage;
    public int finalStage;

    public int init(int n, int p){
        if(n == 2 || n == 3) {
            if(p == 1 || p == 2) {
                if (n == 2) {
                    firstStage = 5;
                    secondStage = 10;
                    finalStage = 15;
                } else {
                    firstStage = 12;
                    secondStage = 30;
                    finalStage =6;
                }
                piece = p;
                board = new Board(n);
                board.print();
                if(piece == 1) {
                    opponentPiece = 2;
                } else {
                    opponentPiece = 1;
                }
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public Move makeMove(){
        // beginning part
        // at the beginning part(for 2-dimension, it is 5 move for my player, for 3-dimension, it is 12 move for my player.)
        // The move will be random, but the player would not choose one edge resulting in 5 captured edges in one hexagon and
        // if there is a hexagon that already have 5 captured edges, my player would definitely choose that empty edge.
        moveNum++;
        Move move = new Move();
        move.P = piece;
        if(moveNum <= firstStage) {
            if(Board.hexagonsWithFiveCapturedEdges.size() != 0) {
                Hexagon hex = Board.hexagonsWithFiveCapturedEdges.get(0);
                int col = hex.col;
                int row = hex.row;
                if(board.edgesMap.get(String.valueOf(col - 1) + String.valueOf(row - 1)).capturedBy == 0) {
                    move.Col = col - 1;
                    move.Row = row - 1;
//                    return move;
                }

                if(board.edgesMap.get(String.valueOf(col - 1) + String.valueOf(row)).capturedBy == 0) {
                    move.Col = col - 1;
                    move.Row = row;
//                    return move;
                }

                if(board.edgesMap.get(String.valueOf(col) + String.valueOf(row - 1)).capturedBy == 0) {
                    move.Col = col;
                    move.Row = row - 1;
//                    return move;
                }

                if(board.edgesMap.get(String.valueOf(col - 1) + String.valueOf(row - 1)).capturedBy == 0) {
                    move.Col = col;
                    move.Row = row + 1;
//                    return move;
                }

                if(board.edgesMap.get(String.valueOf(col - 1) + String.valueOf(row - 1)).capturedBy == 0) {
                    move.Col = col + 1;
                    move.Row = row;
//                    return move;
                }

                if(board.edgesMap.get(String.valueOf(col - 1) + String.valueOf(row - 1)).capturedBy == 0) {
                    move.Col = col + 1;
                    move.Row = row + 1;
//                    return move;
                }
            }else {
                Random random = new Random();
                boolean flag = true;
                int index;
                Edge edge = new Edge();
                while(flag) {
                    List list = new ArrayList(board.emptyEdges.values());
                    index = random.nextInt(list.size());
                    edge = (Edge)list.get(index);
                    boolean flag2 = false;
                    for (int i = 0; i < edge.relativeHexagons.size(); i++) {
                        if(edge.relativeHexagons.get(i).capturedEdgeNum == 4) {
                            flag2 = true;
                        }
                    }
                    if (flag2 == false) {
                        flag = false;
                    }
                }
                move.Row = edge.row;
                move.Col = edge.col;
//                return move;
            }
        } else if (moveNum <= secondStage && moveNum > firstStage) {
            int[] result = minimax(4, piece, Integer.MIN_VALUE, Integer.MAX_VALUE);
            move.Row = result[1];
            move.Col = result[2];
//            return move;
        } else{
            int[] result = minimax(Integer.MAX_VALUE, piece, Integer.MIN_VALUE, Integer.MAX_VALUE);
            move.Row = result[1];
            move.Col = result[2];
//            return move;
        }
        board.captureOneEdge(String.valueOf(move.Row) + String.valueOf(move.Col), piece);
        return move;
    }

    public int opponentMove(Move m){
        String coordinate = String.valueOf(m.Row) + String.valueOf(m.Col);
        if(board.edgesMap.containsKey(coordinate)) {
            Edge edge = board.edgesMap.get(coordinate);
            if(edge.capturedBy == 0) {
                board.captureOneEdge(coordinate, m.P);
                for (int i = 0; i < edge.relativeHexagons.size(); i++) {
                    if(edge.relativeHexagons.get(i).capturedEdgeNum == 6){
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
            if(board.emptyEdges.size() == 0) {
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

    //alpha-beta prunning algorithm
    public int[] minimax(int depth, int player, int alpha, int beta) {
        Map<String, Edge> currentAvailableEdges = getCurrentAvailableEdges();
        int score;
        int bestRow = -1;
        int bestCol = -1;
        // evaluate the score when game is over or depth is reached
        if(board.emptyEdges.size() == 0 || depth == 0) {
            score = evaluate();
            //pln(score);
            return new int[] {score, bestRow, bestCol};
        } else {
            // try this move for the current player
            for(Map.Entry<String, Edge> entry: currentAvailableEdges.entrySet()) {
                String key = entry.getKey();
                Edge edge = entry.getValue();
                board.captureOneEdge(key, player);
                if(player == piece) {
                    // piece is the maximizing palyer
                    score = minimax(depth -1, opponentPiece, alpha, beta)[0];
                    if(score > alpha) {
                        alpha = score;
                        bestRow = edge.row;
                        bestCol = edge.col;
                    }
                } else {
                    // opponentPiece is minimizing player
                    score = minimax(depth - 1, piece, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestRow = edge.row;
                        bestCol = edge.col;
                    }
                }
                // undo move
                board.releaseOneEdge(key,player);
                // cut-off
                if (alpha >= beta)
                    break;
            }
            return new int[] {(player == piece)?alpha : beta, bestRow, bestCol};
        }
    }

    public Map<String, Edge> getCurrentAvailableEdges() {
        Map<String, Edge> currentAvailableEdges = new HashMap<String, Edge>();
        for(Map.Entry<String, Edge> entry: board.emptyEdges.entrySet()) {
            currentAvailableEdges.put(entry.getKey(), entry.getValue());
        }
        return currentAvailableEdges;
    }

    // evaluation function
    public int evaluate() {
        int score;
        if (piece == 1){
            score = 6 * Hexagon.variousHexagonsNum.get("61") - 6 * Hexagon.variousHexagonsNum.get("62");
        } else {
            score = 6 * Hexagon.variousHexagonsNum.get("62") - 6 * Hexagon.variousHexagonsNum.get("61");
        }
        return 1 * Hexagon.variousHexagonsNum.get("0")
                + 2 * Hexagon.variousHexagonsNum.get("2")
                + 3 * Hexagon.variousHexagonsNum.get("3")
                + 4 * Hexagon.variousHexagonsNum.get("4")
                - 5 * Hexagon.variousHexagonsNum.get("5")
                + score;
    }




    public static void main(String args[]) {
        Lindongl lindongl = new Lindongl();
        lindongl.init(3, 1);
//        for(Map.Entry<String, Edge> entry: lindongl.board.emptyEdges.entrySet()) {
//            String key = entry.getKey();
//            lindongl.board.captureOneEdge(entry.getKey(), 1);
//
//            System.out.println(key);
//            lindongl.board.releaseOneEdge(key, 1);
//            break;
//        }
        int a = 5;


    }

    public static void pln(Object object) {
        System.out.println(object);
    }
}