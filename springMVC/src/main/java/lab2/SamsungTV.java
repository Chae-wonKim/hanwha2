package lab2;

public class SamsungTV implements TV {
	public SamsungTV() {
		System.out.println("SamsungTV������");
	}
	
	public void powerOn() {
		//class �̸��� �������� �о�´�
		System.out.println(this.getClass().getSimpleName()+"������ �մϴ�");
	}
	
	public void powerOff() {
		System.out.println(this.getClass().getSimpleName()+"������ ���ϴ�");
	}
	
	
	

}
