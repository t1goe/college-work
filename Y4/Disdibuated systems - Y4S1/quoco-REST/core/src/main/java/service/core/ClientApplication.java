package service.core;

import java.io.Serializable;
import java.util.List;

public class ClientApplication implements Serializable{
	public ClientApplication(int ID, ClientInfo client, List<Quotation> quotations) {
		this.ID = ID;
		this.client = client;
		this.quotations = quotations;
	}

	public ClientApplication(){}

	private int ID;
	private ClientInfo client;
	private List<Quotation> quotations;

	public int getID() {
		return this.ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public ClientInfo getClient() {
		return this.client;
	}

	public void setClient(ClientInfo client) {
		this.client = client;
	}

	public List<Quotation> getQuotations() {
		return this.quotations;
	}

	public void setQuotations(List<Quotation> quotations) {
		this.quotations = quotations;
	}

}
