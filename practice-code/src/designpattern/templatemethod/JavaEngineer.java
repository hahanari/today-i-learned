package designpattern.templatemethod;

public class JavaEngineer extends Engineer {

    @Override
    protected void doCoding() {
        System.out.println("Java로 코딩을 한다.");
    }
}
