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

	public void setName(String name) {
		this.name = name;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}
	
	
	
}
