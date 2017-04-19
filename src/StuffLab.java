import java.awt.Color;
import java.util.Random;

public class StuffLab {
    // CONSTANT DECLARATIONS
    public static final int ROWS = 150;
    public static final int COLS = 150;

    public static void main(String[] args) {
        StuffLab lab = new StuffLab(ROWS, COLS);
        lab.run();
    }

    // PARTICLE CONSTANTS
    public static final int EMPTY = 0;
    public static final int SAND = 1;
    public static final int WATER = 2;
    public static final int GAS = 3;
    public static final int WALL = 4;
    public static final int WALLY = 5;
    public static final int BRICK = 6;
    public static final int MISSILE = 7;
    public static final int SLUDGE = 8;
    public static final int LAVA = 9;


    // FIELDS (can add more)
    private int[][] grid;
    private StuffDisplay display;


    public StuffLab(int numRows, int numCols)
    {
        String[] names;
        names = new String[10]; // MUST CHANGE LENGTH IF YOU WANT TO ADD SOMETHING !!!!!!!!!!!!!!!!!!!!!!!!!!!!
        names[EMPTY] = "Empty";
        names[SAND] = "Sand ";
        names[WATER] = "Water";
        names[GAS] = "Gas";
        names[WALL] = "Wall (.)";
        names[WALLY] = "Wall (+)";
        names[BRICK] = "Brick";
        names[MISSILE] = "Missile";
        names[SLUDGE] = "Sludge";
        names[LAVA] = "Lava";
        display = new StuffDisplay("Falling Sand", numRows, numCols, names);
        grid = new int[numRows][numCols];
    }

    // CALLED ON CLICK OF SCREEN
    private void locationClicked(int row, int col, int tool) {
        if (grid[row][col] != EMPTY && grid[row][col] != WATER && grid[row][col] != GAS){ // if neither empty nor water nor gas
            return;
        }

        if (tool == WALLY && row != 0 && row != ROWS-1 && col != 0 && col != COLS -1){
            grid[row][col] = tool;
            grid[row+1][col] = tool;
            grid[row-1][col] = tool;
            grid[row][col+1] = tool;
            grid[row][col-1] = tool;
        }
        else{
            grid[row][col] = tool;
        }
        // could get more creative with this later
        // spots could appear around an area like larger brush
    }

