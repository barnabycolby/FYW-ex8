package ex8;

import java.awt.Color;

public class Ex8Block {
	private Color color;
	private int x;
	private int y;
	
	public Ex8Block(Color color){
		this.setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
