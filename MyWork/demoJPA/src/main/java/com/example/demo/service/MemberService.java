package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.MemberDTO;
import com.example.demo.entity.Member;

public interface MemberService {

	// SelectList
	List<Member> selectList();

	List<Member> findByJno(String jno);

	void updatePassword1(String id, String password);

	// selectOne
	Member selectOne(String id);

	// insert
	Member save(Member entity);

	// pwUpdate
	Member pwUpdate(Member entity);

	// delete
	void deleteById(String id);

	// ** Join
	List<MemberDTO> findMemberJoin();
}