package hpms.fx.agenda;

import java.time.LocalDateTime;

/**
 * This abstract class encapsulates the minimal information to define an
 * appointment: the dates of beginning and ending.
 */
public abstract class AbstractAppointment {

   private LocalDateTime _from;
   private LocalDateTime _to;

   /**
    * Construct an appointment.
    * @param from the beginning of the appointment, must be before "to".
    * @param to   the ending    of the appointment, must be after "from".
    */
   public AbstractAppointment( LocalDateTime from, LocalDateTime to ) {
      _from = from;
      _to   = to;
   }

   /**
    * Returns the beginning of the appointment.
    * @return the beginning of the appointment.
    */
   public LocalDateTime getFrom() {
      return _from;
   }

   /**
    * Sets the beginning of the appointment.
    * @param from the beginning of the appointment.
    */
   public void setFrom( LocalDateTime from ) {
      _from = from;
   }

   /**
    * Returns the ending of the appointment.
    * @return the ending of the appointment.
    */
   public LocalDateTime getTo() {
      return _to;
   }

   /**
    * Sets the ending of the appointment.
    * @param to the ending of the appointment.
    */
   public void setTo( LocalDateTime to ) {
      _to = to;
   }
}
