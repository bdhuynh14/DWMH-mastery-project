Game plan for DWMH:

Create packages


Create models for host:
host uuid hostId
string last name
string email
string phone
string address
string city
string state
string zipcode
bigdecimal standard rate
bigdecimal weekend rate


create models for guest:
int for guest id
string first name
string last name
string email
string state


create models for reservation:
public Host host
LocalDate startDate
LocalDate endDate
public Guest guest
bigdecimal total amount


create classes
data:
reservation repository

reservation file repository:
viewReservationByHost()
addReservation()
editReservation()
cancelReservation()
getFilePath()
serialize()
writeAll()

host repository

hostfilerepository
findAll()
-Guest Repository

GuestFileRepository
findAll()

domain:
reservation service
viewReservationByHost()
addReservation()
editReservation()
cancelReservation()
validate()
validateNulls()
validateFields()

results:
isSuccess()
addErrorMessage()
getErrorMessage()

user interface:
Controller run()
runMenu()
viewReservationByHost()
addReservation()
editReservation()
cancelReservation()
getHost()
getGuest()

view:
mainMenu()
displayReservations()
promptHostEmail()
promptGuestEmail()
displayHeader()
displayMessage()



spring di


##domain
###reservation service
view reservations : write tests
make reservation : write tests
edit reservation: write tests
cancel reservation: write tests
validation: check to see if any of the functions above using the tests
#userinterface
#controller
view reservations test
make reservation test
edit reservation test
cancel reservation test

####view
view reservations test
make reservation test
edit reservation test
cancel reservation test

## quick fixes and polish
run tests and eventually app as a whole and check for functionality
note bugs/hiccups in code found

troubleshooting

additional polish for niceness of organization/conciseness if needed