package learn.house.models;


import java.util.Objects;

/**Guest: A customer. Someone who wants to book a place to stay. Guest data is provided via a zip download.
 *
 */
public class Guest {

    private String guestId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private States state;

    public Guest() {}

    public Guest(String guestID, String firstName, String lastName, String email, String phone, States state) {
        this.guestId = guestID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.state = state;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    /** Built for unit testing: every field must be equivalent. Can overload as needed.
     * use like `foo.equals(bar);`
     * @param o The object to compare to.
     * @return true means equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(guestId, guest.guestId) &&
                Objects.equals(firstName, guest.firstName) &&
                Objects.equals(lastName, guest.lastName) &&
                Objects.equals(email, guest.email) &&
                Objects.equals(phone, guest.phone) &&
                state == guest.state;
    }
}