package mplftaskmanager;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class MPLFTable extends JTable{
    private final ImageIcon iiDelete;
    private final String[] columnNames={"№","Клиент","Цель","Выполнено","Стадия",
            "Время","Расходы","Состояние","Удалить"};
    MPLFTable(){
        iiDelete=new ImageIcon(System.getProperty("user.dir")
                +"\\src\\mplftaskmanager\\icon-delete.png");
        setModel(new MPLFTableModel());
        setFillsViewportHeight(true);
        setRowHeight(40);
        setShowGrid(true);
        setGridColor(Color.GRAY);
        arrangeColumns();
        setAutoCreateRowSorter(true);
        updateRowsHeight();
    }
    private void arrangeColumns(){
        TableColumn tc;
        for(int i=0;i<columnNames.length;i++){
            tc=getColumnModel().getColumn(i);
            switch(i){
                case 0:{
                    tc.setMaxWidth(25);
                }
                case 1:{
                    tc.setPreferredWidth(130);
                    tc.setCellRenderer(new MPLFTextAreaRender());
                    tc.setCellEditor(new MPLFTextAreaEditor(new JTextField()));
                }
                break;
                case 2:{
                    tc.setPreferredWidth(160);
                    tc.setCellRenderer(new MPLFTextAreaRender());
                    tc.setCellEditor(new MPLFTextAreaEditor(new JTextField()));
                }
                break;
                case 3:{
                    tc.setPreferredWidth(200);
                    tc.setCellRenderer(new MPLFTextAreaRender());
                    tc.setCellEditor(new MPLFTextAreaEditor(new JTextField()));
                }
                break;
                case 4:{
                    tc.setPreferredWidth(195);
                    tc.setCellRenderer(new MPLFTextAreaRender());
                    tc.setCellEditor(new MPLFTextAreaEditor(new JTextField()));
                }
                break;
                case 5:{
                    tc.setPreferredWidth(75);
                    tc.setCellRenderer(new MPLFTextAreaRender());
                    tc.setCellEditor(new MPLFTextAreaEditor(new JTextField()));
                }
                break;
                case 6:{
                    tc.setMaxWidth(80);
                    tc.setCellRenderer(new MPLFLabelIntRender());
                }
                break;
                case 7:{
                    tc.setMaxWidth(75);
                    tc.setCellRenderer(new MPLFCheckBoxRender());
                }
                break;
                case 8:{
                    tc.setMaxWidth(60);
                    tc.setCellRenderer(new MPLFButtonRender(iiDelete));
                    tc.setCellEditor(new MPLFButtonEditor(new JCheckBox(),iiDelete));
                }
                break;
            }
        }
    }
    private void updateRowsHeight(){
        for(int row=0;row<getRowCount();row++){
            int rowHeight=getRowHeight(row);
            for(int column=0;column<6;column++){
                Component comp=prepareRenderer(getCellRenderer(row, column), row, column);
                rowHeight=Math.max(rowHeight, comp.getPreferredSize().height);
            }
            setRowHeight(row, rowHeight);
        }
    }
    class MPLFTableModel extends DefaultTableModel{
        
        MPLFTableModel(){
            addTableModelListener(new TableModelListener(){
                @Override
                public void tableChanged(TableModelEvent e) {
                    updateRowsHeight();
                }
            });
        }
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex){
            return columnIndex>0;
        }
        @Override
        public int getRowCount() {
            return Main.employee.size();
        }
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Object value=null;
            if(Main.employee.isEmpty())
                return value;
            else{
                TargetObject to=Main.employee.get(rowIndex);
                switch(columnIndex){
                case 0:value=rowIndex+1;
                break;
                case 1:value=to.getClient();
                break;
                case 2:value=to.getTask();
                break;
                case 3:value=to.getHasMade();
                break;
                case 4:value=to.getStage();
                break;
                case 5:value=to.getSpendTime();
                break;
                case 6:value=to.getCosts();
                break;
                case 7:value=to.getState();
                break;
                }
            return value;
            }
        }
        @Override
        public String getColumnName(int colomn){
            return columnNames[colomn];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex){
            if(getValueAt(0,columnIndex)==null){
                return Object.class;
            }
            return getValueAt(0,columnIndex).getClass();
        }
        @Override
        public void setValueAt(Object value,int rowIndex, int columnIndex){
            TargetObject to=Main.employee.get(rowIndex);
            switch(columnIndex){
                case 0:
                break;
                case 1:to.setClient((String)value);
                break;
                case 2:to.setTask((String)value);
                break;
                case 3:to.setHasMade((String)value);
                break;
                case 4:to.setStage((String)value);
                break;
                case 5:to.setSpendTime((String)value);
                break;
                case 6:to.setCosts((int)value);
                break;
                case 7:to.setState((boolean)value);
                break;
            }
        fireTableDataChanged();
        Func.editing=true;
        }
        @Override
        public void removeRow(int row){
            Main.employee.remove(row);
            fireTableRowsDeleted(row, row);
        }
    }
    class MPLFTextAreaRender extends JTextArea implements TableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table,Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setWrapStyleWord(true);
            setLineWrap(true);
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getPreferredWidth(),
                    Short.MAX_VALUE);
            setOpaque(true);
            setBorder(new EmptyBorder(3,3,0,5));

            if(isSelected){
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
            else{
                setBackground(new Color(204,204,255));
                setForeground(table.getForeground());
            }
            return this;
        }
    }
    class MPLFTextAreaEditor extends DefaultCellEditor{
        JTextArea jta;
        public MPLFTextAreaEditor(JTextField textField) {
            super(textField);
            jta=new JTextArea();
            jta.setLineWrap(true);
            jta.setWrapStyleWord(true);
            jta.setBorder(new EmptyBorder(3,3,0,3));
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            jta.setText((String)value);
            JScrollPane jsp=new JScrollPane(jta);
            return jsp;
        }
        @Override
        public Object getCellEditorValue() {
            return jta.getText();
        }
    }    
    class MPLFLabelIntRender extends JLabel implements TableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table,Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            if(isSelected){
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
            else{
                setBackground(new Color(204,204,255));
                setForeground(table.getForeground());
            }    
            return this;
        }
    }    
    class MPLFCheckBoxRender extends JCheckBox implements TableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            setOpaque(true);
            if(isSelected){
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
            else{
                setBackground(new Color(204,204,255));
                setForeground(table.getForeground());
            }
            setHorizontalAlignment(SwingConstants.CENTER);
            setSelected((boolean)value);
            return this;
        }
    }
    class MPLFButtonRender extends JButton implements TableCellRenderer{
        MPLFButtonRender(ImageIcon ii){
            super(ii);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            setOpaque(false);
            setBackground(Color.LIGHT_GRAY);
            return this;
        }  
    }
    class MPLFButtonEditor extends DefaultCellEditor{
        ImageIcon ii;
        JButton jb;
        public MPLFButtonEditor(JCheckBox checkBox, ImageIcon ii) {
            super(checkBox);
            this.ii=ii;
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column){
            jb=new JButton(ii);
            jb.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirm;
                    confirm=JOptionPane.showConfirmDialog(null,"Удалить задание?","Удаление задания",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirm!=0)
                        return;
                    MPLFTableModel mtm=(MPLFTableModel)table.getModel();
                    mtm.removeRow(row);
                    Func.editing=true;
                    cancelCellEditing();
                }
            });
            return jb;
        }
    }    
}
