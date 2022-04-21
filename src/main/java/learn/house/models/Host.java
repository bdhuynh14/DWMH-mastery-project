package learn.house.models;

import java.math.BigDecimal;
import java.util.Objects;

/** Host:
 The accommodation provider. Someone who has a property to rent per night. Host data is provided.
 Location:
 A rental property. In Don't Wreck My House, Location and Host are combined. The application enforces a limit on one
 Location per Host, so we can think of a Host and Location as a single thing.
 *
 */
public class Host {


    private String id;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private States state;
    private String postalCode;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;


    public Host() {}

    public Host(String id, String lastName, String email, String phone, String address, String city, States state, String postalCode,
                BigDecimal standardRate, BigDecimal weekendRate) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.standardRate = standardRate;
        this.weekendRate = weekendRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postal_code) {
        this.postalCode = postal_code;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(BigDecimal standard_rate) {
        this.standardRate = standard_rate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    public void setWeekendRate(BigDecimal weekend_rate) {
        this.weekendRate = weekend_rate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(id, host.id) &&
                Objects.equals(lastName, host.lastName) &&
                Objects.equals(email, host.email) &&
                Objects.equals(phone, host.phone) &&
                Objects.equals(address, host.address) &&
                Objects.equals(city, host.city) &&
                state == host.state &&
                Objects.equals(postalCode, host.postalCode) &&
                Objects.equals(standardRate, host.standardRate) &&
                Objects.equals(weekendRate, host.weekendRate);
    }
}