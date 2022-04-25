package learn.house.ui;


import learn.house.data.DataException;
import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.List;

public class Controller {

    private final HostService hostService;
    private final GuestService guestService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(HostService hostService, GuestService guestService, ReservationService reservationService, View view) {
        this.hostService = hostService;
        this.guestService = guestService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to \"Don't Wreck My House\"!!!");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_BY_HOST:
                    reservationsByHost();
                    break;
                case MAKE_NEW_RESERVATION:
                    newReservation();
                    break;
                case EDIT_A_RESERVATION:
                    editReservation();
                    break;
                case CANCEL_A_RESERVATION:
                    cancelReservation();
                    break;
//                case ADD_HOST:
//                    addHost();
//                    break;
//                case ADD_GUEST:
//                    addGuest();
//                    break;
//                case EDIT_HOST:
//                    editHost();
//                    break;
//                case EDIT_GUEST:
//                    editGuest();
//                    break;
//                case REMOVE_HOST:
//                    removeHost();
//                    break;
//                case REMOVE_GUEST:
//                    removeGuest();
//                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    // top level menu
    private void reservationsByHost() throws DataException {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_HOST.getMessage());
        Host host = selectHost();
        if (host == null) return;
        view.printReservations(reservationService.resByHost(host.getId()), host);
        view.enterToContinue();
    }

    private Host selectHost() throws DataException {
        Host criteria = view.getHostCriteria();
        List<Host> hosts = hostService.getHosts(criteria);
        return view.selectHost(hosts);
    }

    private Guest selectGuest() throws DataException {
        Guest criteria = view.getGuestCriteria();
        List<Guest> guests = guestService.getGuests(criteria);
        return view.selectGuest(guests);
    }

    private void newReservation() throws DataException {
        view.displayHeader(MainMenuOption.MAKE_NEW_RESERVATION.getMessage());
        Guest guest = selectGuest();
        if (guest == null) {
            view.enterToContinue();
            return;
        }
        Host host = selectHost();
        if (host == null) {
            view.enterToContinue();
            return;
        }
        view.displayHeader("Existing future reservations:");
        view.printReservations(reservationService.resByHostFuture(host.getId()), host);
        Boolean finish = false;
        while (!finish) {
            Reservation newReservation = new Reservation();
            newReservation = view.getDates(newReservation, host);
            newReservation.setGuestID(guest.getGuestId());
            finish = !view.displayDatesConfirmation(newReservation);
            if (finish) {
                Result result = reservationService.add(host.getId(), newReservation);
                finish = result.isSuccess();
                view.displayStatus(result.isSuccess(), result.getErrorMessages());
            }
            view.enterToContinue();
        }
    }

    private void editReservation() throws DataException {
        view.displayHeader(MainMenuOption.EDIT_A_RESERVATION.getMessage());
        Host host = selectHost();
        if (host == null) {
            view.enterToContinue();
            return;
        }
        view.displayHeader("Existing future reservations:");
        List<Reservation> reservations = reservationService.resByHostFuture(host.getId());
        Reservation oldReservation = view.selectReservation(reservations, host);
        Boolean finish = oldReservation == null;
        while (!finish) {
            Reservation newReservation = oldReservation.duplicate();
            newReservation = view.getDates(newReservation, host);
            finish = !view.displayDatesConfirmation(newReservation);
            if (finish) {
                Result result = reservationService.update(host.getId(), newReservation);
                finish = result.isSuccess();
                view.displayStatus(result.isSuccess(), result.getErrorMessages());
            }
            view.enterToContinue();
        }
    }

    private void cancelReservation() throws DataException {
        view.displayHeader(MainMenuOption.CANCEL_A_RESERVATION.getMessage());
        Host host = selectHost();
        if (host == null) return;
        view.displayHeader("Existing future reservations:");
        List<Reservation> reservations = reservationService.resByHostFuture(host.getId());
        Boolean finish = reservations == null || reservations.size() == 0;
        while (!finish) {
            Reservation oldReservation = view.selectReservation(reservations, host);
            if (oldReservation == null) {
                view.enterToContinue();
                return;
            }
            finish = view.displayDeleteConfirmation(oldReservation);
            if (finish) {
                Boolean result = reservationService.cancel(host.getId(), oldReservation);
                finish = result;
                view.displayStatus(result, result ? "" : "Delete Failed.");
            }
            view.enterToContinue();
        }
    }
}