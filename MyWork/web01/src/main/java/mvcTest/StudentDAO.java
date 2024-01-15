package mvcTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


//** DAO(Data Access Object)
//=> SQL 구문 처리
//=> CRUD 구현 
// Create(Insert), Read(selectList, selectOne), Update, Detete

//** 첫번째 예제 DBStart 와 DAO 와 차이점
// => 결과를 직접 처리하지 않고 요청자에게 제공해야함
// => 즉, 메서드 역할별로 처리결과를 return 해야함
// => 그러므로, 특히 select 의 결과를 전달하기 위해 결과를 객체화 해야함

public class StudentDAO {

	// ** 전역변수 정의
	private static Connection cn = DBConnection.getConnection();
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;
	
	// ** Join Test
	// => sno, name, age, jno, jname, project, captain, captain.name
	// => JoDTO 만들기, 메서드 작성 ( Controller, service, DAO )
	
	public List<StudentDTO> joinList() {
		sql= "SELECT s.sno, s.name, s.age, s.jno, j.jname, j.project, j.captain "
				+ "FROM student s LEFT JOIN jo j ON s.jno=j.jno";
		List<StudentDTO> list = new ArrayList<StudentDTO>();
		
		try {
			pst=cn.prepareStatement(sql);
			rs=pst.executeQuery();
			
			if(rs.next()) {
				do {
					
				StudentDTO dto = new StudentDTO();
				dto.setSno(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setAge(rs.getInt(3));
				dto.setJno(rs.getInt(4));
				dto.setJname(rs.getString(5));
				dto.setProject(rs.getString(6));
				dto.setCaptain(rs.getInt(7));
				
				list.add(dto);
				} while (rs.next());
			}
			return list;
			
		} catch (Exception e) {
			System.out.println("** joinList Exception => " + e.toString());
			return null;
		}
	}
	
	// ** selectList
	public List<StudentDTO> selectList() {
		sql = "SELECT * FROM student";
		List<StudentDTO> list = new ArrayList<StudentDTO>();

		try {

			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();

			// 결과의 존재 여부 확인
			// 존재 : list 에 담기
			// 없음 : return null;
			if (rs.next()) {
				do {
					StudentDTO dto = new StudentDTO();
					dto.setSno(rs.getInt(1));
					dto.setName(rs.getString(2));
					dto.setAge(rs.getInt(3));
					dto.setJno(rs.getInt(4));
					dto.setInfo(rs.getString(5));
					dto.setPoint(rs.getDouble(6));

					list.add(dto);
				} while (rs.next());
				return list;
			} else {
				return null;
			}

		} catch (Exception e) {
			System.out.println("** selectList Exception => " + e.toString());
			return null;
		}

	}

	// ** selectOne
	public StudentDTO selectOne(int sno) {
		sql = "SELECT * FROM student WHERE sno=?";
		// 로그인 기능을 구현할때 password를 암호화 하기때문에
		// DB와 직접비교 불가능, 그래서 하나의 데이터를 가져온 후 비교

		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, sno);
			rs = pst.executeQuery();

			if (rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setSno(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setAge(rs.getInt(3));
				dto.setJno(rs.getInt(4));
				dto.setInfo(rs.getString(5));
				dto.setPoint(rs.getDouble(6));
				return dto;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("** selectOne Exception => " + e.toString());
			return null;
		}
	}

	// ** insert
	public int insert(StudentDTO dto) {
		sql = "INSERT INTO student(name,age,jno,info) VALUES (?,?,?,?) ";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getName());
			pst.setInt(2, dto.getAge());
			pst.setInt(3, dto.getJno());
			pst.setString(4, dto.getInfo());

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** insert Exception => " + e.toString());
			return 0;
		}
	}

	// ** update
	public int update(StudentDTO dto) {
		sql = "UPDATE student SET name=?, info=?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getName());
			pst.setString(2, dto.getInfo());

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** update Exception => " + e.toString());
			return 0;
		}
	}

	// ** delete
	public int delete(int sno) {
		sql = "DELETE FROM student WHERE sno=?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, sno);

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** update Exception => " + e.toString());
			return 0;
		}
	}
	
	 // ** Transaction Test
	 // => Connection 객체가 관리
	 // => 기본값은 AutoCommit  true 임.
	 // => setAutoCommit(false) -> commit 또는 rollback 
	 // => Test 사항
	 //   - 동일자료를 2번 입력 -> 2번째 입력에서 p.key 중복 오류발생 

	 // 1) Transaction 적용전
	 // => 동일자료를 2번 입력
	 //   - 1번째는 입력완료 되고, 2번째 입력에서 p.key 중복 오류발생 
	 //   - Rollback 불가능
	 //   - MySql Command 로 1번째 입력 확인 가능 
	public void transactionTest() {
		sql = "INSERT INTO student VALUES (19, '김길동' , 35 , 9 , '자기소개', 100)";
		
		try {
			pst = cn.prepareStatement(sql);
			pst.executeUpdate(); // 1
			pst.executeUpdate(); // 0 p.key 종복오류 발생 -> catch 블럭으로 이동
		} catch (Exception e) {
			System.out.println("Transaction 적용전 , transactionTest => "+ e);
		}
	}
	 // 2) Transaction 적용후 
	 // => 동일자료를 2번 입력 
	 //   - 1번째는 입력완료 되고, 2번째 입력에서 p.key 중복 오류발생
	 //   - Rollback 가능 -> 둘다 취소됨
	public void transactionTest2() {
		sql = "INSERT INTO student VALUES (30, '김길동' , 35 , 9 , '자기소개', 100)";
		
		try {
			cn.setAutoCommit(false); // Start Transaction
			pst = cn.prepareStatement(sql);
			pst.executeUpdate(); // 1 입력완료 되었지만 Buffer에 보관
			pst.executeUpdate(); // 0 p.key 종복오류 발생 -> catch 블럭 -> rollback
			cn.commit();
		} catch (Exception e) {
			System.out.println("** Transaction 적용후 , transactionTest => "+ e);
			try {
				cn.rollback();
				System.out.println("** Rollback 성공 **");
			} catch (Exception e2) {
				System.out.println("** Transaction 적용후 롤백 오류 , transactionTest => "+ e);
			}
		}
	}
	
	
} // class
