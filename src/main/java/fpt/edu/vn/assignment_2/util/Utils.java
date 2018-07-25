package fpt.edu.vn.assignment_2.util;

import org.bson.internal.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {

    /**
     * Gets timestamp in millis and converts it to HH:mm (e.g. 16:44).
     */
    public static String formatTime(long timeInMillis) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatCheck = new SimpleDateFormat("MM/dd/yyyy");
        String format = "HH:mm";
        if (!formatCheck.format(timeInMillis).equals(formatCheck.format(ts.getTime()))) {
            format = "dd/MM HH:mm";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(timeInMillis);
    }

    /**
     * Get Image from text
     */
    /**
     * Encodes the byte array into base64 string
     */
    // Doan nay co phan su dung GridFS, ma no' lang nhang` hon nhieu`
    // http://mongodb.github.io/mongo-java-driver/3.4/driver/tutorials/gridfs/
    // Cai nay chi su dung cho cac tap tin vuot qua' 16mb. dung luong lon hon file document bson binh thuong

    public static String encodeImage(File file) throws IOException {
        FileInputStream imageInFile = new FileInputStream(file);
        byte imageData[] = new byte[(int) file.length()];
        imageInFile.read(imageData);
        imageInFile.close();
        return Base64.encode(imageData);
    }

    /**
     * Decodes the base64 string into byte array
     *

     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString);
    }


    /**
     * Check if String is Null or Empty
     */
    public boolean isNullOrEmpty(String s) {
        return (s == null || s.isEmpty());
    }

    public Utils() {
    }
}
