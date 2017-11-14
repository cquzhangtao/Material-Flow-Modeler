package ui.editor;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import basic.volume.RandomVolume;

public class RandomVolumePopupEditor extends JDialog {

	public RandomVolumePopupEditor(RandomVolume volume1) {
		super((Frame) null, null, true);
		setSize(220, 265);
		setUndecorated(true);
		JButton close = new JButton("-");
		close.setToolTipText("Close");
		close.setBounds(200, 0, 12, 12);
		close.setMargin(new Insets(0, 0, 0, 0));
		close.setFont(close.getFont().deriveFont(1));
		this.add(close);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new RandomNumberEditor(volume1),
				BorderLayout.CENTER);
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RandomVolumePopupEditor.this.setVisible(false);

			}
		});

	}
}