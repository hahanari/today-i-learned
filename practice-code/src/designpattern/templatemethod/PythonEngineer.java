package designpattern.templatemethod;

public class PythonEngineer extends Engineer {
    @Override
    protected void doCoding() {
        System.out.println("Python으로 코딩을 한다.");
    }
}
