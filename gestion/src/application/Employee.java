package application;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private double salary;
	private String gender;

	public Employee(int id, String firstName, String lastName, double salary, String gender) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public double getSalary() {
		return salary;
	}

	public String getGender() {
		return gender;
	}
}
