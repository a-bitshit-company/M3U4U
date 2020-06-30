package src.Exceptions;

public class SongNotFoundException extends Exception{
	String method;


	public SongNotFoundException() {
			System.out.printf("Song was not found\n");
			
		}

}

