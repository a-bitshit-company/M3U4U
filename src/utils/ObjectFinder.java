package src.utils;

import java.util.ArrayList;

import src.Exceptions.PlaylistNotFoundException;
import src.Exceptions.SongNotFoundException;
import src.Types.Playlist;
import src.Types.Song;

public class ObjectFinder {

	public ObjectFinder() {
		// TODO Auto-generated constructor stub
	}
	
	public static Playlist findPLaylist(int playlistId, ArrayList<Playlist> playlistArrayList) throws PlaylistNotFoundException {
		for(Playlist p : playlistArrayList) {
			if(p.getPlaylistId() == playlistId) {
				return p;
			}
		}
		throw new PlaylistNotFoundException();
	}
	
	
	public static Playlist findPLaylist(String name,ArrayList<Playlist> playlistArrayList) throws PlaylistNotFoundException {
		for(Playlist p : playlistArrayList) {
			if(p.getName() == name) {
				return p;
			}
		}
		throw new PlaylistNotFoundException();
	}
	
	public static Song findSong(int songId, ArrayList<Song> arrayList) throws SongNotFoundException {
		for(Song s : arrayList) {
			if(s.getSongId() == songId) {
				return s;
			}
		}
		throw new SongNotFoundException();
	}
	
	public static Song findSong(String name, ArrayList<Song> songArrayList) throws SongNotFoundException {
		for(Song s : songArrayList) {
			if(s.getName().equals(name)) {
				return s;
			}
	}
		throw new SongNotFoundException();
	}
}
