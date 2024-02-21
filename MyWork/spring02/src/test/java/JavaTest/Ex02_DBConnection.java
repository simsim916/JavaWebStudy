package JavaTest;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class Ex02_DBConnection {

	@Test
	// non-static & void
	// => test 메서드 작성
	public void test() {
		
		assertNotNull(getConnection());
	}
	
	public static Connection getConnection() { // 외부와 in-out이 일어난다

		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
			Connection cn = DriverManager.getConnection(url, "root", "mysql");
			System.out.println("** DB Connection 성공 **");
			return cn;
		} catch (Exception e) {
			System.out.println("** DB Connection Exception => " + e.toString());
			return null;
		} // try
	} // getConnection
}
