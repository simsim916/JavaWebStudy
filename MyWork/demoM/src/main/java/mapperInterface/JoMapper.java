package mapperInterface;

import java.util.List;

import com.example.demo.domain.JoDTO;

public interface JoMapper {
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
