package ex8;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Ex8Grid extends JPanel implements ActionListener, KeyListener {
	private Ex8Block[][] blockList;
	private Ex8Shape fallingShape;
	private final int blockSize;
	private final Timer timer;
	private final int blockBorderWidth;
	private Ex8InfoPanel infoPanel;

	public Ex8Grid(int width, int height, int blockSize, int blockBorderWidth) {
		// Initialise block list
		this.setBlockList(new Ex8Block[width][height]);

		// Store provided information
		this.blockSize = blockSize;
		this.blockBorderWidth = blockBorderWidth;

		this.generateFallingShape();

		// Create the timer to allow animation
		this.timer = new Timer(200, this);
	}

	public void start() {
		this.getTimer().start();
	}

	public void stop() {
		this.getTimer().stop();
	}

	private Ex8ShapeTypes randomType() {
		Random random = new Random();
		return Ex8ShapeTypes.values()[random
				.nextInt(Ex8ShapeTypes.values().length)];
	}

	public void addShape(Ex8Shape shape) {
		for (int i = 0; i < shape.getBlocks().length; i++) {
			this.addBlock(shape.getBlocks()[i]);
		}
	}

	private void addBlock(Ex8Block block) {
		this.getBlockList()[block.getX()][block.getY()] = block;
	}

	public int getGridWidth() {
		return this.getBlockList().length;
	}

	public int getGridHeight() {
		return this.getGridWidth() > 0 ? this.getBlockList()[0].length : 0;
	}

	public Ex8Block[][] getBlockList() {
		return blockList;
	}

	public void setBlockList(Ex8Block[][] blockList) {
		this.blockList = blockList;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Paint the blocks already on the screen
		for (int i = 0; i < this.getBlockList().length; i++) {
			for (int j = 0; j < this.getBlockList()[i].length; j++) {
				if (this.getBlockList()[i][j] != null) {
					drawBlock(g, i, j, this.getBlockList()[i][j].getColor());
				} else {
					// Draw an empty block
					drawBlock(g, i, j, Color.WHITE);
				}
			}
		}

		// Paint the currently falling shape
		if (this.getFallingShape() != null) {
			for (int i = 0; i < this.getFallingShape().getBlocks().length; i++) {
				this.drawBlock(g, this.getFallingShape().getBlocks()[i].getX(),
						this.getFallingShape().getBlocks()[i].getY(), this
								.getFallingShape().getBlocks()[i].getColor());
			}
		}

		// Paint the currently falling shape shadow
		for (int i = 0; i < this.getFallingShape().getShadow().getBlocks().length; i++) {
			this.drawBlock(g, this.getFallingShape().getShadow().getBlocks()[i]
					.getX(), this.getFallingShape().getShadow().getBlocks()[i]
					.getY(), this.getFallingShape().getShadow().getBlocks()[i]
					.getColor());
		}
	}

	private void drawBlock(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		int left = ((this.getBlockSize() + this.getBlockBorderWidth()) * x)
				+ this.getBlockBorderWidth();
		int top = ((this.getBlockSize() + this.getBlockBorderWidth()) * y)
				+ this.getBlockBorderWidth();
		g.fillRect(left, top, this.getBlockSize(), this.getBlockSize());
	}

	public int getBlockSize() {
		return blockSize;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			this.getFallingShape().moveDown();
		} catch (Ex8CannotMoveDownException e) {
			// Add the shape to the list of static blocks
			this.addShape(this.getFallingShape());

			// Remove completed rows
			this.removeCompletedRowsCausedByShape(this.getFallingShape());

			// Create a new falling shape
			generateFallingShape();
		} catch (Ex8GameOverException e) {
			this.stop();
			this.getInfoPanel().storeHighscore();
		} finally {
			this.repaint();
		}

	}

	private void generateFallingShape() {
		this.setFallingShape(new Ex8Shape(this.randomType(), this, false));
	}

	private void removeCompletedRowsCausedByShape(Ex8Shape shape) {
		// Sort blocks list by ascending order
		ArrayList<Integer> rowsToDelete = new ArrayList<Integer>();

		for (int i = 0; i < shape.getBlocks().length; i++) {
			if (!rowsToDelete.contains(shape.getBlocks()[i].getY())
					&& this.isRowComplete(shape.getBlocks()[i].getY())) {
				rowsToDelete.add(shape.getBlocks()[i].getY());
			}
		}

		// Sort list into ascending order because we want to delete the top row
		// first
		Collections.sort(rowsToDelete);

		for (int i = 0; i < rowsToDelete.size(); i++) {
			this.deleteRow(rowsToDelete.get(i));
		}
	}

	private void deleteRow(int y) {
		// Delete the completed row
		for (int i = 0; i < this.getGridWidth(); i++) {
			this.getBlockList()[i][y] = null;
		}

		// Move higher rows down
		for (int i = y - 1; i > 0; i--) {
			for (int j = 0; j < this.getGridWidth(); j++) {
				this.getBlockList()[j][i + 1] = this.getBlockList()[j][i];
			}
		}

		// Add score
		if (this.getInfoPanel() != null) {
			this.getInfoPanel().addPointsToScore(300);
		}
	}

	private boolean isRowComplete(int y) {
		for (int i = 0; i < this.getGridWidth(); i++) {
			if (this.getBlockList()[i][y] == null) {
				return false;
			}
		}

		return true;
	}

	public Ex8Shape getFallingShape() {
		return fallingShape;
	}

	public void setFallingShape(Ex8Shape fallingShape) {
		this.fallingShape = fallingShape;
	}

	public Timer getTimer() {
		return timer;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			this.getFallingShape().moveLeft();
			this.repaint();
			break;
		case KeyEvent.VK_RIGHT:
			this.getFallingShape().moveRight();
			this.repaint();
			break;
		case KeyEvent.VK_DOWN:
			if (this.getTimer().isRunning()) {
				if (this.getInfoPanel() != null) {
					this.getInfoPanel().addPointsToScore(10);
				}
				this.getTimer().setDelay(30);
			}
			break;
		case KeyEvent.VK_UP:
			this.getFallingShape().rotate();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.getTimer().setDelay(
					this.getInfoPanel() != null ? this.getInfoPanel()
							.getInterval() : 200);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public int getBlockBorderWidth() {
		return blockBorderWidth;
	}

	public Ex8InfoPanel getInfoPanel() {
		return infoPanel;
	}

	public void addInfoPanel(Ex8InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	public void reset() {
		int width = this.getBlockList().length;
		int height = 0;
		if (width > 0) {
			height = this.getBlockList()[0].length;
		}

		this.setBlockList(new Ex8Block[width][height]);

		this.generateFallingShape();
	}
}
