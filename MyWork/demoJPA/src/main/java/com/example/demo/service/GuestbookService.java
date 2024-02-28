package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.GuestbookDTO;
import com.example.demo.domain.PageRequestDTO;
import com.example.demo.domain.PageResultDTO;
import com.example.demo.entity.Guestbook;

// JPA CRUD 구현
// => Entity 와 DTO 용도 분리
// => dtoToEntity() 와 entityToDto() default 메서드 추가
public interface GuestbookService {
	
	// ** JPA Pageable 을 이용한 Pageing 기능
   	PageResultDTO<GuestbookDTO, Guestbook> pageList(PageRequestDTO requestDTO);
	
	//	=> JPA CRUD
	List<Guestbook> selectList();
	Guestbook selectOne(Long gno);
	Long register(GuestbookDTO dto); // insert, update 모두사용, key 를 return
	void delete(Long gno); 
	
	// => dtoToEntity()
	// insert, update 위해 주로 사용되므로 date 는 제외
	default Guestbook dtotoEntity(GuestbookDTO dto) {
		Guestbook entity = Guestbook.builder()
														.gno(dto.getGno())
														.title(dto.getTitle())
														.content(dto.getContent())
														.writer(dto.getWriter())
														.build();
		return entity;
	}

	// => entityToDTO()
	// 결과 출력시 주로 사용
	default GuestbookDTO entityToDTO(Guestbook entity) {
		GuestbookDTO dto = GuestbookDTO.builder()
				.gno(entity.getGno())
				.title(entity.getTitle())
				.content(entity.getContent())
				.writer(entity.getWriter())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}
}
