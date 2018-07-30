package hpms.fx.agenda;

import java.time.LocalDateTime;

/**
 * The contract of an appointment editor.
 * @param <T> The concrete appointment's type.
 */
public interface IAppointmentEditor<T extends AbstractAppointment> {

   /**
    * Show the editor of a blank cell to create a new entry.
    * @param instant the date and time of the appointment.
    */
   void edit( LocalDateTime instant );

   /**
    * Show the editor of an already existing appointment to consult
    * or/and change it.
    * @param appointment the targeted appointment.
    */
   void edit( T appointment );
}
