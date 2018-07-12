
package justdailscrapper.vik.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import justdailscrapper.vik.GUI.JustDailGUI;
import static justdailscrapper.vik.GUI.JustDailGUI.detailList;
import static justdailscrapper.vik.GUI.JustDailGUI.logTextArea;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class GenerateCsvFile extends Thread {
    String location,Name;
   public GenerateCsvFile(String location,String Name){
       this.location=location;
       this.Name=Name;
               
   }
    
    @Override
    public void run() {
        try {
            excel();
        } catch (Exception E) {
            JustDailGUI.loggerTextArea.append("\nError in wirting output File");
        }
    }
    
    public void excel() throws FileNotFoundException, IOException {
        
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Business Details");
        HSSFRow rowhead = sheet.createRow((int) 0);
        rowhead.createCell((int) 0).setCellValue("S.No.");
        rowhead.createCell((int) 1).setCellValue("Business Name");
        rowhead.createCell((int) 2).setCellValue("TelePhone Number");
        rowhead.createCell((int) 3).setCellValue("Mobile Number");
        rowhead.createCell((int) 4).setCellValue("Website");
        rowhead.createCell((int) 5).setCellValue("Address");
        rowhead.createCell((int) 6).setCellValue("Rating");
        rowhead.createCell((int) 7).setCellValue("Review Count");
        rowhead.createCell((int) 8).setCellValue("Tag");
        rowhead.createCell((int) 9).setCellValue("Hour Of Operation");
        rowhead.createCell((int) 10).setCellValue("Mode of Payment");
        rowhead.createCell((int) 11).setCellValue("Year of Established");
        rowhead.createCell((int) 12).setCellValue("Source Link");
        
        try {
            
            int k = 0;
            for (k = 0; k < detailList.size(); k++) {
                
                HSSFRow row = sheet.createRow((int) k + 2);
                try {
                    row.createCell((int) 0).setCellValue(k + 1);
                } catch (Exception sd) {
                }
                try {
                    row.createCell((int) 1).setCellValue(detailList.get(k).getBusinessName() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 2).setCellValue(detailList.get(k).getTelephoneNumber() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 3).setCellValue(detailList.get(k).getMobileNumber() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 4).setCellValue(detailList.get(k).getWebsite() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 5).setCellValue(detailList.get(k).getAddress() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 6).setCellValue(detailList.get(k).getRating() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 7).setCellValue(detailList.get(k).getReviewCount() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 8).setCellValue(detailList.get(k).getTag() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 9).setCellValue(detailList.get(k).getHoursOfOperation() + "");
                } catch (Exception sd) {
                }
                try {
                    row.createCell((int) 10).setCellValue(detailList.get(k).getModeOfPayments() + "");
                } catch (Exception sd) {
                }
                try {
                    row.createCell((int) 11).setCellValue(detailList.get(k).getEstablished() + "");
                } catch (Exception sd) {
                }
                
                try {
                    row.createCell((int) 12).setCellValue(detailList.get(k).getSourceLink() + "");
                } catch (Exception sd) {
                }
                
            }
            
        } catch (Exception aaa) {
        }
         
        JFileChooser fileChooser = new JFileChooser();
        File desktop = new File(System.getProperty("user.home"), "Desktop");
        
        System.out.println("" + desktop);
        String a = desktop.toString();
        Date now =new Date();
        String ee=now.toString().replace(" ", "_");
        ee=ee.toString().replace(":", "_");
        System.out.println(ee);
        File file = new File(a + "\\JustDail");
        if (!file.exists()) {
            if (file.mkdir()) {
                String filename = file + "\\" + "JD_"+location+Name+ee+".csv";
                System.out.println("Directory is created!");
                FileOutputStream fileOut = new FileOutputStream(filename);
                hwb.write(fileOut);
                fileOut.close();
                logTextArea.append("Your excel file has been generated!\n"+ filename);
               
                JOptionPane.showMessageDialog(null, "Excel File regenerated successfully\n" + filename);
            } else {
                System.out.println("Failed to create directory!");
            }
        } else {
            String filename = file + "\\" + "JD_"+location+Name+ee+".csv";
            System.out.println("Directory is created!");
            FileOutputStream fileOut = new FileOutputStream(filename);
            hwb.write(fileOut);
            fileOut.close();
            logTextArea.append("Your excel file has been generated!\n");
            JOptionPane.showMessageDialog(null, "Excel File regenerated successfully\n" + filename);
        }
        
        
    }
}
