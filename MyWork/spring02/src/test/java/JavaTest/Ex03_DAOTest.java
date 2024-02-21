package JavaTest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ncs.spring02.domain.MemberDTO;
import com.ncs.spring02.model.MemberDAO;

//** DAO Test 시나리오
//=> Detail 정확성 
// -> Test Data
// -> 정확한 id 를 사용하면 not null : Green_Line
// -> 없는 id 를 사용하면 null : Red_Line

//=> Insert 정확성
// -> 입력 가능한 Data 적용 : 1 return : Green_Line
// -> 입력 불가능한 Data 적용 : 0 return : Red_Line

public class Ex03_DAOTest {
	
	MemberDAO dao = new MemberDAO();
	MemberDTO dto = new MemberDTO();
	
	@Test
	// 1) Detail 정확성
	public void detailTest() {
		String id = "simsim916";
		assertNotNull(dao.selectOne(id));
	}
	@Test
	// 1) Insert 정확성
	public void insertTest() {
		dto.setId(null);
		dto.setPassword(null);
		dto.setName(null);
		dto.setAge(0);
		dto.setJno(0);
		assertNotNull(dao.selectOne(dto.getId()));
	}
}
