package lindongl;

public class Hexagon {
    public int availableEdgeNum;
    public char capturedBy;

    public int getAvailableEdgeNum() {
        return availableEdgeNum;
    }

    public void setAvailableEdgeNum(int availableEdgeNum) {
        this.availableEdgeNum = availableEdgeNum;
    }

    public char getCapturedBy() {
        return capturedBy;
    }

    public void setCapturedBy(char capturedBy) {
        this.capturedBy = capturedBy;
    }

    public Hexagon() {
        availableEdgeNum = 6;
        capturedBy = '-';
    }

    public void oneEdgeCaptured(int p){
        availableEdgeNum--;
        if(availableEdgeNum == 0) {
            Board.availableHexagonNum--;
            if (p == 1) {
                Board.hexagonsCapturedByBlue++;
            } else {
                Board.hexagonsCapturedByRed++;
            }
        }
    }
}