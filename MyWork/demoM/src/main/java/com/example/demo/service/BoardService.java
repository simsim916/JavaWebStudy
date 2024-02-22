package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.BoardDTO;

import pageTest.SearchCriteria;

public interface BoardService {

	// bCheckList
	public List<BoardDTO> bCheckList(SearchCriteria cri);
	
	// bCheckRowsCount
	public int bCheckRowsCount(SearchCriteria cri);
	
	// bPageList
	public List<BoardDTO> bPageList(SearchCriteria cri);

	// totalRowsCount
	public int totalRowsCount(SearchCriteria cri);

	// selectList
	public List<BoardDTO> selectList();

	// selectOne
	public BoardDTO selectOne(int seq);

	// insert
	public int insert(BoardDTO dto);

	// replyInsert
	public int rinsert(BoardDTO dto);

	// update
	public int update(BoardDTO dto);

	// delete
	public int delete(BoardDTO dto);

} // class
