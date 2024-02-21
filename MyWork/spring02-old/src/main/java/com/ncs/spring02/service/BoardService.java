package com.ncs.spring02.service;

import java.util.List;

import com.ncs.spring02.domain.BoardDTO;

import pageTest.Criteria;

public interface BoardService {

	// List
	public List<BoardDTO> selectList() ;

	// Detail
	public BoardDTO selectOne(int seq) ;

	// Insert
	public int insert(BoardDTO dto) ;

	// Update
	public int update(BoardDTO dto) ;

	// Delete
	public int delete(int seq) ;
	
	public List<BoardDTO> bPageList(Criteria cri);

    public int totalRowsCount(Criteria cri);

}
