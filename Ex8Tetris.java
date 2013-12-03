package ex8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Ex8Tetris {

	private static void createGUI() {
		JFrame frame = new JFrame("Ex8Tetris");
		GridLayout gridLayout = new GridLayout(1, 2);
		frame.setLayout(gridLayout);

		// GUI settings
		int blockSize = 12;
		int gridWidth = 20;
		int gridHeight = 30;
		int blockBorderWidth = 1;
		int actualWidth = ((blockBorderWidth + blockSize) * gridWidth)
				+ blockBorderWidth;
		int actualHeight = ((blockBorderWidth + blockSize) * gridHeight)
				+ blockBorderWidth;

		// Create grid panel
		Ex8Grid gridPanel = new Ex8Grid(gridWidth, gridHeight,
				blockSize, blockBorderWidth);
		gridPanel.setPreferredSize(new Dimension(actualWidth, actualHeight));
		gridPanel.setBackground(Color.BLACK);

		// Create information panel
		Ex8InfoPanel infoPanel = new Ex8InfoPanel(frame, gridPanel);
		infoPanel.setPreferredSize(new Dimension(actualWidth, actualHeight));
		gridPanel.addInfoPanel(infoPanel);
		
		// Add a keylistener to the frame
		frame.addKeyListener(gridPanel);

		// Frame configuration
		frame.add(gridPanel);
		frame.add(infoPanel);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * EXTENSIONS
		 * ===================
		 * - More difficult the higher the users score
		 *   Blocks start to fall faster
		 * - Added shadows
		 */
		
		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				createGUI();
			}

		});

	}

}
