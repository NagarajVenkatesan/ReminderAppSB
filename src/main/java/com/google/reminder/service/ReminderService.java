package com.google.reminder.service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.reminder.dao.EmployeeRepository;
import com.google.reminder.dto.EmailDetails;
import com.google.reminder.model.Employee;

@Service
public class ReminderService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmployeeRepository employeeRepository;

	public void sendReminderEmailtoAllEmployees() {
		List<Employee> allEmployees = employeeRepository.findAll();		
		allEmployees.forEach((emp) -> {
			sendReminderEmail(emp);
		});
	}
	
	@Async
	public void sendReminderEmail(Employee emp) {
		Set<Integer> filledWeeks = emp.getFilledWeeks();
		List<Integer> missedWeeks = new ArrayList<>();
		if (filledWeeks != null) {
			missedWeeks = getMissedWeeks(getCurrentWeek(), filledWeeks);
			
		} else {
			Set<Integer> noFilledWeeks = new TreeSet<>();
			noFilledWeeks.add(0);
			missedWeeks = getMissedWeeks(getCurrentWeek(), noFilledWeeks);
		}
		
		if (!missedWeeks.isEmpty()) {
			String message = "<h2>Hello " + emp.getEmpName() + "</h2>"
					+ "<p>Below are pending weeks. Please fill PPM</p>" + getDatesInHTML(missedWeeks);
			EmailDetails ed = new EmailDetails();
			ed.setSubject("PPM Pending");
			ed.setRecipient(emp.getEmpEmail());
			ed.setMsgBody(message);
			boolean sendMail = emailService.sendSimpleMail(ed);
			if(sendMail) {
				System.out.println("email sent to ::::"+emp.getEmpEmail());
			} else {
				System.out.println("email could not sent to ::::"+emp.getEmpEmail());
			}
		} else {
			System.out.println("There is no pending for ::" + emp.getEmpEmail());
		}
	}

	public static Integer getCurrentWeek() {
		ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		return now.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
	}

	public static LocalDate getDate(int year, int week) {
		LocalDate desiredDate = LocalDate.now().withYear(year).with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
				.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		return desiredDate;
	}

	private StringBuilder getDatesInHTML(List<Integer> missedWeeks) {
		StringBuilder sb = new StringBuilder();
		for (int i : missedWeeks) {
			LocalDate date = getDate(LocalDate.now().getYear(), i);
			LocalDate to = date.plusDays(4);
			sb.append("<table style=\"border: 1px solid black; border-collapse: collapse;\"><tr>");
			sb.append("<td>");
			sb.append(date + " - " + to);
			sb.append("</td>");
			sb.append("</tr></table>");
		}
		return sb;
	}

	public static List<Integer> getMissedWeeks(Integer currentWeek, Set<Integer> filledWeeks) {
		filledWeeks.add(currentWeek + 1);
		Integer N = filledWeeks.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
		BitSet allBitSet = new BitSet(N + 1);
		IntStream.rangeClosed(1, N).forEach(allBitSet::set);
		BitSet presentBitSet = new BitSet(N + 1);
		filledWeeks.stream().forEach(presentBitSet::set);
		allBitSet.and(presentBitSet);
		List<Integer> result = IntStream.rangeClosed(1, N).filter(i -> !allBitSet.get(i)).boxed().sorted()
				.collect(Collectors.toList());
		return result;
	}
}
