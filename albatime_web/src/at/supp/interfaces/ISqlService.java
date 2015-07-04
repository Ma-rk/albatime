package at.supp.interfaces;

import at.custExc.SqlRetrivalFailuerException;

public interface ISqlService {
	String getSql(String key) throws SqlRetrivalFailuerException;
}
