package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import src.utils.PropertyReader;
import java.io.IOException;

public class Db {
	
	public Db() throws IOException, ClassNotFoundException, SQLException{
		PropertyReader rd = new PropertyReader("connection.properties");
		String user = rd.get("user");
		String pwd = rd.get("password");
		String URL = rd.get("URL");
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL,user,pwd);
	}
}
