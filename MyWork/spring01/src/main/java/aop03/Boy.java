package aop03;

import java.util.Random;

//** Aop 구현
//1 단계 : 핵심적 관심사항 과  공통적 관심사항 분리
//=> 핵심적 관심사항만 구현
//=> 공통적 관심사항(Aspect) 분리 : 별도의 클래스로 분리 -> MyAspect.java

public class Boy implements Programmer{

	@Override
	public void doStudying()  throws Exception{
		
			System.out.println("boy 트라이중 -> 핵심적 관심사항" );
	}
	
}
