package ui.editor.network;

import java.awt.BasicStroke;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PathLine implements Shape,Serializable{

	private Path2D line;
	private Shape shape;

	private double width = 6;

	public PathLine(double pathWidth) {
		width = pathWidth;

		line = new Path2D.Double();

	}
	
	public PathLine(PathLine pl){
		this.width=pl.width;
		this.line=new Path2D.Double(pl.line);
		this.shape=new Path2D.Double(pl.shape);
	}

	public Point2D getStartPoint() {
		PathIterator interator = line.getPathIterator(null);
		while (!interator.isDone()) {
			double[] coords = new double[6];
			interator.currentSegment(coords);
			return new Point2D.Double(coords[0], coords[1]);

		}
		return null;
	}

	public Point2D getEndPoint() {
		PathIterator interator = line.getPathIterator(null);
		double[] coords = new double[6];
		while (!interator.isDone()) {

			interator.currentSegment(coords);
			interator.next();

		}
		return new Point2D.Double(coords[0], coords[1]);
	}

	public void moveTo(Point2D point) {

		line.moveTo(point.getX(), point.getY());
		BasicStroke stroke = new BasicStroke((int) width, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_ROUND);
		this.shape = stroke.createStrokedShape(line);
	}

	public void lineTo(Point2D point) {

		line.lineTo(point.getX(), point.getY());
		BasicStroke stroke = new BasicStroke((int) width, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_ROUND);
		this.shape = stroke.createStrokedShape(line);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return shape.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return shape.getBounds2D();
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return shape.contains(x, y);
	}

	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return shape.contains(p);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return shape.intersects(x, y, w, h);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return shape.intersects(r);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return shape.contains(x, y, w, h);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return shape.contains(r);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return shape.getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return shape.getPathIterator(at, flatness);
	}

	public void transform(AffineTransform transform) {
		line = (Path2D) transform.createTransformedShape(line);
		shape = transform.createTransformedShape(shape);

	}

}
