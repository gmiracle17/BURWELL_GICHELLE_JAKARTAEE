package org.ibm.jakarta.backingbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
 * Unit tests for the ReportEditBean class.
 * Tests the edit functionality including loading, updating, and canceling report edits.
 * 
 * @author IBM Jakarta EE Team
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ReportEditBeanTest {

    /** Mock repository for testing report operations */
    @Mock
    private ReportRepository reportRepository;

    /** The bean instance under test */
    @InjectMocks
    private ReportEditBean reportEditBean;

    /** Test report for testing purposes */
    private ReportDto existingReport;

    /**
     * Sets up the test environment before each test method.
     * Initializes a test report with sample data.
     */
    @BeforeEach
    void setUp() {
        // Create test report with initial values
        existingReport = new ReportDto();
        existingReport.setTitle("Original Title");
        existingReport.setDetail("Original Detail");
    }

    /**
     * Cleans up the test environment after each test method.
     * Sets all test objects to null to free up memory.
     */
    @AfterEach
    void tearDown() {
        // Clean up test objects
        reportRepository = null;
        reportEditBean = null;
        existingReport = null;
    }

    /**
     * Tests the load() method when a report exists.
     * Verifies that report fields are properly populated from the repository.
     */
    @Test
    void testLoad_ShouldPopulateFields_WhenReportExists() {
        // Set up test data
        reportEditBean.setId(1L);
        when(reportRepository.findById(1L)).thenReturn(existingReport);

        // Execute load operation
        reportEditBean.load();

        // Verify fields were populated correctly
        assertEquals("Original Title", reportEditBean.getTitle(), "Title should match the existing report");
        assertEquals("Original Detail", reportEditBean.getDetail(), "Detail should match the existing report");
        verify(reportRepository, times(1)).findById(1L);
    }

    /**
     * Tests the load() method when a report does not exist.
     * Verifies that fields remain null when the report is not found.
     */
    @Test
    void testLoad_ShouldLeaveFieldsNull_WhenReportDoesNotExist() {
        // Set up test data for non-existing report
        reportEditBean.setId(99L);
        when(reportRepository.findById(99L)).thenReturn(null);

        // Execute load operation
        reportEditBean.load();

        // Verify fields remain null
        assertNull(reportEditBean.getTitle(), "Title should be null when report does not exist");
        assertNull(reportEditBean.getDetail(), "Detail should be null when report does not exist");
        verify(reportRepository, times(1)).findById(99L);
    }

    /**
     * Tests the update() method when a report exists.
     * Verifies that the report is updated in the repository and navigation outcome is returned.
     */
    @Test
    void testUpdate_ShouldUpdateReportAndReturnNavigationOutcome_WhenReportExists() {
        // Set up test data
        reportEditBean.setId(1L);
        reportEditBean.setTitle("Updated Title");
        reportEditBean.setDetail("Updated Detail");
        when(reportRepository.findById(1L)).thenReturn(existingReport);

        // Execute update operation
        String outcome = reportEditBean.update();

        // Verify navigation outcome
        assertEquals("reportList?faces-redirect=true", outcome, "Should return correct navigation outcome");
        
        // Verify report was updated with new values
        assertEquals("Updated Title", existingReport.getTitle(), "Title should be updated");
        assertEquals("Updated Detail", existingReport.getDetail(), "Detail should be updated");
        verify(reportRepository, times(1)).update(existingReport);
    }

    /**
     * Tests the update() method when a report does not exist.
     * Verifies that repository update is not called when the report is not found.
     */
    @Test
    void testUpdate_ShouldNotCallRepositoryUpdate_WhenReportDoesNotExist() {
        // Set up test data for non-existing report
        reportEditBean.setId(404L);
        reportEditBean.setTitle("Doesn't Matter");
        when(reportRepository.findById(404L)).thenReturn(null);

        // Execute update operation
        String outcome = reportEditBean.update();

        // Verify navigation outcome is still returned
        assertEquals("reportList?faces-redirect=true", outcome, "Should return navigation outcome even when report not found");
        
        // Verify repository update was not called
        verify(reportRepository, never()).update(any(ReportDto.class));
    }

    /**
     * Tests the cancel() method.
     * Verifies that the correct navigation outcome is returned.
     */
    @Test
    void testCancel_ShouldReturnNavigationOutcome() {
        // Execute cancel operation
        String outcome = reportEditBean.cancel();

        // Verify navigation outcome
        assertEquals("reportList?faces-redirect=true", outcome, "Should return correct navigation outcome");
    }

    /**
     * Tests the getters and setters for all bean properties.
     * Verifies that values are stored and retrieved correctly.
     */
    @Test
    void testGettersAndSetters_ShouldStoreAndReturnValues() {
        // Set values
        reportEditBean.setId(7L);
        reportEditBean.setTitle("Some Title");
        reportEditBean.setDetail("Some Detail");

        // Verify values are retrieved correctly
        assertEquals(7L, reportEditBean.getId(), "ID should match the set value");
        assertEquals("Some Title", reportEditBean.getTitle(), "Title should match the set value");
        assertEquals("Some Detail", reportEditBean.getDetail(), "Detail should match the set value");
    }
}