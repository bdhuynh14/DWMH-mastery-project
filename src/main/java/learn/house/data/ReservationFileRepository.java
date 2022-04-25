package learn.house.data;

import learn.house.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    private String getFilePath(String id) {
        return Paths.get(directory, id + ".csv").toString();
    }

    @Override
    public List<Reservation> findById(String id) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(id)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Reservation add(String hostId, Reservation reservation) throws DataException {
        List<Reservation> all = findById(hostId);
        reservation.setId(all.isEmpty()? "1" : Integer.toString(Integer.parseInt(all.get(all.size()-1).getId())+1));
        //TODO: maintain String type for future unique identifier upgrade
        all.add(reservation);
        writeAll(hostId, all);
        return reservation;
    }

    private void writeAll(String hostId,List<Reservation> reservations) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            for (Reservation host : reservations) {
                writer.println(serialize(host));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    @Override
    public boolean update(String hostId, Reservation reservation) throws DataException {
        List<Reservation> all = findById(hostId);
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
        List<Reservation> all = findById(hostId);
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