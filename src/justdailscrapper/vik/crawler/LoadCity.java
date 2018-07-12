package justdailscrapper.vik.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import justdailscrapper.vik.GUI.JustDailGUI;
import static justdailscrapper.vik.GUI.JustDailGUI.logTextArea;
import static justdailscrapper.vik.GUI.JustDailGUI.loggerTextArea;

public class LoadCity extends Thread {

    public static JustDailGUI jdCity;
    public static ArrayList<String> cityList = new ArrayList();

    public LoadCity() {
    }

    public LoadCity(JustDailGUI jdThis) {
        this.jdCity = jdThis;
    }

    @Override
    public void run() {

        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(jdCity);
        FileSystemView a = fc.getFileSystemView();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            logTextArea.append("Selected City file name : " + file.getAbsolutePath()+"\n");
            System.out.println(fc.getFileSystemView());
            try {

                FileReader fileReader = new FileReader(file.getAbsolutePath());

                BufferedReader br = new BufferedReader(fileReader);
                int counter = 0;
                String line;

                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    cityList.add(line);
                    counter++;

                }
                logTextArea.append("Number of City Name : " + counter+"\n");

            } catch (Exception e) {
                logTextArea.append("Please Import a Valid .txt file!!\n");
            }
        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
            logTextArea.append("City Name Loading from file is canceled\n");
        } else {
            logTextArea.append("Please Import a Valid .txt file!!\n");
        }
    }

}
