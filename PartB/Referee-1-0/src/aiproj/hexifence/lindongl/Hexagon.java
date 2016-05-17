package aiproj.hexifence.lindongl;

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
        board.variousHexagonsNum.put("0", board.variousHexagonsNum.get("0") + 1);
    }

    public int oneEdgeCaptured(int p){
        capturedEdgeNum++;
        if(capturedEdgeNum == 5) {
            board.hexagonsWithFiveCapturedEdges.add(this);
        }
        if(capturedEdgeNum == 6) {
            board.availableHexagonNum--;
            if (p == 1) {
                board.variousHexagonsNum.put("5", (board.variousHexagonsNum.get("5") - 1));
                board.variousHexagonsNum.put("61", (board.variousHexagonsNum.get("61") + 1));
                board.configuration[row][col] = 'b';
                board.hexagonsCapturedByBlue++;
                return 1;
            } else {
                board.hexagonsCapturedByRed++;
                board.variousHexagonsNum.put("5", (board.variousHexagonsNum.get("5") - 1));
                board.variousHexagonsNum.put("62", (board.variousHexagonsNum.get("62") + 1));
                board.configuration[row][col] = 'r';
                return 2;
            }
        }
        board.variousHexagonsNum.put(String.valueOf(capturedEdgeNum - 1), (board.variousHexagonsNum.get(String.valueOf(capturedEdgeNum - 1)) - 1));
        board.variousHexagonsNum.put(String.valueOf(capturedEdgeNum), (board.variousHexagonsNum.get(String.valueOf(capturedEdgeNum)) + 1));
        return 0;
    }

    public void oneEdgeReleased(int p) {
        capturedEdgeNum--;
        if(capturedEdgeNum == 5 ) {
            board.availableHexagonNum++;
            if(p == 1) {
                board.variousHexagonsNum.put("5", (board.variousHexagonsNum.get("5") + 1));
                board.variousHexagonsNum.put("61", (board.variousHexagonsNum.get("61") - 1));
                board.hexagonsCapturedByBlue--;
                board.configuration[row][col] = '-';
            } else {
                board.variousHexagonsNum.put("5", (board.variousHexagonsNum.get("5") + 1));
                board.variousHexagonsNum.put("62", (board.variousHexagonsNum.get("62") - 1));
                board.hexagonsCapturedByRed--;
                board.configuration[row][col] = '-';
            }
        } else {
            board.variousHexagonsNum.put(String.valueOf(capturedEdgeNum + 1), (board.variousHexagonsNum.get(String.valueOf(capturedEdgeNum + 1)) - 1));
            board.variousHexagonsNum.put(String.valueOf(capturedEdgeNum), (board.variousHexagonsNum.get(String.valueOf(capturedEdgeNum)) + 1));
        }

    }



}