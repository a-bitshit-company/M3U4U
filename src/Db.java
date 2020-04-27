package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.Types.*;
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
	public ArrayList<Playlist> getPlaylists() throws SQLException{
		ArrayList<Playlist> pls = new ArrayList<Playlist>();
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("SELECT * FROM Playlists");
		while(result.next()){
			Playlist temp = new Playlist(result.getString("name"), result.getString("genre"), result.getString("description"), result.getInt("PlaylistId"));
			pls.add(temp);
		}
		return pls;
		
		
	}
}
