package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

//** Service
//=> 요청클래스 와 repository클래스 사이의 연결(완충지대) 역할
//=> 요청클래스(컨트롤러) 와 repository클래스 사이에서 변경사항이 생기더라도 서로 영향   받지않도록해주는 역할
//결합도는 낮추고, 응집도는 높인다

//** interface 자동완성 
//=> Alt + Shift + T  
//=> 또는 마우스우클릭 PopUp Menu 의  Refactor - Extract Interface...

// ** Mybatis 적용
// => CRUD 처리를 repository 를 이용
// => DAO 대신 repository interface -> ~repository.xml

// ** Mybatis interface 방식으로 적용
// => MemberDAO 대신 Memberrepository 사용
// => Memberrepository 의 인스턴스를 스프링이 생성해주고 이를 주입받아 실행함
// (스프링이 생성해주는 동일한 타입의 클래스는 JUnit Test 로 확인가능, 추후 실습)
// => 단, 설정화일에 <mybatis-spring:scan base-package="repositoryInterface"/> 반드시 추가해야함
//    MemberDAO의 Sql구문 처리를 repositoryInterface 사용으로 Memberrepository 가 역할을 대신함

// => SQL 구문 : xml 로작성 -> 이 화일을 repository 라 함
// => repository 작성규칙
// -> repositoryInterface 와 패키지명, 화일명이 동일해야함
//	즉, Java interface, repository, repository의 namespace가 동일해야한다
//	그리고 해당 메서드는 repository의 xml 구문의 id 속성값으로 찾는다

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository repository;

	// SelectList
	@Override
	public List<Member> selectList() {
		return repository.findAll();
	}
	
	@Override
	public void updatePassword1(String id, String password) {
		repository.updatePassword1(id, password);
	}
	
	@Override
	public List<Member> findByJno(String jno) {
		return repository.findByJno(jno);
	}
	
	// selectOne
	@Override
	public Member selectOne(String id) {
		Optional<Member> result = repository.findById(id);
		if (result.isPresent()) return result.get();
		return null;
	}

	// insert & update
	@Override
	public Member save(Member entity) {
		return repository.save(entity);
	}

	// pwUpdate
	@Override
	public Member pwUpdate(Member entity) {
		return null;
	}

	// delete
	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	   // ** Join
	   @Override
	   public List<MemberDTO> findMemberJoin() {
	      return repository.findMemberJoin();
	   }
}
