package ui.editor.network;

import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;

import basic.DefaultNode;

public class GraphicNode<A> extends DefaultNode<A> implements Serializable{
	private Shape shape;
	private Image image;
	
public void move(Point2D fromPoint, Point2D toPoint) {
		
		AffineTransform transform = new AffineTransform();
		transform.translate(fromPoint.getX()
				- toPoint.getX(), fromPoint.getY()
						- toPoint.getY());
		
		if(shape instanceof PathLine){
			((PathLine) shape).transform(transform);
		}else{
			shape= transform.createTransformedShape(shape);
		}
	}

	public boolean hasImage() {
		return !(image == null);
	}

	public Shape getShape() {
		return shape;
	}

	public Image getImage() {
		return image;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	

}
