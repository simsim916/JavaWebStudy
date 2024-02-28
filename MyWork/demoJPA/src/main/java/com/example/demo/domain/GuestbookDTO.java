package com.example.demo.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestbookDTO {

	private Long gno; // auto_increment
	private String title;
	private String content;
	private String writer;
	private LocalDateTime regDate, modDate;
	
}
