import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class GraphicsManager {
	public static CellGraphics cellGraphics;
	public static ColorViewer colorViewer;
	public static JLabel iterationCounter;

	/**
	 * Method setting up the basic elements for ALL graphics elements
	 */
	private static void graphicsHelper(JComponent component, Dimension screenSize, JLayeredPane layeredPane, int layer) {
		component.setBounds(0, 0, screenSize.width, screenSize.height);
		layeredPane.add(component, Integer.valueOf(layer));
	}
	
	public static void createGrid(Dimension screenSize, JLayeredPane layeredPane, int layer, JFrame frame) {
		Grid grid = new Grid(frame);
		graphicsHelper(grid, screenSize, layeredPane, layer);
	}
	
	public static void createCellGraphics(Dimension screenSize, JLayeredPane layeredPane, int layer, JFrame frame) {
		CellGraphics cellGraphics = new CellGraphics(frame);
		graphicsHelper(cellGraphics, screenSize, layeredPane, layer);
		
		GraphicsManager.cellGraphics = cellGraphics;
	}
	
	public static void createColorVeiwer(Dimension screenSize, JLayeredPane layeredPane, int layer, JFrame frame) {
		ColorViewer colorVeiwer = new ColorViewer(frame);
		graphicsHelper(colorVeiwer, screenSize, layeredPane, layer);
		
		GraphicsManager.colorViewer = colorVeiwer;
	}
	
	public static void addUI(Dimension screenSize, JLayeredPane layeredPane, int layer, JFrame frame) throws FontFormatException, IOException {
		InputStream inputFile = GraphicsManager.class.getResourceAsStream("/fonts/Tiny5-Regular.ttf");
		Font pixelFont = Font.createFont(Font.TRUETYPE_FONT, inputFile).deriveFont(20f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(pixelFont);
		
		JLabel iterationCounter = new JLabel() {
			public void paintComponent(Graphics g) {
				g.setColor(Color.white);
				if(CellularAutomata.gameStarted) {
					g.drawString("Iteration: " + CellularAutomata.iteration, 10, frame.getContentPane().getHeight() - 20);
				}
			}
		};
		
		iterationCounter.setFont(pixelFont);
		
		iterationCounter.setBounds(0, 0, screenSize.width, screenSize.height);
		layeredPane.add(iterationCounter, Integer.valueOf(layer));
		
		GraphicsManager.iterationCounter = iterationCounter;
	}
	
	public static Color getAvgColor(ArrayList<Cell> cells) {
		double red = 0;
		double green = 0;
		double blue = 0;
		
		for(Cell cell : cells) {
			red += Math.pow(cell.color.getRed(), 2);
			green += Math.pow(cell.color.getGreen(), 2);
			blue += Math.pow(cell.color.getBlue(), 2);
		}
		
		red = Math.sqrt(red / cells.size());
		green = Math.sqrt(green / cells.size());
		blue = Math.sqrt(blue / cells.size());
		
		return new Color((float)(red / 255), (float)(green / 255), (float)(blue / 255));
	}
}

class Grid extends JComponent{
	int cellSize;
	int width;
	int height;
	
	public Grid(JFrame frame) {		
		
		cellSize = Cell.getCellSize(frame);
		width = frame.getContentPane().getWidth();
		height = frame.getContentPane().getHeight();
		

        this.setPreferredSize(new Dimension(width, height));		
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(5, 5, 5));
		for(int x = 0; x < width; x += cellSize) {
			g.drawLine(x, 0, x, height);	
		}	
		for(int y = 0; y < height; y += cellSize) {
			g.drawLine(0, y, width, y);
		}
	}	
}

class CellGraphics extends JComponent{

	final double PADDING = 0.95;
    double MARGIN;
    
	int cellSize;

	public CellGraphics(JFrame frame) {		
		
		cellSize = Cell.getCellSize(frame);
		int width = frame.getContentPane().getWidth();
		int height = frame.getContentPane().getHeight();
        this.setPreferredSize(new Dimension(width, height));

		MARGIN = (cellSize - (cellSize * PADDING)) / 2;	
	}

	public void paintComponent(Graphics g) {
		
		for(int x = 0; x < CellularAutomata.GAME_SIZE; x++) {
			for(int y = 0; y < CellularAutomata.GAME_SIZE; y++) {
				int realX = x * cellSize;
				int realY = y * cellSize;
				
				// Draw shadow cells first
				if(CellularAutomata.gameStarted && CellularAutomata.shadowCells[x][y] != null) {
					g.setColor(CellularAutomata.shadowCells[x][y].color);
					g.fillRect((int)(realX + MARGIN), (int)(realY + MARGIN), (int)(cellSize * PADDING), (int)(cellSize * PADDING));
				}	
				if(CellularAutomata.cells[x][y] != null) {
					g.setColor(CellularAutomata.cells[x][y].color);
					g.fillRect((int)(realX + MARGIN), (int)(realY + MARGIN), (int)(cellSize * PADDING), (int)(cellSize * PADDING));
				}
			}
		}
		
	}	
}

class ColorViewer extends JComponent{ 
	JFrame frame;
	public ColorViewer(JFrame frame) {
		int width = frame.getContentPane().getWidth();
		int height = frame.getContentPane().getHeight();
        this.setPreferredSize(new Dimension(width, height));
        
        this.frame = frame;

	}
	
	public void paintComponent(Graphics g) {
		if(!CellularAutomata.gameStarted) {
			g.setColor(CellularAutomata.colors[CellularAutomata.colorIndex]);
			g.fillRect(10, frame.getContentPane().getHeight() - 40, 25, 25);
			
			g.setColor(Color.white);
			g.drawRect(10, frame.getContentPane().getHeight() - 40, 25, 25);
		}
	}
}
