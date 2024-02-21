package springTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ncs.spring02.domain.MemberDTO;

//*** DataSourceTest
//=> pom.xml 에 <dependency> spring-jdbc 추가
//=> 인터페이스 DataSource 구현객체 DriverManagerDataSource 를 bean 등록하고 (servlet~.xml 또는 root~.xml 에)
//=> DB Connection 생성 확인
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class Ex02_DataSourceTest {
	@Autowired
	DataSource dataSource;
	// ** 계층도 확인 ( Ctrl+T )
	// => DataSource (interface)
	//   -> AbstractDataSource
	//   -> AbstractDriverBasedDataSource
	//   -> DriverManagerDataSource  ( root~.xml 에 Bean 설정)
	//      org.springframework.jdbc.datasource.DriverManagerDataSource
	@Autowired
	MemberDTO dto;
	
	@Test
	// 1) DBConnection 확인
	public void connectionTest() {
		try {
			assertNotNull(dataSource.getConnection());
			System.out.println("DB Connection 여부 => " + dataSource.getConnection());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("DB Connection 여부 => " + e);
		}
		
	}
	// 2) SQL 구문 실행 Test
	public int delete(String id) {
		String sql = "delete from member Where id = "+id;
		
		try {
			Connection cn = dataSource.getConnection();
			PreparedStatement pst = cn.prepareStatement(sql);
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println(" delete test => " + e);
			return 0;
		}
	}
	
	@Test
	public void test() {
		String id = "";
		assertEquals(delete(id), 1);
	}
	
	@Before
	public int insert(MemberDTO dto) {
	      String sql = "insert into member values(?,?,?,?,?,?,?,?,?,?)";
	      try {
	         Connection cn = dataSource.getConnection();
	         PreparedStatement pst = cn.prepareStatement(sql);
	         pst.setString(1, dto.getId());
	         pst.setString(2, dto.getPassword());
	         pst.setString(3, dto.getName());
	         pst.setInt(4, dto.getAge());
	         pst.setInt(5, dto.getJno());
	         pst.setString(6, dto.getInfo());
	         pst.setDouble(7, dto.getPoint());
	         pst.setString(8, dto.getBirthday());
	         pst.setString(9, dto.getRid());
	         pst.setString(10, dto.getUploadfile());
	         return pst.executeUpdate();
	      } catch (Exception e) {
	         System.out.println("** insert Test Exception => "+e.toString());
	         return 0;
	      }
	   } //insert   
}
