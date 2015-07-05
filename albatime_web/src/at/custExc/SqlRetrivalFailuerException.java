package at.custExc;

public class SqlRetrivalFailuerException extends RuntimeException {
	private static final long serialVersionUID = 108214921400909884L;

	public SqlRetrivalFailuerException(String msg) {
		super(msg);
	}

	public SqlRetrivalFailuerException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
