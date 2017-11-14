package ui.editor.network;

import java.io.Serializable;

public class GraphicPanelParameter implements Serializable{
	private String name;
	private int width;
	private int height;
	private String image;
	private Action action;
	private Class objectClass;
	private int index=0;
	
	public GraphicPanelParameter(String name,int width,int height,String image,Action action,Class objectClass)
	{
	this.name=name;
	this.width=width;
	this.height=height;
	this.image=image;
	this.action=action;
	this.objectClass=objectClass;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public String getImage() {
		return image;
	}
	public Action getAction() {
		return action;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setElementType(Action action) {
		this.action = action;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public Class getObjectClass() {
		return objectClass;
	}
	public void setObjectClass(Class objectClass) {
		this.objectClass = objectClass;
	}
	public int getIndex() {
		return ++index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	


}
