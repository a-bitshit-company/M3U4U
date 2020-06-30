package src;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import src.Exceptions.CustomSQLException;
import src.Exceptions.SongNotFoundException;
import src.utils.ObjectFinder;
import src.utils.UI;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, CustomSQLException, SongNotFoundException{
		Db db = new Db();
		UI ui = new UI();
		ObjectFinder of = new ObjectFinder();
		File test = new File("/home/matteo/test/huan");

			
		Scanner scan = new Scanner(System.in);
			
		while(true) {
			String [] command = scan.nextLine().split(" +(?!$)");

			//[show delete] [song playlist PLAYLISTNAME]
			//show [song playlist
			if(command.length < 2) {
				System.out.println("SYNTAX ERROR: not enough arguments");
				continue;
			}
//TODO: list song(show playlists where song is in)
			switch(command[0]) {
			
				case "list":
					switch (command[1]){
						case "playlists":
							ui.showPlaylists(db);
							break;
						
						case "playlist":
							if(command.length < 3){
								System.out.println("SYNTAX ERROR: not enough arguments");
								break;
							}
							ui.showPLaylist(db, Integer.parseInt(command[2]));
							break;
							
						case "songs":
							ui.showSongs(db); 
							break;		
							
						default:
							System.out.printf("ARGUMENT ERROR: no list of %s found\n", command[1]);
					}
					break;

					
				case "delete":
					if(command.length < 3){
						System.out.println("SYNTAX ERROR: not enough arguments");
						continue;
					}

					switch (command[1]){
						case "playlist":
							db.deletePlaylist(of.findPLaylist(command[2],db.getPlaylistArrayList()));
							break;
							
						case "song":
							break;
							
						case "file":
							if(!db.deleteUnused(of.findSong(Integer.parseInt(command[2]), db.getSongArrayList()))) {
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
						//case 
					}
					break;
				case "find":
					
			}
		}
	}
}