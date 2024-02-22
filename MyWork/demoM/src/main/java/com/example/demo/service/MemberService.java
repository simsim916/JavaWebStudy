package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.MemberDTO;

public interface MemberService {

	// SelectList
	List<MemberDTO> selectList();

	// selectJoList
	List<MemberDTO> selectJoList(String jno);

	// selectOne
	MemberDTO selectOne(String id);

	// insert
	int insert(MemberDTO dto);

	// update
	int update(MemberDTO dto);

	// pwUpdate
	int pwUpdate(MemberDTO dto);

	// delete
	int delete(String id);

}