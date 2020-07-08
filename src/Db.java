package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import src.utils.ObjectFinder;
import src.utils.PropertyReader;

//TODO methods:converter zu m3u von datenbank, playlist(in m3u format) in datenbank einspeisen

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
	        cleanUp();
	}
      
		public void getSongs() throws CustomSQLException{
		songArrayList = new ArrayList<>();
		try {
			Statement stmt;
			stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM Songs");
			while(result.next()){
				Song temp = new Song(result.getInt("SongId"), result.getInt("PlaylistId"), result.getString("name"));
				songArrayList.add(temp);
			}
		}catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName()); //name from method
		}
		
	}

	public void getPlaylists() throws CustomSQLException{
		PlaylistArrayList = new ArrayList<Playlist>();
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

	public MP3File getFile(Song s) throws  IOException, CustomSQLException{
		String sql = "SELECT * FROM Files WHERE SongId = ?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, s.getSongId());
			ResultSet result = stmt.executeQuery(); 
			
			result.first(); // move to first line of resultset
			
			return new MP3File(result.getInt("SongId"), parser(result.getBlob("file"), s));
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}
	
	private File parser(Blob blob, Song s) throws IOException, FileNotFoundException, SQLException{
		
		//find songname and uses as file name
		String fileName = "";
		for (Song a : songArrayList) {
			if(a.getSongId() == s.getSongId()){
				fileName = a.getName();
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
	
	public void deleteSong(Song s) throws CustomSQLException {
		try {
			//Song table
			System.out.println(s.getName());
			String sql = "DELETE FROM Songs WHERE SongId = ?";
			PreparedStatement stmt;
			stmt = con.prepareStatement(sql);
			stmt.setInt(1,s.getSongId());
			stmt.execute();
			
			getSongs(); //update list
			cleanUp();
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}
	
	public void cleanUp() throws CustomSQLException{
		try {
			Statement stmt;
			stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT SongId from Songs");
			boolean del = true;
			while(result.next()){
				for(Song s  : songArrayList) {
					if(Integer.parseInt(result.getString("SongId")) == s.getSongId()) {
						del = false;
						break;
					}
				}
				if(del) {
					deleteFile(Integer.parseInt(result.getString("SongId")));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean deleteUnused(Song s) throws CustomSQLException {
		for(Song a  : songArrayList) {
			if(s.getSongId()==a.getSongId()) {
				return false;				//stops execution if file is used
			}
		}
		deleteFile(s.getSongId());
		
		getSongs();
		
		return true;
	}
		
	public void deleteFile(int songId) throws CustomSQLException {
		try {
			//file table
			String sql = "DELETE FROM Files WHERE SongId = ?";
			PreparedStatement stmt;
			stmt = con.prepareStatement(sql);
			stmt.setInt(1,songId);
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}

	public void addToPlaylist(Song s, Playlist p) throws CustomSQLException {
		try {
			String sql = "INSERT INTO Songs VALUES(?, ?, ?)";
			PreparedStatement stmt;
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, s.getSongId());
			stmt.setInt(2, p.getPlaylistId());
			stmt.setString(3, s.getName());
			stmt.execute();
			
			getSongs(); 
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}

	public void deleteFromPlaylist(Song s, Playlist p) throws CustomSQLException {
		try {
			String sql = "DELETE FROM Songs WHERE SongId = ? AND PlaylistId = ?";
			PreparedStatement stmt;
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, s.getSongId());
			stmt.setInt(2, p.getPlaylistId());
			stmt.execute();
			
			getSongs();
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}
	public void deletePlaylist(Playlist p) throws CustomSQLException {
		try {
			//songs table
			String sql = "DELETE from Songs WHERE PlaylistId = ?";
			PreparedStatement stmt;
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, p.getPlaylistId());
			stmt.execute();
			
			//playlist table
			sql = "DELETE from Playlists WHERE PlaylistId = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, p.getPlaylistId());
			stmt.execute();
			
			getSongs();
	        getPlaylists();  
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}
	
	public boolean filePresent(int songId) throws CustomSQLException {
		try {
			//files table
			String sql = "SELECT SongId FROM Files WHERE SongId = ?";
			PreparedStatement stmt;
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, songId);
			ResultSet result = stmt.executeQuery(); 
			return result.next();
		}catch(SQLException e){
			e.printStackTrace();
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}
	
	public void addPlaylist(Playlist p) throws CustomSQLException {
		try {
			String sql = "INSERT INTO Playlists(name,genre,description) VALUES( ?, ?, ?)";
			PreparedStatement stmt;
			stmt = con.prepareStatement(sql);
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getGenre());
			stmt.setString(3, p.getDesc());
			stmt.execute();
			
			getSongs();
			getPlaylists();
		} catch (SQLException e) {
			throw new CustomSQLException(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}
	
	//public void addPlaylist()
	
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
