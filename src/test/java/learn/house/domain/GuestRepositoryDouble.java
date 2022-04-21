package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.models.Guest;
import learn.house.models.States;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestRepositoryDouble implements GuestRepository {

    private final ArrayList<Guest> guests = new ArrayList<>();
    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
//    private final String filePath;

    public GuestRepositoryDouble() {
        guests.add(new Guest(
                "1",
                "Sullivan",
                "Lomas",
                "sl.omas0@mediafire.minnstate.edu",
                "(702) 7768761",
                States.NV));
        guests.add(new Guest(
                "2",
                "Sullivan",
                "Hacler",
                "slomas0@mediafire.edu",
                "(218) 7768761",
                States.NY));
        guests.add(new Guest(
                "3",
                "Tim",
                "George",
                "barret@gmail.com",
                "(335) 7768761",
                States.DC));
        guests.add(new Guest(
                "4",
                "Irina",
                "Lomas",
                "bbb123@msn.com",
                "(882) 7768761",
                States.DC));
    }

    @Override
    public List<Guest> findAll() throws DataException {
        ArrayList<Guest> result = new ArrayList<>();
        return guests;
    }

    @Override
    public List<Guest> findByLastName(String lastName) throws DataException {
        return findAll().stream()
                .filter(i -> i.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Guest> findByState(States state) throws DataException {
        return findAll().stream()
                .filter(i -> i.getState().equals(state))
                .collect(Collectors.toList());
    }

    @Override
    public Guest findById(String id) throws DataException {
        return findAll().stream()
                .filter(i -> i.getGuestId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }


    private String serialize(Guest guest) {
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getGuestId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getState());
    }

    private Guest deserialize(String[] fields) {
        Guest guest = new Guest();
        guest.setGuestId(fields[0]);
        guest.setFirstName(fields[1]);
        guest.setLastName(fields[2]);
        guest.setEmail(fields[3]);
        guest.setPhone(fields[4]);
        guest.setState(States.valueOf(fields[5]));
        return guest;
    }
}