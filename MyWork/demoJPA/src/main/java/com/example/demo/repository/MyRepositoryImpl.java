package com.example.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;

@Transactional
@Repository
public class MyRepositoryImpl implements MyRepository {

	private final EntityManager em;

	public MyRepositoryImpl(EntityManager em) {
		this.em=em;
	}
	
	@Override
	public List<Member> emMemberList() {

//		return em.createQuery("select * from Member ", Member.class)
//		.getResultList();
		
		// => JPQL 적용
		// => "select * from Member " 500 오류 발생
		//		Entity를 통해 접근하기 때문에 * 사용금지, 엘리어스를 통해 컬럼명 접근
		
		return em.createQuery("select m from Member m", Member.class)
		.getResultList();
	}

	public List<Member> emMemberList2() {
		return em.createQuery("select m from Member m where jno < :jno",Member.class)
				.setParameter("jno", 7)
				.getResultList();
	}
	
	@Override
	public Member emMemberDetatil(String id) {
		return em.createQuery("select m from Member m where id = :id",Member.class)
				.setParameter("id", "simsim916")
				.getSingleResult();
	}
}
