package org.ibm.jakarta.infrastracture.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.ibm.jakarta.dto.ReportDto;
import org.ibm.jakarta.infrastructure.repository.ReportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ReportRepository class.
 * Tests all CRUD operations and query methods for managing ReportDto objects.
 * 
 * @author Gichelle Burwell
 * @version 1.0
 */
class ReportRepositoryTest {

	/** The repository instance under test */
	private ReportRepository reportRepository;
	
	/** First test report for testing purposes */
	private ReportDto testReport1;
	
	/** Second test report for testing purposes */
	private ReportDto testReport2;
	
	/**
	 * Sets up the test environment before each test method.
	 * Initializes a new ReportRepository instance and creates two test reports.
	 * Each test gets a fresh repository instance to ensure test isolation.
	 */
	@BeforeEach
	void setUp() {
		// Initialize a fresh repository for each test to ensure isolation
		reportRepository = new ReportRepository();
		
		// Create first test report (ID will be assigned by repository)
		testReport1 = new ReportDto();
		testReport1.setTitle("Test Report 1");
		testReport1.setDetail("This is the first report.");
		
		// Create second test report (ID will be assigned by repository)
		testReport2 = new ReportDto();
		testReport2.setTitle("Test Report 2");
		testReport2.setDetail("This is the second report.");		
	}
	
	/**
	 * Cleans up the test environment after each test method.
	 * Sets all test objects to null to free up memory.
	 */
	@AfterEach
	void tearDown() {
		// Clean up test objects
		reportRepository = null;
		testReport1 = null;
		testReport2 = null;
	}
	
	/**
	 * Tests the create() method of ReportRepository.
	 * Verifies that reports are created with proper IDs and stored correctly.
	 */
	@Test
	void testCreate() {
		// Create first report
		reportRepository.create(testReport1);
		
		// Verify first report was created with correct ID and properties
		assertNotNull(testReport1.getId(), "Report ID should not be null after creation.");
		assertEquals(1L, testReport1.getId(), "Report ID should be 1 after first creation");
		assertEquals("Test Report 1", testReport1.getTitle(), "Title should match");
		assertEquals("This is the first report.", testReport1.getDetail(), "Detail should match");
		assertEquals(1, reportRepository.findAll().size(), "Repository should contain 1 report");
		
		// Create second report
		reportRepository.create(testReport2);
		
		// Verify second report was created with correct ID and properties
		assertNotNull(testReport2.getId(), "Report ID should not be null after creation.");
		assertEquals(2L, testReport2.getId(), "Report ID should be 2 after second creation");
		assertEquals("Test Report 2", testReport2.getTitle(), "Title should match");
		assertEquals("This is the second report.", testReport2.getDetail(), "Detail should match");
		assertEquals(2, reportRepository.findAll().size(), "Repository should contain 2 reports");	
	}
	
	/**
	 * Tests the findAll() method of ReportRepository.
	 * Verifies that all reports are retrieved correctly from empty and populated repositories.
	 */
	@Test
	void testFindAll() {
		// Verify repository is initially empty
		assertEquals(0, reportRepository.findAll().size(), "Repository should be empty initially");
		
		// Add first report and verify
		reportRepository.create(testReport1);
		assertEquals(1, reportRepository.findAll().size(), "Repository should contain 1 report");
		assertTrue(reportRepository.findAll().contains(testReport1), "Repository should contain testReport1");
		
		// Add second report and verify both are present
		reportRepository.create(testReport2);
		assertEquals(2, reportRepository.findAll().size(), "Repository should contain 2 reports");
		assertTrue(reportRepository.findAll().contains(testReport1), "Repository should contain testReport1");
		assertTrue(reportRepository.findAll().contains(testReport2), "Repository should contain testReport2");
	}

	/**
	 * Tests the findById() method of ReportRepository.
	 * Verifies that reports can be found by their ID and returns null for non-existing IDs.
	 */
	@Test
	void testFindById() {
		// Create test reports
		reportRepository.create(testReport1);
		reportRepository.create(testReport2);
		
		// Find first report by ID and verify properties
		ReportDto foundReport1 = reportRepository.findById(testReport1.getId());
		assertNotNull(foundReport1, "Should find report with ID 1");
		assertEquals(testReport1.getId(), foundReport1.getId(), "ID should match");
		assertEquals("Test Report 1", foundReport1.getTitle(), "Title should match");
		assertEquals("This is the first report.", foundReport1.getDetail(), "Detail should match");
		
		// Find second report by ID and verify properties
		ReportDto foundReport2 = reportRepository.findById(testReport2.getId());
		assertNotNull(foundReport2, "Should find report with ID 2");
		assertEquals(testReport2.getId(), foundReport2.getId(), "ID should match");
		assertEquals("Test Report 2", foundReport2.getTitle(), "Title should match");
		assertEquals("This is the second report.", foundReport2.getDetail(), "Detail should match");
		
		// Verify null is returned for non-existing ID
		ReportDto notFound = reportRepository.findById(999L);
		assertNull(notFound, "Should return null for non-existing ID");
	}

	/**
	 * Tests the update() method of ReportRepository.
	 * Verifies that report properties can be updated while maintaining repository integrity.
	 */
	@Test
	void testUpdate() {
		// Create test reports
		reportRepository.create(testReport1);
		reportRepository.create(testReport2);
		
		// Update first report's properties
		testReport1.setTitle("Updated Report 1");
		testReport1.setDetail("This is the updated first report.");
		reportRepository.update(testReport1);
		
		// Verify first report was updated correctly
		ReportDto updatedReport = reportRepository.findById(testReport1.getId());
		assertNotNull(updatedReport, "Updated report should still exist");
		assertEquals("Updated Report 1", updatedReport.getTitle(), "Title should be updated");
		assertEquals("This is the updated first report.", updatedReport.getDetail(), "Detail should be updated");
		assertEquals(2, reportRepository.findAll().size(), "Repository size should remain the same");
		
		// Update second report's properties
		testReport2.setTitle("Updated Report 2");
		testReport2.setDetail("This is the updated second report.");
		reportRepository.update(testReport2);
		
		// Verify second report was updated correctly
		ReportDto updatedReport2 = reportRepository.findById(testReport2.getId());
		assertNotNull(updatedReport2, "Updated report should still exist");
		assertEquals("Updated Report 2", updatedReport2.getTitle(), "Title should be updated");
		assertEquals("This is the updated second report.", updatedReport2.getDetail(), "Detail should be updated");
	}

	/**
	 * Tests the delete() method of ReportRepository.
	 * Verifies that reports are removed from the repository and their properties are cleared.
	 */
	@Test
	void testDelete() {
		// Create test reports
		reportRepository.create(testReport1);
		reportRepository.create(testReport2);
		
		// Verify both reports exist
		assertEquals(2, reportRepository.findAll().size(), "Repository should contain 2 reports");
		
		// Delete first report
		reportRepository.delete(testReport1);
		
		// Verify first report was deleted and properties cleared
		assertEquals(null, testReport1.getTitle(), "Report title should be null after deletion");
		assertEquals(null, testReport1.getDetail(), "Report detail should be null after deletion");
		assertEquals(1, reportRepository.findAll().size(), "Repository should contain 1 report");
		
		// Delete second report
		reportRepository.delete(testReport2);
		
		// Verify second report was deleted and properties cleared
		assertEquals(null, testReport2.getTitle(), "Report title should be null after deletion");
		assertEquals(null, testReport2.getDetail(), "Report detail should be null after deletion");
		assertEquals(0, reportRepository.findAll().size(), "Repository should be empty");
	}
}
