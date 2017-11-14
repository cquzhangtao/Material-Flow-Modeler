package basic.entity;

import java.awt.Dimension;

import basic.Point;


public class SpatialEntity extends Entity{
	private Point position;
	private Dimension size;

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
}
