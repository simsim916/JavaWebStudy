package com.ncs.spring02.service;

import java.util.List;

import com.ncs.spring02.domain.JoDTO;

public interface JoService {

	// joList
	List<JoDTO> selectList();

	// joDetail
	JoDTO selectOne(String jno);

	// joInsert
	int insert(JoDTO dto);

	// joUpdate
	int update(JoDTO dto);

	// joDelete
	int delete(String jno);

}