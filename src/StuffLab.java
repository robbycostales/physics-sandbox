import java.awt.Color;
import java.util.Random;
import java.awt.color.ColorSpace;

public class StuffLab {
    // CONSTANT DECLARATIONS
    public static final int ROWS = 150;
    public static final int COLS = 150;
    public Color[] color;
    public Random side = new Random();
    public int choice = side.nextInt(2);
    public int choicee = side.nextInt(2);

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
    public static final int MISSILE_ = 8;
    public static final int LAVA = 9;


    // FIELDS (can add more)
    private int[][] grid;
    private StuffDisplay display;


    public StuffLab(int numRows, int numCols)
    {
        String[] names;
        names = new String[10]; // MUST CHANGE LENGTH IF YOU WANT TO ADD SOMETHING !!!!!!!!!!!!!!!!!!!!!!!!!!!!
        names[EMPTY] = "ERASE";
        names[SAND] = "Sand ";
        names[WATER] = "Water";
        names[GAS] = "Gas";
        names[WALL] = "Wall (-)";
        names[WALLY] = "Wall (+)";
        names[BRICK] = "Brick";
        names[MISSILE_] = "Missile";
        names[LAVA] = "Lava";
        display = new StuffDisplay("Falling Sand", numRows, numCols, names);
        grid = new int[numRows][numCols];

        color = new Color[10];
        color[EMPTY] = new Color(0, 0, 0);
        color[SAND] = new Color(255, 241, 150);
        color[WATER] = new Color(150, 239, 255);
        color[GAS] = new Color(151, 158, 158);
        color[WALL] = new Color(224, 224, 224);
        color[WALLY] = new Color(224, 224, 224);
        color[BRICK] = new Color(181, 110, 88);
        color[MISSILE] = new Color(102, 102, 102);
        color[MISSILE_] = new Color(90, 90, 90);
        color[LAVA] = new Color(255, 59, 0);
    }

    // CALLED ON CLICK OF SCREEN
    private void locationClicked(int row, int col, int tool) {

        if (tool == EMPTY && row != ROWS-1 && col != 0 && col != COLS -1){
            grid[row][col] = tool;
            grid[row+1][col] = tool;
            grid[row-1][col] = tool;
            grid[row][col+1] = tool;
            grid[row][col-1] = tool;
            grid[row+1][col+1] = tool;
            grid[row-1][col-1] = tool;
            grid[row-1][col+1] = tool;
            grid[row+1][col-1] = tool;
            return;
        }

        if (grid[row][col] != EMPTY && grid[row][col] != WATER && grid[row][col] != GAS){ // if neither empty nor water nor gas
            return;
        }

        if (tool == WALLY && row != 0 && row != ROWS-1 && col != 0 && col != COLS -1){
            grid[row][col] = tool;
            grid[row+1][col] = tool;
            grid[row-1][col] = tool;
            grid[row][col+1] = tool;
            grid[row][col-1] = tool;
            grid[row+1][col+1] = tool;
            grid[row-1][col-1] = tool;
            grid[row-1][col+1] = tool;
            grid[row+1][col-1] = tool;
            return;
        }

        if (tool == WALL){
            grid[row][col] = tool;
            return;
        }

        if (tool == MISSILE_ && row != 0 && row > 6 && col > 2 && col < COLS -1){
            choice = side.nextInt(20);
            if (choice == 0){
                createMissile(col, row);
            }

        }

        else{
            grid[row][col] = tool;
            if( row > 2 && row < ROWS-2 && col > 2 && col < COLS -2) {
                choice = side.nextInt(4);
                if (choice == 0) {
                    grid[row][col + 2] = tool;
                }
                if (choice == 1) {
                    grid[row][col - 2] = tool;
                }
                if (choice == 2) {
                    grid[row + 2][col] = tool;
                }
                if (choice == 3) {
                    grid[row + 2][col] = tool;
                }
            }
            if( row > 4 && row < ROWS-4 && col > 4 && col < COLS -4) {
                choice = side.nextInt(4);
                if (choice == 0) {
                    grid[row][col + 4] = tool;
                }
                if (choice == 1) {
                    grid[row][col - 4] = tool;
                }
                if (choice == 2) {
                    grid[row + 4][col] = tool;
                }
                if (choice == 3) {
                    grid[row + 4][col] = tool;
                }
                return;
            }

        }
        // could get more creative with this later
        // spots could appear around an area like larger brush
    }

