package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoDTO {
	private int jno;
	protected String jname;
	private String captain;
	protected String project;
	private String slogan;
	private String cname;
}