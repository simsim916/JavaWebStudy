package com.example.demo.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="jo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Jo {
	
	@Id
    private String jno;
    private String jname;
    private String captain;
    private String project;
    private String slogan;
    
    @Transient
    private String cname;

}//class