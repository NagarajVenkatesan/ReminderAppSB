package com.google.reminder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.reminder.dto.TimesheetUpdateRequest;
import com.google.reminder.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class EmployeeRestController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("timesheet")
	private ResponseEntity<String> updateAttendance(@Valid @RequestBody TimesheetUpdateRequest req) {
		employeeService.updateTimesheet(req.getEmpId(), req.getWeekNumber());
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}
