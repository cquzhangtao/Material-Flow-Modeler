package ui.editor.network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;

import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import ui.editor.listener.GraphicElementMouseListener;
import ui.editor.listener.GraphicSaveListener;
import ui.utilities.ImageUtilities;

public class GraphicalFrame extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<GraphicSaveListener> saveListener;
	GraphicalPanel graphicPanel ;
	public GraphicalFrame(GraphicElements graphicalElements,
			GraphicPanelParameters parameters) {
		this.setLayout(new BorderLayout());
		graphicPanel = new GraphicalPanel(graphicalElements,
				parameters);

		this.add(createJToolBar(graphicPanel, parameters), BorderLayout.NORTH);

		this.add(graphicPanel, BorderLayout.CENTER);
		saveListener=new ArrayList<GraphicSaveListener>();
	}

	private JToolBar createJToolBar(final GraphicalPanel graphicPanel,
			GraphicPanelParameters parameters) {
		JToolBar bar = new JToolBar();
		final List<Component> alternativeButtion = new ArrayList<Component>();

		FocusListener listener = new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (!alternativeButtion.contains(e.getOppositeComponent())) {
					for (Component but : alternativeButtion) {
						setButtonBorder(((JButton) but), 0);

					}
				}
				setButtonBorder(((JButton) e.getSource()), 1);

			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (alternativeButtion.contains(e.getOppositeComponent())) {
					setButtonBorder(((JButton) e.getSource()), 0);

				}

			}
		};

		//addButton(bar, "Open", "open.png", this);
		addButton(bar, "Save", "save.png", this);
		addButton(bar, Action.UNDO.name(), "undo.png", graphicPanel);
		addButton(bar, Action.REDO.name(), "redo.png", graphicPanel);
		addButton(bar, Action.COPY.name(), "copy.png", graphicPanel);
		addButton(bar, Action.PASTE.name(), "paste.png", graphicPanel);
		addButton(bar, Action.DELETE.name(), "delete.png", graphicPanel);
		bar.addSeparator();
		
		JButton select = addButton(bar, Action.SELECT.name(), "select.png",
				graphicPanel);
		select.addFocusListener(listener);
		setButtonBorder(select, 1);
		alternativeButtion.add(select);

		JButton line = addButton(bar, Action.ADD_LINE.name(), "line.png",
				graphicPanel);
		line.addFocusListener(listener);
		alternativeButtion.add(line);
		for (GraphicPanelParameter para : parameters.values()) {
			final JButton but = addButton(bar, para.getName(), para.getImage(),
					graphicPanel);
			but.addFocusListener(listener);
			alternativeButtion.add(but);
		}
		bar.addSeparator();
	
		JButton but = addButton(bar, Action.PAN.name(), "pan.png", graphicPanel);
		but.addFocusListener(listener);
		alternativeButtion.add(but);

		addButton(bar, Action.ZOOM_IN.name(), "zoomin.png", graphicPanel);
		addButton(bar, Action.ZOOM_OUT.name(), "zoomout.png", graphicPanel);
		addButton(bar, Action.ZOOM_ALL.name(), "zoomall.png", graphicPanel);
		bar.addSeparator();
		addButton(bar, Action.LABEL_SWITCH.name(), "comment.png", graphicPanel);
		addButton(bar, Action.GRID_SWITCH.name(), "grid.png", graphicPanel);
		return bar;
	}

	private void setButtonBorder(JButton but, int raise) {
		Border empty = BorderFactory.createEmptyBorder(2, 8, 2, 8);
		if(raise==1){
		Border bevel = BorderFactory.createBevelBorder(raise);
		but.setBorder(BorderFactory.createCompoundBorder(empty, bevel));
		}else{
			but.setBorder(empty);
		}
	}

	private JButton addButton(JToolBar bar, String command, String image,
			ActionListener listener) {

		JButton button = new JButton();
		setButtonBorder(button, 0);
		// button.setMargin(new Insets(5,18,5,18));
		button.setToolTipText(command);
		button.setActionCommand(command);
		button.addActionListener(listener);
		bar.add(button);

		BufferedImage bg = null;
		try {
			bg = ImageUtilities.getScaledInstance(
					ImageIO.read(this.getClass().getResource(
							 image)), 22, 22);
			button.setIcon(new ImageIcon(bg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return button;

	}
	
	public void addGraphicFrameSaveListener(GraphicSaveListener l){
		saveListener.add(l);
	}
	public void addGraphicElementMouseListener(GraphicElementMouseListener l){
		graphicPanel.addGraphicElementMouseListener(l);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Save")){
			for(GraphicSaveListener l:saveListener){
				l.onSave();
			}
		}

	}
}
