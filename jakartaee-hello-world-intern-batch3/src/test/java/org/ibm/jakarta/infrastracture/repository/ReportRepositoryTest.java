package org.ibm.jakarta.infrastracture.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.ibm.jakarta.dto.ReportDto;
import org.ibm.jakarta.infrastructure.repository.ReportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportRepositoryTest {

	private ReportRepository reportRepository;
	private ReportDto testReport1;
	private ReportDto testReport2;
	
	@BeforeEach
	void setUp() {
		reportRepository = new ReportRepository();
		
		testReport1 = new ReportDto();
		testReport1.setTitle("Test Report 1");
		testReport1.setDetail("This is the first report.");
		
		testReport2 = new ReportDto();
		testReport2.setTitle("Test Report 2");
		testReport2.setDetail("This is the second report.");		
	}
	
	@AfterEach
	void tearDown() {
		reportRepository = null;
		testReport1 = null;
		testReport2 = null;
	}
	
	@Test
	void testCreate() {
		reportRepository.create(testReport1);
		
		assertNotNull(testReport1.getId(), "Report ID should not be null after creation.");
		assertEquals(1L, testReport1.getId(), "Report ID should be 1 after first creation");
		assertEquals("Test Report 1", testReport1.getTitle());
		assertEquals("This is the first report.", testReport1.getDetail());
		assertEquals(1, reportRepository.findAll().size());
		
		reportRepository.create(testReport2);
		assertNotNull(testReport2.getId(), "Report ID should not be null after creation.");
		assertEquals(2L, testReport2.getId(), "Report ID should be 2 after second creation");
		assertEquals("Test Report 2", testReport2.getTitle());
		assertEquals("This is the second report.", testReport2.getDetail());
		assertEquals(2, reportRepository.findAll().size());	
	}
	
	@Test
	void testDelete() {
		reportRepository.create(testReport1);
		reportRepository.create(testReport2);
		
		assertEquals(2, reportRepository.findAll().size());
		
		reportRepository.delete(testReport1);
		assertEquals(null, testReport1.getTitle(), "Report title should be null after deletion");
		assertEquals(null, testReport1.getDetail(), "Report detail should be null after deletion");
		assertEquals(1, reportRepository.findAll().size());
		
		reportRepository.delete(testReport2);
		assertEquals(null, testReport2.getTitle(), "Report title should be null after deletion");
		assertEquals(null, testReport2.getDetail(), "Report detail should be null after deletion");
		assertEquals(0, reportRepository.findAll().size());
	}
}
