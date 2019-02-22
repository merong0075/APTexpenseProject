package Model;

public class Employee {

private String number;
private String name;
private String age;
private String department;
private String Employmentday;
private String position;
private String address;
private String Phone;
private String image;
public Employee(String number, String name, String age, String department, String employmentday, String position,
		String address, String phone, String image) {
	super();
	this.number = number;
	this.name = name;
	this.age = age;
	this.department = department;
	Employmentday = employmentday;
	this.position = position;
	this.address = address;
	Phone = phone;
	this.image = image;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getEmploymentday() {
	return Employmentday;
}
public void setEmploymentday(String employmentday) {
	Employmentday = employmentday;
}
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPhone() {
	return Phone;
}
public void setPhone(String phone) {
	Phone = phone;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}


}
