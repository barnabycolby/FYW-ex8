package ex8;

import java.awt.Color;

public class Ex8Shadow extends Ex8Shape {

	private final Ex8Shape shape;

	public Ex8Shadow(Ex8Shape shape, Ex8Grid grid) {
		super(shape.getType(), grid, true);
		
		this.shape = shape;

		// Set the colour to gray
		for (int i = 0; i < this.getBlocks().length; i++) {
			this.getBlocks()[i].setColor(Color.decode("#AAAAAA"));
		}
	}

	public void update() {
		// Reset shadow position
		for(int i = 0; i < this.getBlocks().length; i++){
			this.getBlocks()[i].setX(this.getShape().getBlocks()[i].getX());
			this.getBlocks()[i].setY(this.getShape().getBlocks()[i].getY());
		}
		
		// Move it down as far as it will go
		try {
			while (true) {
				this.moveDown();
			}
		} catch (Ex8CannotMoveDownException | Ex8GameOverException e) {
			// Do nothing
		}
	}

	public Ex8Shape getShape() {
		return shape;
	}
}
