package lindongl;

import java.util.*;
import lindongl.Edge;
import lindongl.Hexagon;

public class Board{
    public int dimension;    // the dimension of the board
    public char[][] configuration;   // the configuration of the board
    public int availableHexagonNum;
    public int hexagonsCapturedByRed;
    public int hexagonsCapturedByBlue;
    public List<Hexagon> hexagonsWithFiveCapturedEdges;
    public Map<String, Hexagon> hexagonsMap;
    public Map<String, Edge> edgesMap;
    public Map<String, Edge> emptyEdges;
    public Map<String, Integer> variousHexagonsNum = new HashMap<String, Integer>();


    public Board(int dimension) {
        this.dimension = dimension;
        hexagonsMap = new HashMap<String, Hexagon>();
        edgesMap = new HashMap<String, Edge>();
        emptyEdges = new HashMap<String, Edge>();
        configuration = new char[4 * dimension - 1][4 * dimension - 1];
        hexagonsWithFiveCapturedEdges = new ArrayList<Hexagon>();
        variousHexagonsNum.put("0", 0);
        variousHexagonsNum.put("1", 0);
        variousHexagonsNum.put("2", 0);
        variousHexagonsNum.put("3", 0);
        variousHexagonsNum.put("4", 0);
        variousHexagonsNum.put("5", 0);
        variousHexagonsNum.put("61", 0);
        variousHexagonsNum.put("62", 0);
        int centerX;
        int centerY;
        for (int i = 0; i < 2 * dimension - 1; i++) {
            if( i <= dimension - 1) {
                for (int j = 0; j < i + dimension; j++) {
                    centerX = 2 * i + 1;
                    centerY = 2 * j + 1;
                    configuration[centerX][centerY] = '-';
                    Hexagon tempHex = new Hexagon(centerX, centerY, this);
                    hexagonsMap.put(String.valueOf(centerX) + String.valueOf(centerY), tempHex);
                    configuration[centerX - 1][centerY - 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX - 1) + String.valueOf(centerY - 1 ))) {
                        edgesMap.get(String.valueOf(centerX -1) + String.valueOf(centerY - 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX - 1, centerY - 1);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX - 1) + String.valueOf(centerY - 1), edge);
                    }

                    configuration[centerX - 1][centerY] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX - 1) + String.valueOf(centerY))) {
                        edgesMap.get(String.valueOf(centerX - 1) + String.valueOf(centerY)).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX - 1, centerY);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX - 1) + String.valueOf(centerY), edge);
                    }

                    configuration[centerX][centerY - 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX) + String.valueOf(centerY - 1 ))) {
                        edgesMap.get(String.valueOf(centerX) + String.valueOf(centerY - 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX, centerY - 1);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX) + String.valueOf(centerY - 1), edge);
                    }

                    configuration[centerX][centerY + 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX) + String.valueOf(centerY + 1 ))) {
                        edgesMap.get(String.valueOf(centerX) + String.valueOf(centerY + 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX, centerY + 1);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX) + String.valueOf(centerY + 1), edge);
                    }

                    configuration[centerX + 1][centerY] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX + 1) + String.valueOf(centerY))) {
                        edgesMap.get(String.valueOf(centerX + 1) + String.valueOf(centerY)).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX + 1, centerY);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX + 1) + String.valueOf(centerY), edge);
                    }

                    configuration[centerX + 1][centerY + 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX + 1) + String.valueOf(centerY + 1 ))) {
                        edgesMap.get(String.valueOf(centerX + 1) + String.valueOf(centerY + 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX + 1, centerY + 1);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX + 1) + String.valueOf(centerY + 1), edge);
                    }

                }
            } else {
                for (int j = 0; j < 3 * dimension - 2 - i; j++) {
                    centerX = 2 * i + 1;
                    centerY = 2 * (i - dimension + 1) + (2 * j + 1);
                    configuration[centerX][centerY] = '-';
                    Hexagon tempHex = new Hexagon(centerX, centerY, this);
                    hexagonsMap.put(String.valueOf(centerX) + String.valueOf(centerY), tempHex);
                    configuration[centerX - 1][centerY - 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX - 1) + String.valueOf(centerY - 1 ))) {
                        edgesMap.get(String.valueOf(centerX -1) + String.valueOf(centerY - 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX - 1, centerY - 1);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX - 1) + String.valueOf(centerY - 1), edge);
                    }

                    configuration[centerX - 1][centerY] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX - 1) + String.valueOf(centerY))) {
                        edgesMap.get(String.valueOf(centerX - 1) + String.valueOf(centerY)).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX - 1, centerY);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX - 1) + String.valueOf(centerY), edge);
                    }
                    configuration[centerX][centerY - 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX) + String.valueOf(centerY - 1 ))) {
                        edgesMap.get(String.valueOf(centerX) + String.valueOf(centerY - 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX, centerY - 1);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX) + String.valueOf(centerY - 1), edge);
                    }
                    configuration[centerX][centerY + 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX) + String.valueOf(centerY + 1 ))) {
                        edgesMap.get(String.valueOf(centerX) + String.valueOf(centerY + 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX, centerY + 1);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX) + String.valueOf(centerY + 1), edge);
                    }
                    configuration[centerX + 1][centerY] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX + 1) + String.valueOf(centerY))) {
                        edgesMap.get(String.valueOf(centerX + 1) + String.valueOf(centerY)).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX + 1, centerY);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX + 1) + String.valueOf(centerY), edge);
                    }
                    configuration[centerX + 1][centerY + 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX + 1) + String.valueOf(centerY + 1 ))) {
                        edgesMap.get(String.valueOf(centerX + 1) + String.valueOf(centerY + 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge(centerX + 1, centerY + 1);
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX + 1) + String.valueOf(centerY + 1), edge);
                    }

                }
            }
        }
        for (int i = 0; i < configuration.length; i++) {
            for(int j = 0; j < configuration[1].length; j++) {
                if (configuration[i][j] == '\0') {
                    configuration[i][j] = '-';
                }
            }
        }

        if (dimension == 2) {
            availableHexagonNum = 7;
        } else {
            availableHexagonNum = 19;
        }

        for(Map.Entry<String, Edge> entry: edgesMap.entrySet()){
            emptyEdges.put(entry.getKey(),entry.getValue());
        }
    }

    public void captureOneEdge(String coordinate, int player) {
        Edge edge = edgesMap.get(coordinate);
        edge.capturedBy = player;
        if(player == 1) {
            configuration[edge.row][edge.col] = 'B';
        } else {
            configuration[edge.row][edge.col] = 'R';
        }
        emptyEdges.remove(coordinate);
        for (int i = 0; i < edge.relativeHexagons.size(); i++) {
            edge.relativeHexagons.get(i).oneEdgeCaptured(player);
        }
    }

    public void releaseOneEdge(String coordinate, int player) {
        Edge edge = edgesMap.get(coordinate);
        edge.capturedBy = 0;
        configuration[edge.row][edge.col] = '+';
        emptyEdges.put(coordinate, edge);
        for (int i = 0; i < edge.relativeHexagons.size(); i++) {
            edge.relativeHexagons.get(i).oneEdgeReleased(player);
        }
    }

    public static void pln(Object object) {
        System.out.println(object);
    }

//    // return the only empty Edge from one hexagon with 5 captured edges
//    public int[] returnTheOnlyEmptyEdge(Hexagon hexagon) {
//        if(edgesMap.get(String.valueOf(hexagon.row - 1) + String.valueOf(hexagon.col - 1)))
//
//    }

    public void print() {
        for (int i = 0; i < configuration.length; i++) {
            for(int j = 0; j < configuration[1].length; j++) {
                System.out.print(configuration[i][j]);
                if(j != configuration[1].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
//        System.out.println(edgesMap.size());
//        System.out.println(hexagonsMap.size());
    }

}


