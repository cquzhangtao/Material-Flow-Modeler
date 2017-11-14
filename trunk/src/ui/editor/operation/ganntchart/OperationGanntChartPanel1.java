package ui.editor.operation.ganntchart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import basic.entity.TreeNode;


import manu.product.Operation;


public class OperationGanntChartPanel1 extends JPanel{
	private Operation operation;
	private Insets insects=new Insets(10,10,10,10);
	private int rowHeight=25;
	private int[]columenWidth={30,120};
	public OperationGanntChartPanel1(Operation operation){
		this.operation=operation;
	}
	
	@Override
	public void paintComponent(Graphics g){
		this.removeAll();
		Graphics2D g2=(Graphics2D) g;
		
		drawTable((Graphics2D) g2.create());
		drawActivity((Graphics2D) g2.create());
		drawAddButton((Graphics2D) g2.create());
		
	}
	
	private void drawAddButton(Graphics2D g2) {
		JTextField text=new JTextField("aaa");
		this.add(text);
		text.setBounds(100, 100, 50, 40);
		//text.paint(g2);
		
	}

	private void drawActivity(Graphics2D g2) {
		// TODO Auto-generated method stub
		for(TreeNode act:operation.getActivities()){
			
		}
	}

	private void drawTable(Graphics2D g2){
		g2.setColor(Color.white);
		g2.fillRect(insects.left, insects.top,getWidth()-insects.right-insects.left, getHeight()/rowHeight*rowHeight);
		g2.setColor(new Color(220,220,220));
		int y;
		for(y=insects.top;y<getHeight()-insects.bottom;y+=rowHeight){
			g2.drawLine(insects.left, y, getWidth()-insects.right, y);
		}
		int x=insects.left;
		for(int i=-1;i<=columenWidth.length;i++){
			g2.drawLine(x, insects.top , x,y-rowHeight);
			if(i<columenWidth.length-1)
			x=x+columenWidth[i+1];
			else
				x=getWidth()-insects.right;
		}
		g2.dispose();

	}

}
