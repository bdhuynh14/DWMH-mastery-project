package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepository;
import learn.house.models.Host;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HostService {
    private final HostRepository repository;
    public HostService(HostRepository repository) {this.repository = repository;}


    public List<Host> getHosts(Host criteria) throws DataException {
        List<Host> results = null;
        results = repository.findAll().stream().filter(g -> guestStreamFilter(criteria, g))
                .collect(Collectors.toList());
        results.sort(Comparator.comparing(h -> h.getStandardRate()));
        return results;
    }

    private Boolean guestStreamFilter(Host c, Host h) {
        return  (c.getId() == null || c.getId().equalsIgnoreCase(h.getId())) &&
                (c.getLastName() == null || c.getLastName().equalsIgnoreCase(h.getLastName())) &&
                (c.getEmail() == null || c.getEmail().equalsIgnoreCase(h.getEmail())) &&
                (c.getPhone() == null || c.getPhone().equalsIgnoreCase(h.getPhone())) &&
                (c.getAddress() == null || c.getAddress().equalsIgnoreCase(h.getAddress())) &&
                (c.getCity() == null || c.getCity().equalsIgnoreCase(h.getCity())) &&
                (c.getState() == null || c.getState().equals(h.getState())) &&
                (c.getPostalCode() == null || c.getPostalCode().equalsIgnoreCase(h.getPostalCode())) &&
                (c.getStandardRate() == null || c.getStandardRate().compareTo(h.getStandardRate())==0) &&
                (c.getWeekendRate() == null || c.getWeekendRate().compareTo(h.getWeekendRate())==0);
    }



}