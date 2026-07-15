package org.ibm.jakarta.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.ibm.jakarta.dto.ReportDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportRepository {
	private List<ReportDto> reports = new ArrayList<>();
	
	public List<ReportDto> findAll() {
		return reports;
	}
	
	public void create(ReportDto report) {
		reports.add(report);
	}
	
	public void delete(ReportDto report) {
		reports.remove(report);
		report.setTitle(null);
		report.setDetail(null);
	}
}
