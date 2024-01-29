package com.ncs.spring02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.spring02.domain.JoDTO;
import com.ncs.spring02.model.JoDAO;

@Service
public class JoService {
	@Autowired
	JoDAO dao;

	// joList
	public List<JoDTO> selectList() {
		return dao.selectList();
	}

	// joDetail
	public JoDTO selectOne(String jno) {
		return dao.selectOne(jno);
	}

	// joInsert
	public int insert(JoDTO dto) {
		return dao.insert(dto);
	}

	// joUpdate
	public int update(JoDTO dto) {
		return dao.update(dto);
	}

	// joDelete
	public int delete(String jno) {
		return dao.delete(jno);
	}

} // class
