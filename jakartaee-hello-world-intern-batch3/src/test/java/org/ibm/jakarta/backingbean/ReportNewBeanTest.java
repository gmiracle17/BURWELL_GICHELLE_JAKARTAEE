package org.ibm.jakarta.backingbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.ibm.jakarta.dto.ReportDto;
import org.ibm.jakarta.infrastructure.repository.ReportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the ReportNewBean class.
 * Tests the creation of new reports and validation of report data.
 * 
 * @author Gichelle Burwell
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ReportNewBeanTest {

    /** Mock repository for testing report operations */
    @Mock
    private ReportRepository reportRepository;

    /** The bean instance under test */
    @InjectMocks
    private ReportNewBean reportNewBean;

    /**
     * Sets up the test environment before each test method.
     * Initializes the test bean instance.
     */
    @BeforeEach
    void setUp() {
        // Bean is automatically initialized by @InjectMocks
        // Additional setup can be added here if needed
    }

    /**
     * Cleans up the test environment after each test method.
     * Sets all test objects to null to free up memory.
     */
    @AfterEach
    void tearDown() {
        // Clean up test objects
        reportRepository = null;
        reportNewBean = null;
    }

    /**
     * Tests the create() method with valid report data.
     * Verifies that a ReportDto is built with the given fields and passed to the repository.
     */
    @Test
    void testCreate_ShouldBuildReportDtoWithGivenFieldsAndCallRepositoryCreate() {
        // Set up test data
        reportNewBean.setTitle("New Title");
        reportNewBean.setDetail("New Detail");

        // Create argument captor to capture the ReportDto passed to repository
        ArgumentCaptor<ReportDto> captor = ArgumentCaptor.forClass(ReportDto.class);

        // Execute create operation
        reportNewBean.create();

        // Verify repository create was called with correct data
        verify(reportRepository, times(1)).create(captor.capture());
        ReportDto captured = captor.getValue();
        assertEquals("New Title", captured.getTitle(), "Title should match the input value");
        assertEquals("New Detail", captured.getDetail(), "Detail should match the input value");
    }

    /**
     * Tests the create() method return value.
     * Verifies that the correct navigation outcome is returned.
     */
    @Test
    void testCreate_ShouldReturnNavigationOutcome() {
        // Set up test data
        reportNewBean.setTitle("New Title");
        reportNewBean.setDetail("New Detail");

        // Execute create operation
        String outcome = reportNewBean.create();

        // Verify navigation outcome
        assertEquals("/reportList.xhtml?faces-redirect=true", outcome, "Should return correct navigation outcome");
    }

    /**
     * Tests the create() method when detail is null.
     * Verifies that repository create is still called even when detail is null.
     */
    @Test
    void testCreate_ShouldCallRepositoryCreate_EvenWhenDetailIsNull() {
        // Set up test data with null detail
        reportNewBean.setTitle("Title Only");
        reportNewBean.setDetail(null);

        // Create argument captor to capture the ReportDto passed to repository
        ArgumentCaptor<ReportDto> captor = ArgumentCaptor.forClass(ReportDto.class);

        // Execute create operation
        reportNewBean.create();

        // Verify repository create was called with correct data
        verify(reportRepository, times(1)).create(captor.capture());
        assertEquals("Title Only", captor.getValue().getTitle(), "Title should match the input value");
        assertEquals(null, captor.getValue().getDetail(), "Detail should be null as set");
    }

    /**
     * Tests that create() calls repository exactly once.
     * Verifies that the repository create method is invoked only one time.
     */
    @Test
    void testCreate_ShouldCallRepositoryCreate_ExactlyOnce() {
        // Set up test data
        reportNewBean.setTitle("Some Title");

        // Execute create operation
        reportNewBean.create();

        // Verify repository create was called exactly once
        verify(reportRepository, times(1)).create(any(ReportDto.class));
    }

    /**
     * Tests the getters and setters for all bean properties.
     * Verifies that values are stored and retrieved correctly.
     */
    @Test
    void testGettersAndSetters_ShouldStoreAndReturnValues() {
        // Set values
        reportNewBean.setTitle("Some Title");
        reportNewBean.setDetail("Some Detail");

        // Verify values are retrieved correctly
        assertEquals("Some Title", reportNewBean.getTitle(), "Title should match the set value");
        assertEquals("Some Detail", reportNewBean.getDetail(), "Detail should match the set value");
    }
}
