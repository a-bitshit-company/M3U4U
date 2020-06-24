package src;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import src.Exceptions.CustomSQLException;
import src.utils.UI;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, CustomSQLException{
		Db db = new Db();
		UI ui = new UI();
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

			switch(command[0]) {
			
				case "list":
					switch (command[1]){
						case "playlists":
							ui.showPlaylists(db);
							break;
							
						case "songs":
							ui.showSongs(db); //TODO implement showSongs
							break;
							
						case "files":
							ui.showFiles(db); //TODO implement showFiles
							break;
							
						default:
							System.out.printf("ARGUMENT ERROR: no list of %s found", command[1]);
					}
					break;

					
				case "delete":
					if(command.length < 3){
						System.out.println("SYNTAX ERROR: not enough arguments");
						continue;
					}

					switch (command[1]){
						case "playlist":
							break;
							
						case "song":
							break;
							
						case "file":
							//db.deleteFile();
							break;
							
						default:
							System.out.printf("ARGUMENT ERROR: invalid type");
					}
					break;

				case "add":
					switch (command[1]){
					}
					break;
			}
		}
	}
}