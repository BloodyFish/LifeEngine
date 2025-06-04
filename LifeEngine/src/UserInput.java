import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class UserInput implements MouseListener, KeyListener, MouseWheelListener {
	
	JFrame frame;
	
	public UserInput(JFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!CellularAutomata.gameStarted) {
			int x = e.getX();
			int y = e.getY();
			
			int cellX = (int)Math.floor(x / Cell.getCellSize(frame));
			int cellY = (int)Math.floor(y / Cell.getCellSize(frame));
			
			CellularAutomata.updateCell(cellX, cellY);
			
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {	
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			GraphicsManager.iterationCounter.repaint();
			CellularAutomata.gameStarted = !CellularAutomata.gameStarted;
		}
		
		if(!CellularAutomata.gameStarted) {
			if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
				CellularAutomata.setUpCells();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(!CellularAutomata.gameStarted) {
			if(-e.getWheelRotation() > 0) {
				CellularAutomata.colorIndex++;
			}
			else if(-e.getWheelRotation() < 0) {
				CellularAutomata.colorIndex--;
			}
			
			if(CellularAutomata.colorIndex < 0) {CellularAutomata.colorIndex = CellularAutomata.colors.length - 1;}
			if(CellularAutomata.colorIndex >= CellularAutomata.colors.length) {CellularAutomata.colorIndex = 0;}
			
			GraphicsManager.colorViewer.repaint();
			
		}
		
	}
}
