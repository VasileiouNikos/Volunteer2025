package gr.hua.dit.ds.ds_lab.Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Κλάση για δοκιμές της κλάσης Event
class EventTest {

    // Τεστ για τις μεθόδους getId και setId
    @Test
    void testGetAndSetId() {
        Organization organization = new Organization("Green Initiative", "info@greeninititative.org");
        Event event = new Event(1L, "Test Event", "This is a test description.", true, EventStatus.PENDING, organization, LocalDate.now());
        event.setId(1L);
        assertEquals(1L, event.getId(), "Το ID πρέπει να είναι 1.");
    }

    // Τεστ για τις μεθόδους getName και setName
    @Test
    void testGetAndSetName() {
        Organization organization = new Organization("Green Initiative", "info@greeninitiative.org");
        Event event = new Event(1L, "Test Event", "This is a test description.", true, EventStatus.PENDING, organization, LocalDate.now());
        event.setName("Updated Event");
        assertEquals("Updated Event", event.getName(), "Το όνομα πρέπει να είναι 'Updated Event'.");
    }

    // Τεστ για τις μεθόδους getDescription και setDescription
    @Test
    void testGetAndSetDescription() {
        Organization organization = new Organization("Green Initiative", "info@greeninitiative.org");
        Event event = new Event(1L, "Test Event", "This is a test description.", true, EventStatus.PENDING, organization, LocalDate.now());
        event.setDescription("Updated description.");
        assertEquals("Updated description.", event.getDescription(), "Η περιγραφή πρέπει να είναι 'Updated description.'.");
    }

    // Τεστ για τις μεθόδους isApproved και setApproved
    @Test
    void testIsAndSetApproved() {
        Organization organization = new Organization("Green Initiative", "info@greeninitiative.org");
        Event event = new Event(1L, "Test Event", "This is a test description.", true, EventStatus.PENDING, organization, LocalDate.now());
        event.setApproved(false);
        assertFalse(event.isApproved(), "Η έγκριση πρέπει να είναι false.");
    }

    // Τεστ για τη μέθοδο setStatus
    @Test
    void testEventStatus() {
        Organization organization = new Organization("Green Initiative", "info@greeninitiative.org");
        Event event = new Event(1L, "Test Event", "This is a test description.", true, EventStatus.PENDING, organization, LocalDate.now());
        event.setStatus(EventStatus.APPROVED);
        assertEquals(EventStatus.APPROVED, event.getStatus(), "Η κατάσταση πρέπει να είναι 'APPROVED'.");
    }

    // Τεστ για τον constructor
    @Test
    void testConstructor() {
        Organization organization = new Organization("Green Initiative", "info@greeninititative.org");
        organization.setId(100L);
        Event event = new Event(1L, "Test Event", "This is a test description.", true, EventStatus.PENDING, organization, LocalDate.now());

        assertEquals(1L, event.getId(), "Το ID πρέπει να είναι 1.");
        assertEquals("Test Event", event.getName(), "Το όνομα πρέπει να είναι 'Test Event'.");
        assertEquals("This is a test description.", event.getDescription(), "Η περιγραφή πρέπει να είναι 'This is a test description.'.");
        assertTrue(event.isApproved(), "Η έγκριση πρέπει να είναι true.");
        assertEquals(organization, event.getOrganization(), "Το organization πρέπει να είναι το mock αντικείμενο.");
    }
}



