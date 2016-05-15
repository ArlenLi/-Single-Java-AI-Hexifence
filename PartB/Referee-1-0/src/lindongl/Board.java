package lindongl;

import java.util.HashMap;
import java.util.Map;

public class Board{
    public int dimension;    // the dimension of the board
    public char[][] configuration;   // the configuration of the board
    public static int availableHexagonNum;
    public static int hexagonsCapturedByRed;
    public static int hexagonsCapturedByBlue;
    public Map<String, Hexagon> hexagonsMap;
    public Map<String, Edge> edgesMap;

    public Board(int dimension) {
        hexagonsMap = new HashMap<String, Hexagon>();
        edgesMap = new HashMap<String, Edge>();
        configuration = new char[4 * dimension - 1][4 * dimension - 1];
        int centerX;
        int centerY;
        for (int i = 0; i < 2 * dimension - 1; i++) {
            if( i <= dimension - 1) {
                for (int j = 0; j < i + dimension; j++) {
                    centerX = 2 * i + 1;
                    centerY = 2 * j + 1;
                    configuration[centerX][centerY] = '-';
                    Hexagon tempHex = new Hexagon();
                    hexagonsMap.put(String.valueOf(centerX) + String.valueOf(centerY), tempHex);
                    configuration[centerX - 1][centerY - 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX - 1) + String.valueOf(centerY - 1 ))) {
                        edgesMap.get(String.valueOf(centerX -1) + String.valueOf(centerY - 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX - 1) + String.valueOf(centerY - 1), edge);
                    }

                    configuration[centerX - 1][centerY] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX - 1) + String.valueOf(centerY))) {
                        edgesMap.get(String.valueOf(centerX - 1) + String.valueOf(centerY)).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX - 1) + String.valueOf(centerY), edge);
                    }
                    configuration[centerX][centerY - 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX) + String.valueOf(centerY - 1 ))) {
                        edgesMap.get(String.valueOf(centerX) + String.valueOf(centerY - 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX) + String.valueOf(centerY - 1), edge);
                    }
                    configuration[centerX][centerY + 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX) + String.valueOf(centerY + 1 ))) {
                        edgesMap.get(String.valueOf(centerX) + String.valueOf(centerY + 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX) + String.valueOf(centerY + 1), edge);
                    }
                    configuration[centerX + 1][centerY] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX + 1) + String.valueOf(centerY))) {
                        edgesMap.get(String.valueOf(centerX + 1) + String.valueOf(centerY)).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX + 1) + String.valueOf(centerY), edge);
                    }
                    configuration[centerX + 1][centerY + 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX + 1) + String.valueOf(centerY + 1 ))) {
                        edgesMap.get(String.valueOf(centerX + 1) + String.valueOf(centerY + 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX + 1) + String.valueOf(centerY + 1), edge);
                    }

                }
            } else {
                for (int j = 0; j < 3 * dimension - 2 - i; j++) {
                    centerX = 2 * i + 1;
                    centerY = 2 * (i - dimension + 1) + (2 * j + 1);
                    configuration[centerX][centerY] = '-';
                    Hexagon tempHex = new Hexagon();
                    hexagonsMap.put(String.valueOf(centerX) + String.valueOf(centerY), tempHex);
                    configuration[centerX - 1][centerY - 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX - 1) + String.valueOf(centerY - 1 ))) {
                        edgesMap.get(String.valueOf(centerX -1) + String.valueOf(centerY - 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX - 1) + String.valueOf(centerY - 1), edge);
                    }

                    configuration[centerX - 1][centerY] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX - 1) + String.valueOf(centerY))) {
                        edgesMap.get(String.valueOf(centerX - 1) + String.valueOf(centerY)).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX - 1) + String.valueOf(centerY), edge);
                    }
                    configuration[centerX][centerY - 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX) + String.valueOf(centerY - 1 ))) {
                        edgesMap.get(String.valueOf(centerX) + String.valueOf(centerY - 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX) + String.valueOf(centerY - 1), edge);
                    }
                    configuration[centerX][centerY + 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX) + String.valueOf(centerY + 1 ))) {
                        edgesMap.get(String.valueOf(centerX) + String.valueOf(centerY + 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX) + String.valueOf(centerY + 1), edge);
                    }
                    configuration[centerX + 1][centerY] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX + 1) + String.valueOf(centerY))) {
                        edgesMap.get(String.valueOf(centerX + 1) + String.valueOf(centerY)).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
                        edge.addHexagon(tempHex);
                        edgesMap.put(String.valueOf(centerX + 1) + String.valueOf(centerY), edge);
                    }
                    configuration[centerX + 1][centerY + 1] = '+';
                    if(edgesMap.containsKey(String.valueOf(centerX + 1) + String.valueOf(centerY + 1 ))) {
                        edgesMap.get(String.valueOf(centerX + 1) + String.valueOf(centerY + 1 )).addHexagon(tempHex);
                    } else {
                        Edge edge = new Edge();
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
    }

    public static void pln(Object object) {
        System.out.println(object);
    }

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
        System.out.println(edgesMap.size());
        System.out.println(hexagonsMap.size());
    }

}


