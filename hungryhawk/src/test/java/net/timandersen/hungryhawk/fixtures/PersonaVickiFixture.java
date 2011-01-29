package net.timandersen.hungryhawk.fixtures;

import fitlibrary.DoFixture;
import net.timandersen.model.domain.Reservation;
import net.timandersen.repository.ReservationRepository;
import net.timandersen.util.SpringContextWrapper;

public class PersonaVickiFixture extends DoFixture {

  public boolean makesAReservationForPeople(int numberOfPeople) {
    Reservation reservation = new Reservation();
    reservation.setName("Vicki");
    reservation.setGuests(numberOfPeople);
    SpringContextWrapper.getBean(ReservationRepository.class).save(reservation);
    return true;
  }

  public boolean receivesConfirmation() {
    return true;
  }

}