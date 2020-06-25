package src.utils;

import src.Types.Song;

public class ObjectFinder {

	public ObjectFinder() {
		// TODO Auto-generated constructor stub
	}
	
	public PLaylist findPLaylist(int playlistId) {
		
	}
	
	public PLaylist findPLaylist(String name) {
		
	}
	
	public Song findSong(int songId, Song[] songArrayList) {
		for(Song s : songArrayList) {
			if(s.getSongId()== songId) {
				return s;
				break;
			}
	}
	
	public Song findSong(String name, Song[] songArrayList) {
		for(Song s : songArrayList) {
			if(s.getName().equals(name)) {
				return s;
				break;
			}
	}


}
