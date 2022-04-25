package learn.house.domain;

import learn.house.data.DataException;
import learn.house.models.Host;
import learn.house.models.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HostServiceTest {

    HostService service;

    @BeforeEach
    void setup() {
        HostRepositoryDouble hostRepoDouble = new HostRepositoryDouble();
        service = new HostService(hostRepoDouble);
    }

    @Test
    void shouldFindFirstHost() throws DataException {
        Host criteria = new Host(
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

        List<Host> results = service.getHosts(criteria);
        assertTrue(criteria.equals(results.get(0)));
    }

    @Test
    void shouldFindAllHostsNoCriteriaSortedByStandardRate() throws DataException {
        Host criteria = new Host();

        List<Host> results = service.getHosts(criteria);
        assertTrue(results.size() ==4);
        assertTrue(results.get(0).getStandardRate().compareTo(new BigDecimal("141"))==0);
        assertTrue(results.get(1).getStandardRate().compareTo(new BigDecimal("142"))==0);
        assertTrue(results.get(2).getStandardRate().compareTo(new BigDecimal("143"))==0);
        assertTrue(results.get(3).getStandardRate().compareTo(new BigDecimal("144"))==0);

    }
    @Test
    void shouldFindTwoHostsByLastName() throws DataException {
        Host criteria = new Host();
        criteria.setLastName("Gaveltone");
        List<Host> results = service.getHosts(criteria);
        assertTrue(results.get(0).getLastName().equalsIgnoreCase("Gaveltone"));
        assertTrue(results.get(1).getId().equalsIgnoreCase("1bc449f4-e2cf-4e1c-b6ca-f00b526cddf3"));
        assertTrue(results.size() == 2);
    }
    @Test
    void shouldFindAllByPostalCode() throws DataException {
        Host criteria = new Host();
        criteria.setPostalCode("49006");
        List<Host> results = service.getHosts(criteria);
        assertTrue(results.size()==4);
    }
    @Test
    void shouldFindSertainByWeekendRate() throws DataException {
        Host criteria = new Host();
        criteria.setWeekendRate(new BigDecimal( "206.25"));
        List<Host> results = service.getHosts(criteria);
        assertTrue(results.get(0).getId().equalsIgnoreCase("1bc449f4-e2cf-4e1c-b6ca-f00b526cddf5"));
    }

}