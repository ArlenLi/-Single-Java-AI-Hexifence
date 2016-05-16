package lindongl;

import java.util.ArrayList;
import java.util.List;

public class Edge{
    public List<Hexagon> relativeHexagons;
    public int capturedBy;
    public int row;
    public int col;
    public Edge(int row, int col) {
        relativeHexagons = new ArrayList<Hexagon>();
        capturedBy = 0;
        this.row = row;
        this.col = col;
    }

    public Edge(){};

    public void addHexagon(Hexagon hex) {
        relativeHexagons.add(hex);
    }
}