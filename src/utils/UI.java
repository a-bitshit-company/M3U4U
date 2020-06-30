package src.utils;


import src.Db;
import src.Types.Playlist;
import src.Types.Song;

public class UI {

	public UI() {
	}
	
	public void showPLaylist(Db db,int playlistId) {
		for(Playlist p : db.getPlaylistArrayList()) {
			if(p.getPlaylistId() == playlistId) {
				p.show(db);
				return;
			}
		}
		System.out.println("ARGUMENT ERROR: No PLaylist with this name has been found");
	}
	
	public void showPlaylists(Db db) {
		for(Playlist p : db.getPlaylistArrayList()) {
			System.out.printf("ID: %d	Name: %s\n",p.getPlaylistId(),p.getName());
		}
	}
	public void showSongs(Db db) {
		for(Song s : db.getSongArrayList()) {
			s.show();
		}
	}
	public void showFiles(Db db) {
		
	}

}
