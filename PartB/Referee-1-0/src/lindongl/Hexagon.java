package lindongl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hexagon {
    public int capturedEdgeNum;
    public char capturedBy;
    public Board board;
    public int row;
    public int col;
    public static Map<String, Integer> variousHexagonsNum = new HashMap<String, Integer>();


    public char getCapturedBy() {
        return capturedBy;
    }

    public void setCapturedBy(char capturedBy) {
        this.capturedBy = capturedBy;
    }

    public Hexagon(int row, int col, Board board) {
        capturedEdgeNum = 0;
        capturedBy = '-';
        this.row = row;
        this.col = col;
        this.board = board;
        variousHexagonsNum.put("0", variousHexagonsNum.get("0") + 1);
    }

    public int oneEdgeCaptured(int p){
        capturedEdgeNum++;
        if(capturedEdgeNum == 5) {
            Board.hexagonsWithFiveCapturedEdges.add(this);
        }
        if(capturedEdgeNum == 6) {
            Board.availableHexagonNum--;
            if (p == 1) {
                variousHexagonsNum.put("5", variousHexagonsNum.get("5") - 1);
                variousHexagonsNum.put("61", variousHexagonsNum.get("61") + 1);
                board.configuration[row][col] = 'b';
                Board.hexagonsCapturedByBlue++;
                return 1;
            } else {
                Board.hexagonsCapturedByRed++;
                variousHexagonsNum.put("5", variousHexagonsNum.get("5") - 1);
                variousHexagonsNum.put("62", variousHexagonsNum.get("62") + 1);
                board.configuration[row][col] = 'r';
                return 2;
            }
        }
        variousHexagonsNum.put(String.valueOf(capturedEdgeNum - 1), variousHexagonsNum.get(String.valueOf(capturedEdgeNum - 1)) - 1);
        variousHexagonsNum.put(String.valueOf(capturedEdgeNum), variousHexagonsNum.get(String.valueOf(capturedEdgeNum)) + 1);
        return 0;
    }

    public void oneEdgeReleased(int p) {
        capturedEdgeNum--;
        if(capturedEdgeNum == 5 ) {
            Board.availableHexagonNum++;
            if(p == 1) {
                variousHexagonsNum.put("5", variousHexagonsNum.get("5") + 1);
                variousHexagonsNum.put("61", variousHexagonsNum.get("61") - 1);
                Board.hexagonsCapturedByBlue--;
                board.configuration[row][col] = '-';
            } else {
                variousHexagonsNum.put("5", variousHexagonsNum.get("5") + 1);
                variousHexagonsNum.put("62", variousHexagonsNum.get("62") - 1);
                Board.hexagonsCapturedByRed--;
                board.configuration[row][col] = '-';
            }
        } else {
            variousHexagonsNum.put(String.valueOf(capturedEdgeNum + 1), variousHexagonsNum.get(String.valueOf(capturedEdgeNum + 1)) - 1);
            variousHexagonsNum.put(String.valueOf(capturedEdgeNum), variousHexagonsNum.get(String.valueOf(capturedEdgeNum)) + 1);
        }

    }



}