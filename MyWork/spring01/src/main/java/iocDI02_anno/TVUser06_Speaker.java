package iocDI02_anno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

//** TV의 의존성 처리
//=> TV 는 Speaker 를 사용
//=> 생성자 주입, setter 주입

//** 의존성 해결 Test
//1) 고전적 방법 (직접 new)
//2) IOC/DI -> 생성자 주입 
//3) IOC/DI -> setter 주입

//** IOC: 제어의 역전 (외부에서 Control)
//** DI : 주입 받음으로 해결

//@Component
class Speaker {
	public Speaker() {
		System.out.println("~~ Speaker Default 생성자 ~~");
	}

	public void volumeUp() {
		System.out.println("~~ Speaker volumeUp ~~");
	}

	public void volumeDown() {
		System.out.println("~~ Speaker volumeDown ~~");
	}
} // Speaker

// 1) 고전적 방법 (직접 new : 소스 재컴파일)
// => Speaker 생성 : 직접코드에서
//@Component("sstv")
class SsTVs implements TV {

	private Speaker speaker = new Speaker();

	public SsTVs() {
		System.out.println("~~ SsTVs Default 생성자 ~~");
	}

	@Override
	public void powerOn() {
		System.out.println("~~ SsTVs powerOn ~~");
	}

	@Override
	public void powerOff() {
		System.out.println("~~ SsTVs powerOff ~~");
	}

	@Override
	public void volumeDown() {
		speaker.volumeDown();
	}

	@Override
	public void volumeUp() {
		speaker.volumeUp();
	}
} // SsTVs

// 2) IOC/DI -> 맴버변수를 통해 직접 전달
// => Speaker 전달 : 맴버변수를 통해 직접 전달
// => @Autowired : 자동주입, 맴버변수에 적용, 
//                          Only 전달 : 생성문 " ~ = new Speaker()" 에서 = 의 역할
//						     그러므로 해당 객체는 반드시 생성 되어 있어야함

//=> Autowired 의 실행규칙 
//-> 메모리에서 타입이 일치하는 객체를 찾아 있으면 제공
//-> 없으면 Autowired 구문에서 오류 : UnsatisfiedDependencyException: Error creating bean wit....
//-> required = false ( default 값은 true -> 없으면 Exception 발생 )
//  못찾으면 Exception 을 발생하지않고 null return 
// 그러므로 인스턴스 호출구문( volumeUp, volumeDown) 에서 NullPointExceptiopn 발생

//=> 맴버별(개별적으로)로 각각 지정해야함
//@Autowired
//private Speaker speaker ;
//@Autowired
//private Student student;

//@Component("lgtv")
class LgTVs implements TV {
	
	@Autowired(required = false)
	private Speaker speaker;
	private String color;
	private int price;

	public LgTVs() {
		System.out.println("~~ LgTVs Default 생성자 ~~");
	}

	public LgTVs(Speaker speaker, String color, int price) {
		this.speaker = speaker;
		this.color = color;
		this.price = price;
		System.out.printf("~~ LgTVs 초기화 생성자 : color=%s, price=%d \n", color, price);
	}

	@Override
	public void powerOn() {
		System.out.println("~~ LgTVs powerOn ~~");
	}

	@Override
	public void powerOff() {
		System.out.println("~~ LgTVs powerOff ~~");
	}

	@Override
	public void volumeDown() {
		speaker.volumeDown();
	}

	@Override
	public void volumeUp() {
		speaker.volumeUp();
	}
} // LgTVs

// 3) IOC/DI -> setter 주입
// => Speaker 생성 : ~xml 에서 설정, setter 주입
//@Component("aitv")
class AiTVs implements TV {
	@Autowired(required=false)
	private Speaker speaker;
	@Autowired(required=false)
	private Class student;
	private String color;
	private int price;

	public AiTVs() {
		System.out.println("~~ AiTVs Default 생성자 ~~");
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public void powerOn() {
		System.out.printf("~~ AiTVs powerOn : color=%s, price=%d \n", color, price);
	}

	@Override
	public void powerOff() {
		System.out.println("~~ AiTVs powerOff ~~");
	}

	@Override
	public void volumeDown() {
		speaker.volumeDown();
	}

	@Override
	public void volumeUp() {
		speaker.volumeUp();
	}
} // AiTVs

public class TVUser06_Speaker {

	public static void main(String[] args) {
		// 1. 스프링 컨테이너 생성
		AbstractApplicationContext sc = new GenericXmlApplicationContext("iocDI02_anno/app05.xml");
		// 2. 필요한 객체를 전달받고 서비스 실행
		System.out.println("** 1) 고전적 방법 (직접 new : 소스 재컴파일) **");
		TV sstv = (TV) sc.getBean("sstv");
		sstv.powerOn();
		sstv.volumeUp();
		sstv.volumeDown();
		sstv.powerOff();

		System.out.println("** 2) IOC/DI -> 생성자 주입 **");
		TV lgtv = (TV) sc.getBean("lgtv");
		lgtv.powerOn();
		lgtv.volumeUp();
		lgtv.volumeDown();
		lgtv.powerOff();

		System.out.println("** 3) IOC/DI -> setter 주입 **");
		TV aitv = (TV) sc.getBean("aitv");
		aitv.powerOn();
		aitv.volumeUp();
		aitv.volumeDown();
		aitv.powerOff();

		sc.close();
	}

}
