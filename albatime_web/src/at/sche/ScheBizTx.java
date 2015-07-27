package at.sche;

import at.sche.interfaces.IScheBiz;

public class ScheBizTx implements IScheBiz {
	/*
	 * DI codes
	 */
	private IScheBiz scheBiz;

	public void setScheBiz(IScheBiz scheBiz) {
		this.scheBiz = scheBiz;
	}

}
