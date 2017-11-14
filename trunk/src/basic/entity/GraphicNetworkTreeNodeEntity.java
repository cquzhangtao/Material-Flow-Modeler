package basic.entity;

import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import ui.editor.network.GraphicElement;
import ui.editor.network.PathLine;

public class GraphicNetworkTreeNodeEntity extends NetworkTreeNodeEntity implements GraphicElement{
	private Shape shape;
	private String image;
	private int grahpicType;
	
	public GraphicNetworkTreeNodeEntity(){
		super();
		
		grahpicType=GraphicElement.NODE;
	}
	
	@Override 
	public Object clone(){
		GraphicNetworkTreeNodeEntity graphicNetworkTreeNodeEntity=new GraphicNetworkTreeNodeEntity();
		clone(graphicNetworkTreeNodeEntity);
		return graphicNetworkTreeNodeEntity;
	}
	public void clone(GraphicNetworkTreeNodeEntity graphicNetworkTreeNodeEntity){
		graphicNetworkTreeNodeEntity.grahpicType=grahpicType;
		graphicNetworkTreeNodeEntity.image=image;
		if (shape instanceof PathLine) {
			graphicNetworkTreeNodeEntity.shape = new PathLine((PathLine) shape);
		} else {
			graphicNetworkTreeNodeEntity.shape = new Rectangle2D.Double(getShape().getBounds2D().getX(),
					getShape().getBounds2D().getY(), getShape()
							.getBounds2D().getWidth(), getShape()
							.getBounds2D().getHeight());
		}
		super.clone(graphicNetworkTreeNodeEntity);
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
		return grahpicType;
	}

	public void setGraphicType(int type) {
		this.grahpicType = type;
	}
}
