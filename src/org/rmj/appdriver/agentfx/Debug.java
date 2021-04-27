package org.rmj.appdriver.agentfx;

/**
 * 
 * @author Michael Torres Cuison
 * @version 1.0
 * @since March 31, 2020
 * 
 * Strict mode of terminal printing of an object.
 * Uses app.debug.mode system property.
 */

public class Debug {
    public static void Print(Object foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    /*
    public static void Print(String foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Print(boolean foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Print(char foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Print(char [] foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
    }
    
    public static void Print(double foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Print(float foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Print(int foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Print(long foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.print(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }*/
    
    public static void Println(Object foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    /*public static void Println(String foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Println(boolean foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Println(char foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Println(char [] foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Println(double foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Println(float foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Println(int foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }
    
    public static void Println(long foValue){
        if (System.getProperty("app.debug.mode").equals("1"))
            System.out.println(foValue);
        else
            System.out.print("System is not in debug mode. Unable output object.");
    }*/
}
