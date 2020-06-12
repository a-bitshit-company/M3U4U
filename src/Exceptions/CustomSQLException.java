package src.Exceptions;

public class CustomSQLException extends Exception {
	String method;

	public CustomSQLException(String method) {
		this.method = method;
		
	}


}
