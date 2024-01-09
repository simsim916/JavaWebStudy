package jdbc01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 *  순서
 *  1) JDBC API 에 정의된 필요한 객체들을 전역변수 정의
 *  2) CRUD 기능 메서드
 *  3) main 에서 사용
 */

public class DBStart {

	private static Connection cn = DBConnection.getConnection();
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;

	// studentList
	// => MySql Command
	// -> Login -> DB선택 -> sql 구문 실행 -> 결과
	// => JDBC
	// -> Connection 객체 생성 -> sql 구문 : Statement 또는 PreparedStatement
	// -> 결과 : ResultSet 에 전달됨
	public static void selectList() {

		sql = "SELECT * FROM student";

		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			// 결과 출력
			// => 결과존재 확인
			// => ResultSet 객체는 이를 위한메서드 제공
			// => next() : 다음에 Data가 존재하면 true, 현재 Data를 제공
			System.out.println("---------Student List-----------");
			System.out.println("--------------------------------");
			System.out.println("| sno | name | age | jno | info | point |");
			System.out.println("--------------------------------");

			if (rs.next()) {
				// => selectList 결과가 존재함

				do {
					System.out.print(rs.next() + " ");
					System.out.print(rs.getInt(1) + " ");
					System.out.print(rs.getString("name") + " ");
					System.out.print(rs.getInt(3) + " ");
					System.out.print(rs.getInt("jno") + " ");
					System.out.print(rs.getString(5) + " ");
					System.out.print(rs.getDouble("point") + " \n");
				} while (rs.next());

			} else {
				// => selectList 결과가 1건도 없음을 의미
				System.out.println("** selectList 결과가 1건도 없음 **");
			} // if_else

		} catch (Exception e) {
			System.out.println("** selectList Exception => " + e.toString());
		} // try
	} // selectList

	// statement 활용
	public static void joList(int num) {

		sql = "SELECT * FROM student WHERE jno=" + num;

		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);

			System.out.printf("------%d조 Student List----------\n", num);
			System.out.println("--------------------------------");
			System.out.println("| sno | name | age | jno | info | point |");
			System.out.println("--------------------------------");

			if (rs.next()) {

				do {
					System.out.printf("%5d | ", rs.getInt(1));
					System.out.print(rs.getString("name") + " |  ");
					System.out.print(rs.getInt(3) + " | ");
					System.out.printf("%2d | ", rs.getInt("jno"));
					System.out.print(rs.getString(5) + " ");
					System.out.print(rs.getDouble("point") + " \n");
				} while (rs.next());

			} else {
				System.out.println("** selectList 결과가 1건도 없음 **");
			} // if_else

		} catch (Exception e) {
			System.out.println("** selectList Exception => " + e.toString());
		} // try
	} // joList

	// ** insert (문제점)
	// => 입력에 필요한 컬럼을 모두 매개변수로 전달 받아야함
	// 매개변수가 많으면 처리가 불편 -> 객체화 
	// -> 엔티티(Entity) -> Java Class 로 객체화
	// -> 자바 클래스를 ~DTO(data trans object), ~VO(value, object), Entity(JPA) 로 정의함
	// => sql 구문을 만들기 위해서 문자열 연산을 작성해야함
	// INSERT INTO student(name, age, jno, info) VALUES('홍길동', 22 , 9 , '자기소개');
	// => 이러한 점을 보완하기 위해 제공된 객체가 Preparedstatement
	// 변수의 위치에 ?(바인딩변수) 를 사용
	// INSERT INTO student(name, age, jno, info) VALUES(?,?,?,?);
	// ? 에 대응값은 JavaCode로 처리 (Preparedstatement 의 메서드)

	// Preparedstatement 활용
	public static void joListPS(int num) {

		sql = "SELECT * FROM student WHERE jno=?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, num);
			rs = pst.executeQuery();

			System.out.printf("------%d조 Student List----------\n", num);
			System.out.println("--------------------------------");
			System.out.println("| sno | name | age | jno | info | point |");
			System.out.println("--------------------------------");

			if (rs.next()) {

				do {
					System.out.printf("%5d | ", rs.getInt(1));
					System.out.print(rs.getString("name") + " |  ");
					System.out.print(rs.getInt(3) + " | ");
					System.out.printf("%2d | ", rs.getInt("jno"));
					System.out.print(rs.getString(5) + " ");
					System.out.print(rs.getDouble("point") + " \n");
				} while (rs.next());

			} else {
				System.out.println("** selectList 결과가 1건도 없음 **");
			} // if_else

		} catch (Exception e) {
			System.out.println("** selectList Exception => " + e.toString());
		} // try
	} // joList

	public static void insertStudent(String name, int age, int jno, String info) {
		sql = "INSERT INTO student VALUES(?,?,?,?)";
		
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setInt(2, age);
			pst.setInt(3, jno);
			pst.setString(4, info);
			int cnt = pst.executeUpdate(); // insert, update, delete 실행된 갯수 return
			if (cnt>0) {
				System.out.println("** 입력 성공 ** => "+cnt);
				
			}
		} catch (Exception e) {
			System.out.println("** 입력 오류 **");
			System.out.println("** insert Exception => "+ e.toString());
		}
		
		
	}
	
	public static void main(String[] args) {
		// 1) Connection 확인
		// => 출력문에서 인스턴스명을 사용하면 toString()메서드를 호출
		System.out.println("** Connection 확인 => " + cn);

		// 2) Student List
//		selectList();

		// 3) 조별 List 출력
		joList(3);

		// 4) 학생 데이터 입력 (PreparedStatement 이용)
		insertStudent("홍길동", 23, 9, "자기소개");
	}
}
