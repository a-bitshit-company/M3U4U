package src;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import src.Exceptions.CustomSQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, CustomSQLException{
			Db db = new Db();
			//db.getFile(1000000); 
			File test = new File("/home/matteo/test/test");
			db.uploadSong(test);
	}
}
