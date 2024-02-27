package com.example.demo.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() { // 외부와 in-out이 일어난다

		// 사용하는 SQL의 종류가 달라도 아래 코드는 동일
		try { // in-out이 일어나는 경우
				// 무한대기를 방지하기 위해 checked, try-catch가 필요
				// 역할을 처리해주는 DB를 찾고 계정의 정보를 넘겨준다

			// forName : 해당하는 클래스를 찾아 로드해준다
			// mysql의 드라이버 주소, 다른 sql을 사용할 때 여기를 변경
			// Oracle forName : Class.forName(“oracle.jdbc.driver.OracleDriver");
			Class.forName("com.mysql.cj.jdbc.Driver");

			// localhost -> 동일값(ip주소) @127.0.0.1 내 컴퓨터로
			// 외부DB를 사용할 때 localhost에 주소와 포트번호를 변경
			// 쿼리 스트링 mydb ? 이후 부터 파라미터를 전달
			// allowPublicKeyRetrieval=true : local DB open 하지 않아도 connection 허용
			// Oracle url: jdbc:oracle:thin:@172.16.4.100:1521:PROJ
			String url = "jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
			Connection cn = DriverManager.getConnection(url, "root", "mysql");
			System.out.println("** DB Connection 성공 **");
			return cn;
		} catch (Exception e) {
			System.out.println("** DB Connection Exception => " + e.toString());
			return null;
		} // try
	} // getConnection
} // class