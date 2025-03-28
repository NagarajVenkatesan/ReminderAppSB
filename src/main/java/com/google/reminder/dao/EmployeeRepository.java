package com.google.reminder.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.reminder.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
