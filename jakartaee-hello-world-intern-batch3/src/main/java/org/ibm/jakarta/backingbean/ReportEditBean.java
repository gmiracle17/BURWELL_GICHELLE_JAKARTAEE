package org.ibm.jakarta.backingbean;

import java.io.Serializable;

import org.ibm.jakarta.dto.ReportDto;
import org.ibm.jakarta.infrastructure.repository.ReportRepository;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ReportEditBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String detail;

    @Inject
    private ReportRepository reportRepository;

    public void load() {
        ReportDto report = reportRepository.findById(id);
        if (report != null) {
            this.title = report.getTitle();
            this.detail = report.getDetail();
        }
    }

    public String update() {
        ReportDto report = reportRepository.findById(id);
        if (report != null) {
            report.setTitle(title);
            report.setDetail(detail);
            reportRepository.update(report);
        }
        return "reportList?faces-redirect=true";
    }

    public String cancel() {
        return "reportList?faces-redirect=true";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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