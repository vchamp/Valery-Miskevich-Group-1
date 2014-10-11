package by.shknaj;

import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

/**
 * Created by ilya.shknaj on 04.10.14.
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static final Scanner in = new Scanner(System.in);

    private static final String PATH = "/Users/ilya.shknaj/IdeaProjects/ClassLoader/func/";

    private static final String PACKAGE = "by.shknaj.";

    public static void main(String[] args) throws Exception {
        File addFunctionalityFolder = new File(PATH);

        if (!addFunctionalityFolder.exists()) {
            System.out.println("File not exists");
            return;
        }

        if (!addFunctionalityFolder.isDirectory()) {
            System.out.println("Can't found directory with additional functionality");
            return;
        }

        while (true) {
            loadNewJar(addFunctionalityFolder);
        }

    }

    private static void loadNewJar(File functionalityFolder) throws Exception {

        System.out.println("\n\nPlease enter func which need load");

        for (File file : functionalityFolder.listFiles()) {
            System.out.println(file.getName());
        }

        String fileName = in.next();

        File jarFile = new File(PATH + File.separator + fileName);

        ClassLoader classLoader = new URLClassLoader(new URL[]{jarFile.toURL()});
        Class loadedClass = classLoader.loadClass(PACKAGE + getFileNameWithoutExtensional(jarFile));
        Object loadedClassInstance = loadedClass.newInstance();
        Method loadFuncMethod = loadedClass.getMethod("load");
        logger.info(loadFuncMethod.invoke(loadedClassInstance));
    }

    private static String getFileNameWithoutExtensional(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.indexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        }
        return fileName;
    }

}
