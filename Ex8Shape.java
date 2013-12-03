package ex8;

import java.awt.Color;

public class Ex8Shape {

	private final Ex8Block[] blocks;
	private final Ex8Grid grid;
	private final Ex8ShapeTypes type;
	private Ex8ShapeOrientations orientation;
	private Ex8Shadow shadow;

	public Ex8Shape(Ex8ShapeTypes type, Ex8Grid grid, boolean isShadow) {
		// Initialise the list of blocks
		this.blocks = new Ex8Block[4];

		// Store the grid
		this.grid = grid;

		this.type = type;
		this.setOrientation(Ex8ShapeOrientations.NORTH);

		switch (type) {
		case SQUARE:
			this.createSquare();
			break;
		case LINE:
			this.createLine();
			break;
		case T:
			this.createT();
			break;
		case ZIG1:
			this.createZig1();
			break;
		case ZIG2:
			this.createZig2();
			break;
		case L1:
			this.createL1();
			break;
		case L2:
			this.createL2();
			break;
		default:
			break;
		}

		// Create the shadow
		if (!isShadow) {
			this.shadow = new Ex8Shadow(this, this.getGrid());
		}
	}

	public void moveDown() throws Ex8CannotMoveDownException,
			Ex8GameOverException {
		try {
			for (int i = 0; i < this.getBlocks().length; i++) {
				// Make sure that the block doesn't move off the grid
				if (this.getBlocks()[i].getY() + 1 >= this.getGrid()
						.getGridHeight()) {
					throw new Ex8CannotMoveDownException();
				}

				// Check that there isn't any current shapes in the way
				else if (this.getBlocks()[i].getX() >= 0
						&& this.getBlocks()[i].getY() >= 0
						&& this.getGrid().getBlockList()[this.getBlocks()[i]
								.getX()][this.getBlocks()[i].getY() + 1] != null) {
					throw new Ex8CannotMoveDownException();

				}
			}
		} catch (Ex8CannotMoveDownException e) {
			// Check that no blocks are off the screen
			for (int i = 0; i < this.getBlocks().length; i++) {
				if (this.getBlocks()[i].getY() < 0) {
					throw new Ex8GameOverException();
				}
			}

			throw e;
		}

		// Move the shape down
		for (int i = 0; i < this.getBlocks().length; i++) {
			this.getBlocks()[i].setY(this.getBlocks()[i].getY() + 1);
		}

		// Update the shadow
		if (this.getShadow() != null) {
			this.getShadow().update();
		}
	}

	private void createL2() {
		int halfway = this.getGrid().getGridWidth() / 2;
		this.getBlocks()[0] = this.createBlock(halfway, -3, Color.MAGENTA);
		this.getBlocks()[1] = this.createBlock(halfway, -2, Color.MAGENTA);
		this.getBlocks()[2] = this.createBlock(halfway, -1, Color.MAGENTA);
		this.getBlocks()[3] = this.createBlock(halfway - 1, -1, Color.MAGENTA);
	}

	private void createL1() {
		int halfway = this.getGrid().getGridWidth() / 2;
		// 9400d3
		this.getBlocks()[0] = this.createBlock(halfway - 1, -3,
				Color.decode("#FFBB00"));
		this.getBlocks()[1] = this.createBlock(halfway - 1, -2,
				Color.decode("#FFBB00"));
		this.getBlocks()[2] = this.createBlock(halfway - 1, -1,
				Color.decode("#FFBB00"));
		this.getBlocks()[3] = this.createBlock(halfway, -1,
				Color.decode("#FFBB00"));
	}

	private void createZig2() {
		int halfway = this.getGrid().getGridWidth() / 2;
		this.getBlocks()[0] = this.createBlock(halfway - 1, -1, Color.CYAN);
		this.getBlocks()[1] = this.createBlock(halfway, -1, Color.CYAN);
		this.getBlocks()[2] = this.createBlock(halfway, -2, Color.CYAN);
		this.getBlocks()[3] = this.createBlock(halfway + 1, -2, Color.CYAN);
	}

	private void createSquare() {
		int halfway = this.getGrid().getGridWidth() / 2;
		this.getBlocks()[0] = this.createBlock(halfway - 1, -2, Color.YELLOW);
		this.getBlocks()[1] = this.createBlock(halfway, -2, Color.YELLOW);
		this.getBlocks()[2] = this.createBlock(halfway - 1, -1, Color.YELLOW);
		this.getBlocks()[3] = this.createBlock(halfway, -1, Color.YELLOW);
	}

