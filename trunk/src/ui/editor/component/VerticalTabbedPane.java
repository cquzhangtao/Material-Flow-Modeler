package ui.editor.component;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class VerticalTabbedPane extends JTabbedPane {

	@Override
	public void addTab(final String title, final Component component) {

		super.addTab(title, component);
		int index = indexOfComponent(component);
		String tit = "  " + title + "  ";
		JLabel labTab1 = new JLabel(tit);
		// int width=(int) labTab1.getFont().getStringBounds(tit,
		// labTab1.getGraphics().getFontMetrics().getFontRenderContext()).getWidth();
		labTab1.setHorizontalAlignment(JLabel.CENTER);
		//labTab1.setPreferredSize(new Dimension(10, 110));
		labTab1.setUI(new VerticalLabelUI(false));

		setTabComponentAt(index, labTab1);

	}

}
