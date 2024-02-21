package aop03;

import java.util.Random;

public class Girl implements Programmer {

	@Override
	public void doStudying()  throws Exception{
			System.out.println("girl 트라이중 -> 핵심적 관심사항" );
			throw new Exception("girl 에러발생");
	}
	
}
