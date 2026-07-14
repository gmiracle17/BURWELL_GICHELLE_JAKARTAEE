package org.ibm.jakarta.backingbean;

import org.ibm.jakarta.dto.ReportDto;
import org.ibm.jakarta.infrastructure.repository.ReportRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;

@Named
@RequestScoped
public class ReportNewBean {
	@NotBlank(message = "You cannot leave the title blank.")
	private String title;
	private String detail;
	
	@Inject
	private ReportRepository reportRepository;
	
	public String create() {
		ReportDto newReport = new ReportDto();
		newReport.setTitle(title);
		newReport.setDetail(detail);
		reportRepository.create(newReport);
		
		return "/reportList.xhtml?faces-redirect=true";
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
