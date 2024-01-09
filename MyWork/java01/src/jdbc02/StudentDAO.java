package jdbc02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc01.DBConnection;

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
} // class
