import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class WindowManager {
	public static void CreateWindow() throws FontFormatException, IOException {
		JFrame frame = new JFrame("LifeEngine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(screenSize);
		layeredPane.setBounds(0, 0, screenSize.width, screenSize.height);
		frame.setContentPane(layeredPane);	
		
		frame.setResizable(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
				
		JPanel background = new JPanel();
		background.setBackground(Color.BLACK);
		background.setBounds(0, 0, screenSize.width, screenSize.height);

		layeredPane.add(background, Integer.valueOf(0));
		GraphicsManager.createGrid(screenSize, layeredPane, 1, frame);
		GraphicsManager.createCellGraphics(screenSize, layeredPane, 2, frame);
		GraphicsManager.createColorVeiwer(screenSize, layeredPane, 3, frame);
		GraphicsManager.addUI(screenSize, layeredPane, 4, frame);

		layeredPane.revalidate();  // Ensure the layout manager (if present) updates
		layeredPane.repaint();
		    
		UserInput input = new UserInput(frame);
		
		frame.getContentPane().addMouseListener(input);
		frame.addKeyListener(input);
		frame.addMouseWheelListener(input);
		
		frame.setVisible(true);
	}
}
