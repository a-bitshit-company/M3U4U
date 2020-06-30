package src.Types;

public class Song {
	private int songId;
	private int PlaylistId;
	private String name;
	
	public Song(int songId, int playlistId, String name) {
		this.songId = songId;
		PlaylistId = playlistId;
		this.name = name;
	}
	
	public void show() {
		System.out.printf("	%d	%s\n", songId, name);
	}

	public int getSongId() {
		return songId;
	}

	public int getPlaylistId() {
		return PlaylistId;
	}

	public String getName() {
		return name;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public void setPlaylistId(int playlistId) {
		PlaylistId = playlistId;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
