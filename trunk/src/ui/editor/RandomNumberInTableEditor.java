package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

import basic.unit.TimeUnitEnum;
import basic.volume.RandomVolume;

/*
 * The editor button that brings up the dialog.
 */

public class RandomNumberInTableEditor extends DefaultCellEditor
    implements TableCellEditor
{
    private RandomVolumePopupEditor popup;
    private Object currentText ;
    private JButton editorComponent;

    public RandomNumberInTableEditor()
    {
        super(new JTextField());

        setClickCountToStart(1);

        //  Use a JButton as the editor component

        editorComponent = new JButton();
        editorComponent.setBackground(Color.white);
        editorComponent.setBorderPainted(false);
        editorComponent.setContentAreaFilled( false );

        // Make sure focus goes back to the table when the dialog is closed
        editorComponent.setFocusable( false );

        //  Set up the dialog where we do the actual editing

       
    }

    public Object getCellEditorValue()
    {
        return currentText;
    }

    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column)
    {
    	 popup = new RandomVolumePopupEditor((RandomVolume) value);
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                Point p = editorComponent.getLocationOnScreen();
                popup.setLocation(p.x, p.y + editorComponent.getSize().height);
               
                popup.setVisible(true);
               
                fireEditingStopped();
            }
        });

        currentText = value;
        editorComponent.setText(((RandomVolume) value).getShortString());
        return editorComponent;
    }

    /*
    *   Simple dialog containing the actual editing component
    */
    
    private static void createAndShowUI()
    {
    	RandomVolume<TimeUnitEnum> a = new RandomVolume<TimeUnitEnum>();
		a.setUnit(TimeUnitEnum.Day);
		RandomVolume<TimeUnitEnum> b = new RandomVolume<TimeUnitEnum>();
		b.setUnit(TimeUnitEnum.Year);
		RandomVolume<TimeUnitEnum> c = new RandomVolume<TimeUnitEnum>();
		c.setUnit(TimeUnitEnum.Millisecond);
        String[] columnNames = {"Item", "Description"};
        Object[][] data =
        {
            {"Item 1", a},
            {"Item 2", b},
            {"Item 3", c}
        };

        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);

        // Use the popup editor on the second column

        RandomNumberInTableEditor popupEditor = new RandomNumberInTableEditor();
        table.getColumnModel().getColumn(1).setCellEditor( popupEditor );

        JFrame frame = new JFrame("Popup Editor Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JTextField(), BorderLayout.NORTH);
        frame.add( scrollPane );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowUI();
            }
        });
    }

}


