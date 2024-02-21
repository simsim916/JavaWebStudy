package mapperInterface;

import java.util.List;

import com.ncs.spring02.domain.MemberDTO;

public interface MemberMapper {

	List<MemberDTO> selectList();

	List<MemberDTO> selectList(String jno);

	MemberDTO selectOne(String id);

	int insert(MemberDTO dto);

	int update(MemberDTO dto);

	int delete(String id);
	
	int pwUpdate(MemberDTO dto);
	
}
