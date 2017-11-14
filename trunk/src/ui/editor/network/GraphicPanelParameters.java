package ui.editor.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphicPanelParameters extends HashMap<String,GraphicPanelParameter>implements Serializable{
public void add(GraphicPanelParameter parameter){
	this.put(parameter.getName(), parameter);
}
}
