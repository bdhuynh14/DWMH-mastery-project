package learn.house.domain;

import learn.house.data.DataException;
import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.models.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service;

    @BeforeEach
    void setup() {
        ReservationRepositoryDouble repo = new ReservationRepositoryDouble();
        service = new ReservationService(repo);
    }

    @Test
    void shouldFindAll() throws DataException {
        List<Reservation> results1 = service.resByHost("1");
        List<Reservation> results2 = service.resByHost("2");
        assertEquals(3, results1.size());
        assertEquals(3, results2.size());
        assertTrue(results1.get(0).getId().equalsIgnoreCase("1"));
        assertTrue(results2.get(0).getId().equalsIgnoreCase("4"));
    }

    @Test
    void shouldOnlyFuture() throws DataException {
        List<Reservation> results1 = service.resByHostFuture("1");
        List<Reservation> results2 = service.resByHostFuture("2");
        assertEquals(0, results1.size());
        assertEquals(2, results2.size());
        assertTrue(results2.get(1).getId().equalsIgnoreCase("6"));
    }

    @Test
    void shouldFilterReservationsFromFuture() throws DataException {
        Reservation sample = new Reservation();
        sample.setId("5");
        sample.setStartDate(LocalDate.parse("2023-11-12"));
        sample.setEndDate(LocalDate.parse("2023-11-14"));
        sample.setGuestID("666");
        sample.setTotal(new BigDecimal("300"));
        List<Reservation> results = service.getReservations("2", sample);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getId().equalsIgnoreCase(sample.getId()));
    }


    @Test
    void shouldDeleteCorrectReservation() throws DataException {
        Reservation sample = new Reservation();
        sample.setId("5");
        sample.setStartDate(LocalDate.parse("2023-11-12"));
        sample.setEndDate(LocalDate.parse("2023-11-14"));
        sample.setGuestID("666");
        sample.setTotal(new BigDecimal("300"));
        service.cancel("2", sample);
        List<Reservation> results = service.resByHost("2");
        assertEquals(2, results.size());
        assertTrue(results.get(0).getId().equalsIgnoreCase("4"));
        assertTrue(results.get(1).getId().equalsIgnoreCase("6"));

    }

    @Test
    void shouldChangeDatesAndTotal() throws DataException {
        Reservation sample = new Reservation();
        sample.setId("2");
        sample.setStartDate(LocalDate.parse("2025-11-12"));
        sample.setEndDate(LocalDate.parse("2025-11-14"));
        sample.setGuestID("666");
        sample.setTotal(new BigDecimal("350"));
        service.update("1", sample);
        List<Reservation> results = service.resByHost("1");
        assertEquals(3, results.size());
        assertTrue(results.get(0).getId().equalsIgnoreCase("1"));
        assertTrue(results.get(2).getId().equalsIgnoreCase("2"));
        assertTrue(results.get(2).getStartDate().compareTo(sample.getStartDate())==0);
        assertTrue(results.get(2).getEndDate().compareTo(sample.getEndDate())==0);
        assertTrue(results.get(2).getTotal().compareTo(sample.getTotal())==0);
    }

    @Test
    void shouldAutoCalculateTotalWithUpdate() throws DataException {
        Host host = new Host(
                "1bc449f4-e2cf-4e1c-b6ca-f00b526cddf2",
                "Gaveltone",
                "agaveltoneqq@craigslist.org",
                "(269) 2211425",
                "8924 Green Parkway",
                "Kalamazoo",
                States.MI,
                "49006",
                new BigDecimal("141"),
                new BigDecimal("176.25"));


        Reservation sample = new Reservation();
        sample.setId("2");
        Response res1 = sample.setStartDate(LocalDate.parse("2025-11-12"), host.getStandardRate(),  host.getWeekendRate()); // wednesday
        Response res2 = sample.setEndDate(LocalDate.parse("2025-11-15"), host.getStandardRate(), host.getWeekendRate()); // friday
        sample.setGuestID("666");

        System.out.println(res1.getErrorMessages());
        System.out.println(res2.getErrorMessages());
        System.out.println(sample.getTotal().toString());

        service.update("1", sample);
        List<Reservation> results = service.resByHost("1");
        assertEquals(3, results.size());
        assertTrue(results.get(0).getId().equalsIgnoreCase("1"));
        assertTrue(results.get(2).getId().equalsIgnoreCase("2"));
        assertTrue(results.get(2).getStartDate().compareTo(sample.getStartDate())==0);
        assertTrue(results.get(2).getEndDate().compareTo(sample.getEndDate())==0);
        assertTrue(results.get(2).getTotal().compareTo(new BigDecimal("458.25"))==0);
    }

    @Test
    void addShouldFailDateOverlap() throws DataException {
        Reservation sample = new Reservation();
        sample.setStartDate(LocalDate.parse("2023-11-12"));
        sample.setEndDate(LocalDate.parse("2023-11-14"));
        sample.setGuestID("666");
        sample.setTotal(new BigDecimal("300"));
        assertFalse(service.add("2", sample).isSuccess());

    }

    @Test
    void testShouldFailMissingFields() throws DataException {
        Reservation sample = new Reservation();
        sample.setStartDate(LocalDate.parse("2023-11-12"));
        sample.setEndDate(LocalDate.parse("2023-11-14"));
        sample.setTotal(new BigDecimal("300"));
        assertFalse(service.reservationTest("1", sample).isSuccess());
    }


}


