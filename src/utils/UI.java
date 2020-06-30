package src.utils;


import src.Db;
import src.Types.Playlist;
import src.Types.Song;

public class UI {

	public static void showPLaylist(Db db,int playlistId) {
		for(Playlist p : db.getPlaylistArrayList()) {
			if(p.getPlaylistId() == playlistId) {
				p.show(db);
				return;
			}
		}
		System.out.println("ARGUMENT ERROR: No PLaylist with this ID has been found");
	}
	
	public static void showPlaylists(Db db) {
		for(Playlist p : db.getPlaylistArrayList()) {
			System.out.printf("ID: %d	Name: %s\n",p.getPlaylistId(),p.getName());
		}
	}
	public static void showSongs(Db db) {
		for(Song s : db.getSongArrayList()) {
			s.show();
		}
	}
	public static void showFiles(Db db) {
		
	}
}
