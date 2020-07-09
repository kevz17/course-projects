## Write-up

* This program is a theater's reservation system, using the MVC architecture, where Model consists of ```Seat```, ```Row``` and ```Theater```, View and Controller are represented as methods ```displaySeats``` and ```reserve``` of ```ReservationsService``` respectively.

* The entry point of this system is ```ReservationSystem``` class, started by the main method. It creates a new instance of Theater, then calls the service implemented in ```ReservationService``` with theater object as its argument. Then perform corresponding task as per user's input commands.

* In the ```Row``` class, in order to make ```Seat``` class has corresponding toString representation (_ , =, X), **Chain of Responsibility Pattern** is used to set each seat of the row as wheelchair accessible, when the row is set as wheelchair accessible.

* In the ```Seat``` class, as mentioned above, **State Pattern** is used to enable ```Seat``` object to change its behavior(toString will print different information) based on the state(accessible) it's in.
