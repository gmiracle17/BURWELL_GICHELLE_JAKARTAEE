package org.ibm.jakarta.backingbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ibm.jakarta.dto.ReportDto;
import org.ibm.jakarta.infrastructure.repository.ReportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the ReportListBean class.
 * Tests the list functionality including initialization, updates, and deletions of reports.
 * 
 * @author Gichelle Burwell
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ReportListBeanTest {

    /** Mock repository for testing report operations */
    @Mock
    private ReportRepository reportRepository;

    /** The bean instance under test */
    @InjectMocks
    private ReportListBean reportListBean;

    /** First test report for testing purposes */
    private ReportDto report1;
    
    /** Second test report for testing purposes */
    private ReportDto report2;

    /**
     * Sets up the test environment before each test method.
     * Initializes two test reports with sample data.
     */
    @BeforeEach
    void setUp() {
        // Create first test report
        report1 = new ReportDto();
        report1.setTitle("Report One");
        report1.setDetail("Detail One");

        // Create second test report
        report2 = new ReportDto();
        report2.setTitle("Report Two");
        report2.setDetail("Detail Two");
    }

    /**
     * Cleans up the test environment after each test method.
     * Sets all test objects to null to free up memory.
     */
    @AfterEach
    void tearDown() {
        // Clean up test objects
        reportRepository = null;
        reportListBean = null;
        report1 = null;
        report2 = null;
    }

    /**
     * Tests the init() method with populated repository.
     * Verifies that reports are properly loaded from the repository.
     */
    @Test
    void testInit_ShouldPopulateReportsFromRepository() {
        // Set up test data
        List<ReportDto> allReports = new ArrayList<>(List.of(report1, report2));
        when(reportRepository.findAll()).thenReturn(allReports);

        // Execute init operation
        reportListBean.init();

        // Verify reports were loaded correctly
        assertEquals(2, reportListBean.getReports().size(), "Should contain 2 reports");
        assertSame(allReports, reportListBean.getReports(), "Should return the same list from repository");
        verify(reportRepository, times(1)).findAll();
    }

    /**
     * Tests the init() method with empty repository.
     * Verifies that an empty list is handled correctly.
     */
    @Test
    void testInit_ShouldHandleEmptyList() {
        // Set up test data with empty list
        when(reportRepository.findAll()).thenReturn(new ArrayList<>());

        // Execute init operation
        reportListBean.init();

        // Verify empty list is handled correctly
        assertTrue(reportListBean.getReports().isEmpty(), "Reports list should be empty");
    }

    /**
     * Tests the getReports() and setReports() methods.
     * Verifies that the reports list can be stored and retrieved correctly.
     */
    @Test
    void testGetReportsAndSetReports_ShouldStoreAndReturnValue() {
        // Create custom list
        List<ReportDto> customList = List.of(report1);

        // Set custom list
        reportListBean.setReports(customList);

        // Verify list is stored and retrieved correctly
        assertSame(customList, reportListBean.getReports(), "Should return the same list that was set");
    }

    /**
     * Tests the updateReport() method with valid data.
     * Verifies that the repository update is called when reports are initialized and report is not null.
     */
    @Test
    void testUpdateReport_ShouldCallRepositoryUpdate_WhenReportsInitializedAndReportNotNull() {
        // Set up test data
        reportListBean.setReports(new ArrayList<>(List.of(report1)));

        // Execute update operation
        reportListBean.updateReport(report1);

        // Verify repository update was called
        verify(reportRepository, times(1)).update(report1);
    }

    /**
     * Tests the updateReport() method when reports list is null.
     * Verifies that repository update is not called when reports list is null.
     */
    @Test
    void testUpdateReport_ShouldNotCallRepository_WhenReportsIsNull() {
        // Set reports to null
        reportListBean.setReports(null);

        // Execute update operation
        reportListBean.updateReport(report1);

        // Verify repository update was not called
        verify(reportRepository, never()).update(report1);
    }

    /**
     * Tests the updateReport() method when report parameter is null.
     * Verifies that repository update is not called when report is null.
     */
    @Test
    void testUpdateReport_ShouldNotCallRepository_WhenReportIsNull() {
        // Set up test data
        reportListBean.setReports(new ArrayList<>(List.of(report1)));

        // Execute update operation with null report
        reportListBean.updateReport(null);

        // Verify repository update was not called
        verify(reportRepository, never()).update(null);
    }

    /**
     * Tests the deleteReport() method with valid data.
     * Verifies that the report is removed from the list and repository delete is called.
     */
    @Test
    void testDeleteReport_ShouldRemoveFromListAndCallRepositoryDelete_WhenReportsInitializedAndReportNotNull() {
        // Set up test data
        List<ReportDto> reports = new ArrayList<>(List.of(report1, report2));
        reportListBean.setReports(reports);

        // Execute delete operation
        reportListBean.deleteReport(report1);

        // Verify report was removed from list
        assertFalse(reportListBean.getReports().contains(report1), "Report should be removed from list");
        assertEquals(1, reportListBean.getReports().size(), "List should contain 1 report after deletion");
        
        // Verify repository delete was called
        verify(reportRepository, times(1)).delete(report1);
    }

    /**
     * Tests the deleteReport() method when reports list is null.
     * Verifies that repository delete is not called when reports list is null.
     */
    @Test
    void testDeleteReport_ShouldNotModifyListOrCallRepository_WhenReportsIsNull() {
        // Set reports to null
        reportListBean.setReports(null);

        // Execute delete operation
        reportListBean.deleteReport(report1);

        // Verify repository delete was not called
        verify(reportRepository, never()).delete(report1);
    }

    /**
     * Tests the deleteReport() method when report parameter is null.
     * Verifies that repository delete is not called when report is null.
     */
    @Test
    void testDeleteReport_ShouldNotCallRepository_WhenReportIsNull() {
        // Set up test data
        List<ReportDto> reports = new ArrayList<>(List.of(report1, report2));
        reportListBean.setReports(reports);

        // Execute delete operation with null report
        reportListBean.deleteReport(null);

        // Verify list size remains unchanged
        assertEquals(2, reportListBean.getReports().size(), "List size should remain unchanged");
        
        // Verify repository delete was not called
        verify(reportRepository, never()).delete(null);
    }
}
