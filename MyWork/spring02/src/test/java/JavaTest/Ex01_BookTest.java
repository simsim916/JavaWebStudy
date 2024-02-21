package JavaTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


// ** Book class
//=> 맴버필드 3개 (author, title, price) 정의, 이들을 모두 초기화하는 생성자
//=> 접근범위 default

public class Ex01_BookTest {
	@Test
	//1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인
	public void equalsTest() {
		Book b1 = new Book("a", "a", 1);
		assertEquals(b1.author,"b");
		// => 값의 일치성 확인 -> true: green 라인 / false: red 라인
	}
	@Test
	//2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인
	public void sameTest() {
		Book b1 = new Book("a", "a", 1);
		Book b2 = new Book("b", "b", 2);
		assertSame(b1,b2);
		// => 값의 일치성 확인 -> true: green 라인 / false: red 라인
	}
	@Test
	//3) assertTrue(a) : a가 참인지 확인
	public void trueTest() {
		Book b1 = new Book("a", "a", 1);
		assertTrue(b1.isBook(true));
	}
	@Test
	//4) assertNotNull(a) : a객체가 Null 이 아님을 확인
	public void nullTest() {
		Book b1 = new Book("a", "a", 1);
		Book b2 = null;
		assertNotNull(b1);
	}
	
	@Test
	//5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인
	public void arrayEquals() {
		String[] ar1 = { "a" , "b", "c" };
		String[] ar2 = { "a" , "b", "c" };
		String[] ar3 = { "c" , "b", "a" };
		String[] ar4 = { "A" , "B", "C" };
//		assertArrayEquals(ar1,ar2);
//		assertArrayEquals(ar1,ar3);
//		assertArrayEquals(ar1,ar4);
		Book b1 = new Book("a", "a", 1);
		Book b2 = new Book("b", "b", 2);
		Book b3 = new Book("c", "c", 3);
		Book b4 = new Book("d", "d", 4);
		Book[] arb1 = {b1,b2,b3};
		Book[] arb4 = {b1,b2,b3};
		Book[] arb2 = {b3,b2,b1};
		Book[] arb3 = {b4,b3,b2};
		assertArrayEquals(arb1,arb4);
	}

}

class Book {
	String author;
	String title;
	int price;
	
	public Book(String author, String title, int price) {
		this.author = author;
		this.title = title;
		this.price = price;
	}
	
	public boolean isBook(boolean b) { return b; }
		
	
}
//** 테스트 레벨 4단계
//=> 단위테스트 -> 통합테스트 -> 시스템테스트 -> 인수테스트

//** 테스트 주도 개발 (Test-Driven Development , TDD)
//=> JUnit 활용
// Java 개발시 가장 많이 이용되는 단위테스트 프레임
// 오픈 소스 형태로 개발되며 계속 업그레이드 되고 있음.
// JUnit4 부터 에너테이션 적용 ( Java 가 5 부터 언어적 개선이 이루어짐에 따른 변화임 )

//** @ 종류
//=> @Before - @Test - @After
// -> 하나의 클래스에서 @ 들을 반복사용하면 오류는 안나지만, 앞쪽 @이 실행됨
//=> @ 적용 테스트 메서드 : non static, void 로 정의 해야 함.

//** org.junit.Assert 가 지원하는 다양한 Test용 Method 
//1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인
//2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인
//3) assertTrue(a) : a가 참인지 확인
//4) assertNotNull(a) : a객체가 Null 이 아님을 확인
//5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인

//=> 자동 import 가 안되는경우
// -> 프로젝트 우클릭 -> Build Path -> Configure Build Path.. 
//       -> Libraries -> Add Library  -> JUnit5
// -> @Test: import org.junit.Test 확인

//=> pom.xml
// -> junit version : 4.12 로 수정
// -> dependency 추가 ( Spring MVC Mybatis Test )