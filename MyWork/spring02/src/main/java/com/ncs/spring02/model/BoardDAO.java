package com.ncs.spring02.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ncs.spring02.domain.BoardDTO;

@Repository
public class BoardDAO {
	String sql;
	private static Connection cn = DBConnection.getConnection();
	private static PreparedStatement pst;
	private static ResultSet rs;

	// List
	public List<BoardDTO> selectList() {
		sql = "select * from board order by seq DESC";
		List<BoardDTO> list = new ArrayList<BoardDTO>();

		try {
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					BoardDTO dto = new BoardDTO();
					dto.setSeq(rs.getInt(1));
					dto.setId(rs.getString(2));
					dto.setTitle(rs.getString(3));
					dto.setContent(rs.getString(4));
					dto.setRegdate(rs.getString(5));
					dto.setCnt(rs.getInt(6));
					dto.setRoot(rs.getInt(7));
					dto.setStep(rs.getInt(8));
					dto.setIndent(rs.getInt(9));
					list.add(dto);
				} while (rs.next());
				return list;
			} else {
				System.out.println("BoardDAO.selectList : 출력 자료 없음");
				return null;
			}
		} catch (Exception e) {
			System.out.println("BoardDAO.selectList :" + e.toString());
			return null;
		}
	}

	// Detail
	public BoardDTO selectOne(int seq) {
		sql = "select * from board where seq="+seq;

		try {
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setSeq(rs.getInt(1));
					dto.setId(rs.getString(2));
					dto.setTitle(rs.getString(3));
					dto.setContent(rs.getString(4));
					dto.setRegdate(rs.getString(5));
					dto.setCnt(rs.getInt(6));
					dto.setRoot(rs.getInt(7));
					dto.setStep(rs.getInt(8));
					dto.setIndent(rs.getInt(9));
				return dto;
			} else {
				System.out.println("BoardDAO.selectOne : 출력 자료 없음");
				return null;
			}
		} catch (Exception e) {
			System.out.println("BoardDAO.selectOne :" + e.toString());
			return null;
		}
	}

	// Insert
	public int insert(BoardDTO dto) {
		sql = "Insert Into board Values((select * from (select ifNull(max(seq),0)+1 from board) as temp),"
				+ "?,?,?, Current_TimeStamp ,0, (select * from (select ifNull(max(seq),0)+1 from board) as temp), 0, 0)";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getId());
			pst.setString(2, dto.getTitle());
			pst.setString(3, dto.getContent());
			
			return pst.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("BoardDAO.insert :" + e.toString());
			return 0;
		}
	}

	// Update
	public int update(BoardDTO dto) {
		sql = "Update board set title = ? , content =? where seq = ?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, dto.getTitle());
			pst.setString(2, dto.getContent());
			pst.setInt(3, dto.getSeq());
			
			return pst.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("BoardDAO.update :" + e.toString());
			return 0;
		}
	}

	// Delete
	public int delete(int seq) {
		sql = "Delete From board Where seq =" + seq;

		try {
			pst = cn.prepareStatement(sql);
			
			return pst.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("BoardDAO.update :" + e.toString());
			return 0;
		}
	}
}
