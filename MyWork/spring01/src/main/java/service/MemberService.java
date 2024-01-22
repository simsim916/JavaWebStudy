package service;

import java.util.List;

import domain.MemberDTO;
import model.MemberDAO;

public class MemberService {
	private MemberDAO dao = new MemberDAO();
	
	public List<MemberDTO> selectList(){
		return dao.selectList();
	}
	
	public MemberDTO selectOne(String id) {
		return dao.selectOne(id);
	}
	
	public int insert(MemberDTO dto) {
		return dao.insert(dto);
	}
	
	public int update(MemberDTO dto) {
		return dao.update(dto);
	}
	
	public int delete(String id) {
		return dao.delete(id);
	}
}
