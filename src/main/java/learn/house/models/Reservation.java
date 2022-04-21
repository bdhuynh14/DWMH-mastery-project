package learn.house.models;

import learn.house.domain.Response;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Objects;

/** Reservation:
 One or more days where a Guest has exclusive access to a Location (or Host). Reservation data is provided.
 *
 */
public class Reservation {

    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String guestId;
    private BigDecimal total;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public LocalDate getStartDate() {return startDate;}

    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

    public Response setStartDate(LocalDate startDate, BigDecimal weekdayRate, BigDecimal weekendRate) {
        Response result = new Response();
        if (this.endDate != null){
            if (!this.endDate.isAfter(startDate)) {
                result.addErrorMessage(String.format(
                        "Failed to add Start Date. Must come before end date: %s", this.endDate));
                return result;
            }
            this.startDate = startDate;
            this.setTotal(weekdayRate,weekendRate);
            return result;
        }
        this.startDate = startDate;
        return result;
    }

    public LocalDate getEndDate() {return endDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    public Response setEndDate(LocalDate endDate, BigDecimal weekdayRate, BigDecimal weekendRate) {
        Response result = new Response();
        if (this.startDate != null){
            if (!this.startDate.isBefore(endDate)) {
                result.addErrorMessage(String.format(
                        "Failed to add End Date. Must come after start date: %s", this.startDate));
                return result;
            }
            this.endDate = endDate;
            this.setTotal(weekdayRate, weekendRate);
            return result;
        }
        this.endDate = endDate;
        return result;
    }

    public String getGuestID() {return guestId;}

    public void setGuestID(String guestID) {this.guestId = guestID;}

    public BigDecimal getTotal() { return total;}

    private void setTotal(BigDecimal weekdayRate, BigDecimal weekendRate) {
        this.total = BigDecimal.ZERO;
        startDate.datesUntil(endDate).toList().forEach(d -> {
            DayOfWeek day = DayOfWeek.of(d.get(ChronoField.DAY_OF_WEEK));
            this.total = this.total.add((day.equals(DayOfWeek.FRIDAY) || day.equals(DayOfWeek.SATURDAY)) ? weekendRate : weekdayRate);
        });
        //this.total = sum;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return  Objects.equals(id, reservation.id) &&
                Objects.equals(guestId, reservation.guestId) &&
                total.equals(reservation.total);
    }

    public boolean testEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return  Objects.equals(id, reservation.id) &&
                Objects.equals(startDate, reservation.startDate) &&
                Objects.equals(endDate, reservation.endDate) &&
                Objects.equals(guestId, reservation.guestId) &&
                total.equals(reservation.total);
    }

    public Reservation duplicate() {
        Reservation newReservation = new Reservation();
        newReservation.setId(this.getId());
        newReservation.setStartDate(getStartDate());
        newReservation.setEndDate(this.getEndDate());
        newReservation.setGuestID(this.getGuestID());
        newReservation.setTotal(this.getTotal());
        return newReservation;
    }
}