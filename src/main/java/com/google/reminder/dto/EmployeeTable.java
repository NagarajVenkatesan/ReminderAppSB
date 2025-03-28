package com.google.reminder.dto;

public class EmployeeTable {
	
	
	private Long id;
	private String empName;
	private String empEmail;	
	private Integer pending;
	
	public EmployeeTable() {
		
	}
	
	public EmployeeTable(Long id, String empName, String empEmail, Integer pending) {		
		this.id = id;
		this.empName = empName;
		this.empEmail = empEmail;
		this.pending = pending;
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

	public Integer getPending() {
		return pending;
	}

	public void setPending(Integer pending) {
		this.pending = pending;
	}

	@Override
	public String toString() {
		return "EmployeeTable [id=" + id + ", empName=" + empName + ", empEmail=" + empEmail + ", pending=" + pending
				+ "]";
	}
	
	
}
