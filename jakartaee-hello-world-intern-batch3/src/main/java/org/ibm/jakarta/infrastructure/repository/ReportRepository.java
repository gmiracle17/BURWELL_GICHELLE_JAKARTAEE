package org.ibm.jakarta.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.ibm.jakarta.dto.ReportDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportRepository {
	private List<ReportDto> reports = new ArrayList<>();
	private Long nextId = 1L;

	public List<ReportDto> findAll() {
		return reports;
	}

	public ReportDto findById(Long id) {
		for (ReportDto r : reports) {
			if (r.getId().equals(id)) {
				return r;
			}
		}
		return null;
	}

	public void create(ReportDto report) {
		report.setId(nextId++);
		reports.add(report);
	}

	public void update(ReportDto report) {
	    for (ReportDto r : reports) {
	        if (r.getId().equals(report.getId())) {
	            r.setTitle(report.getTitle());
	            r.setDetail(report.getDetail());
	            break;
	        }
	    }
	}

	public void delete(ReportDto report) {
		reports.removeIf(r -> r.getId().equals(report.getId()));
		report.setTitle(null);
		report.setDetail(null);
	}

}