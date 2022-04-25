package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.States;

import java.util.List;

public interface GuestRepository {
    Guest findById(String id) throws DataException;

    List<Guest> findAll() throws DataException;

    List<Guest> findByLastName(String lastName) throws DataException;

    List<Guest> findByState(States state) throws DataException;
}