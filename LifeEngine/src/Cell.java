import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Cell{
	Point position;
    public Color color;
	
	public static int getCellSize(JFrame frame) {
		int cellSize;
		cellSize = frame.getContentPane().getSize().width / CellularAutomata.GAME_SIZE;
		
		return cellSize;
	}
	
	public Cell(int x, int y) {
		position = new Point(x, y);
	}
	
	public Point getPosition() {
		return position;
	}
	
	
	public static ArrayList<Cell> getNeighbors(int x, int y) {
		ArrayList<Cell> neighbors = new ArrayList<>();
		
		for(int m_x = x - Rules.rule.neighborPadding; m_x <= x + Rules.rule.neighborPadding; m_x++) {
			for(int m_y = y - Rules.rule.neighborPadding; m_y <= y + Rules.rule.neighborPadding; m_y++) {
				
				if(m_x == x && m_y == y) { continue; }
				if(m_x < 0 || m_x >= CellularAutomata.GAME_SIZE || m_y < 0 || m_y >= CellularAutomata.GAME_SIZE) { continue; }
					
				if(CellularAutomata.cells[m_x][m_y] != null) {
					neighbors.add(CellularAutomata.cells[m_x][m_y]);
				}
			}
		}
		
		return neighbors;
	}
}
