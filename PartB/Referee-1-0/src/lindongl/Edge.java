package lindongl;

import java.util.ArrayList;
import java.util.List;

public class Edge{
    public List<Hexagon> relativeHexagons;
    public int capturedBy;
    public Edge() {
        relativeHexagons = new ArrayList<Hexagon>();
        capturedBy = 0;
    }

    public void addHexagon(Hexagon hex) {
        relativeHexagons.add(hex);
    }
}