package learn.house.data;

import learn.house.models.Reservation;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_DIR_PATH = "./data/reservations-seed";
    static final String TEST_DIR_PATH = "./data/reservations-test";

    ReservationFileRepository repository;

    @BeforeEach
    void setup() throws IOException {
        File seedFolder = new File(SEED_DIR_PATH);
        File testFolder = new File(TEST_DIR_PATH);
        FileUtils.copyDirectory(seedFolder, testFolder);
        repository = new ReservationFileRepository(TEST_DIR_PATH);
    }

    @Test
    void shouldFind12ById() throws DataException {
        List<Reservation> results = repository.findById("2e72f86c-b8fe-4265-b4f1-304dea8762db");

        assertEquals(12, results.size());
    }

    @Test
    void shouldDeserializeReservation() throws DataException {
        List<Reservation> results = repository.findById("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        Reservation sample = new Reservation();
        sample.setId("1");
        sample.setStartDate(LocalDate.parse("2021-10-12"));
        sample.setEndDate(LocalDate.parse("2021-10-14"));
        sample.setGuestID("663");
        sample.setTotal(new BigDecimal("400"));

        assertTrue(sample.testEquals(results.get(0)));
    }

    @Test
    void shouldAddReservation() throws DataException {
        Reservation sample = new Reservation();
        sample.setStartDate(LocalDate.parse("2022-03-05"));
        sample.setEndDate(LocalDate.parse("2022-03-07"));
        sample.setGuestID("663");
        sample.setTotal(new BigDecimal("400"));
        repository.add("2e72f86c-b8fe-4265-b4f1-304dea8762db", sample);

        List<Reservation> results = repository.findById("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        assertFalse(sample.testEquals(results.get(0)));
        assertFalse(sample.testEquals(results.get(11)));
        assertTrue(sample.testEquals(results.get(12)));
        assertEquals("13", results.get(12).getId());
    }

    @Test
    void shouldDeleteReservation() throws DataException {
        Reservation sample = new Reservation();
        sample.setId("1");
        sample.setStartDate(LocalDate.parse("2021-10-12"));
        sample.setEndDate(LocalDate.parse("2021-10-14"));
        sample.setGuestID("663");
        sample.setTotal(new BigDecimal("400"));
        repository.delete("2e72f86c-b8fe-4265-b4f1-304dea8762db", sample);

        List<Reservation> results = repository.findById("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        assertEquals("2", results.get(0).getId());
        assertEquals(11, results.size());
    }

    @Test
    void shouldUpdateReservation() throws DataException {
        Reservation sample = new Reservation();
        sample.setId("1");
        sample.setStartDate(LocalDate.parse("2021-10-11"));
        sample.setEndDate(LocalDate.parse("2021-10-15"));
        sample.setGuestID("663");
        sample.setTotal(new BigDecimal("600"));
        repository.update("2e72f86c-b8fe-4265-b4f1-304dea8762db", sample);

        List<Reservation> results = repository.findById("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        assertEquals("1", results.get(0).getId());
        assertEquals(LocalDate.parse("2021-10-11"), results.get(0).getStartDate());
        assertEquals(LocalDate.parse("2021-10-15"), results.get(0).getEndDate());
        assertEquals("663", results.get(0).getGuestID());
        assertEquals(new BigDecimal("600"), results.get(0).getTotal());
        assertEquals(12, results.size());
    }

}
