/*
 * Author:  Mohamed Taman, 16/6/2018
 * Course:  Hands-On Java 10 Programming with JShell
 * 		    By Packt Publisher
 * section: JShell, Advanced Topics
 * Video:   3.5 examples - Execute Java Code Like OS Shell Script
 */

// JShell_Script.jsh contents

/* If you observe it does not need a class declaration.
 * Using the power of jshell, we can write functions and 
 * call functions without creating class, 
 * just like functional programming.
 */

 // You can also include comments like above, anywhere in the script

System.out.println();

String hello = "Hello";

System.out.println(hello);

public int getInt1(){
    return 2;
}
public int getInt2(){
    return 4;
}

getInt1() + getInt2();

public class MyClass{
    public void sayHelloWorld(){
        System.out.println("Hello functional programming World");
    }
}

new MyClass().sayHelloWorld()
