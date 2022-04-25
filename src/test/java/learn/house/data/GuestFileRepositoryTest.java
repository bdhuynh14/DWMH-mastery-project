package learn.house.data;

import learn.house.data.DataException;
import learn.house.data.GuestFileRepository;
import learn.house.models.Guest;
import learn.house.models.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/guest-seed.csv";
    static final String TEST_FILE_PATH = "./data/guests-test.csv";
    static final int GUEST_COUNT = 1000;


    GuestFileRepository repository;

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
        repository = new GuestFileRepository(TEST_FILE_PATH);
    }

    @Test
    void shouldFind1000() throws DataException {
        List<Guest> all = repository.findAll();
        assertEquals(GUEST_COUNT, all.size());
    }
    @Test
    void shouldMakeGuest() throws DataException {
        List<Guest> all = repository.findAll();
        Guest sample = new Guest(
                "1",
                "Sullivan",
                "Lomas",
                "slomas0@mediafire.com",
                "(702) 7768761",
                States.NV);
        assertTrue(sample.equals(all.get(0)));
    }

    @Test
    void shouldFindOneWithSpecifiedLastName() throws DataException {
        List<Guest> results = repository.findByLastName("Lomas");
        assertEquals(1, results.size());
    }

    @Test
    void shouldFindTwoWithSpecifiedLastName() throws DataException, IOException {
        Path seedPath2 = Paths.get("./data/guests-small-seed.csv");
        Path testPath2 = Paths.get("./data/guests-small-test.csv");
        Files.copy(seedPath2, testPath2, StandardCopyOption.REPLACE_EXISTING);
        GuestFileRepository repository2 = new GuestFileRepository("./data/guests-small-test.csv");

        List<Guest> results = repository2.findByLastName("Lomas");
        assertEquals(2, results.size());
    }

    @Test
    void shouldFindOneNevada() throws DataException, IOException {
        Path seedPath2 = Paths.get("./data/guests-small-seed.csv");
        Path testPath2 = Paths.get("./data/guests-small-test.csv");
        Files.copy(seedPath2, testPath2, StandardCopyOption.REPLACE_EXISTING);
        GuestFileRepository repository2 = new GuestFileRepository("./data/guests-small-test.csv");

        List<Guest> results = repository2.findByState(States.NV);
        assertEquals(1, results.size());
    }
    @Test
    void shouldFindOneNewYork() throws DataException, IOException {
        Path seedPath2 = Paths.get("./data/guests-small-seed.csv");
        Path testPath2 = Paths.get("./data/guests-small-test.csv");
        Files.copy(seedPath2, testPath2, StandardCopyOption.REPLACE_EXISTING);
        GuestFileRepository repository2 = new GuestFileRepository("./data/guests-small-test.csv");

        List<Guest> results = repository2.findByState(States.NY);
        assertEquals(1, results.size());
    }
    @Test
    void shouldFindThreeDC() throws DataException, IOException {
        Path seedPath2 = Paths.get("./data/guests-small-seed.csv");
        Path testPath2 = Paths.get("./data/guests-small-test.csv");
        Files.copy(seedPath2, testPath2, StandardCopyOption.REPLACE_EXISTING);
        GuestFileRepository repository2 = new GuestFileRepository("./data/guests-small-test.csv");

        List<Guest> results = repository2.findByState(States.DC);
        assertEquals(3, results.size());
    }

    @Test
    void findById() throws DataException {
        Guest result = repository.findById("982");
        Guest sample = new Guest(
                "982",
                "Querida",
                "Braiden",
                "qbraidenr9@whitehouse.gov",
                "(321) 7877077",
                States.FL);
        assertTrue(sample.equals(result));
    }
}
