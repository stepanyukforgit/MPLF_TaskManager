package mplftaskmanager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame{
    JButton addButton;
    JMenuBar menuBar;
    JMenu fileMenu,editMenu,viewMenu,helpMenu;
    JScrollPane tableScrollPain;
    MPLFTable table;
    Dimension screenSize;
    final int FRAME_WIDTH=1000;
    final int FRAME_HEIGHT=500;
    private final ImageIcon iiRead;
    
    GUI(){
        setLFModel();
        setTitle("MPLF TaskManager");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                if(Func.editing){
                    int exit;
                    exit=JOptionPane.showConfirmDialog(GUI.this,"Сохранить изменения перед выходом?",
                        "Сохранение изменений",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(exit==0){
                        Func.saveTM();
                        System.exit(0);
                    }else if(exit==-1)
                        return;
                    else
                        System.exit(0);
                }
                else
                    System.exit(0);
            }
        });
        screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-FRAME_WIDTH)/2,
                (screenSize.height-FRAME_HEIGHT)/2,FRAME_WIDTH,FRAME_HEIGHT);
        iiRead=new ImageIcon(System.getProperty("user.dir")
                +"\\src\\mplftaskmanager\\icon-read.png");
        menuBar=new JMenuBar();
        createJMenuFile();
        createJMenuEdit();
        createJMenuHelp();
        setJMenuBar(menuBar);
        createAddButton();
        table=new MPLFTable();
        tableScrollPain=new JScrollPane(table);
        add(tableScrollPain);
        setVisible(true);
    }
    public void setLFModel(){
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){
            JOptionPane.showConfirmDialog(GUI.this,"Для лучшего отображения компонентов установите Java 8.0",
                    "Информационное сообщение",JOptionPane.OK_OPTION,JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void createJMenuFile() {
        fileMenu=new JMenu("Файл");
        JMenuItem jmiSave=new JMenuItem("Сохранить");
        jmiSave.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Func.saveTM();
            }
        });
        JMenuItem jmiExportTXT=new JMenuItem("Экспорт в формат TXT");
        jmiExportTXT.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setDialogTitle("Export");
                jfc.setDialogType(JFileChooser.SAVE_DIALOG);
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.setCurrentDirectory(new File("D:\\"));
                jfc.setSelectedFile(new File("Список дел.txt"));
                int open=jfc.showSaveDialog(GUI.this);
                if(open==JFileChooser.APPROVE_OPTION)
                Func.exportTXT(jfc.getSelectedFile());
            }
        });
        JMenuItem jmiExit=new JMenuItem("Выход");
        jmiExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(jmiSave);
        fileMenu.add(jmiExportTXT);
        fileMenu.add(jmiExit);
        menuBar.add(fileMenu);
    }
    private void createJMenuEdit() {
        editMenu=new JMenu("Правка");
        JMenuItem jmiAdd=new JMenuItem("Добавить");
        jmiAdd.addActionListener(new AddButtonListener());
        editMenu.add(jmiAdd);
        menuBar.add(editMenu);
    }
    private void createJMenuHelp() {
        helpMenu=new JMenu("Помощь");
        
        JMenuItem jmiReference=new JMenuItem("Юридические советы");
        jmiReference.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GUI.this,"ПОЧИТАЙ закон!)","Помощь",
                        JOptionPane.OK_OPTION,iiRead);
            }
        });
        helpMenu.add(jmiReference);
        menuBar.add(helpMenu);
    }
    private void createAddButton(){
        addButton=new JButton("Добавить новое задание");
        addButton.addActionListener(new AddButtonListener());
        add(addButton, BorderLayout.SOUTH);
    }
    class AddButtonListener implements ActionListener{
                private String choise;
                private String client;
                private String task;
                private String hasMade;
                private String stage;
                private String spentTime;
                private int spentMoney;
                private boolean state;
                Object[] strState={"Не выполнено","Выполнено"};
        @Override
        public void actionPerformed(ActionEvent e) {             
            choise=JOptionPane.showInputDialog(GUI.this, "Имя клиента:",
                        "Новое задание",JOptionPane.QUESTION_MESSAGE);
                if(choise==null)
                    return;
                else if(choise.length()==0)
                    client="Не указано";
                else
                    client=choise;
            choise=JOptionPane.showInputDialog(GUI.this, "Конечная цель:",
                        "Новое задание",JOptionPane.QUESTION_MESSAGE);
                if(choise==null)
                    return;
                else if(choise.length()==0)
                    task="Не указано";
                else
                    task=choise;
            choise=JOptionPane.showInputDialog(GUI.this, "Проделанная работа:",
                        "Новое задание",JOptionPane.QUESTION_MESSAGE);
                if(choise==null)
                    return;
                else if(choise.length()==0)
                    hasMade="Не указано";
                else
                    hasMade=choise;
            choise=JOptionPane.showInputDialog(GUI.this, "Стадия выполнения:",
                        "Новое задание",JOptionPane.QUESTION_MESSAGE);
                if(choise==null)
                    return;
                else if(choise.length()==0)
                    stage="Не указано";
                else
                    stage=choise;       
            choise=JOptionPane.showInputDialog(GUI.this, "Затраченное время:",
                            "Новое задание",JOptionPane.QUESTION_MESSAGE);
                if(choise==null)
                    return;
                else if(choise.length()==0)
                    spentTime="Не указано";
                else
                    spentTime=choise;        
            try{    
                choise=JOptionPane.showInputDialog(GUI.this, "Расходы:",
                            "Новое задание",JOptionPane.QUESTION_MESSAGE);
                    if(choise==null)
                        return;
                    else
                        spentMoney=Integer.parseInt(choise);        
            }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(GUI.this,"Расходы не указаны или неправильный формат расходов.",
                            "Ошибка",JOptionPane.INFORMATION_MESSAGE);
                    spentMoney=0;        
            }
            choise=(String)JOptionPane.showInputDialog(GUI.this, "Состояние:",
                        "Новое задание", JOptionPane.QUESTION_MESSAGE, null, strState, "Активно");
                if(choise==null)
                    return;
                else
                    state=choise.equals("Выполнено");     
                Func.addTargetObject(client, task, hasMade, stage, spentTime, spentMoney, state);
                Func.editing=true;
                DefaultTableModel dtm=(DefaultTableModel)table.getModel();
                dtm.fireTableDataChanged();
        }
    }
}
