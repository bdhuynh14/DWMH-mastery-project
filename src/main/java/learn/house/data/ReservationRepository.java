package learn.house.data;

import learn.house.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findById(String hostId) throws DataException;

    Reservation add(String hostId, Reservation reservation) throws DataException;

    boolean update(String hostId, Reservation reservation) throws DataException;

    Boolean delete(String hostId, Reservation reservation) throws DataException;
}