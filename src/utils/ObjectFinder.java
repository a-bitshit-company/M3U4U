package src.utils;

import java.util.ArrayList;

import src.Exceptions.SongNotFoundException;
import src.Types.Playlist;
import src.Types.Song;

public class ObjectFinder {

	public ObjectFinder() {
		// TODO Auto-generated constructor stub
	}
	
	public Playlist findPLaylist(int playlistId, ArrayList<Playlist> playlistArrayList) {
		for(Playlist p : playlistArrayList) {
			if(p.getPlaylistId() == playlistId) {
				return p;
			}
		}
		System.out.printf("ARGUMENT ERROR: PLaylist with ID: %d not found",playlistId);
		return null;
	}
	
	
	public Playlist findPLaylist(String name,ArrayList<Playlist> playlistArrayList) {
		for(Playlist p : playlistArrayList) {
			if(p.getName() == name) {
				return p;
			}
		}
		System.out.printf("ARGUMENT ERROR: PLaylist with name: %s not found",name);
		return null;
	}
	
	public Song findSong(int songId, ArrayList<Song> arrayList) throws SongNotFoundException {
		for(Song s : arrayList) {
			s.show();
			if(s.getSongId() == songId) {
				return s;
			}
		}
		throw new SongNotFoundException();
	}
	
	public Song findSong(String name, ArrayList<Song> songArrayList) throws SongNotFoundException {
		for(Song s : songArrayList) {
			if(s.getName().equals(name)) {
				return s;
			}
	}
		throw new SongNotFoundException();
	}
	
	
}
