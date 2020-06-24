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
				String s = scan.nextLine();
				String sub = s.substring(0,s.indexOf(" "));
				
				switch(sub) {
				
					case "show":
					ui.showPLaylist(db, s.substring(s.indexOf(" ")+1));
					break;
					
					case "delete":
					//unterscheiden ob playlist oder song deleted wird
						
					case "playlists":
						ui.showPlaylists(db);
				}
			}
	}
}
