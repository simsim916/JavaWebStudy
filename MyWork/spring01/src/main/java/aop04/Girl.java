package aop04;

import java.util.Random;

public class Girl implements Programmer {

	@Override
	public String doStudying(int n) throws Exception {
		System.out.printf("girl 트라이중 %d -> 핵심적 관심사항\n", n);
		if(true)
		throw new Exception("girl 에러발생");
		return "girl 리턴";
	}

}
