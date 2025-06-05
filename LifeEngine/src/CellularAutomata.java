import java.awt.Color;
import java.util.ArrayList;

public class CellularAutomata  {
	public static final int GAME_SIZE = 250;
	static Cell[][] cells = new Cell[GAME_SIZE][GAME_SIZE];
	static Cell[][] shadowCells = new Cell[GAME_SIZE][GAME_SIZE];
	
	public static boolean gameStarted = false;
	
	public static Color[] colors = {Color.white, Color.red, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta};
	public static int colorIndex = 0;
	
	public static int iteration = 0;
	
	public static void play() {
	    new Thread(() -> {
	        while (true) {
	            if (gameStarted) {
	                iterate();
	                
	                javax.swing.SwingUtilities.invokeLater(() -> {
	                    GraphicsManager.cellGraphics.repaint();
	                });

	                try {
	                    Thread.sleep(100);
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            } else {
	            	iteration = 0;
	                try {
	                    Thread.sleep(50);
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	        }
	    }, "GameLoopThread").start();
	}
	
	/**
	 * Sets up all the empty cells!
	 */
	public static void setUpCells() {
		cells = new Cell[GAME_SIZE][GAME_SIZE];
		shadowCells = new Cell[GAME_SIZE][GAME_SIZE];
		
		GraphicsManager.cellGraphics.repaint();
	}
	
	public static void updateCell(int x, int y) {
		if(cells[x][y] != null) {
			cells[x][y] = null;
		}
		else {
			cells[x][y] = new Cell(x, y);
			cells[x][y].color = colors[colorIndex];
		}
		
		GraphicsManager.cellGraphics.repaint();	
	}
	
	/**
	 * One call of this method is one iteration in the automata
	 */
	private static void iterate() {

        Cell[][] new_Layout = new Cell[GAME_SIZE][GAME_SIZE];


        for (int x = 0; x < GAME_SIZE; x++){
            for (int y = 0; y < GAME_SIZE; y++){
                ArrayList<Cell> neighbors = Cell.getNeighbors(x, y);
                // FOR A CELL THAT IS POPULATED:
                if (cells[x][y] != null){
                    
                	// DEATH 
                    if (neighbors.size() <= Rules.rule.deathValues[0] || neighbors.size() >= Rules.rule.deathValues[1]){
                    	shadowCells[x][y] = new Cell(x,y);
                    	Color shadowColor = cells[x][y].color;
                    	shadowCells[x][y].color = new Color(shadowColor.getRed(),shadowColor.getGreen(), shadowColor.getBlue(), (int)(shadowColor.getAlpha() * 0.25));
                    	                    	
                    }
                    if(Rules.rule.deathValues.length > 2) {
                    	for(int i = 2; i < Rules.rule.deathValues.length; i++) {
                    		if(neighbors.size() == Rules.rule.deathValues[i]) {
                    			shadowCells[x][y] = new Cell(x,y);
                            	Color shadowColor = cells[x][y].color;
                            	shadowCells[x][y].color = new Color(shadowColor.getRed(),shadowColor.getGreen(), shadowColor.getBlue(), (int)(shadowColor.getAlpha() * 0.25));
                    		}
                    	}
                    }
 
                    // SUVIVIAL
                    else {
                    	for(int i = 0; i < Rules.rule.survivialValues.length; i++) {
                            if(neighbors.size() == Rules.rule.survivialValues[i]) {
                        		new_Layout[x][y] = new Cell(x, y);
                                new_Layout[x][y].color = cells[x][y].color;
                            }
                    	}
                    }
                }

                // For a space that is empty or un-populated
                else if (cells[x][y] == null){
                	
                	// POPULATION
                	for(int i = 0; i < Rules.rule.populationValues.length; i++) {
                        if(neighbors.size() == Rules.rule.populationValues[i]) {
                           new_Layout[x][y] = new Cell(x, y);
                           new_Layout[x][y].color = GraphicsManager.getAvgColor(neighbors);
                        }
                	}	
                }
                
                if(shadowCells[x][y] != null) {
                	Color shadowColor = shadowCells[x][y].color;
                	shadowCells[x][y].color = new Color(shadowColor.getRed(),shadowColor.getGreen(), shadowColor.getBlue(), (int)(shadowColor.getAlpha() * 0.95));
                	
                	if(shadowCells[x][y].color.getAlpha() == 0) {
                		shadowCells[x][y] = null;
                	}
                }
            }
        }

        cells = new_Layout;
        iteration++;
	}
}
