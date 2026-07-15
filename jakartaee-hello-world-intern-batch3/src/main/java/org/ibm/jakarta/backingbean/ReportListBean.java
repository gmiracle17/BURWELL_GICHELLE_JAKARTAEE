package org.ibm.jakarta.backingbean;

import java.util.List;

import org.ibm.jakarta.dto.ReportDto;
import org.ibm.jakarta.infrastructure.repository.ReportRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ReportListBean {
	private List<ReportDto> reports;
	
	@Inject
	private ReportRepository reportRepository;
	
	@PostConstruct
	public void init() {
		reports = reportRepository.findAll();
	}
	
	public List<ReportDto> getReports() {
		return reports;
	}
	
	public void setReports(List<ReportDto> reports) {
		this.reports = reports;
	}
	
	public void deleteReport(ReportDto report) {
		if (reports != null && report != null) {
			reports.remove(report);
			reportRepository.delete(report);
		}
	}
}