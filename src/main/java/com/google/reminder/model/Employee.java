package com.google.reminder.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String empName;
	private String empEmail;
	//@Column(name = "filled_weeks", columnDefinition = "VARCHAR(255)")
	private Set<Integer> filledWeeks;
	
	public Employee() {
		
	}

	public Employee(Long id, String empName, String empEmail, Set<Integer> filledWeeks) {
		super();
		this.id = id;
		this.empName = empName;
		this.empEmail = empEmail;
		this.filledWeeks = filledWeeks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public Set<Integer> getFilledWeeks() {
		return filledWeeks;
	}

	public void setFilledWeeks(Set<Integer> filledWeeks) {
		this.filledWeeks = filledWeeks;
	}

	@Override
	public int hashCode() {
		return Objects.hash(empEmail, empName, filledWeeks, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(empEmail, other.empEmail) && Objects.equals(empName, other.empName)
				&& Objects.equals(filledWeeks, other.filledWeeks) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", empName=" + empName + ", empEmail=" + empEmail + ", filledWeeks=" + filledWeeks
				+ "]";
	}

}
