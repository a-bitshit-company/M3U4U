package src.Types;

public class Song {
	private int SongId;
	private int PlaylistId;
	private String name;
	
	public Song(int songId, int playlistId, String name) {
		SongId = songId;
		PlaylistId = playlistId;
		this.name = name;
	}

	public int getSongId() {
		return SongId;
	}

	public int getPlaylistId() {
		return PlaylistId;
	}

	public String getName() {
		return name;
	}
	
	
	
}
