package com.ncs.spring02.service;

import java.util.List;

import com.ncs.spring02.domain.MemberDTO;

public interface MemberService {

	List<MemberDTO> selectList();

	List<MemberDTO> selectList(String jno);

	MemberDTO selectOne(String id);

	int insert(MemberDTO dto);

	int update(MemberDTO dto);

	int delete(String id);

}