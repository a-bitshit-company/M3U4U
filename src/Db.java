package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import src.utils.PropertyReader;
import java.io.IOException;

public class Db {
	Connection con;
	
	public Db() throws IOException, ClassNotFoundException, SQLException{
		PropertyReader rd = new PropertyReader("connection.properties");
		String user = rd.get("user");
		String pwd = rd.get("password");
		String URL = rd.get("URL");
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(URL,user,pwd);
	}
	
	public void test() throws SQLException{
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("show tables");
		while(result.next()){
			System.out.println(result.getString("Tables_in_m3u4u"));
		}
		
	}
}
