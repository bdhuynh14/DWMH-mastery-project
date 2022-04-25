package learn.house.data;

import learn.house.data.DataException;
import learn.house.data.HostFileRepository;
import learn.house.models.Host;
import learn.house.models.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static learn.house.models.States.DC;
import static learn.house.models.States.TX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HostFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./data/hosts-test.csv";
    static final int HOST_COUNT = 1000;


    HostFileRepository repository;

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
        repository = new HostFileRepository(TEST_FILE_PATH);
    }

    @Test
    void shouldFind1000() throws DataException {
        List<Host> all = repository.findAll();
        assertEquals(HOST_COUNT, all.size());
    }
    @Test
    void shouldMakeHost() throws DataException {
        List<Host> all = repository.findAll();
        Host sample = new Host(
                "8a97d62f-bb36-4e27-89c1-c3299705a76e",
                "Waldram",
                "gwaldramq0@statcounter.com",
                "(937) 2096222",
                "79902 Packers Junction",
                "Dayton",
                States.OH,
                "45470",
                new BigDecimal("187"),
                new BigDecimal("233.75"));
        assertTrue(sample.equals(all.get(936)));
    }

    @Test
    void findById() throws DataException {
        Host result = repository.findById("1bc449f4-e2cf-4e1c-b6ca-f00b526cddf2");
        Host sample = new Host(
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
        assertTrue(sample.equals(result));
    }

    @Test
    void shouldFindSixTexas() throws DataException, IOException {
        Path seedPath2 = Paths.get("./data/hosts-small-seed.csv");
        Path testPath2 = Paths.get("./data/hosts-small-test.csv");
        Files.copy(seedPath2, testPath2, StandardCopyOption.REPLACE_EXISTING);
        HostFileRepository repository2 = new HostFileRepository("./data/hosts-small-test.csv");

        List<Host> results = repository2.findByState(States.TX);
        assertEquals(6, results.size());
    }
    @Test
    void shouldFindOneGeorgia() throws DataException, IOException {
        Path seedPath2 = Paths.get("./data/hosts-small-seed.csv");
        Path testPath2 = Paths.get("./data/hosts-small-test.csv");
        Files.copy(seedPath2, testPath2, StandardCopyOption.REPLACE_EXISTING);
        HostFileRepository repository2 = new HostFileRepository("./data/hosts-small-test.csv");

        List<Host> results = repository2.findByState(States.GA);
        assertEquals(1, results.size());
    }
    @Test
    void shouldFindOneDC() throws DataException, IOException {
        Path seedPath2 = Paths.get("./data/hosts-small-seed.csv");
        Path testPath2 = Paths.get("./data/hosts-small-test.csv");
        Files.copy(seedPath2, testPath2, StandardCopyOption.REPLACE_EXISTING);
        HostFileRepository repository2 = new HostFileRepository("./data/hosts-small-test.csv");

        List<Host> results = repository2.findByState(DC);
        assertEquals(1, results.size());
    }

    @Test
    void shouldFindOneWithSpecifiedLastName() throws DataException {
        List<Host> results = repository.findByLastName("Yearnes");
        assertEquals(1, results.size());
    }

    @Test
    void shouldFindTwoWithState() throws DataException, IOException {
        Path seedPath2 = Paths.get("./data/hosts-small-seed.csv");
        Path testPath2 = Paths.get("./data/hosts-small-test.csv");
        Files.copy(seedPath2, testPath2, StandardCopyOption.REPLACE_EXISTING);
        HostFileRepository repository2 = new HostFileRepository("./data/hosts-small-test.csv");

        List<Host> results = repository2.findByState(TX);
        assertEquals(6, results.size());
    }

}