    // GRID -> DISPLAY
    public void updateDisplay() {
        for (int row=0; row < ROWS; row++) {
            for (int column=0; column < COLS; column++) {
                if (grid[row][column]==(EMPTY)) {display.setColor(row, column, Color.black);}
                else if (grid[row][column]==(SAND)){display.setColor(row, column, Color.yellow);}
                else if (grid[row][column]==(WATER)){display.setColor(row, column, Color.blue);}
                else if (grid[row][column]==(WALL)){display.setColor(row, column, Color.white);}
                else if (grid[row][column]==(WALLY)){display.setColor(row, column, Color.white);}
                else if (grid[row][column]==(GAS)){display.setColor(row, column, Color.darkGray);}
                // for error-finding purposes, set anything else to red
                else {display.setColor(row, column, Color.red);}
            }
        }
    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step(int x, int y) {
        // NOTE TO SELF: Remember that we are in form grid[y][x]

        // if sand

        switch (grid[y][x]) {
            case SAND:
                // if at bottom, do nothing
                if (y == ROWS - 1) {
                    return;
                }
                // if nothing below sand
                if (grid[y + 1][x] == EMPTY) {
                    // sand falls
                    grid[y + 1][x] = SAND;
                    grid[y][x] = EMPTY;
                    return;
                }

                // if water below sand
                if (grid[y + 1][x] == WATER) {
                    // sand falls through water
                    grid[y + 1][x] = SAND;
                    grid[y][x] = WATER;
                    return;
                }
                break;

            case WATER:
                // if at bottom, do nothing
                if (y == ROWS - 1) {
                    return;
                }
                // if nothing below water
                if (grid[y + 1][x] == EMPTY) {
                    // water falls
                    grid[y + 1][x] = WATER;
                    grid[y][x] = EMPTY;
                    return;
                }

                // if gas below water
                if (grid[y + 1][x] == GAS) {
                    // water falls
                    grid[y + 1][x] = WATER;
                    grid[y][x] = GAS;
                    return;
                }

                // if lava below water
                if (grid[y + 1][x] == LAVA) {
                    // water falls
                    grid[y][x] = GAS;
                    return;
                }

                // if something below water
                if (grid[y + 1][x] != EMPTY) {
                    // if on sides, move away
                    if (x == 0 && grid[y][x + 1] == EMPTY) {
                        grid[y][x] = EMPTY;
                        grid[y][x + 1] = WATER;
                        return;
                    }
                    if (x == COLS - 1 && grid[y][x - 1] == EMPTY) {
                        grid[y][x] = EMPTY;
                        grid[y][x - 1] = WATER;
                        return;
                    }
                    // if on sides and trapped, stay
                    if (x == 0 || x == COLS - 1) {
                        return;
                    }
                    // if otherwise locked in, stay
                    if (grid[y][x + 1] != EMPTY && grid[y][x - 1] != EMPTY) {
                        return;
                    }
                    // else, choose randomly between left and right movement
                    Random side = new Random();
                    int choice = side.nextInt(2);
                    // if 0, preference towards leftward movement
                    if (choice == 0) {
                        if (grid[y][x - 1] == EMPTY) {
                            grid[y][x - 1] = WATER;
                            grid[y][x] = EMPTY;
                            return;
                        } else {
                            grid[y][x + 1] = WATER;
                            grid[y][x] = EMPTY;
                            return;
                        }
                    }
                    // if 1, preference towards rightward movement
                    else {
                        if (grid[y][x + 1] == EMPTY) {
                            grid[y][x + 1] = WATER;
                            grid[y][x] = EMPTY;
                            return;
                        } else {
                            grid[y][x - 1] = WATER;
                            grid[y][x] = EMPTY;
                            return;
                        }
                    }
                }
                break;

            case GAS:
                // if at top, do nothing
                if (y == 0) {
                    return;
                }
                // if nothing above gas
                if (grid[y - 1][x] == EMPTY) {
                    // gas rises
                    grid[y - 1][x] = GAS;
                    grid[y][x] = EMPTY;
                    return;
                }

                // if something above gas
                if (grid[y - 1][x] != EMPTY) {
                    // if on sides, move away
                    if (x == 0 && grid[y][x + 1] == EMPTY) {
                        grid[y][x] = EMPTY;
                        grid[y][x + 1] = GAS;
                        return;
                    }
                    if (x == COLS - 1 && grid[y][x - 1] == EMPTY) {
                        grid[y][x] = EMPTY;
                        grid[y][x - 1] = GAS;
                        return;
                    }
                    // if on sides and trapped, stay
                    if (x == 0 || x == COLS - 1) {
                        return;
                    }
                    // if otherwise locked in, stay
                    if (grid[y][x + 1] != EMPTY && grid[y][x - 1] != EMPTY) {
                        return;
                    }

                    // else, choose randomly between left and right movement
                    Random side1 = new Random();
                    int doice = side1.nextInt(2);
                    // if 0, preference towards leftward movement
                    if (doice == 0) {
                        if (grid[y][x - 1] == EMPTY) {
                            grid[y][x - 1] = GAS;
                            grid[y][x] = EMPTY;
                            return;
                        } else {
                            grid[y][x + 1] = GAS;
                            grid[y][x] = EMPTY;
                            return;
                        }
                    }
                    // if 1, preference towards rightward movement
                    else {
                        if (grid[y][x + 1] == EMPTY) {
                            grid[y][x + 1] = GAS;
                            grid[y][x] = EMPTY;
                            return;
                        } else {
                            grid[y][x - 1] = GAS;
                            grid[y][x] = EMPTY;
                            return;
                        }
                    }
                }
                break;
            case WALL:
                break;
            case WALLY:
                break;
            case BRICK:
                break;
            case MISSILE:
                break;
            case SLUDGE:
                break;
            case LAVA:
                Random side1 = new Random();
                int choice = side1.nextInt(4);
                if (choice == 0 || choice == 1 || choice == 2){
                    return;
                }
                // if at bottom, do nothing
                if (y == ROWS - 1) {
                    return;
                }
                // if nothing below water
                if (grid[y + 1][x] == EMPTY) {
                    // water falls
                    grid[y + 1][x] = LAVA;
                    grid[y][x] = EMPTY;
                    return;
                }

                // if gas below water
                if (grid[y + 1][x] == GAS) {
                    // water falls
                    grid[y + 1][x] = LAVA;
                    grid[y][x] = GAS;
                    return;
                }

                // if something below water
                if (grid[y + 1][x] != EMPTY) {
                    // if on sides, move away
                    if (x == 0 && grid[y][x + 1] == EMPTY) {
                        grid[y][x] = EMPTY;
                        grid[y][x + 1] = LAVA;
                        return;
                    }
                    if (x == COLS - 1 && grid[y][x - 1] == EMPTY) {
                        grid[y][x] = EMPTY;
                        grid[y][x - 1] = LAVA;
                        return;
                    }
                    // if on sides and trapped, stay
                    if (x == 0 || x == COLS - 1) {
                        return;
                    }
                    // if otherwise locked in, stay
                    if (grid[y][x + 1] != EMPTY && grid[y][x - 1] != EMPTY) {
                        return;
                    }
                    // else, choose randomly between left and right movement
                    Random side = new Random();
                    choice = side.nextInt(1);
                    // if 0, preference towards leftward movement
                    if (choice == 0) {
                        if (grid[y][x - 1] == EMPTY) {
                            grid[y][x - 1] = LAVA;
                            grid[y][x] = EMPTY;
                            return;
                        } else {
                            grid[y][x + 1] = LAVA;
                            grid[y][x] = EMPTY;
                            return;
                        }
                    }
                    // if 1, preference towards rightward movement
                    else {
                        if (grid[y][x + 1] == EMPTY) {
                            grid[y][x + 1] = LAVA;
                            grid[y][x] = EMPTY;
                            return;
                        } else {
                            grid[y][x - 1] = LAVA;
                            grid[y][x] = EMPTY;
                            return;
                        }
                    }
                }
                break;
            case EMPTY:
                break;
            default:
                break;
        }
    }

    //do not modify
    public void run()
    {
        while (true)
        {
            for (int i = 0; i < display.getSpeed(); i++)
            {
                // CALLS STEP FUNCTION WITH RANDOM (X, Y)
                Random ranX = new Random();
                int X = ranX.nextInt(COLS);
                Random ranY = new Random();
                int Y = ranY.nextInt(ROWS);
                step(X, Y);
            }
            updateDisplay();
            display.repaint();
            display.pause(1);  //wait for redrawing and for mouse
            int[] mouseLoc = display.getMouseLocation();
            if (mouseLoc != null)  //test if mouse clicked
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
        }
    }
}