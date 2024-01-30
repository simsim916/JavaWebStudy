package com.ncs.spring02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ncs.spring02.domain.MemberDTO;
import com.ncs.spring02.model.MemberDAO;


//@Component
@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO dao;
	
	@Override
	public List<MemberDTO> selectList(){
		return dao.selectList();
	}
	
	@Override
	public List<MemberDTO> selectList(String jno){
		return dao.selectList(jno);
	}
	
	@Override
	public MemberDTO selectOne(String id) {
		return dao.selectOne(id);
	}
	
	@Override
	public int insert(MemberDTO dto) {
		return dao.insert(dto);
	}
	
	@Override
	public int update(MemberDTO dto) {
		return dao.update(dto);
	}
	
	@Override
	public int delete(String id) {
		return dao.delete(id);
	}
}
