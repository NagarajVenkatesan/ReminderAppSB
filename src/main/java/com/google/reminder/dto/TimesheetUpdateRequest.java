package com.google.reminder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TimesheetUpdateRequest {

	@NotBlank(message = "Please provide Employee ID")
	@NotNull
	private Long empId;
	
	@NotBlank(message = "Please provide Timesheet week number")
	private Integer weekNumber;
	
	public TimesheetUpdateRequest() {
		
	}

	public TimesheetUpdateRequest(Long empId, Integer weekNumber) {
		this.empId = empId;
		this.weekNumber = weekNumber;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	@Override
	public String toString() {
		return "TimesheetUpdateRequest [empId=" + empId + ", weekNumber=" + weekNumber + "]";
	}
}
