package ui.editor.network;

import java.awt.Image;
import java.awt.Shape;
import java.io.Serializable;

import basic.entity.IEntity;



public interface GraphicElement extends Serializable {
	public static int NODE = 0;
	public static int LINK = 1;
	public static int PATH = 2;
	public boolean hasImage() ;

	public Shape getShape() ;

	public String getImage() ;

	public void setShape(Shape shape) ;
	public void setImage(String image) ;
	
	public int getGraphicType() ;

	public void setGraphicType(int type) ;
}
