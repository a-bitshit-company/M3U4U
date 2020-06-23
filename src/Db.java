package src;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.Exceptions.CustomSQLException;
import src.Types.*;
import src.utils.PropertyReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//TODO methods: addtoplaylist, removefromplaylist, deletesong, converter zu m3u von datenbank, playlist(in m3u format) in datenbank einspeisen

public class Db {
	private Connection con;
	private String MusicFolderpath;
	private ArrayList<Song> songArrayList;
	private ArrayList<Playlist> PlaylistArrayList;
	
	public Db() throws IOException, ClassNotFoundException,CustomSQLException, SQLException{
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
      
		public void getSongs() throws CustomSQLException{
		songArrayList = new ArrayList<>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM Songs");
			while(result.next()){
				Song temp = new Song(result.getInt("SongId"), result.getInt("PlaylistId"), result.getString("name"));
				songArrayList.add(temp);
			}
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName()); //name from method
		}
		
	}

	public void getPlaylists() throws CustomSQLException{
		ArrayList<Playlist> PlaylistArrayList = new ArrayList<Playlist>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM Playlists");
			while(result.next()){
				Playlist temp = new Playlist(result.getString("name"), result.getString("genre"), result.getString("description"), result.getInt("PlaylistId"));
				PlaylistArrayList.add(temp);
			}
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
	}

	public MP3File getFile(int songId) throws  IOException, CustomSQLException{
		String sql = "SELECT * FROM Files WHERE SongId = ?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, songId);
			ResultSet result = stmt.executeQuery(); 
			
			result.first(); // move to first line of resultset
			
			return new MP3File(result.getInt("SongId"), parser(result.getBlob("file"), result.getInt("SongId")));
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
	}
	
	private File parser(Blob blob, int SongId) throws IOException, FileNotFoundException, SQLException{
		
		//find songname and uses as file name
		String fileName = "";
		for (Song s : songArrayList) {
			if(s.getSongId() == SongId){
				fileName = s.getName();
				break;
			}
			
		}
		File temp;
		temp = new File(MusicFolderpath + "/" + fileName); //works on *nix and windows
		
		FileOutputStream output = new FileOutputStream(temp);
		InputStream in = blob.getBinaryStream();
        byte[] buffer = new byte[1024];
              
        while (in.read(buffer) > 0) {
        	output.write(buffer);
        }
        return temp;
        
        
              
	}
	public void uploadSong(File file) throws FileNotFoundException, CustomSQLException{
		//md5 hash to check if file already present
		try {
			//files table
			String sql = "INSERT INTO Files(file,MD5) VALUES(?,MD5(file))";
			PreparedStatement stmt;
			stmt = con.prepareStatement(sql);
			InputStream in = new FileInputStream(file);
			stmt.setBlob(1,in);
			stmt.execute();
			
			//Songs table
			sql = "INSERT INTO Songs VALUES(LAST_INSERT_ID(),null,?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, file.getName());
			stmt.execute();
			
			getSongs(); //update list
			
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}
	public void deleteSong(String name) {
		for(Song s : songArrayList) {
			if(s.getName()==name) {
				deleteSong(s.getSongId());
				break;
			}
		}
	}
	
	public void deleteSong(int songId) {
		
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
