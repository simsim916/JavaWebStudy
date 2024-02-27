package com.example.demo.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.JoDTO;

@Repository
public class JoDAO {
	private static Connection cn = DBConnection.getConnection();
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;

//	SELECT SS.SNO, SS.NAME, SS.AGE, SS.JNO, JNAME, CAPTAIN, 
//	(SELECT NAME 
//	 FROM STUDENT
//	 WHERE SNO = SS.CAPTAIN        
//	) 조장명
//	FROM (
//	   SELECT S1.SNO, S1.NAME, S1.AGE, S1.JNO, JNAME, CAPTAIN
//	   FROM STUDENT S1 LEFT OUTER JOIN JO J ON S1.JNO = J.JNO ) SS;
	// joList
	public List<JoDTO> selectList() {
//		sql = "select * from jo";
		sql = "select J.JNO, J.JNAME, J.CAPTAIN, M.NAME 조장명, J.PROJECT, J.SLOGAN, J.uploadfile FROM JO J, MEMBER M WHERE J.CAPTAIN = M.ID";
		List<JoDTO> list = new ArrayList<JoDTO>();

		try {
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			// 결과의 존재여부 확인
			if (rs.next()) {
				do {
					// setter 사용
					JoDTO dto = new JoDTO();
					dto.setJno(rs.getInt(1));
					dto.setJname(rs.getString(2));
					dto.setCaptain(rs.getString(3));
					dto.setCname(rs.getString(4));
					dto.setProject(rs.getString(4));
					dto.setSlogan(rs.getString(5));

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
	} // joList

	// joDetail
	public JoDTO selectOne(String jno) {
		sql = "SELECT * FROM jo WHERE jno=?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, jno);
			rs = pst.executeQuery();

			if (rs.next()) {
				JoDTO dto = new JoDTO();
				dto.setJno(rs.getInt(1));
				dto.setJname(rs.getString(2));
				dto.setCaptain(rs.getString(3));
				dto.setProject(rs.getString(4));
				dto.setSlogan(rs.getString(5));
				return dto;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("** selectOne Exception => " + e.toString());
			return null;
		}
	}// joDetail

	// joInsert
	public int insert(JoDTO dto) {
		sql = "insert into jo values(?,?,?,?,?)";
		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, dto.getJno());
			pst.setString(2, dto.getJname());
			pst.setString(3, dto.getCaptain());
			pst.setString(4, dto.getProject());
			pst.setString(5, dto.getSlogan());

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** insert Exception => " + e.toString());
			return 0;
		}
	} // joInsert

	// joUpdate
	public int update(JoDTO dto) {
		sql = "update jo set jname=?, captain=?, project=?, slogan=? where jno=?";
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getJname());
			pst.setString(2, dto.getCaptain());
			pst.setString(3, dto.getProject());
			pst.setString(4, dto.getSlogan());
			pst.setInt(5, dto.getJno());

			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** update Exception => " + e.toString());
			return 0;
		}
	} // joUpdate

	// joDelete
	public int delete(String jno) {
		sql = "delete from jo where jno=?";
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, jno);

			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** delete Exception => " + e.toString());
			return 0;
		}
	} // joDelete

} // class
