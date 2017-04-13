package mplftaskmanager;

import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Main {
    public static ArrayList<TargetObject> employee=new ArrayList<TargetObject>();
 
    public static void main(String[] args) {
        Func.downloadTM();
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new GUI();
            }
        });
    }
}
