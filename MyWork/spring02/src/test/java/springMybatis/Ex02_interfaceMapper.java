package springMybatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ncs.spring02.domain.MemberDTO;
import com.ncs.spring02.service.MemberService;

import mapperInterface.MemberMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class Ex02_interfaceMapper {
	
	// ** interface Mapper 설정
	// => Controller 
	// 	-> Service 
	// 	-> (DAO) 
	// 	-> interface Mapper : xml의 sql 구문을 이용해서 DB처리
	@Autowired
	MemberMapper mapper;
	
	// => 성공: MemberMapper mapper = new MemberMapper구현객체 ;
	//   -> 구현객체 생성 부터는 Spring과 Mybatis가 규칙에 의해 처리해줌 
	//   -> 규칙: 패키지 명과 클래스명을 interface , mapper xml, xml의 namespace 모두 동일하게 해줌.
	//     이를 위한 경로 설정 
	//     <mybatis-spring:scan base-package="mapperInterface"/>    
	
	@Autowired
	MemberDTO dto;
	
	// ** mapper 동작 Test
	// => getClass().getName() : 실제동작하는 클래스(MemberMapper의 구현객체) 의 이름 확인가능
	//    이를 통해 우리는 Mapper interface 만 작성했지만, 
	//    내부적으로는 동일한 타입의 클래스가 만들어졌음을 알 수 있다.  
//	@Test
	public void mapperTest() {
		assertNotNull(mapper);
		System.out.println("mapper interface 구현객체 =>" + mapper.getClass().getName());
		System.out.println("DTO 인스턴스의 동작하는 클래스명 =>" + dto.getClass().getName());
	}
	
	// ** mapper의 메서드 Test
	// => Mybatis 사용시 주의사항
	//	  	-> 참조형 매개변수 사용 시 매개변수 주소를 공유하지 않음 주의
	//		selectDTO(MemberDTO dto)
	//		-> 매개변수는 1개만 사용 가능 (Type은 무관하다)
	//      	그러므로 주로 객체형으로	사용하지만, 복수의 매개변수를 사용하려면 
	//			복수의 매개변수를 사용하려면 @Param을 이용할 수 있음
	//		-> xml 대신 @ 으로 Sql 구현 가능
	
	// 1) selectOne
	public void selectOne() {
		String id = "simsim916";
		dto=mapper.selectOne(id);
		System.out.println("dto =>" + dto);
		
	}
	
	// 2) selectDTO(MemberDTO dto) 형식
	public void selectDTO() {
		dto.setId("simsim916");
		mapper.selectDTO(dto);
		assertEquals(dto, mapper.selectDTO(dto));
		System.out.println("DTO => " + dto);
		System.out.println("selectDTO => " + mapper.selectDTO(dto));
	}
	
	// 3) 복수의 매개변수 사용 Test
	// => Mybatis 에서 2개이상의 매개변수 처리
	// => Mapper interface 에서 @Param 적용가능
	// => selectParam(id , jno)
	@Test
	public void paramTest() {
		String id = "simsim916";
		int jno = 1;
		dto=mapper.selectParam(id,jno);
		System.out.println("dto => " + dto);
		assertNotNull(dto);
	}
	
}
