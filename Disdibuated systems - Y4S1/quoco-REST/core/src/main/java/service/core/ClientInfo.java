package service.core;
 
/**
 * Interface to define the state to be stored in ClientInfo objects
 * 
 * @author Rem
 *
 */
public class ClientInfo {
	public static final char MALE				= 'M';
	public static final char FEMALE				= 'F';
	
	public ClientInfo(String name, char sex, int age, int points, int noClaims, String licenseNumber) {
		this.name = name;
		this.gender = sex;
		this.age = age;
		this.points = points;
		this.noClaims = noClaims;
		this.licenseNumber = licenseNumber;
	}
	
	public ClientInfo() {}

	private String name;
	private char gender;
	private int age;
	private int points;
	private int noClaims;
	private String licenseNumber;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getGender() {
		return this.gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getNoClaims() {
		return this.noClaims;
	}

	public void setNoClaims(int noClaims) {
		this.noClaims = noClaims;
	}

	public String getLicenseNumber() {
		return this.licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

}
