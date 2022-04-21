package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.models.Guest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GuestService {
    private final GuestRepository repository;
    public GuestService(GuestRepository repository) {this.repository = repository;}


    public List<Guest> getGuests(Guest criteria) throws DataException{
        List<Guest> results = null;
        results = repository.findAll().stream().filter(g -> guestStreamFilter(criteria, g)).collect(Collectors.toList());
        results.sort(Comparator.comparing(g -> g.getLastName()));
        return results;
    }

    private Boolean guestStreamFilter(Guest c, Guest g) {
        return (c.getGuestId() == null || c.getGuestId().equalsIgnoreCase(g.getGuestId())) &&
                (c.getFirstName() == null || c.getFirstName().equalsIgnoreCase(g.getFirstName())) &&
                (c.getLastName() == null || c.getLastName().equalsIgnoreCase(g.getLastName())) &&
                (c.getEmail() == null || c.getEmail().equalsIgnoreCase(g.getEmail())) &&
                (c.getPhone() == null || c.getPhone().equalsIgnoreCase(g.getPhone())) &&
                (c.getState() == null || c.getState().equals(g.getState()));
    }



}
