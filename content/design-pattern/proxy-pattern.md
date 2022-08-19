# Proxy Pattern
- 프록시에게 어떤 일을 대신 시키는 것.
- 구체적으로 인터페이스를 사용하고 실행시킬 클래스에 대한 객체가 들어갈 자리에 대리자 객체를 대신 투입.
- 흐름제어만 할 뿐, 결과값을 조작하거나 변경시키면 안된다.

## 프록시 패턴의 장점
- 사이즈가 큰 객체가 로딩되기 전에도, 프록시를 통해 참조할 수 있다.
- 실제 객체의 public, protected 메소드를 숨기고, 인터페이스를 통해 노출시킬 수 있다.
- 로컬에 있지 않고 떨어져 있는 객체를 사용할 수 있다.
- 원래 객체의 접근에 대해서 사전처리를 할 수 있다.

## 프록시 패턴의 단점
- 객체를 생성할 때 한단계를 거치게 되므로, 빈번한 객체 생성이 필요한 경우 성능이 저하될 수 있다.
- 프록시 내부에서 객체 생성을 위해 스레드가 생성되므로, 동기화가 구현되어야 하는 경우 성능이 저하될 수 있다.
- 로직이 난해해져 가독성이 떨어질 수 있다.

```java
public interface Image {
	void displayImage();
}

public class RealImage implements Image {
	private String fileName;

	public RealImage(String fileName) {
		this.fileName = fileName;
		loadFromDisk(fileName);
	}

	private void loadFromDisk(String fileName) {
		System.out.println("Loading " + fileName);
	}

	@Override
	public void displayImage() {
		System.out.println("Displaying " + fileName);
	}
}

public class ProxyImage implements Image {
	private RealImage realImage;
	private String fileName;

	public ProxyImage(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void displayImage() {
		if (realImage == null) {
			realImage = new RealImage(fileName);
		}
		realImage.displayImage();
	}
}

public class ProxyMain {
	public static void main(String[] args) {
		Image image1 = new ProxyImage("test-1.png");
		Image image2 = new ProxyImage("test-2.png");

		image1.displayImage();
		System.out.println();
		image2.displayImage();
	}
}
```
----
######reference
- https://coding-factory.tistory.com/711
