import java.util.Random;

public class Simulation {
    public static void main(String[] args) {

        boolean CENSOR = true;
        if (args[0].equals("t")) {CENSOR = true;}
        else if (args[0].equals("f")) {CENSOR = false;}
        else {System.exit(-1);}

        final int ROWS = 100;
        final int COLS = 150;

        Cell[][] grid = new Cell[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = new Cell();
            }
        }

        Random rand = new Random();

        int others = 6 * ((ROWS * COLS) / 100);

        int randRow;
        int randCol;
        
        for (int i = 0; i < others; i++) {
            randRow = rand.nextInt(100);
            randCol = rand.nextInt(150);
            while ( !(grid[randRow][randCol].getType().equals(Type.UNDECIDED)) ) {
                randRow = rand.nextInt(100);
                randCol = rand.nextInt(150);
            }
            
            switch (i % 6) {
                case 0: 
                    grid[randRow][randCol].setType(Type.PARTIAL_A);
                break;
                case 1: 
                    grid[randRow][randCol].setType(Type.PARTIAL_B);
                break;
                case 2: 
                    grid[randRow][randCol].setType(Type.TRUTH_LOW);
                break;
                case 3: 
                    grid[randRow][randCol].setType(Type.TRUTH_HI);
                break;
                case 4: 
                    grid[randRow][randCol].setType(Type.UNTRUTH_LOW);
                break;
                case 5: 
                    grid[randRow][randCol].setType(Type.UNTRUTH_HI);
                break;
            }
        }

        for (int n = 0; n < 100; n++) {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    int neighborRow = 0;
                    int neighborCol = 0;
                    
                    while (neighborRow == 0 && neighborCol == 0) {
                        if (i == 0) {neighborRow = rand.nextInt(2);} 
                        else if (i == ROWS - 1) {neighborRow = rand.nextInt(2) - 1;} 
                        else {neighborRow = rand.nextInt(3) - 1;}

                        if (j == 0) {neighborCol = rand.nextInt(2);}
                        else if (j == COLS - 1) {neighborCol = (rand.nextInt(2) - 1);}
                        else {neighborCol = rand.nextInt(3) - 1;}
                    }

                    Cell neighbor = grid[i + neighborRow][j + neighborCol];

                    grid[i][j].getInfluence(neighbor, CENSOR);
                }
            }

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    grid[i][j].shift();
                }
            }
        }

        int truthHi = 0;
        int truthLow = 0;
        int untruthHi = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j].getType().equals(Type.TRUTH_HI)) {truthHi++;}
                if (grid[i][j].getType().equals(Type.TRUTH_LOW)) {truthLow++;}
                if (grid[i][j].getType().equals(Type.UNTRUTH_HI)) {untruthHi++;}
            }
        }


        System.out.println("\nMill's Principle of Truth Simulation - 100 Iterations\nCensor : " + CENSOR + "\n");
        System.out.println("Truth High : " + truthHi);
        System.out.println("Truth Low : " + truthLow);
        System.out.println("Truth High + Truth Low : " + (truthHi + truthLow));
        System.out.println("Untruth High : " + untruthHi + "\n");


    }
}