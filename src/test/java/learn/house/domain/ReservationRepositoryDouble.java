package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.ReservationRepository;
import learn.house.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationRepositoryDouble implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    ArrayList<Reservation> reservations1 = new ArrayList<>();
    ArrayList<Reservation> reservations2 = new ArrayList<>();


    public ReservationRepositoryDouble() {
        Reservation one = new Reservation();
        one.setId("1");
        one.setStartDate(LocalDate.parse("2021-10-12"));
        one.setEndDate(LocalDate.parse("2021-10-14"));
        one.setGuestID("663");
        one.setTotal(new BigDecimal("400"));

        Reservation two = new Reservation();
        two.setId("2");
        two.setStartDate(LocalDate.parse("2021-11-12"));
        two.setEndDate(LocalDate.parse("2021-11-14"));
        two.setGuestID("666");
        two.setTotal(new BigDecimal("300"));

        Reservation three = new Reservation();
        three.setId("3");
        three.setStartDate(LocalDate.parse("2021-12-12"));
        three.setEndDate(LocalDate.parse("2021-12-14"));
        three.setGuestID("667");
        three.setTotal(new BigDecimal("350"));

        reservations1.add(one);
        reservations1.add(two);
        reservations1.add(three);


        Reservation four = new Reservation();
        four.setId("4");
        four.setStartDate(LocalDate.parse("2021-10-12"));
        four.setEndDate(LocalDate.parse("2021-10-14"));
        four.setGuestID("663");
        four.setTotal(new BigDecimal("400"));

        Reservation five = new Reservation();
        five.setId("5");
        five.setStartDate(LocalDate.parse("2023-11-12"));
        five.setEndDate(LocalDate.parse("2023-11-14"));
        five.setGuestID("666");
        five.setTotal(new BigDecimal("300"));

        Reservation six = new Reservation();
        six.setId("6");
        six.setStartDate(LocalDate.parse("2023-12-12"));
        six.setEndDate(LocalDate.parse("2023-12-14"));
        six.setGuestID("667");
        six.setTotal(new BigDecimal("350"));

        reservations2.add(four);
        reservations2.add(five);
        reservations2.add(six);
    }


    @Override
    public ArrayList<Reservation> findById(String id) throws DataException {
        switch (id) {
            case "1":
                return reservations1;
            case "2":
                return reservations2;
        }
        return null;
    }

    @Override
    public Reservation add(String hostId, Reservation reservation) throws DataException {
        ArrayList<Reservation> all = findById(hostId);
        reservation.setId(all.size()==0? "1" : Integer.toString(Integer.parseInt(all.get(all.size()-1).getId())+1));
        //TODO: maintain String type for future unique identifier upgrade
        all.add(reservation);
        writeAll(hostId, all);
        return reservation;
    }

    private void writeAll(String hostId,ArrayList<Reservation> reservations) throws DataException {
        switch (hostId) {
            case "1":
                reservations1 = reservations;
                break;
            case "2":
                reservations2 = reservations;
                break;
        }
    }

    @Override
    public boolean update(String hostId, Reservation reservation) throws DataException {
        ArrayList<Reservation> all = findById(hostId);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(reservation.getId())) {
                all.set(i, reservation);
                writeAll(hostId, all);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean delete(String hostId, Reservation reservation) throws DataException {
        ArrayList<Reservation> all = findById(hostId);
        for (int i = 0; i < all.size(); i++) {
            if (reservation.testEquals(all.get(i))) {
                all.remove(i);
                writeAll(hostId, all);
                return true;
            }
        }
        return false;
    }


    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate().toString(),
                reservation.getEndDate().toString(),
                reservation.getGuestID(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields) {
        Reservation reservation = new Reservation();
        reservation.setId(fields[0]);
        reservation.setStartDate(LocalDate.parse(fields[1]));
        reservation.setEndDate(LocalDate.parse(fields[2]));
        reservation.setGuestID(fields[3]);
        reservation.setTotal(new BigDecimal(fields[4]));
        return reservation;
    }
}