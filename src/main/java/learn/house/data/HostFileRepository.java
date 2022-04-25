package learn.house.data;

import learn.house.models.Host;
import learn.house.models.States;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HostFileRepository implements HostRepository {

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String filePath;

    public HostFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
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