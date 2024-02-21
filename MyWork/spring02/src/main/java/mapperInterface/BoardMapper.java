package mapperInterface;

import java.util.List;

import com.ncs.spring02.domain.BoardDTO;

import pageTest.Criteria;
import pageTest.SearchCriteria;

public interface BoardMapper {
	public List<BoardDTO> bCheckList(SearchCriteria cri);
	
	public int bCheckRowsCount(SearchCriteria cri);
	// Board Search Paging - bSearchList, bSearchRowsCount
	public List<BoardDTO> bSearchList(SearchCriteria cri);

	public int bSearchRowsCount(SearchCriteria cri);

	// Board_Paging - bPageList, totalRowsCount
	public List<BoardDTO> bPageList(Criteria cri);

	public int totalRowsCount(Criteria cri);

	// selectList
	public List<BoardDTO> selectList();

	// selectOne
	public BoardDTO selectOne(int seq);

	// insert
	public int insert(BoardDTO dto);

	// replyInsert
	public int rinsert(BoardDTO dto);

	// stepUpdate
	public int stepUpdate(BoardDTO dto);

	// update
	public int update(BoardDTO dto);

	// delete
	public int delete(BoardDTO dto);

}