	private void createT() {
		int halfway = this.getGrid().getGridWidth() / 2;
		this.getBlocks()[0] = this.createBlock(halfway - 1, -2, Color.BLUE);
		this.getBlocks()[1] = this.createBlock(halfway, -2, Color.BLUE);
		this.getBlocks()[2] = this.createBlock(halfway + 1, -2, Color.BLUE);
		this.getBlocks()[3] = this.createBlock(halfway, -1, Color.BLUE);
	}

	private void createZig1() {
		int halfway = this.getGrid().getGridWidth() / 2;
		this.getBlocks()[0] = this.createBlock(halfway - 1, -2, Color.GREEN);
		this.getBlocks()[1] = this.createBlock(halfway, -2, Color.GREEN);
		this.getBlocks()[2] = this.createBlock(halfway, -1, Color.GREEN);
		this.getBlocks()[3] = this.createBlock(halfway + 1, -1, Color.GREEN);
	}

	private void createLine() {
		int halfway = this.getGrid().getGridWidth() / 2;
		this.getBlocks()[0] = this.createBlock(halfway, -4, Color.RED);
		this.getBlocks()[1] = this.createBlock(halfway, -3, Color.RED);
		this.getBlocks()[2] = this.createBlock(halfway, -2, Color.RED);
		this.getBlocks()[3] = this.createBlock(halfway, -1, Color.RED);
	}

	private Ex8Block createBlock(int x, int y, Color color) {
		Ex8Block returnBlock = new Ex8Block(color);
		returnBlock.setX(x);
		returnBlock.setY(y);

		return returnBlock;
	}

	public Ex8Block[] getBlocks() {
		return blocks;
	}

	public Ex8Grid getGrid() {
		return grid;
	}

	public void moveLeft() {
		// Check that we can move the shape left
		for (int i = 0; i < this.getBlocks().length; i++) {
			if (!this.isValidPosition(this.getBlocks()[i].getX() - 1,
					this.getBlocks()[i].getY())) {
				return;
			}
		}

		// Move it left
		for (int i = 0; i < this.getBlocks().length; i++) {
			this.getBlocks()[i].setX(this.getBlocks()[i].getX() - 1);
		}

		// Update the shadow
		this.getShadow().update();
	}

	public void moveRight() {
		// Check that we can move the shape right
		for (int i = 0; i < this.getBlocks().length; i++) {
			if (!this.isValidPosition(this.getBlocks()[i].getX() + 1,
					this.getBlocks()[i].getY())) {
				return;
			}
		}

		// Move it right
		for (int i = 0; i < this.getBlocks().length; i++) {
			this.getBlocks()[i].setX(this.getBlocks()[i].getX() + 1);
		}

		// Update the shadow
		this.getShadow().update();
	}

	private boolean isValidPosition(int x, int y) {
		// Check that the coordinates are valid grid coordinates
		if (x < 0 || x >= this.getGrid().getGridWidth()) {
			return false;
		}
		if (y < 0 || y >= this.getGrid().getGridHeight()) {
			return false;
		}

		// Check that no blocks are currently occupying the position
		if (this.getGrid().getBlockList()[x][y] != null) {
			return false;
		}

		// Valid grid position
		return true;
	}

	public void rotate() {
		switch (this.getType()) {
		case L1:
			this.rotateL1();
			break;
		case L2:
			this.rotateL2();
			break;
		case LINE:
			this.rotateLine();
			break;
		case SQUARE:
			break;
		case T:
			this.rotateT();
			break;
		case ZIG1:
			this.rotateZig1();
			break;
		case ZIG2:
			this.rotateZig2();
			break;
		}

		this.getShadow().update();
		this.getGrid().repaint();
	}

