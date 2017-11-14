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
import java.util.ArrayList;
import java.util.List;

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

public class MultiSelectInTableEditor extends DefaultCellEditor
    implements TableCellEditor
{
    private MultiSelectPopupEditor popup;
    private Object currentText ;
    private JButton editorComponent;
	private List data;

    public MultiSelectInTableEditor(List data)
    {
        super(new JTextField());
this.data=data;
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
        final JTable table, Object value, boolean isSelected, int row, final int column)
    {
    	 popup = new MultiSelectPopupEditor(data,(List) value);
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                Point p = editorComponent.getLocationOnScreen();
                popup.setLocation(p.x, p.y + editorComponent.getSize().height);
                popup.pack();
                popup.setSize(table.getColumnModel().getColumn(column).getWidth(), popup.getHeight());
                popup.setVisible(true);
               
                fireEditingStopped();
            }
        });

        currentText = value;
        if(value!=null)
        editorComponent.setText(value.toString());
        else
        	 editorComponent.setText("Wait...");
        return editorComponent;
    }

    /*
    *   Simple dialog containing the actual editing component
    */
    
    private static void createAndShowUI()
    {
    	Integer a1=new Integer(1);
    	Integer a2=new Integer(2);
    	Integer a3=new Integer(3);
    	Integer a4=new Integer(4);
    	Integer a5=new Integer(5);
  List a=new ArrayList();
  a.add(a1);
  a.add(a2);
  a.add(a3);
  a.add(a4);
  List b=new ArrayList();
  b.add(a2);
 b.add(a3);

  List c=new ArrayList();
  c.add(a2);
  c.add(a3);

  List d=new ArrayList();
  d.add(a1);
  d.add(a2);
  d.add(a3);
  d.add(a4);
  d.add(a5);
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

        MultiSelectInTableEditor popupEditor = new MultiSelectInTableEditor(d);
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


