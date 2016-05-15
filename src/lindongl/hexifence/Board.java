// Gabriel Snider 809203 gsnider
// Lindong Li 655251 lindongl
package lindongl.hexifence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lld on 16/3/19.
 */
public class Board {
    protected int dimension;    // the dimension of the board
    protected char[][] configuration;   // the configuration of the board
    protected int answer1 = 0;
    protected int answer2;
    protected int answer3;
    protected List<String> availableEdges = new ArrayList<String>();  // includes all Edges could be used to capture one hexagon by one move
    protected void stateCheck() {
        // Note: one layer means a layer of hexagons, for example if the dimension is the 2, there would be 3 layers and
        // the number of hexagons in each layer would be 2, 3, 2
        // Calculate the number of possible moves by count the number of the '+'
        for (int i = 0; i < configuration.length; i++) {
            for(int j = 0; j < configuration[1].length; j++) {
                if(configuration[i][j] == '+') {
                    answer1++;
                }
            }
        }

        int lspx = 0;   // x coordinate of the left top edge of the first hexagon in each layer
        int lspy = 0;   // y coordinate of the left top edge of the first hexagon in each layer
        int hspx = 0;   // x coordinate of the top left edge of current hexagon
        int hspy = 0;   // y coordinate of the top left edge of current hexagon
        int plusNum;    // plusNum used to judge if the current hexagon could be captured by one move
        // From the first layer to the N layer, the number of the hexagons in each layer would be N, N+1 ... 2N - 1
        for (int i = dimension; i <= 2 * dimension - 1 ; i++) {
            // Calculate the lspx of each layer, lspy would not change and is 0
            lspx = 0 + (i - dimension) * 2;
            // Set the first hexagon as the current hexagon of this layer
            hspx = lspx;
            for (int j = 0; j < i; j++) {
                // Calculate the y coordinate of the top left edge of current hexagon
                // hspx would not change and is always equal to the lspx
                hspy = lspy + j * 2;
                plusNum = 0;
                // if there is no or more than one '+', this hexagon could not be captured
                // if there is only one '+', this hexagon could be captured. Thus the number of the answer3 adds one
                // and put the coordinate of this edge in one list in order to answer the question 2
                if(configuration[hspx][hspy] == '+') {
                    availableEdges.add(String.valueOf(hspx) + String.valueOf(hspy));
                    plusNum++;
                }
                if(configuration[hspx][hspy + 1] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx) + String.valueOf(hspy + 1));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                if(configuration[hspx + 1][hspy] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx + 1) + String.valueOf(hspy));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                if(configuration[hspx + 1][hspy + 2] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx + 1) + String.valueOf(hspy + 2));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                if(configuration[hspx + 2][hspy + 1] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx + 2) + String.valueOf(hspy + 1));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                if(configuration[hspx + 2][hspy + 2] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx + 2) + String.valueOf(hspy + 2));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                // when there is one plus in one hexagon, the hexagon could be captured by one move
                if (plusNum == 1) {
                    answer3 ++;
                }
            }
        }

        // From the N + 1 layer to the 2N - 1 layer, the number of the hexagons in each layer would be 2N - 2, 2N -3 ... N
        for (int i = 0; i < (2 * dimension - 2 - dimension + 1); i++) {
            // Calculate x coordinate of the top left edge of the first hexagon of each layer
            hspx = lspx + (i + 1) * 2;
            // Calculate y coordinate of the top left edge of the first hexagon of this layer
            lspy = 0 + (i + 1) * 2;
            for (int j = 0; j < (2 * dimension - 2 - i); j++) {
                //  Calculate the y coordinate of the top left edge of current hexagon
                hspy = lspy + j * 2;
                plusNum = 0;
                // if there is no or more than one '+', this hexagon could not be captured
                // if there is only one '+', this hexagon could be captured. Thus the number of the answer3 adds one
                // and put the coordinate of this edge in one list in order to answer the question 2
                if(configuration[hspx][hspy] == '+') {
                    availableEdges.add(String.valueOf(hspx) + String.valueOf(hspy));
                    plusNum++;
                }
                if(configuration[hspx][hspy + 1] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx) + String.valueOf(hspy + 1));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                if(configuration[hspx + 1][hspy] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx + 1) + String.valueOf(hspy));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                if(configuration[hspx + 1][hspy + 2] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx + 1) + String.valueOf(hspy + 2));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                if(configuration[hspx + 2][hspy + 1] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx + 2) + String.valueOf(hspy + 1));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                if(configuration[hspx + 2][hspy + 2] == '+') {
                    if (plusNum == 0) {
                        availableEdges.add(String.valueOf(hspx + 2) + String.valueOf(hspy + 2));
                        plusNum++;
                    } else {
                        availableEdges.remove(availableEdges.size()-1);
                        continue;
                    }
                }
                // when there is one plus in one hexagon, the hexagon could be captured by one move
                if (plusNum == 1) {
                    answer3 ++;
                }
            }
        }

        // if there is no hexagon could be captured, the answer of the question 2 would be 0
        if(answer3 == 0) {
            answer2 = 0;
        } else {
            boolean maxTwo = false;     // the flag used to represent if it is possible to capture 2 hexagons
            // if there is the repeated edge in the availableEdges, it means the answer of the question2 would be 2
            // if not, the answer of the question 2 would be 1
            for (int i = 0; i < availableEdges.size(); i++) {
                String s = availableEdges.get(i);
                availableEdges.remove(i);
                if (availableEdges.contains(s)) {
                    maxTwo = true;
                    break;
                } else {
                    availableEdges.add(i,s);
                }
            }
            if (maxTwo == true) {
                answer2 = 2;
            } else {
                answer2 = 1;
            }
        }
    }
    public static void main(String[] args) {
        Board board = new Board();
        try{
            int d = System.in.read();
            // Judge if the dimension is 2 or 3, if not, the program will exit
            if ((d == (int)'2')) {
                board.dimension = 2;
                board.configuration = new char[(4 * board.dimension) - 1][(4 * board.dimension) - 1];
            }
            else if (d == (int)'3'){
                board.dimension = 3;
                board.configuration = new char[(4 * board.dimension) - 1][(4 * board.dimension) - 1];
            }else {
                System.out.println("error");
                System.exit(0);
            }
            d = System.in.read();
            d = System.in.read();
            // Input will contain all useful characters from input including +, -, R, B
            List<Character> input = new ArrayList<Character>();
            int count = 0;
            while(d != -1){
                // if the input has illegal character, it will output error and exit
                if (d == (int)'+' || d == (int)'R' || d == (int)'B'|| d == (int)'-' || d == (int)' '|| d == (int)'\n'|| d == (int)'\r') {
                    if (d == (int) '+' || d == (int) 'R' || d == (int) 'B' || d == (int) '-') {
                        input.add((char) d);
                        count++;
                        d = System.in.read();
                    } else {
                        d = System.in.read();
                    }
                    // if the number of edges in each row is not correct, it will output error and exit
                    if (d == (int) '\n') {
                        if (count == (4 * board.dimension) - 1) {
                            count = 0;
                        } else {
                            System.out.println("error");
                            System.exit(0);
                        }
                    }
                } else {
                    System.out.println("error");
                    System.exit(0);
                }
            }

            // If the size of the input is not correct, the program will exit
            if (input.size() != ((4 * board.dimension) - 1) * ((4 * board.dimension) - 1)) {
                System.out.println("error");
                System.exit(0);
            }

            // using input to create the board
            for (int i = 0; i < board.configuration.length; i++) {
                for(int j = 0; j < board.configuration[1].length; j++) {
                    board.configuration[i][j] = input.get((i * board.configuration[1].length) + j);
                }
            }



            board.stateCheck();
            System.out.println(board.answer1);
            System.out.println(board.answer2);
            System.out.println(board.answer3);
        }catch(Exception e)
        {
            System.out.println("Error!");
            System.exit(0);
        }
    }
}
