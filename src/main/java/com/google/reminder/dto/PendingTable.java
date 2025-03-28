package com.google.reminder.dto;

import java.time.LocalDate;

public class PendingTable {

	private LocalDate from;
	private LocalDate to;
	private Integer weekNumber;
	
	public PendingTable() {
		
	}

	public PendingTable(LocalDate from, LocalDate to, Integer weekNumber) {
		this.from = from;
		this.to = to;
		this.weekNumber = weekNumber;
	}

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	@Override
	public String toString() {
		return "PendingTable [from=" + from + ", to=" + to + ", weekNumber=" + weekNumber + "]";
	}
}
