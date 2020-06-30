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
import src.utils.ObjectFinder;
import src.utils.UI;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, CustomSQLException, SongNotFoundException, NumberFormatException, PlaylistNotFoundException{
		UI ui = new UI();
		Db db = new Db();
		File test = new File("/home/matteo/test/huan");

		Scanner scan = new Scanner(System.in);
			
		while(true) {
			String [] command = scan.nextLine().split("\\s+(?!$)");

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

							if(!StringUtils.isStrictlyNumeric(command[2])) {
								System.out.println("ARGUMENT ERROR: second argument is not and ID");
								break;
							}
							
							ui.showPLaylist(db, Integer.parseInt(command[2]));
							break;
							
						case "songs":
							ui.showSongs(db); 
							break;		
							
						default:
							System.out.printf("ARGUMENT ERROR: no list of \"%s\" found\n", command[1]);
							break;	
					}
					break;

				case "delete":
					if(command.length < 3){
						System.out.println("SYNTAX ERROR: not enough arguments");
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
							db.deleteSong(ObjectFinder.findSong(Integer.parseInt(command[2]), db.getSongArrayList()),of);
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
						//case 
					}
					break;
				case "find":
					if(command.length < 3){
						System.out.println("SYNTAX ERROR: not enough arguments");
						break;
					}
					switch (command[1]){
						case "playlist":
							ObjectFinder.findPLaylist(command[2], );
							break;
						case "song":

							break;
					}
				case "help":
					
				default:
					System.out.printf("SYNTAX ERROR: command \"%s\" is not valid", Arrays.deepToString(command).replace("[", "").replace(",", "").replace("]", ""));	
			}
		}
	}
}