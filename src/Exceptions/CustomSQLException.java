package src.Exceptions;

public class CustomSQLException extends Exception {
	String method;

	public CustomSQLException(String method) {
		this.method = method;
		System.out.printf("SQL ERROR: the method %s resulted in an error\n",method);
		
	}


}
