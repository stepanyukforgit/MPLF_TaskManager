package mplftaskmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Func{
    public static boolean editing=false;
    public static void saveTM(){
        try(FileOutputStream fos=new FileOutputStream("employee.txt");
                ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(Main.employee);
            editing=false;
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null,"Ошибка при сохранении.",
                    "Информационное сообщение",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void downloadTM(){
        try(FileInputStream fis = new FileInputStream("employee.txt");
                ObjectInputStream ois=new ObjectInputStream(fis);){
            Main.employee=(ArrayList<TargetObject>)ois.readObject();
        }catch(FileNotFoundException fnfe){
            JOptionPane.showMessageDialog(null,"Файл employee.txt не найден.",
                    "Информационное сообщение",JOptionPane.INFORMATION_MESSAGE);
        }catch(ClassNotFoundException cnfe){
            JOptionPane.showMessageDialog(null,"Ошибка при загрузке класса.",
                    "Информационное сообщение",JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null,"Ошибка при загрузке.",
                    "Информационное сообщение",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void addTargetObject(String client,String task,String hasMade,String stage,
            String spentTime, int spentMoney,boolean state){
        Main.employee.add(new TargetObject(client,task,hasMade,stage,
            spentTime,spentMoney,state));
    }
    public static void exportTXT(File exportFile){
        try(FileWriter fw=new FileWriter(exportFile)){
            int n;
            for(int i=0;i<Main.employee.size();i++){
                n=i+1;
                fw.write(n+")"+"\r\n");
                fw.write("Клиент: "+Main.employee.get(i).getClient()+";"+"\r\n");
                fw.write("Цель: "+Main.employee.get(i).getTask()+";"+"\r\n");
                fw.write("Выполнено: "+Main.employee.get(i).getHasMade()+";"+"\r\n");
                fw.write("Стадия: "+Main.employee.get(i).getStage()+";"+"\r\n");
                fw.write("Время: "+Main.employee.get(i).getSpendTime()+";"+"\r\n");
                fw.write("Расходы: "+Main.employee.get(i).getCosts()+";"+"\r\n");
                if(Main.employee.get(i).getState())
                    fw.write("Состояние: "+"выполнено."+"\r\n"+"\r\n");
                else
                    fw.write("Состояние: "+"не выполнено."+"\r\n"+"\r\n");
            }
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null,"Ошибка при экспорте.",
                    "Информационное сообщение",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
