package com.xsp.texas;

import java.io.*;

/**
 * Created by shpng on 2016/10/23.
 */
public class ReadWriteObjectToFile {

    public static void writeObjectToFile(Object obj, File file) {


        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(file));
            oos.writeObject(obj);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Object readObjectFromFile(File file) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
