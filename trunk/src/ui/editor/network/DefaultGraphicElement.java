package ui.editor.network;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import basic.entity.Entity;
import basic.entity.GraphicNetworkTreeNodeEntity;




public class DefaultGraphicElement extends Entity implements GraphicElement,Serializable{
	
	private Shape shape;
	private String image;
	private int type;
	private List<DefaultGraphicElement> successors;
	private List<DefaultGraphicElement> predecessors;
	private GraphicNetworkTreeNodeEntity graphicNetworkTreeNodeEntity;

	public DefaultGraphicElement() {
		super();
		successors = new ArrayList<DefaultGraphicElement>();
		predecessors = new ArrayList<DefaultGraphicElement>();
	}

	public DefaultGraphicElement(DefaultGraphicElement ele) {
		super();
		this.setName(ele.getName());
		this.image = ele.getImage();
		this.type = ele.getGraphicType();
		this.successors = new ArrayList<DefaultGraphicElement>(ele.successors);
		this.predecessors = new ArrayList<DefaultGraphicElement>(ele.predecessors);
		if (ele.shape instanceof PathLine) {
			shape = new PathLine((PathLine) ele.shape);
		} else {
			shape = new Rectangle2D.Double(ele.getShape().getBounds2D().getX(),
					ele.getShape().getBounds2D().getY(), ele.getShape()
							.getBounds2D().getWidth(), ele.getShape()
							.getBounds2D().getHeight());
		}
		graphicNetworkTreeNodeEntity=(GraphicNetworkTreeNodeEntity) ele.graphicNetworkTreeNodeEntity.clone();
	}
	
	public DefaultGraphicElement(GraphicNetworkTreeNodeEntity ele){
		successors = new ArrayList<DefaultGraphicElement>();
		predecessors = new ArrayList<DefaultGraphicElement>();
		this.setGUId(ele.getUUID());
		this.setName(ele.getName());
		this.image = ele.getImage();
		this.type = ele.getGraphicType();
		this.shape=ele.getShape();
		graphicNetworkTreeNodeEntity=ele;
	}
	
	public void addGraphicPropertyTo(GraphicNetworkTreeNodeEntity ele){
		ele.setName(this.getName());
		ele.setImage(image);
		ele.setGraphicType(type);
		ele.setShape(shape);
	}

	public void addSuccessor(DefaultGraphicElement element) {
		if (!successors.contains(element)) {
			successors.add(element);
		}
		if (!element.predecessors.contains(this)) {
			element.predecessors.add(this);
		}

	}

	//
	public void removeAllSuccessors() {
		successors.clear();
	}

	public void removeAllPredecessors() {
		predecessors.clear();
	}

	//
	// public void addPredecessor(GraphicElement element) {
	// predecessors.add(element);
	// }
	//
	public void removeSuccessor(DefaultGraphicElement element) {
		successors.remove(element);
	}

	public void removePredecessor(DefaultGraphicElement element) {
		predecessors.remove(element);
	}

	public void move(Point2D endPoint, Point2D startPoint) {

		AffineTransform transform = new AffineTransform();
		transform.translate(endPoint.getX() - startPoint.getX(),
				endPoint.getY() - startPoint.getY());

		if (shape instanceof PathLine) {
			((PathLine) shape).transform(transform);
		} else {
			shape = transform.createTransformedShape(shape);
		}

	}

	public void move(double d) {
		AffineTransform transform = new AffineTransform();
		transform.translate(d, d);

		if (shape instanceof PathLine) {
			((PathLine) shape).transform(transform);
		} else {
			shape = transform.createTransformedShape(shape);
		}
	}

	public void transform(AffineTransform transform) {
		if (shape instanceof PathLine) {
			((PathLine) shape).transform(transform);
		} else {
			shape = transform.createTransformedShape(shape);
		}
	}

	public void scale(double scale, Point2D center) {
		AffineTransform transform = new AffineTransform();
		transform.translate((1 - scale) * center.getX(),
				(1 - scale) * center.getY());
		transform.scale(scale, scale);

		if (shape instanceof PathLine) {
			((PathLine) shape).transform(transform);
		} else {
			shape = transform.createTransformedShape(shape);
		}
	}

	public boolean hasImage() {
		return !(image == null);
	}

	public Shape getShape() {
		return shape;
	}

	public String getImage() {
		return image;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public int getGraphicType() {
		return type;
	}

	public void setGraphicType(int type) {
		this.type = type;
	}

	public List<DefaultGraphicElement> getSuccessors() {
		return successors;
	}

	public List<DefaultGraphicElement> getPredecessors() {
		return predecessors;
	}

	public GraphicNetworkTreeNodeEntity getGraphicNetworkTreeNodeEntity() {
		return graphicNetworkTreeNodeEntity;
	}

	public void setGraphicNetworkTreeNodeEntity(
			GraphicNetworkTreeNodeEntity graphicNetworkTreeNodeEntity) {
		this.graphicNetworkTreeNodeEntity = graphicNetworkTreeNodeEntity;
	}








}
