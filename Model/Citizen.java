package Model;

public class Citizen {
	private String address;
	private String address2;
	private String password;
	private String password2;
	private String name;
	private String gender;
	private String birth;
	private String email;
	private String phone;
	private String dongho;

	 

	public Citizen(String address, String address2, String password, String name, String gender, String birth,
			String email, String phone) {
		super();
		this.address = address;
		this.address2 = address2;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.email = email;
		this.phone = phone;
	
	}

	public Citizen(String dongho, String address, String address2, String password, String password2,String name,
			String gender, String birth, String email, String phone) {
		super();
		this.address = address;
		this.address2 = address2;
		this.password = password;
		this.password2 = password2;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.email = email;
		this.phone = phone;
		this.dongho = dongho;
	}

	public String getDongho() {
		return dongho;
	}

	public void setDongho(String dongho) {
		this.dongho = dongho;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
