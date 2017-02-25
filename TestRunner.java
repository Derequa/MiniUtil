import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import derek.util.*;
import derek.util.graph.*;
import java.util.ArrayList;
import java.io.File;

public class TestRunner {
    public static void main(String[] args) {

        switch (args[0]) {
            case "all":
            for (Class<?> c : getClasses()) {
                Result result = JUnitCore.runClasses(c);
            
                for (Failure failure : result.getFailures()) {
                    System.out.println(failure.toString());
                }
                
                System.out.println(result.wasSuccessful());
            }
            break;
            case "single":
            try {
                System.out.println("Running Test: " + args[1]);
                Result result = JUnitCore.runClasses(ClassLoader.getSystemClassLoader().loadClass(args[1]));
                
                for (Failure failure : result.getFailures()) {
                    System.out.println(failure.toString());
                }
                
                System.out.println(result.wasSuccessful());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            break;
            case "multi":
            for (int i = 1 ; i < args.length ; i++) {
                try {
                    System.out.println("Running Test: " + args[i]);
                    Result result = JUnitCore.runClasses(ClassLoader.getSystemClassLoader().loadClass(args[i]));
                    
                    for (Failure failure : result.getFailures()) {
                        System.out.println(failure.toString());
                    }
                    
                    System.out.println(result.wasSuccessful());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
        
   }

    private static ArrayList<Class<?>> getClasses() {
        return getClassesRecursive(new ArrayList<Class<?>>(), new File("bin"));
    }

    private static ArrayList<Class<?>> getClassesRecursive(ArrayList<Class<?>> list, File current) {
        for (File f : current.listFiles()) {
            if (f.isDirectory())
                getClassesRecursive(list, f);
            else if (f.getName().endsWith("Test.class")) {
                try {
                    String name = f.getName().substring(0, f.getName().length() - ".class".length());
                    File parent = f.getParentFile();
                    while (!parent.getName().equals("bin")) {
                        name = parent.getName() + "." + name;
                        parent = parent.getParentFile();
                    }
                    System.out.println(name);
                    Class<?> c = ClassLoader.getSystemClassLoader().loadClass(name);
                    list.add(c);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Error for file: " + f.getName());
                }
            }
        }
        return list;
    }
} 