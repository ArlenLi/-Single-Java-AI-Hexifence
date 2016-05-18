package aiproj.hexifence.lindongl;


import java.io.*;
import java.util.*;


import java.io.*;
import java.util.*;

public class Lindongl implements Player, Piece {
    public int piece;
    public Board board;
    public boolean invalid = false;
    public int opponentPiece;
    public int moveNum;
    public int firstStage;
    public int secondStage;
    public Map<String, Double> historyTableofTwo = new HashMap<String, Double>();
    public Map<String, Double> historyTableofThree = new HashMap<String, Double>();
    //public double[] weights = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, -5.0, 6.0};

    public double[] weights;

    public List<GameStatus> gamedata;


    public int init(int n, int p){
        if(n == 2 || n == 3) {
            if(p == 1 || p == 2) {
                weights = new double[8];
                if (n == 2) {
                    firstStage = 3;
                    secondStage = 10;
                } else {
                    firstStage = 4;
                    secondStage = 36;
                }
                piece = p;
                board = new Board(n);
                board.print();
                if(piece == 1) {
                    opponentPiece = 2;
                } else {
                    opponentPiece = 1;
                }
//                try {
                    if (n == 2) {
                        for(Map.Entry<String, Edge> entry: board.edgesMap.entrySet()) {
                            historyTableofTwo.put(entry.getKey(), 0.0);
                        }
                    }else {
                        for(Map.Entry<String, Edge> entry: board.edgesMap.entrySet()) {
                            historyTableofThree.put(entry.getKey(), 0.0);
                        }
                    }
//                    if (piece == 1) {
//                        File file = new File("weightsForBlue.txt");
//                        if(!file.exists()){
//                            pln("There is no weighte files.");
//                            System.exit(1);
//                        }
//                        FileReader fr = new FileReader(file);
//                        BufferedReader br = new BufferedReader(fr);
//                        String line = br.readLine();
//                        String[] results = line.split(" ");
//                        for (int i = 0; i < results.length; i++) {
//                            weights[i] = Double.parseDouble(results[i]);
//                        }
//                    } else {
//                        File file = new File("weightsForRed.txt");
//                        if(!file.exists()){
//                            pln("There is no weighte files.");
//                            System.exit(1);
//                        }
//                        FileReader fr = new FileReader(file);
//                        BufferedReader br = new BufferedReader(fr);
//                        String line = br.readLine();
//                        String[] results = line.split(" ");
//                        for (int i = 0; i < results.length; i++) {
//                            weights[i] = Double.parseDouble(results[i]);
//                        }
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
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
            if(board.hexagonsWithFiveCapturedEdges.size() != 0) {
                Hexagon hex = board.hexagonsWithFiveCapturedEdges.get(0);
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
            }
        } else if (moveNum <= secondStage && moveNum > firstStage) {
            double[] result = minimaxWithHistoryHeuristics(3, piece, Integer.MIN_VALUE, Integer.MAX_VALUE);
            move.Row = (int)result[1];
            move.Col = (int)result[2];
        } else{
            double[] result = minimaxWithHistoryHeuristics(Integer.MAX_VALUE, piece, Integer.MIN_VALUE, Integer.MAX_VALUE);
            move.Row = (int)result[1];
            move.Col = (int)result[2];
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
                if(board.hexagonsCapturedByBlue > board.hexagonsCapturedByRed) {
                    return 1;
                } else if (board.hexagonsCapturedByBlue < board.hexagonsCapturedByRed) {
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
//    public int[] minimax(int depth, int player, int alpha, int beta) {
//        Map<String, Edge> currentAvailableEdges = getCurrentAvailableEdges();
//        Map<String, Integer> bestActionFeatures = new HashMap<String, Integer>();
//        int score;
//        int bestRow = -1;
//        int bestCol = -1;
//        // evaluate the score when game is over or depth is reached
//        if(board.emptyEdges.size() == 0 || depth == 0) {
//            score = evaluate();
//            //pln(score);
//            return new int[] {score, bestRow, bestCol};
//        } else {
//            // try this move for the current player
//            for(Map.Entry<String, Edge> entry: currentAvailableEdges.entrySet()) {
//                String key = entry.getKey();
//                Edge edge = entry.getValue();
//                board.captureOneEdge(key, player);
//                if(player == piece) {
//                    // piece is the maximizing palyer
//                    score = minimax(depth -1, opponentPiece, alpha, beta)[0];
//                    if(score > alpha) {
//                        alpha = score;
//                        bestRow = edge.row;
//                        bestCol = edge.col;
//                        bestActionFeatures = board.variousHexagonsNum;
//                    }
//                } else {
//                    // opponentPiece is minimizing player
//                    score = minimax(depth - 1, piece, alpha, beta)[0];
//                    if (score < beta) {
//                        beta = score;
//                        bestRow = edge.row;
//                        bestCol = edge.col;
//                    }
//                }
//                // undo move
//                board.releaseOneEdge(key,player);
//                // cut-off
//                if (alpha >= beta)
//                    break;
//            }
////            pln(alpha);
////            for (Map.Entry<String, Integer> entry: bestActionFeatures.entrySet()) {
////                System.out.println(entry.getKey() + ": " + entry.getValue());
////            }
//            return new int[] {(player == piece)?alpha : beta, bestRow, bestCol};
//        }
//    }

    //alpha-beta prunning algorithm
    public double[] minimaxWithHistoryHeuristics(int depth, int player, double alpha, double beta) {
        Map<String, Edge> currentAvailableEdges = getCurrentAvailableEdges();
        Map<String, Double> sortedMap = new HashMap<String, Double>();
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>();
        if(board.dimension == 2) {
            for (Map.Entry<String, Edge> entry : currentAvailableEdges.entrySet()) {
                sortedMap.put(entry.getKey(), historyTableofTwo.get(entry.getKey()));
            }
//            pln(count);
            // sorting the tempMapTweeter to get the top10 most mentioned tweeter
            list.addAll(sortedMap.entrySet());
            Lindongl.ValueComparator vc = new ValueComparator();
            Collections.sort(list, vc);
        } else {
            for (Map.Entry<String, Edge> entry : currentAvailableEdges.entrySet()) {
                sortedMap.put(entry.getKey(), historyTableofThree.get(entry.getKey()));
            }
            // sorting the tempMapTweeter to get the top10 most mentioned tweeter
            list.addAll(sortedMap.entrySet());
            Lindongl.ValueComparator vc = new ValueComparator();
            Collections.sort(list, vc);
        }
        double score;
        double bestRow = -1;
        double bestCol = -1;
        // evaluate the score when game is over or depth is reached
        if(board.emptyEdges.size() == 0 || depth == 0) {
            score = evaluate();
            return new double[] {score, bestRow, bestCol};
        } else {
            // try this move for the current player
            for(Map.Entry<String, Double> mapping : list) {
                String key = mapping.getKey();
                Edge edge = currentAvailableEdges.get(key);
                board.captureOneEdge(key, player);
                if(player == piece) {
                    // piece is the maximizing palyer
                    double[] result = minimaxWithHistoryHeuristics(depth -1, opponentPiece, alpha, beta);
                    score = result[0];
                    if(score > alpha) {
                        alpha = score;
                        bestRow = edge.row;
                        bestCol = edge.col;
//                        for (int i = 0; i < bestActionFeatures.length; i++) {
//                            bestActionFeatures[i] = result[i + 3];
//                        }
                    }
                } else {
                    // opponentPiece is minimizing player
                    score = minimaxWithHistoryHeuristics(depth - 1, piece, alpha, beta)[0];
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
            if (board.dimension == 2){
                if(bestRow != -1)
                    historyTableofTwo.put((String.valueOf(bestRow) + String.valueOf(bestCol)), (historyTableofTwo.get((String.valueOf((int)bestRow) + String.valueOf((int)bestCol))) + Math.pow(2, depth)));
            }
            if (board.dimension == 3){
                if(bestRow != -1)
                    historyTableofThree.put(String.valueOf(bestRow) + String.valueOf(bestCol), (historyTableofThree.get(String.valueOf((int)bestRow) + String.valueOf((int)bestCol)) + Math.pow(2, depth)));
            }

            return new double[] {(player == piece)?alpha : beta, bestRow, bestCol};
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
    public double evaluate() {
        double score;
        if (piece == 1){
            score = weights[6] * (board.variousHexagonsNum.get("61") - board.variousHexagonsNum.get("62"));
        } else {
            score = weights[6] * (board.variousHexagonsNum.get("62") - board.variousHexagonsNum.get("61"));
        }
        return weights[0] * board.variousHexagonsNum.get("0")
                + weights[1] * board.variousHexagonsNum.get("1")
                + weights[2] * board.variousHexagonsNum.get("2")
                + weights[3] * board.variousHexagonsNum.get("3")
                + weights[4] * board.variousHexagonsNum.get("4")
                - weights[5] * board.variousHexagonsNum.get("5")
                + score;
    }

    private static class ValueComparator implements Comparator<Map.Entry<String,Double>>
    {
        public int compare(Map.Entry<String,Double> m,Map.Entry<String,Double> n) {
            double result = n.getValue() - m.getValue();
            if (result > 0)
                return 1;
            else if (result == 0)
                return 0;
            else
                return -1;
        }
    }

//    public void updateWeights() {
//        double firstSum;
//        double secondSum;
//        for (int i = 0; i < weights.length; i++) {
//            firstSum = 0;
//            for(int j = 0; j < gamedata.size() - 1; j++) {
//                secondSum = 0;
//                for(int m = 0; m < gamedata.size() - 1; m++) {
//                    secondSum = secondSum + (Math.tanh(gamedata.get(m + 1).value) - Math.tanh(gamedata.get(m).value)) * Math.pow(lamda, m - j);
//                }
//                if(i == 6) {
//                    int feature;
//                    if(piece == 1) {
//                        feature = gamedata.get(j).features[6] - gamedata.get(j).features[7];
//                    } else {
//                        feature = gamedata.get(j).features[7] - gamedata.get(j).features[6];
//                    }
//                    firstSum = firstSum + (1 - Math.pow(Math.tanh(gamedata.get(j).value), 2)) * feature * secondSum;
//                } else {
//                    firstSum = firstSum + (1 - Math.pow(Math.tanh(gamedata.get(j).value), 2)) * gamedata.get(j).features[i] * secondSum;
//                }
//            }
//            weights[i] = weights[i] +  (delta * firstSum);
//        }
//        try {
//            if (piece == 1) {
//                File file = new File("weightsForBlue.txt");
//                if (!file.exists()) {
//                    file.createNewFile();
//                } else {
//                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
//                    String line = "";
//                    for (int i = 0; i < weights.length; i++) {
//                        if(i != 0) {
//                            line = line + " " + weights[i];
//                        } else {
//                            line = line + weights[i];
//                        }
//                    }
//                    out.write(line);
//                    out.flush();
//                    out.close();
//                }
//            } else {
//                File file = new File("weightsForRed.txt");
//                if (!file.exists()) {
//                    file.createNewFile();
//                } else {
//                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
//                    String line = "";
//                    for (int i = 0; i < weights.length; i++) {
//                        if(i != 0) {
//                            line = line + " " + weights[i];
//                        } else {
//                            line = line + weights[i];
//                        }
//                    }
//                    out.write(line);
//                    out.flush();
//                    out.close();
//                }
//            }
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



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