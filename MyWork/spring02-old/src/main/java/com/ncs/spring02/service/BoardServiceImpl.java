package com.ncs.spring02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.spring02.domain.BoardDTO;
import com.ncs.spring02.model.BoardDAO;

import mapperInterface.BoardMapper;
import pageTest.Criteria;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardMapper mapper;

	@Override
	public List<BoardDTO> selectList() {
		// TODO Auto-generated method stub
		return mapper.selectList();
	}

	@Override
	public BoardDTO selectOne(int seq) {
		return mapper.selectOne(seq);
	}

	@Override
	public int insert(BoardDTO dto) {
		// TODO Auto-generated method stub
		return mapper.insert(dto);
	}

	@Override
	public int update(BoardDTO dto) {
		// TODO Auto-generated method stub
		return mapper.update(dto);
	}

	@Override
	public int delete(int seq) {
		// TODO Auto-generated method stub
		return mapper.delete(seq);
	}

	//** Board_Paging
    @Override
    public List<BoardDTO> bPageList(Criteria cri) {
        return mapper.bPageList(cri);
    };

    @Override
    public int totalRowsCount(Criteria cri) {
        return mapper.totalRowsCount(cri);

    };
	
}
