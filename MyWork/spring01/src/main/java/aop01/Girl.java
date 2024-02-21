package aop01;

public class Girl implements Programmer {

	@Override
	public void doStudying() {
		System.out.println("공부중 -> Before");
		
			System.out.println("트라이2중 -> 핵심적 관심사항" );
			// Test 를 위해 늘 true 처리
//			if (new Random().nextBoolean()) {
			if (true) {
			} 
	}
	
}
