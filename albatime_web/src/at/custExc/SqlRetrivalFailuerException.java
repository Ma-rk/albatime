package at.custExc;

public class SqlRetrivalFailuerException extends RuntimeException {
	public SqlRetrivalFailuerException(String msg) {
		super(msg);
	}

	public SqlRetrivalFailuerException(String msg, Throwable cause) {
		super(msg, cause);
	}
}