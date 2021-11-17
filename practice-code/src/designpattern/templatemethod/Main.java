package designpattern.templatemethod;

public class Main {
    public static void main(String[] args) {
        JavaEngineer javaEngineer = new JavaEngineer();
        javaEngineer.doWork();
        PythonEngineer pythonEngineer = new PythonEngineer();
        pythonEngineer.doWork();
    }
}