    // GRID -> DISPLAY
    public void updateDisplay() {
        for (int row=0; row < ROWS; row++) {
            for (int column=0; column < COLS; column++) {
                display.setColor(row, column, color[grid[row][column]]);
            }
        }
    }

    public void createMissile(int tipx, int tipy) {
        grid[tipy-6][tipx+1] = EMPTY;
        grid[tipy-6][tipx-1] = EMPTY;
        grid[tipy-5][tipx] = EMPTY;
        grid[tipy-7][tipx+1] = EMPTY;
        grid[tipy-7][tipx-1] = EMPTY;
        grid[tipy-6][tipx] = EMPTY;

        grid[tipy][tipx] = MISSILE_;
        grid[tipy-1][tipx] = MISSILE;
        grid[tipy-2][tipx] = MISSILE;
        grid[tipy-2][tipx+1] = MISSILE;
        grid[tipy-2][tipx-1] = MISSILE;
        grid[tipy-3][tipx] = MISSILE;
        grid[tipy-3][tipx+1] = MISSILE;
        grid[tipy-3][tipx-1] = MISSILE;
        grid[tipy-4][tipx] = MISSILE;
        grid[tipy-4][tipx+1] = MISSILE;
        grid[tipy-4][tipx-1] = MISSILE;
        grid[tipy-5][tipx+1] = MISSILE;
        grid[tipy-5][tipx-1] = MISSILE;
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


                // makes sand fall more naturally if doesn't always update
                choice = side.nextInt(15);
                if (choice == 0) {
                    if (y != (ROWS - 2) && x != (COLS - 1)) {
                        if (grid[y + 1][x] == SAND && grid[y + 2][x] == SAND) {
                            if (grid[y + 1][x + 1] == EMPTY) {
                                grid[y + 1][x] = EMPTY;
                                grid[y + 1][x + 1] = SAND;
                                return;


                            }
                            if (grid[y + 1][x + 1] == WATER) {
                                grid[y + 1][x] = WATER;
                                grid[y + 1][x + 1] = SAND;
                                return;

                            }
                        }
                    }
                    if (y != (ROWS - 2) && x != (0)) {
                        if (grid[y + 1][x] == SAND && grid[y + 2][x] == SAND) {
                            if (grid[y + 1][x - 1] == EMPTY) {
                                grid[y + 1][x] = EMPTY;
                                grid[y + 1][x - 1] = SAND;
                                return;

                            }
                            if (grid[y + 1][x - 1] == WATER) {
                                grid[y + 1][x] = WATER;
                                grid[y + 1][x - 1] = SAND;
                                return;
                            }
                        }
                    }
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
                    choice = side.nextInt(2);
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
                    choice = side.nextInt(2);
                    // if 0, preference towards leftward movement
                    if (choice == 0) {
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
            case MISSILE_:
                if(y != ROWS-1){
                    createMissile(x, y+1);
                }
                break;

            case LAVA:
                choice = side.nextInt(4);
                if (choice == 0 || choice == 1 || choice == 2){
                    return;
                }
                // if at bottom, do nothing
                if (y == ROWS - 1) {
                    return;
                }
                // if nothing below lava
                if (grid[y + 1][x] == EMPTY) {
                    // water falls
                    grid[y + 1][x] = LAVA;
                    grid[y][x] = EMPTY;
                    return;
                }

                // if gas below lava
                if (grid[y + 1][x] == GAS) {
                    // lava falls
                    grid[y + 1][x] = LAVA;
                    grid[y][x] = GAS;
                    return;
                }

                // if water below lava
                if (grid[y + 1][x] == WATER) {
                    // lava falls
                    grid[y + 1][x] = LAVA;
                    grid[y][x] = WATER;
                    return;
                }

                // if something below lava
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

                    if (grid[y][x + 1] == WATER) {
                        grid[y][x+1] = GAS;
                    }

                    if (grid[y][x - 1] == WATER) {
                        grid[y][x-1] = GAS;
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