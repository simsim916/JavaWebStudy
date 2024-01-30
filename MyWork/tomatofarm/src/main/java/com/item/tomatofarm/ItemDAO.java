package com.item.tomatofarm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ItemDAO {
	private static Connection cn = DBConnection.getConnection();
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;
	
	public List<ItemDTO> itemList(String name) {
		sql="SELECT * FROM mealkit";
		
		if(!"".equals(name)) {
			sql = "SELECT * FROM mealkit WHERE name LIKE('%"+name+"%')";
		} 
		
		List<ItemDTO> list = new ArrayList<ItemDTO>();
		
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1,name);
			rs = pst.executeQuery(); 
			if (rs.next()) {
				do {
					ItemDTO dto = new ItemDTO();
					
					dto.setSorta(rs.getString(1));
					dto.setSortb(rs.getString(2));
					dto.setSortc(rs.getString(3));
					dto.setSortd(rs.getString(4));
					dto.setCode(rs.getInt(5));
					dto.setMadeby(rs.getString(6));
					dto.setName(rs.getString(7));
					dto.setSize(rs.getInt(8));
					dto.setStoragetype(rs.getString(9));
					dto.setPackagetype(rs.getString(10));
					dto.setPrice(rs.getInt(11));

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
}
