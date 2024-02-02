package com.ncs.spring02.model;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ncs.spring02.domain.MemberDTO;


//** IOC/DI 적용 ( @Component 의 세분화 ) 
//=> 스프링 프레임워크에서는 클래스들을 기능별로 분류하기위해 @ 을 추가함.
//=>  @Controller :사용자 요청을 제어하는 Controller 클래스
//       DispatcherServlet이 해당 객체를 Controller객체로 인식하게 해줌.    
//=>  @Service : 비즈니스로직을 담당하는 Service 클래스
//=>  @Repository : DB 연동을 담당하는 DAO 클래스
//         DB 연동과정에서 발생하는 예외를 변환 해주는 기능 추가

//@Component
@Repository
public class MemberDAO {

	private static Connection cn = DBConnection.getConnection();
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;

	// ** selectList
	public List<MemberDTO> selectList() {
		sql = "SELECT * FROM member";
		List<MemberDTO> list = new ArrayList<MemberDTO>();

		try {

			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();

			if (rs.next()) {
				do {
					MemberDTO dto = new MemberDTO();
					dto.setId(rs.getString(1));
					dto.setPassword(rs.getString(2));
					dto.setName(rs.getString(3));
					dto.setAge(rs.getInt(4));
					dto.setJno(rs.getInt(5));
					dto.setInfo(rs.getString(6));
					dto.setPoint(rs.getDouble(7));
					dto.setBirthday(rs.getString(8));
					dto.setRid(rs.getString(9));
					dto.setUploadfile(rs.getString(10));

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
	// ** selectList Where jno
	public List<MemberDTO> selectList(String jno) {
		sql = "SELECT * FROM member where jno="+jno;
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		
		try {
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					MemberDTO dto = new MemberDTO();
					dto.setId(rs.getString(1));
					dto.setPassword(rs.getString(2));
					dto.setName(rs.getString(3));
					dto.setAge(rs.getInt(4));
					dto.setJno(rs.getInt(5));
					dto.setInfo(rs.getString(6));
					dto.setPoint(rs.getDouble(7));
					dto.setBirthday(rs.getString(8));
					dto.setRid(rs.getString(9));
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
	public MemberDTO selectOne(String id) {
		sql = "SELECT * FROM member WHERE id=?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString(1));
				dto.setPassword(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setAge(rs.getInt(4));
				dto.setJno(rs.getInt(5));
				dto.setInfo(rs.getString(6));
				dto.setPoint(rs.getDouble(7));
				dto.setBirthday(rs.getString(8));
				dto.setRid(rs.getString(9));
				dto.setUploadfile(rs.getString(10));
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
	public int insert(MemberDTO dto) {
		sql = "INSERT INTO member VALUES (?,?,?,?,?,?,?,?,?,?)";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getId());
			pst.setString(2, dto.getPassword());
			pst.setString(3, dto.getName());
			pst.setInt(4, dto.getAge());
			pst.setInt(5, dto.getJno());
			pst.setString(6, dto.getInfo());
			pst.setDouble(7, dto.getPoint());
			pst.setString(8, dto.getBirthday());
			pst.setString(9, dto.getRid());
			pst.setString(10, dto.getUploadfile());

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** insert Exception => " + e.toString());
			return 0;
		}
	}

	// ** update
	public int update(MemberDTO dto) {
		sql = "UPDATE member SET name=?, age=?" + ", jno=?, info=?, point=?, birthday=?, rid=? where id=?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getName());
			pst.setInt(2, dto.getAge());
			pst.setInt(3, dto.getJno());
			pst.setString(4, dto.getInfo());
			pst.setDouble(5, dto.getPoint());
			pst.setString(6, dto.getBirthday());
			pst.setString(7, dto.getRid());
			pst.setString(8, dto.getId());

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** update Exception => " + e.toString());
			return 0;
		}
	}

	// ** delete
	public int delete(String id) {
		sql = "DELETE FROM member WHERE id=?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, id);

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** update Exception => " + e.toString());
			return 0;
		}
	}
	
	//** Password_Update
		public int pwUpdate(MemberDTO dto) {
			sql="update member set password=? where id=?";
			try {
				pst=cn.prepareStatement(sql);
				pst.setString(1, dto.getPassword());
				pst.setString(2, dto.getId());
				return pst.executeUpdate();
			} catch (Exception e) {
				System.out.println("** PasswordUpdate Exception => " +e.toString());
				return 0;
			}
		}

} // class
