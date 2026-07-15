package org.ibm.jakarta.dto;

public class ReportDto {
	private static Long nextId = (long) 1;

	private Long id;
	private String title;
	private String detail;

	public ReportDto() {
		this.id = nextId++;
	}

	public ReportDto(String title, String detail) {
		this.id = nextId++;
		this.title = title;
		this.detail = detail;
	}

	public Long getId() {
		return id;
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