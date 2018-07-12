package justdailscrapper.vik.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import justdailscrapper.vik.GUI.JustDailGUI;
import static justdailscrapper.vik.GUI.JustDailGUI.logTextArea;
import static justdailscrapper.vik.GUI.JustDailGUI.loggerTextArea;
import static justdailscrapper.vik.GUI.JustDailGUI.proxyList;

public class LoadProxy extends Thread {

    public static JustDailGUI jdShowThis;

    public LoadProxy() {
    }

    public LoadProxy(JustDailGUI jdThis) {
        this.jdShowThis = jdThis;
    }

    @Override
    public void run() {

        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(jdShowThis);
        FileSystemView a = fc.getFileSystemView();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("Selected file: " + file.getAbsolutePath());
            System.out.println(fc.getFileSystemView());
            try {

                FileReader fileReader = new FileReader(file.getAbsolutePath());

                BufferedReader br = new BufferedReader(fileReader);
                int counter = 0;
                String line;
                logTextArea.append("Proxy Loaded :");
                while ((line = br.readLine()) != null) {
                    logTextArea.append(line + "\n");
                    proxyList.add(new ProxyImport(line));
                    counter++;

                }

                logTextArea.append("Number Of Count : " + counter + "\n");
                System.out.println(file.getAbsolutePath());

                logTextArea.append("Proxy Loaded!!\n");
                fileReader.close();

            } catch (Exception e) {
                logTextArea.append("Please Import a Valid .txt file!!\n");
                logTextArea.append("Proxy in the file must be in the following format!!\n");
                logTextArea.append("ProxyIP:ProxyPort:ProxyUsername:ProxyPassword\n");

            }
        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
            logTextArea.append("Proxy Loading from file is canceled\n");
        } else {
            logTextArea.append("Please Import a Valid .txt file!!\n");
            logTextArea.append("Proxy in the file must be in the following format!!\n");
            logTextArea.append("ProxyIP:ProxyPort:ProxyUsername:ProxyPassword\n");
        }
    }

}
