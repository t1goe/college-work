package service.core;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 *
 */
public class Quotation {
	public Quotation(String company, String reference, double price) {
		this.company = company;
		this.reference = reference;
		this.price = price;	
	}

	public Quotation(){}
	
	private String company;
	private String reference;
	private double price;

	public String getCompany(){
		return this.company;
	}

	public void setCompany(String comp){
		this.company = comp;
	}

	public String getRefrence(){
		return this.reference;
	}

	public void setReference(String ref){
		this.reference = ref;
	}

	public double getPrice(){
		return this.price;
	}

	public void setPrice(Double price){
		this.price = price;
	}
}