	private void rotateZig2() {
		switch (this.getOrientation()) {
		// 2 3
		// 0 1
		case NORTH:
			if (this.isValidPosition(this.getBlocks()[0].getX(),
					this.getBlocks()[0].getY() - 2)
					&& this.isValidPosition(this.getBlocks()[1].getX() - 1,
							this.getBlocks()[1].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() - 1,
							this.getBlocks()[3].getY() + 1)) {
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 2);
				this.getBlocks()[1].setX(this.getBlocks()[1].getX() - 1);
				this.getBlocks()[1].setY(this.getBlocks()[1].getY() - 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() - 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() + 1);
				this.setOrientation(Ex8ShapeOrientations.EAST);
			}
			break;
		// 0
		// 1 2
		// 3
		case EAST:
			if (this.isValidPosition(this.getBlocks()[0].getX(),
					this.getBlocks()[0].getY() + 2)
					&& this.isValidPosition(this.getBlocks()[1].getX() + 1,
							this.getBlocks()[1].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() + 1,
							this.getBlocks()[3].getY() - 1)) {
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 2);
				this.getBlocks()[1].setX(this.getBlocks()[1].getX() + 1);
				this.getBlocks()[1].setY(this.getBlocks()[1].getY() + 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() + 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() - 1);
				this.setOrientation(Ex8ShapeOrientations.NORTH);
			}
			break;
		// 2 3
		// 0 1
		default:
			break;
		}
	}

	private void rotateZig1() {
		switch (this.getOrientation()) {
		// 0 1
		// 2 3
		case NORTH:
			if (this.isValidPosition(this.getBlocks()[0].getX() + 1,
					this.getBlocks()[0].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() - 1,
							this.getBlocks()[2].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() - 2,
							this.getBlocks()[3].getY())) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() + 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() - 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() - 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() - 2);
				this.setOrientation(Ex8ShapeOrientations.EAST);
			}
			break;
		// 0
		// 2 1
		// 3
		case EAST:
			if (this.isValidPosition(this.getBlocks()[0].getX() - 1,
					this.getBlocks()[0].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() + 1,
							this.getBlocks()[2].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() + 2,
							this.getBlocks()[3].getY())) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() - 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() + 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() + 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() + 2);
				this.setOrientation(Ex8ShapeOrientations.NORTH);
			}
			break;
		// 0 1
		// 2 3
		default:
			break;
		}
	}

	private void rotateT() {
		switch (this.getOrientation()) {
		// 0 1 2
		// 3
		case NORTH:
			if (this.isValidPosition(this.getBlocks()[0].getX() + 1,
					this.getBlocks()[0].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() - 1,
							this.getBlocks()[2].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() - 1,
							this.getBlocks()[3].getY() - 1)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() + 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() - 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() + 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() - 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() - 1);
				this.setOrientation(Ex8ShapeOrientations.EAST);
			}
			break;
		// 0
		// 3 1
		// 2
		case EAST:
			if (this.isValidPosition(this.getBlocks()[0].getX() + 1,
					this.getBlocks()[0].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() - 1,
							this.getBlocks()[2].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() + 1,
							this.getBlocks()[3].getY() - 1)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() + 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() - 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() - 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() + 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() - 1);
				this.setOrientation(Ex8ShapeOrientations.SOUTH);
			}
			break;
		// 3
		// 2 1 0
		case SOUTH:
			if (this.isValidPosition(this.getBlocks()[0].getX() - 1,
					this.getBlocks()[0].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() + 1,
							this.getBlocks()[2].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() + 1,
							this.getBlocks()[3].getY() + 1)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() - 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() + 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() - 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() + 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() + 1);
				this.setOrientation(Ex8ShapeOrientations.WEST);
			}
			break;
		// 2
		// 1 3
		// 0
		case WEST:
			if (this.isValidPosition(this.getBlocks()[0].getX() - 1,
					this.getBlocks()[0].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() + 1,
							this.getBlocks()[2].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() - 1,
							this.getBlocks()[3].getY() + 1)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() - 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() + 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() + 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() - 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() + 1);
				this.setOrientation(Ex8ShapeOrientations.NORTH);
			}
			break;
		// 0 1 2
		// 3
		default:
			break;
		}
	}

	private void rotateLine() {
		switch (this.getOrientation()) {
		// 0
		// 1
		// 2
		// 3
		case NORTH:
			if (this.isValidPosition(this.getBlocks()[0].getX() + 1,
					this.getBlocks()[0].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() - 1,
							this.getBlocks()[2].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() - 2,
							this.getBlocks()[3].getY() - 2)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() + 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() - 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() - 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() - 2);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() - 2);
				this.setOrientation(Ex8ShapeOrientations.EAST);
			}
			break;
		// 3 2 1 0
		case EAST:
			if (this.isValidPosition(this.getBlocks()[0].getX() - 1,
					this.getBlocks()[0].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() + 1,
							this.getBlocks()[2].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() + 2,
							this.getBlocks()[3].getY() + 2)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() - 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() + 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() + 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() + 2);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() + 2);
				this.setOrientation(Ex8ShapeOrientations.NORTH);
			}
			break;
		default:
			break;
		}

	}

	private void rotateL2() {
		switch (this.getOrientation()) {
		// 0
		// 1
		// 3 2
		case NORTH:
			if (this.isValidPosition(this.getBlocks()[0].getX() + 1,
					this.getBlocks()[0].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() - 1,
							this.getBlocks()[2].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX(),
							this.getBlocks()[3].getY() - 2)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() + 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() - 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() - 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() - 2);
				this.setOrientation(Ex8ShapeOrientations.EAST);
			}
			break;
		// 3
		// 2 1 0
		case EAST:
			if (this.isValidPosition(this.getBlocks()[0].getX() - 1,
					this.getBlocks()[0].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() + 1,
							this.getBlocks()[2].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() + 2,
							this.getBlocks()[3].getY())) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() - 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() + 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() - 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() + 2);
				this.setOrientation(Ex8ShapeOrientations.SOUTH);
			}
			break;
		// 2 3
		// 1
		// 0
		case SOUTH:
			if (this.isValidPosition(this.getBlocks()[0].getX() - 1,
					this.getBlocks()[0].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() + 1,
							this.getBlocks()[2].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX(),
							this.getBlocks()[3].getY() + 2)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() - 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() + 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() + 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() + 2);
				this.setOrientation(Ex8ShapeOrientations.WEST);
			}
			break;
		// 0 1 2
		// 3
		case WEST:
			if (this.isValidPosition(this.getBlocks()[0].getX() + 1,
					this.getBlocks()[0].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() - 1,
							this.getBlocks()[2].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() - 2,
							this.getBlocks()[3].getY())) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() + 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() - 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() + 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() - 2);
				this.setOrientation(Ex8ShapeOrientations.NORTH);
			}
			break;
		// 0
		// 1
		// 3 2
		default:
			break;

		}

	}

	private void rotateL1() {
		switch (this.getOrientation()) {
		// 0
		// 1
		// 2 3
		case NORTH:
			if (this.isValidPosition(this.getBlocks()[0].getX() + 1,
					this.getBlocks()[0].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() - 1,
							this.getBlocks()[2].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() - 2,
							this.getBlocks()[3].getY())) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() + 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() - 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() - 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() - 2);
				this.setOrientation(Ex8ShapeOrientations.EAST);
			}
			break;
		// 2 1 0
		// 3
		case EAST:
			if (this.isValidPosition(this.getBlocks()[0].getX() - 1,
					this.getBlocks()[0].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() + 1,
							this.getBlocks()[2].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[3].getX(),
							this.getBlocks()[3].getY() - 2)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() - 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() + 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() + 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() - 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() - 2);
				this.setOrientation(Ex8ShapeOrientations.SOUTH);
			}
			break;
		// 3 2
		// 1
		// 0
		case SOUTH:
			if (this.isValidPosition(this.getBlocks()[0].getX() - 1,
					this.getBlocks()[0].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() + 1,
							this.getBlocks()[2].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX() + 2,
							this.getBlocks()[3].getY())) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() - 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() + 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() + 1);
				this.getBlocks()[3].setX(this.getBlocks()[3].getX() + 2);
				this.setOrientation(Ex8ShapeOrientations.WEST);
			}
			break;
		// 3
		// 0 1 2
		case WEST:
			if (this.isValidPosition(this.getBlocks()[0].getX() + 1,
					this.getBlocks()[0].getY() - 1)
					&& this.isValidPosition(this.getBlocks()[2].getX() - 1,
							this.getBlocks()[2].getY() + 1)
					&& this.isValidPosition(this.getBlocks()[3].getX(),
							this.getBlocks()[3].getY() + 2)) {
				this.getBlocks()[0].setX(this.getBlocks()[0].getX() + 1);
				this.getBlocks()[0].setY(this.getBlocks()[0].getY() - 1);
				this.getBlocks()[2].setX(this.getBlocks()[2].getX() - 1);
				this.getBlocks()[2].setY(this.getBlocks()[2].getY() + 1);
				this.getBlocks()[3].setY(this.getBlocks()[3].getY() + 2);
				this.setOrientation(Ex8ShapeOrientations.NORTH);
			}
			break;
		// 0
		// 1
		// 2 3
		default:
			break;
		}

	}

	public Ex8ShapeTypes getType() {
		return type;
	}

	public void setOrientation(Ex8ShapeOrientations orientation) {
		this.orientation = orientation;
	}

	public Ex8ShapeOrientations getOrientation() {
		return orientation;
	}

	public Ex8Shadow getShadow() {
		return shadow;
	}

}
