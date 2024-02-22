package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.JoDTO;

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