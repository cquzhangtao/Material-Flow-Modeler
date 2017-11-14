package ui.editor.network;

import javax.swing.JFrame;

public class test {
	
	public  static void main(String args[]){
		JFrame frame=new JFrame();
		GraphicPanelParameters parameters=new GraphicPanelParameters();
//		parameters.add( new GraphicPanelParameter("Machine",30,30,"machine.png",Action.ADD_NODE));
//		parameters.add( new GraphicPanelParameter("Worker",10,30,"worker.png",Action.ADD_NODE));
//		parameters.add( new GraphicPanelParameter("Storage",30,30,"Storage.png",Action.ADD_NODE));
//		parameters.add( new GraphicPanelParameter("Road",6,30,"Road.gif",Action.ADD_PATH));
		
		frame.add(new GraphicalFrame(new GraphicElements(),parameters));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.setVisible(true);
		
	}

}
