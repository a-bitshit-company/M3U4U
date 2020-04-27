package src.Types;

public class Playlist {
	private String name;
	private String genre;
	private String desc;
	private int playlistId;
	
	public Playlist(String name, String genre, String desc, int playlistId){
		this.name = name;
		this.genre = genre;
		this.desc = desc;
		this.playlistId = playlistId;
	}

	public String getName() {
		return name;
	}

	public String getGenre() {
		return genre;
	}

	public String getDesc() {
		return desc;
	}

	public int getPlaylistId() {
		return playlistId;
	}
	
	
	
}
