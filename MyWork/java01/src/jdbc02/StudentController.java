package jdbc02;

import java.util.List;

//** Controller
//=> 요청 : 요청분석 -> 담당 Service -> Service 는 DAO 
//=> 결과 : DAO -> Service -> Controller
//=> View : Controller -> View 담당 (Java:Console // Web:JSP, Html.., React) 

public class StudentController {
	// ** 전역변수 선언
	StudentService service = new StudentService();
	
	// ** View 역할 메서드
	// joinList
	public void printJoinList(List<StudentDTO> list) {
		System.out.println("**** Student List ****");
		// 출력자료의 존재 확인
		if (list != null) {
			// ** List 출력
			for(StudentDTO i: list) {
				System.out.println(i);
			}
		} else {
			System.out.println("출력할 데이터가 없습니다.");
		}
	}
	
	// selectList
	public void printList(List<StudentDTO> list) {
		System.out.println("**** Student List ****");
		// 출력자료의 존재 확인
		if (list != null) {
			// ** List 출력
			for(StudentDTO i: list) {
				System.out.println(i);
			}
		} else {
			System.out.println("출력할 데이터가 없습니다.");
		}
	}
	
	public void printOne(StudentDTO dto) {
		System.out.print("**** Student ****\n");
		if(dto!=null) {
			System.out.println(dto);
		} else {
			System.out.println("출력할 데이터가 없습니다.");
		}
	}
	
	public void insert(int num) {
		if (num>0) {
			System.out.println("**** 인서트 완료 ****");
		} else {
			System.out.println("**** 인서트 실패 ****");
		}
	}
	
	public void update(int num) {
		if (num>0) {
			System.out.println("**** 업데이트 완료 ****");
		} else {
			System.out.println("**** 업데이트 실패 ****");
		}
	}
	
	public void delete(int num) {
		if (num>0) {
			System.out.println("**** 삭제 완료 ****");
		} else {
			System.out.println("**** 삭제 실패 ****");
		}
	}
	
	public static void main(String[] args) {
		
		StudentController sc = new StudentController();
		
		// ** selectList
		// 요청 에 해당하는 Service의 메서드 호출
		// 처리결과를 view 에 전달해서 출력
//		sc.printList(sc.service.selectList());
		
//		sc.printJoinList(sc.service.joinList());
		
//		sc.insert(sc.service.insert(new StudentDTO(0, "테스트",32,9,"자기소개",0)));
//		sc.printOne(sc.service.selectOne(19));
//		sc.update(sc.service.update(new StudentDTO(0, "테스트2",32,9,"자기소개2",0)));
//		sc.printOne(sc.service.selectOne(99));
//		sc.delete(sc.service.delete(19));
		
		sc.service.transactionTest2();
	} // main
} // class
