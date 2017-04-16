package mplftaskmanager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
    public static void exportPDF(File file){
        Document doc=new Document(PageSize.A4.rotate(),30,30,30,30);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            BaseFont bf=BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font=new Font(bf,10);
            PdfPTable table=new PdfPTable(9);
            table.setWidthPercentage(100);
            PdfPCell cell;
            cell=new PdfPCell(new Phrase("Клиент",font));
            table.addCell(cell);
            cell=new PdfPCell(new Phrase("Цель",font));
            table.addCell(cell);
            cell=new PdfPCell(new Phrase("Выполнено",font));
            cell.setColspan(2);
            table.addCell(cell);
            cell=new PdfPCell(new Phrase("Стадия",font));
            cell.setColspan(2);
            table.addCell(cell);
            cell=new PdfPCell(new Phrase("Время",font));
            table.addCell(cell);
            cell=new PdfPCell(new Phrase("Расходы",font));
            table.addCell(cell);
            cell=new PdfPCell(new Phrase("Состояние",font));
            table.addCell(cell);
            for(int i=0;i<Main.employee.size();i++){
                TargetObject to=Main.employee.get(i);
                for(int j=0;j<10;j++){
                    switch(j){
                    case 0:cell=new PdfPCell(new Phrase(to.getClient(),font));
                                table.addCell(cell);
                    break;
                    case 1:cell=new PdfPCell(new Phrase(to.getTask(),font));
                                table.addCell(cell);
                    break;
                    case 2:cell=new PdfPCell(new Phrase(to.getHasMade(),font));
                                cell.setColspan(2);
                                table.addCell(cell);
                    break;
                    case 3:cell=new PdfPCell(new Phrase(to.getStage(),font));
                                cell.setColspan(2);
                                table.addCell(cell);
                    break;
                    case 4:cell=new PdfPCell(new Phrase(to.getSpendTime(),font));
                                table.addCell(cell);
                    break;
                    case 5:cell=new PdfPCell(new Phrase(String.valueOf(to.getCosts()),font));
                                table.addCell(cell);
                    break;
                    case 6:
                        if(to.getState())
                            cell=new PdfPCell(new Phrase("Выполнено",font));
                        else
                            cell=new PdfPCell(new Phrase("Невыполнено",font));
                        table.addCell(cell);
                    break;
                }
            }
        }
            doc.open();
            doc.add(table);
            doc.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Файл не найден.",
                    "Информационное сообщение",JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null,"Ошибка при формировании документа.",
                    "Информационное сообщение",JOptionPane.INFORMATION_MESSAGE);
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Ошибка записи файла.",
                    "Информационное сообщение",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
