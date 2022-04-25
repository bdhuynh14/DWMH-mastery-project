package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepository;
import learn.house.models.Host;
import learn.house.models.States;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HostRepositoryDouble implements HostRepository {

    ArrayList<Host> hosts = new ArrayList<>();
    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";

    public HostRepositoryDouble() {
        hosts.add(new Host(
                "1bc449f4-e2cf-4e1c-b6ca-f00b526cddf2",
                "Gaveltone",
                "agaveltoneqq@craigslist.org",
                "(269) 2211425",
                "8924 Green Parkway",
                "Kalamazoo",
                States.MI,
                "49006",
                new BigDecimal("141"),
                new BigDecimal("176.25")));
        hosts.add(new Host(
                "1bc449f4-e2cf-4e1c-b6ca-f00b526cddf3",
                "Gaveltone",
                "agaveltoneqq@craigslist.edu",
                "(222) 2211425",
                "8924 Green Parkway",
                "Georgetown",
                States.MN,
                "49006",
                new BigDecimal("142"),
                new BigDecimal("186.25")));
        hosts.add(new Host(
                "1bc449f4-e2cf-4e1c-b6ca-f00b526cddf4",
                "Epps",
                "agaveltoneqq@craigslist.msn.edu",
                "(555) 2211425",
                "8924 Green Parkway",
                "St Paul",
                States.MS,
                "49006",
                new BigDecimal("143"),
                new BigDecimal("196.25")));
        hosts.add(new Host(
                "1bc449f4-e2cf-4e1c-b6ca-f00b526cddf5",
                "Sertain",
                "agaveltoneqq@craigslist.gov",
                "(444) 2211425",
                "8924 Green Parkway",
                "Kalamazoo",
                States.DC,
                "49006",
                new BigDecimal("144"),
                new BigDecimal("206.25")));

    }

    @Override
    public List<Host> findAll() {
        return hosts;
    }


    @Override
    public Host findById(String id) {
        return findAll().stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Host> findByState(States state) {
        return findAll().stream()
                .filter(i -> i.getState().equals(state))
                .collect(Collectors.toList());
    }

    @Override
    public List<Host> findByLastName(String lastName) throws DataException {
        return findAll().stream()
                .filter(i -> i.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }



    private String serialize(Host host) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getId(),
                host.getLastName(),
                host.getEmail(),
                host.getPhone(),
                host.getAddress(),
                host.getCity(),
                host.getState(),
                host.getPostalCode(),
                host.getStandardRate(),
                host.getWeekendRate());
    }

    private Host deserialize(String[] fields) {
        Host host = new Host();
        host.setId(fields[0]);
        host.setLastName(fields[1]);
        host.setEmail(fields[2]);
        host.setPhone(fields[3]);
        host.setAddress(fields[4]);
        host.setCity(fields[5]);
        host.setState(States.valueOf(fields[6]));
        host.setPostalCode(fields[7]);
        host.setStandardRate(new BigDecimal(fields[8]));
        host.setWeekendRate(new BigDecimal(fields[9]));
        return host;
    }
}