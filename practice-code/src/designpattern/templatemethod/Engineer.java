package designpattern.templatemethod;

public abstract class Engineer {

    public void doWork() {
        turnOnIntelliJ();
        doCoding();
    }

    protected abstract void doCoding();

    private void turnOnIntelliJ() {
        System.out.println("IntelliJ를 켠다.");
    }
}
