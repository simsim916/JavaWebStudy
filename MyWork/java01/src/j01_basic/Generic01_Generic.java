package j01_basic;

import jdbc02.JoDTO;
import jdbc02.StudentDTO;


/*
 *  <?>
 *   -> 모든 object
 * 
 *  <? extends 부모클래스>  // Lower Bounded
 *   -> 부모클래스 포함 하위 자손들이 다 들어 올 수 있다.
 *   
 *  <? Super 자손클래스>  // Upper Bounded
 *   -> 자손클래스 포함 상위 조상들이 다 들어 올 수 있다. 
 */

class Store{
	private Object data;
	
	public Object getData() {
		return this.data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}// Store

class StoreG<T> {
	private T data;
	
	public T getData() {
		return this.data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
} // StoreG

class GenArray<T> {
	private T[] arr;
	
	public T[] getData() {
		return this.arr;
	}
	
	public void setData(T[] arr) {
		this.arr = arr;
	}
	
	public void arrayPrint() {
		for(T a: arr) {
			System.out.println(a);
		}
	}
}

class Box<T extends Number>{
	private T data;
	
	public void setData(T data) { this.data = data; }
	public T getData() { return this.data; }
}

public class Generic01_Generic {

	public static void main(String[] args) {
		// 1. Object 를 이용하는 기존방식
		Store s1 = new Store();
		
		s1.setData("짜장면");
		s1.setData(123); // int <-> Integer : 기본형은 래퍼클래스와 자동형변환이 일어난다
		s1.setData(123.456); // double <-> Double
//		s1.setData(new JoDTO(7,"banana", 77, "화이팅", "제너릭 공부중"));
		System.out.println(s1.getData());
		
		// ** 단점 Test
//		String s = (String)s1.getData();
		// 컴파일 오류 없으나 런타임오류 발생 : java.lang.ClassCastException
//		System.out.println("** 단점 Test => " +s);
		
		// 2. 
		StoreG g1 = new StoreG(); // 제너릭 미설정시 Object로 설정됨	
		StoreG<?> g11 = new StoreG(); 	
		g11.setData(null);
		StoreG<String> g2 = new StoreG<String>();
		g2.setData("짬뽕");
		
		// GenArray Test
		// 1) String
		String[] ss = {"가","나","Do","Re","Mi"};
		GenArray<String> ga1 = new GenArray<String>();
		ga1.setData(ss);
		ga1.arrayPrint();
		// 2) Integer
		Integer[] ii = {1,2,3,4,5};
		GenArray<Integer> ga2 = new GenArray<Integer>();
		ga2.setData(ii);
		ga2.arrayPrint();
		// 3) 객체
		StudentDTO[] dd = {new StudentDTO(),new StudentDTO(),new StudentDTO(),new StudentDTO(),new StudentDTO()};
		GenArray<StudentDTO> ga3 = new GenArray<StudentDTO>();
		ga3.setData(dd);
		ga3.arrayPrint();
		
		// => 제네릭 클래스의 타입 인자 제한
		Box<Integer> b1 = new Box();
		Box<Double> b2 = new Box();
		b1.setData(123456);
		
	} // main

} // class
