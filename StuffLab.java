public class StuffLab {
    public static void main(String[] args) {
        StuffLab lab = new StuffLab(120, 80);
        lab.run();
    }

    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int SAND = 1;

    // add any more fields if you find it useful
    private int[][] grid;
    private StuffDisplay display;

    public StuffLab(int numRows, int numCols) {
        String[] names;
        names = new String[2];
        names[EMPTY] = "Empty";
        names[SAND] = "Sand";
        display = new StuffDisplay("Falling Sand", numRows, numCols, names);
    }

    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool) {
    }

    //copies each element of grid into the display
    public void updateDisplay() {
    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step() {
    }

    //do not modify
    public void run() {
        while (true) {
            for (int i = 0; i < display.getSpeed(); i++) {
                step();
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