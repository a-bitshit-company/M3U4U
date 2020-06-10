package src;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.Types.*;
import src.utils.PropertyReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Db {
	private Connection con;
	private String MusicFolderpath;
	private ArrayList<Song> songArrayList;
	private ArrayList<Playlist> PlaylistArrayList;
	
	public Db() throws IOException, ClassNotFoundException, SQLException{
		PropertyReader rd = new PropertyReader("connection.properties");
		String user = rd.get("user");
		String pwd = rd.get("password");
		String URL = rd.get("URL");
		MusicFolderpath = rd.get("Directory");
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(URL,user,pwd);
        getSongs();
        getPlaylists();  
	}
	
	public void test() throws SQLException{
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("show tables");
		while(result.next()){
			System.out.println(result.getString("Tables_in_m3u4u"));
		}
	}
	public void getSongs() throws SQLException {
		songArrayList = new ArrayList<>();
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("SELECT * FROM Songs");
		while(result.next()){
			Song temp = new Song(result.getInt("SongId"), result.getInt("PlaylistId"), result.getString("name"));
			songArrayList.add(temp);
		}
	}

	public void getPlaylists() throws SQLException{
		ArrayList<Playlist> PlaylistArrayList = new ArrayList<Playlist>();
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("SELECT * FROM Playlists");
		while(result.next()){
			Playlist temp = new Playlist(result.getString("name"), result.getString("genre"), result.getString("description"), result.getInt("PlaylistId"));
			PlaylistArrayList.add(temp);
		}
	}

	public MP3File getFile(int songId) throws SQLException, IOException{
		String sql = "SELECT * FROM Files WHERE SongId = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, songId);
		ResultSet result = stmt.executeQuery(); 
		
		result.first(); // move to first line of reultset
		
		return new MP3File(result.getInt("SongId"), parser(result.getBlob("file"), result.getInt("SongId")));
	}
	
	private File parser(Blob blob, int SongId) throws IOException, SQLException{
		
		//find songname and uses as file name
		String fileName = "";
		for (Song s : songArrayList) {
			if(s.getSongId() == SongId){
				fileName = s.getName();
				break;
			}
			
		}
		File temp = new File(MusicFolderpath + "/" + fileName);
		
		FileOutputStream output = new FileOutputStream(temp);
		InputStream in = blob.getBinaryStream();
        byte[] buffer = new byte[1024];
              
        while (in.read(buffer) > 0) {
        	output.write(buffer);
        }
              
		return temp;
	}
	
	public String getMusicFolderpath() {
		return MusicFolderpath;
	}

	public ArrayList<Song> getSongArrayList() {
		return songArrayList;
	}

	public ArrayList<Playlist> getPlaylistArrayList() {
		return PlaylistArrayList;
	}
	
}
