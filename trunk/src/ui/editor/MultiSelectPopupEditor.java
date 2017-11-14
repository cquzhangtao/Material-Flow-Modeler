package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.editor.component.CheckBoxListModel;

import basic.entity.IEntity;
import basic.volume.RandomVolume;

public class MultiSelectPopupEditor<T> extends JDialog {

	public MultiSelectPopupEditor(final List<T> data, final List<T> selectedData) {
		super((Frame) null, null, true);
		// setSize(220, 265);
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout());
		JButton close = new JButton("Back");
		close.setBackground(Color.white);
		close.setHorizontalAlignment(JButton.RIGHT);
		close.setOpaque(true);
		close.setToolTipText("Close");
		close.setBounds(200, 0, 12, 12);
		close.setMargin(new Insets(0, 0, 0, 0));
		close.setFont(close.getFont().deriveFont(1));
		this.add(close, BorderLayout.NORTH);

		final JList<T> list = new JList<T>();
		list.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		list.setModel(new CheckBoxListModel<T>(data));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(list, BorderLayout.CENTER);
		

		list.setCellRenderer(new ListCellRenderer<T>() {

			@Override
			public Component getListCellRendererComponent(
					JList<? extends T> list, T value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JCheckBox cb = new JCheckBox(value.toString());
				cb.setBackground(Color.white);
				//cb.putClientProperty("Value", value);
				cb.setOpaque(true);
				if (selectedData.contains(value)) {

					// selectedData.remove(value);
					cb.setSelected(true);
				} else {

					// selectedData.add(value);
					cb.setSelected(false);
				}
				return cb;
			}
		});
//
//		list.addListSelectionListener(new ListSelectionListener() {
//
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				// TODO Auto-generated method stub
//				if (e.getLastIndex() == -1||e.getValueIsAdjusting()) {
//					return;
//				}
//				
//				if (selectedData.contains(data.get(e.getLastIndex()))) {
//					selectedData.remove(data.get(e.getLastIndex()));
//				} else {
//					selectedData.add(data.get(e.getLastIndex()));
//				}
//			}
//		});
		list.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int index=list.locationToIndex(e.getPoint());

				if(index!=-1){
					 Object obj = list.getModel().getElementAt(index);

					if (selectedData.contains(obj)) {
						selectedData.remove(obj);
					} else {
						selectedData.add((T) obj);
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});

		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MultiSelectPopupEditor.this.setVisible(false);

			}
		});

	}
}