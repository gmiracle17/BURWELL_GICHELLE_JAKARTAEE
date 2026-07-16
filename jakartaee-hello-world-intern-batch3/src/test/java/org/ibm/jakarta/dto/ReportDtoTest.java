package org.ibm.jakarta.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ReportDto class.
 * Tests constructors, getters, setters, ID generation, and Serializable implementation.
 * 
 * @author Gichelle Burwell
 * @version 1.0
 */
class ReportDtoTest {

    /** Test report instance for testing purposes */
    private ReportDto testReport;

    /**
     * Sets up the test environment before each test method.
     * Initializes test objects if needed.
     */
    @BeforeEach
    void setUp() {
        // Test objects are created in individual test methods
        // This method is here for consistency with the standard format
    }

    /**
     * Cleans up the test environment after each test method.
     * Sets all test objects to null to free up memory.
     */
    @AfterEach
    void tearDown() {
        // Clean up test objects
        testReport = null;
    }

    /**
     * Tests the default constructor.
     * Verifies that a non-null ID is assigned when using the default constructor.
     */
    @Test
    void testDefaultConstructor_ShouldAssignNonNullId() {
        // Create report using default constructor
        testReport = new ReportDto();

        // Verify ID is not null
        assertNotNull(testReport.getId(), "Report ID should not be null after creation");
    }

    /**
     * Tests the default constructor for null fields.
     * Verifies that title and detail remain null when using the default constructor.
     */
    @Test
    void testDefaultConstructor_ShouldLeaveTitleAndDetailNull() {
        // Create report using default constructor
        testReport = new ReportDto();

        // Verify title and detail are null
        assertNull(testReport.getTitle(), "Title should be null when using default constructor");
        assertNull(testReport.getDetail(), "Detail should be null when using default constructor");
    }

    /**
     * Tests the parameterized constructor.
     * Verifies that title and detail are set correctly when using the parameterized constructor.
     */
    @Test
    void testParameterizedConstructor_ShouldSetTitleAndDetail() {
        // Create report using parameterized constructor
        testReport = new ReportDto("My Title", "My Detail");

        // Verify title and detail are set correctly
        assertEquals("My Title", testReport.getTitle(), "Title should match the constructor parameter");
        assertEquals("My Detail", testReport.getDetail(), "Detail should match the constructor parameter");
    }

    /**
     * Tests the parameterized constructor for ID assignment.
     * Verifies that a non-null ID is assigned when using the parameterized constructor.
     */
    @Test
    void testParameterizedConstructor_ShouldAssignNonNullId() {
        // Create report using parameterized constructor
        testReport = new ReportDto("My Title", "My Detail");

        // Verify ID is not null
        assertNotNull(testReport.getId(), "Report ID should not be null after creation");
    }

    /**
     * Tests unique ID generation for multiple instances.
     * Verifies that each new instance receives a different, incrementing ID.
     */
    @Test
    void testIdGeneration_EachNewInstanceShouldReceiveADifferentId() {
        // Create two report instances
        ReportDto first = new ReportDto();
        ReportDto second = new ReportDto();

        // Verify IDs are different and incrementing
        assertNotSame(first.getId(), second.getId(), "Each instance should have a different ID");
        assertEquals(first.getId() + 1, second.getId(), "Second ID should be one more than first ID");
    }

    /**
     * Tests unique ID generation regardless of constructor used.
     * Verifies that ID increments consistently across different constructor calls.
     */
    @Test
    void testIdGeneration_EachNewInstanceShouldReceiveADifferentId_RegardlessOfConstructorUsed() {
        // Create three report instances using different constructors
        ReportDto first = new ReportDto("Title A", "Detail A");
        ReportDto second = new ReportDto();
        ReportDto third = new ReportDto("Title B", "Detail B");

        // Verify IDs increment consistently
        assertEquals(first.getId() + 1, second.getId(), "Second ID should be one more than first ID");
        assertEquals(second.getId() + 1, third.getId(), "Third ID should be one more than second ID");
    }

    /**
     * Tests the setTitle() method.
     * Verifies that the title can be updated correctly.
     */
    @Test
    void testSetTitle_ShouldUpdateTitle() {
        // Create report and set title
        testReport = new ReportDto();
        testReport.setTitle("Updated Title");

        // Verify title was updated
        assertEquals("Updated Title", testReport.getTitle(), "Title should be updated to the new value");
    }

    /**
     * Tests the setDetail() method.
     * Verifies that the detail can be updated correctly.
     */
    @Test
    void testSetDetail_ShouldUpdateDetail() {
        // Create report and set detail
        testReport = new ReportDto();
        testReport.setDetail("Updated Detail");

        // Verify detail was updated
        assertEquals("Updated Detail", testReport.getDetail(), "Detail should be updated to the new value");
    }

    /**
     * Tests that ReportDto implements Serializable.
     * Verifies that the class properly implements the Serializable interface.
     */
    @Test
    void testSerializable_ReportDtoShouldImplementSerializable() {
        // Create report instance
        testReport = new ReportDto();

        // Verify it implements Serializable
        assertTrue(testReport instanceof Serializable, "ReportDto should implement Serializable interface");
    }
}
