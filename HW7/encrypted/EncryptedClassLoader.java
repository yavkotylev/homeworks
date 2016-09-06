package homeworks.HW7.encrypted;

import java.io.*;
import java.nio.file.Files;
import java.util.Random;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Yaroslav on 19.08.16.
 */
public class EncryptedClassLoader extends ClassLoader {

    private final String key;

    private final File dir;


    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {

        super(parent);

        this.key = key;

        this.dir = dir;

    }

    public File getDir() {
        return dir;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (findLoadedClass(name) != null) return findLoadedClass(name);

        try {
            byte[] data = readClass(name);
            int enKey = getEncryptedKey(key);
            byte[] classArray = new byte[data.length / (enKey + 1)];
            for (int i = 0; i < data.length; i++) {
                if (i % (enKey + 1) == 0) classArray[i / (enKey + 1)] = data[i];
            }
            return defineClass(name, classArray, 0, classArray.length);
        } catch (RuntimeException e) {
            try {
                byte[] data = readClass(name);
                return defineClass(name, data, 0, data.length);
            } catch (RuntimeException ex) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }


    public void encrypt(String name) {
        byte[] data = readClass(name);
        File classFile = new File(dir.getPath() + "/" + name.replace('.', '/') + ".class");

        int enKey = getEncryptedKey(key);
        byte[] encryptedData = new byte[data.length * (enKey + 1)];

        for (int i = 0; i < encryptedData.length; i++) {
            if (i % (enKey + 1) == 0) encryptedData[i] = data[i / (enKey + 1)];
            else encryptedData[i] = (byte) (new Random()).nextInt(126);
        }

        try (FileOutputStream stream = new FileOutputStream(classFile)) {
            stream.write(encryptedData, 0, encryptedData.length);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file");
        }
        System.out.println("File was encrypted");
    }


    private int getEncryptedKey(String key) {
        int enKey = 0;
        for (int i = 0; i < key.length(); i++) {
            enKey += key.charAt(i) * (i + 1);
        }
        enKey *= key.length();
        return enKey;
    }


    private byte[] readClass(String name) {
        File classFile = new File(dir.getPath() + "/" + name.replace('.', '/') + ".class");
        byte[] result;
        try {
            result = Files.readAllBytes(classFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + classFile.getPath());
        }

        return result;
    }
}
