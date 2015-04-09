/*
 */
package me.shafin.crawlerjava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author SHAFIN
 */
public class DataWriter {

    public static boolean writeDataToFile(String filePath, String textData) {
        try {
            File file = new File(filePath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF8")) {
                outputStreamWriter.write(textData);
            }

            //for the system out purpose
//            Writer out = new BufferedWriter(outputStreamWriter);
//            out.append(textData).append("\r\n");
//            out.flush();
//            out.close();

            return true;

        } catch (IOException e) {
            return false;

        }
    }

    
}
