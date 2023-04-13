package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    public static String FORMAT_STRING = "%02x";
    public static String PATH = "src/main/resources/blockchain.properties";
    public static String ERROR_MSG = "ОШИБКА: Файл свойств отсуствует!";


    public static String getPropertyByName(String name) {
        FileInputStream fis;
        String properties = null;
        Properties property = new Properties();
        try {
            fis = new FileInputStream(PATH);
            property.load(fis);
            properties = property.getProperty(name);
        } catch (IOException e) {
            System.err.println(ERROR_MSG);
        }
        return properties;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format(FORMAT_STRING, b));
        }
        return sb.toString();
    }

}
