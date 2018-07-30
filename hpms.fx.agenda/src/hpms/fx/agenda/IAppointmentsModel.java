package hpms.fx.agenda;

import java.time.LocalDate;
import java.util.Set;

/**
 * The contract of the appointment's model.
 * @param <T> The concrete appointment's type.
 * @see AbstractAppointmentsModel
 */
public interface IAppointmentsModel<T extends AbstractAppointment> {

   /**
    * @return the first appointment of a day.
    */
   int getFirstHour();

   /**
    * @return the last appointment of a day.
    */
   int getLastHour();

   /**
    * @param appointment the appointment to add.
    */
   void addAppointment( T appointment );

   /**
    * @param appointment the appointment to remove.
    */
   void removeAppointment( T appointment );

   /**
    * Clears then fills the provided set with the appointments located between
    * from and to.
    * @param from the beginning of the interval.
    * @param to   the ending of the interval.
    * @param appointments the resulting set.
    */
   void getAppointments( LocalDate from, LocalDate to, Set<T> appointments );

   /**
    * @param monday the first day of a week.
    * @return a single string to resume a whole week.
    */
   String getAgendaStatus( LocalDate monday );
}
