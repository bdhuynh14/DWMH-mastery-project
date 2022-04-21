package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.ReservationRepository;
import learn.house.models.Reservation;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    ReservationRepository repository;
    public ReservationService(ReservationRepository reservationRepository) {this.repository = reservationRepository;}

    public List<Reservation> resByHost(String hostId) throws DataException {
        List<Reservation> results = repository.findById(hostId);
        results.sort(Comparator.comparing(r -> r.getStartDate()));
        return results;
    }

    public List<Reservation> getReservations(String hostId, Reservation criteria) throws DataException {
        List<Reservation> results = null;
        results = resByHostFuture(hostId).stream().filter(r -> resStreamFilter(criteria, r)).toList();
        return results;
    }

    private Boolean resStreamFilter(Reservation c, Reservation r) {
        return (c.getId() == null || c.getId().equalsIgnoreCase(r.getId())) &&
                (c.getStartDate() == null || c.getStartDate().compareTo(r.getStartDate())==0) &&
                (c.getEndDate() == null || c.getEndDate().compareTo(r.getEndDate())==0) &&
                (c.getGuestID() == null || c.getGuestID().equalsIgnoreCase(r.getGuestID())) &&
                (c.getTotal() == null || c.getTotal().compareTo(r.getTotal())==0);
    }

    public List<Reservation> resByHostFuture(String hostId) throws DataException {
        List<Reservation> results = resByHost(hostId);
        return results.stream().filter(r -> r.getStartDate().compareTo(LocalDate.now()) >= 0)
                .collect(Collectors.toList());
    }

    public Boolean cancel(String hostId, Reservation reservation) throws DataException {
        return repository.delete(hostId, reservation);
    }

    public Result<Reservation> update(String hostId, Reservation reservation) throws DataException {
        Result<Reservation> result = reservationTest(hostId, reservation);
        if (result.isSuccess()) {
            repository.update(hostId, reservation);
            result.setPayload(reservation);
            return result;
        }
        result.addErrorMessage("Failed to update reservation");
        return result;
    }

    public Result<Reservation> add(String hostId, Reservation reservation) throws DataException {
        Result<Reservation> result = reservationTest(hostId, reservation);
        if (result.isSuccess()) {
            repository.add(hostId, reservation); //assign payload
            result.setPayload(reservation);
            return result;
        }
        result.addErrorMessage("failed to add reservation");
        return result;
    }

    public Result<Reservation> reservationTest(String hostId, Reservation newRes) throws DataException {
        List<Reservation> current = resByHost(hostId);
        Result<Reservation> result = new Result<>();
        Result<Reservation> missingFields = missingFieldTest(newRes);
        if (!missingFields.isSuccess()) {
            missingFields.getErrorMessages().forEach(m -> result.addErrorMessage(m));
        }
        current.stream().forEach(r -> {
            if (!r.getId().equalsIgnoreCase(newRes.getId()) && overlapTest(r, newRes)) {
                result.addErrorMessage(String.format("Date Overlap%nYour dates: %s : %s%nExisting dates: %s : %s",
                        newRes.getStartDate().toString(), newRes.getEndDate().toString(),
                        r.getStartDate().toString(), r.getEndDate().toString()));
            }
        });
        return result;
    }

    private Result<Reservation> missingFieldTest(Reservation r) {
        Result<Reservation> result = new Result<>();
        if (r.getStartDate() == null) {
            result.addErrorMessage("Missing Start Date");
        }
        if (r.getEndDate() == null) {
            result.addErrorMessage("Missing End Date");
        }
        if (r.getGuestID() == null) {
            result.addErrorMessage("Missing Missing Guest ID");
        }
        if (r.getTotal() == null) {
            result.addErrorMessage("Missing Missing Total");
        }
        return result;
    }

    private Boolean overlapTest(Reservation r, Reservation n) {
        if ((r.getStartDate().isBefore(n.getStartDate())) &&
                (r.getStartDate().isBefore(n.getEndDate())) &&
                (r.getEndDate().isBefore(n.getStartDate()) || r.getEndDate().isEqual(n.getStartDate())) &&
                (r.getEndDate().isBefore(n.getEndDate()) || r.getEndDate().isEqual(n.getEndDate()))) {
            return false;
        } else if ((r.getStartDate().isAfter(n.getStartDate())) &&
                (r.getStartDate().isAfter(n.getEndDate()) || r.getStartDate().isEqual(n.getEndDate())) &&
                (r.getEndDate().isAfter(n.getStartDate())) &&
                (r.getEndDate().isAfter(n.getEndDate()) || r.getEndDate().isEqual(n.getEndDate()))) {
            return false;
        }
        return true;
    }

}
