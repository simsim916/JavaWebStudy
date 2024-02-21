package mapperInterface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ncs.spring02.domain.MemberDTO;

public interface MemberMapper {
	
	// JUnit Test
	// => selectDTO, xml 대신 @으로 Sql 구현
	@Select("SELECT * FROM member WHERE id =#{id}")
	MemberDTO selectDTO(MemberDTO dto);
	
	// => selectParam, xml 대신 @으로 Sql 구현
	@Select("SELECT * FROM member WHERE id =#{id} and jno=#{jno2}")
	MemberDTO selectParam(@Param("id") String id, @Param("jno2") Integer jno);
	
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
