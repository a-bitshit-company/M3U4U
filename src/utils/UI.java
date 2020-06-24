package src.utils;


import src.Db;
import src.Types.Playlist;

public class UI {

	public UI() {
	}
	
	public void showPLaylist(Db db,String playlist) {
		for(Playlist p : db.getPlaylistArrayList()) {
			if(p.getName().equalsIgnoreCase(playlist)) {
				p.show(db);
				return;
			}
		}
		System.out.println("No PLaylist with this name has been found");
	}
	
	public void showPlaylists(Db db) {
		for(Playlist p : db.getPlaylistArrayList()) {
			System.out.printf("ID: %d	Name: %s",p.getPlaylistId(),p.getName());
		}
	}

}
