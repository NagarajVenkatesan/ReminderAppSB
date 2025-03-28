package com.google.reminder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.reminder.dto.PendingTable;
import com.google.reminder.model.Employee;
import com.google.reminder.service.EmployeeService;
import com.google.reminder.service.ReminderService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ReminderService reminderService;
	
	@GetMapping("/")
	public String viewIndexPage(Model model) {
		long numberOfEmployees = employeeService.getNumberOfEmployees();
		model.addAttribute("numberOfEmployees", numberOfEmployees);
		return "index";
	}
	
	@GetMapping("/employees")
	public String viewEmployees(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "employees";
	}
	
	@GetMapping("/pendings/{id}")
	public String getPendings(Model model, @PathVariable("id") long id) {
		model.addAttribute("employee", employeeService.getEmployeeDetails(id));
		List<PendingTable> pendings = employeeService.getPendings(id);		
		if(pendings!=null) {
			model.addAttribute("pendings", pendings);
			return "pendings";
		} else {
			return "redirect:/employees";
		}
	}
	
	@PostMapping("/send")
	public String sendNotificationEmail(long id) {
		Employee employee = employeeService.getEmployeeDetails(id);
		reminderService.sendReminderEmail(employee);
		return "redirect:/employees";
	}
	
	@PostMapping("/employee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/employees";
	}
	
	@GetMapping("/delete/{id}")
    public String deleteContactById(@PathVariable(value = "id") int id) {
		employeeService.deleteEmployee(id);
        return "redirect:/employees";
 
    }
}
