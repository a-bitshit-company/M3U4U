package src;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.mysql.cj.util.StringUtils;

import src.Exceptions.CustomSQLException;
import src.Exceptions.PlaylistNotFoundException;
import src.Exceptions.SongNotFoundException;
import src.Types.Playlist;
import src.Types.Song;
import src.utils.ObjectFinder;
import src.utils.UI;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, CustomSQLException, SongNotFoundException, NumberFormatException, PlaylistNotFoundException{
		Db db = new Db();
		File test = new File("/home/matteo/test/huan");

		Scanner scan = new Scanner(System.in);
			
		while(true) {
			String [] command = scan.nextLine().split("\\s+(?!$)");

			switch(command[0]) {
				case "list":
					switch (command[1]){
						case "playlists":
							UI.showPlaylists(db);
							break;
						
						case "playlist":
							if(command.length < 3){
								System.out.println("SYNTAX ERROR: not enough arguments3");
								break;
							}

							if(!StringUtils.isStrictlyNumeric(command[2])) {
								System.out.println("ARGUMENT ERROR: second argument is not and ID");
								break;
							}
							
							UI.showPLaylist(db, Integer.parseInt(command[2]));
							break;
							
						case "songs":
							UI.showSongs(db);
							break;		
							
						default:
							System.out.printf("ARGUMENT ERROR: no list of \"%s\" found\n", command[1]);
							break;	
					}
					break;

				case "delete":
					if(command.length < 3){
						System.out.println("SYNTAX ERROR: not enough arguments2");
						break;
					}
					if(!StringUtils.isStrictlyNumeric(command[2])) {
						System.out.println("ARGUMENT ERROR: second argument is not and ID");
						break;
					}

					switch (command[1]){
						case "playlist":
							db.deletePlaylist(ObjectFinder.findPLaylist(Integer.parseInt(command[2]),db.getPlaylistArrayList()));
							break;
							
						case "song":
							db.deleteSong(ObjectFinder.findSong(Integer.parseInt(command[2]), db.getSongArrayList()));
							break;
							
						case "file":
							if(!db.deleteUnused(ObjectFinder.findSong(Integer.parseInt(command[2]), db.getSongArrayList()))) {
								try {
								if(db.filePresent(Integer.parseInt(command[2]))) {
									System.out.println("DEPENDENCY WARNING: file is still referenced in Songs table. Do you still want to delete the file?(Y/N)");
								}else {
									System.out.println("File was deleted successfully or was never present");
									break;
								}
								if(scan.nextLine().equalsIgnoreCase("Y")) db.deleteFile(Integer.parseInt(command[2]));
								}catch(NumberFormatException e){
									System.out.println("ARGUMENT ERROR: invalid type");
								}
							}
							break;
							
						default:
							System.out.printf("ARGUMENT ERROR: invalid type");
					}
					break;

				case "add":
					switch (command[1]){
						
					}
					break;
					
				case "addto":
					Song s = ObjectFinder.findSong(Integer.parseInt(command[2]), db.getSongArrayList());
					Playlist p = ObjectFinder.findPLaylist(command[1], db.getPlaylistArrayList());
					db.addToPlaylist(s,p);
					System.out.printf("Added %s to playlist %s",s.getName(), p.getName());
					break;
				case "find":
					if(command.length < 3){
						System.out.println("SYNTAX ERROR: not enough arguments1");
						break;
					}
					switch (command[1]){
						case "playlist":

							//this is garbage:
							 String plName = command[2];
							for(int i = 3; i < command.length; i++)
								plName += " " + command[i];
							//end garbage

							System.out.println("Best match for " + plName + ":");
							Playlist pl = ObjectFinder.findPLaylist(command[2], db.getPlaylistArrayList());
							UI.showPLaylist(db, pl.getPlaylistId());
							break;

						case "song":
							//this is garbage:
							plName = command[2];
							for(int i = 3; i < command.length; i++)
								plName += " " + command[i];
							//end garbage
							
							s = ObjectFinder.findSong(command[2], db.getSongArrayList());
							s.show();
							break;
					}
					break;
				case "help":
					System.out.println("USAGE:");
					System.out.println("	help					shows usage of commands");
					System.out.println("	list	playlists			list of playlists");
					System.out.println("		Songs				list of songs");
					System.out.println("		playlist <PlaylistId>		lists songs in playlist");
					System.out.println("");
					System.out.println("	delete	playlist <PlaylistId>		deletes playlist");
					System.out.println("		song <SongId>			deletes song");
					System.out.println("		file <SongId>			deletes file");
					System.out.println("");
					System.out.println("	addto	<PLaylistId> <songId>		adds song to playlist");
					System.out.println("");
					System.out.println("	add	playlist <PlaylistName>		adds playlist");
					System.out.println("		song <PathToFile>		uploads Song to database");
					System.out.println("");
					System.out.println("	find	playlist <PlaylistName>		shows best match for playlistName");
					System.out.println("		song <songName>			show best match for Songname");
					break;
				default:
					System.out.printf("SYNTAX ERROR: command \"%s\" is not valid", Arrays.deepToString(command).replace("[", "").replace(",", "").replace("]", ""));	
			}
		}
	}
}
