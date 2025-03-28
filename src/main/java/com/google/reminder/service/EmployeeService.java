package com.google.reminder.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.reminder.dao.EmployeeRepository;
import com.google.reminder.dto.EmployeeTable;
import com.google.reminder.dto.PendingTable;
import com.google.reminder.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public long getNumberOfEmployees() {
		return employeeRepository.count();
	}

	public List<EmployeeTable> getAllEmployees() {

		Function<Employee, EmployeeTable> fun = emp -> {
			EmployeeTable empTable = new EmployeeTable();
			empTable.setId(emp.getId());
			empTable.setEmpName(emp.getEmpName());
			empTable.setEmpEmail(emp.getEmpEmail());
			Set<Integer> filledWeeks = emp.getFilledWeeks();
			if (filledWeeks == null) {
				empTable.setPending(ReminderService.getCurrentWeek());
			} else {
				List<Integer> missedWeeks = ReminderService.getMissedWeeks(ReminderService.getCurrentWeek(),
						emp.getFilledWeeks());
				empTable.setPending(missedWeeks.size());
			}

			return empTable;
		};

		return employeeRepository.findAll().stream().map(fun).collect(Collectors.toList());
	}

	public Employee getEmployeeDetails(long id) {
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isPresent()) {
			return emp.get();
		}
		return new Employee();
	}

	public List<PendingTable> getPendings(long id) {
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isPresent()) {
			Employee employee = emp.get();
			Set<Integer> filledWeeks = employee.getFilledWeeks();
			List<Integer> missedWeeks = new ArrayList<>();

			if (filledWeeks != null) {
				missedWeeks = ReminderService.getMissedWeeks(ReminderService.getCurrentWeek(),
						employee.getFilledWeeks());
			} else {
				Set<Integer> noFilledWeeks = new TreeSet<>();
				noFilledWeeks.add(0);
				missedWeeks = ReminderService.getMissedWeeks(ReminderService.getCurrentWeek(), noFilledWeeks);
			}

			List<PendingTable> pendingList = new ArrayList<>();

			missedWeeks.forEach(i -> {
				LocalDate date = ReminderService.getDate(LocalDate.now().getYear(), i);
				PendingTable pending = new PendingTable();
				pending.setFrom(date);
				pending.setTo(date.plusDays(4));
				pending.setWeekNumber(i);
				pendingList.add(pending);
			});

			return pendingList;
		}
		return null;
	}

	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public void deleteEmployee(long id) {
		employeeRepository.deleteById(id);
	}

	public boolean updateTimesheet(Long id, Integer weekNumber) {
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isPresent()) {
			Employee employee = emp.get();

			Set<Integer> filledWeeks = new TreeSet<>();

			Set<Integer> existingfilledWeeks = employee.getFilledWeeks();

			if (existingfilledWeeks != null) {
				existingfilledWeeks.forEach(w -> {
					filledWeeks.add(w);
				});
				filledWeeks.add(weekNumber);
			} else {
				filledWeeks.add(weekNumber);
			}

			Employee updateEmp = new Employee();
			updateEmp.setId(employee.getId());
			updateEmp.setEmpName(employee.getEmpName());
			updateEmp.setEmpEmail(employee.getEmpEmail());
			updateEmp.setFilledWeeks(filledWeeks);
			employeeRepository.save(updateEmp);
			return true;
		}
		return false;
	}
}
