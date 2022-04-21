package learn.house.domain;

import learn.house.data.DataException;
import learn.house.models.Guest;
import learn.house.models.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GuestServiceTest {

    GuestService service;

    @BeforeEach
    void setup() {
        GuestRepositoryDouble guestRepoDouble = new GuestRepositoryDouble();
        service = new GuestService(guestRepoDouble);
    }

    @Test
    void shouldFindFirstGuest() throws DataException {
        Guest criteria = new Guest(
                "1",
                "Sullivan",
                "Lomas",
                "sl.omas0@mediafire.minnstate.edu",
                "(702) 7768761",
                States.NV);

        List<Guest> results = service.getGuests(criteria);
        assertTrue(criteria.equals(results.get(0)));
    }
    @Test
    void shouldFindTwoGuestsFirstName() throws DataException {
        Guest criteria = new Guest();
        criteria.setFirstName("Sullivan");
        List<Guest> results = service.getGuests(criteria);
        assertTrue(results.size() == 2);
        assertTrue(criteria.getFirstName().equalsIgnoreCase(results.get(0).getFirstName()));
        assertTrue(results.get(1).getFirstName().equalsIgnoreCase("Sullivan"));
        assertTrue(results.get(0).getEmail().equalsIgnoreCase("slomas0@mediafire.edu"));
    }
    @Test
    void shouldFindAllGuestsNoCriteria() throws DataException {
        Guest criteria = new Guest();

        List<Guest> results = service.getGuests(criteria);
        assertTrue(results.size() ==4);
    }
    @Test
    void shouldFindTwoGuestsByLastName() throws DataException {
        Guest criteria = new Guest();
        criteria.setLastName("Lomas");
        List<Guest> results = service.getGuests(criteria);
        assertTrue(results.get(0).getFirstName().equalsIgnoreCase("Sullivan"));
        assertTrue(results.get(1).getFirstName().equalsIgnoreCase("Irina"));
        assertTrue(results.size() == 2);
    }
    @Test
    void shouldFindTimByEmail() throws DataException {
        Guest criteria = new Guest();
        criteria.setEmail("barret@gmail.com");
        List<Guest> results = service.getGuests(criteria);
        assertTrue(results.get(0).getGuestId().equalsIgnoreCase("3"));
    }
    @Test
    void shouldFindIrinaByPhone() throws DataException {
        Guest criteria = new Guest();
        criteria.setPhone("(882) 7768761");
        List<Guest> results = service.getGuests(criteria);
        assertTrue(results.get(0).getGuestId().equalsIgnoreCase("4"));
    }
    @Test
    void shouldFindTwoByState() throws DataException {
        Guest criteria = new Guest();
        criteria.setState(States.DC);
        List<Guest> results = service.getGuests(criteria);
        assertTrue(results.size() == 2);
    }
}