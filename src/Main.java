package src;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException{
			Db db = new Db();
			db.getFile(1000000); //test if right file for id gets used
	}

}
