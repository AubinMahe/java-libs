package hpms.fx.agenda;

import java.time.LocalDateTime;

/**
 * The controller of the appointment editor.
 * @param <T> The concrete appointment's type.
 */
public interface IAppointmentController<T extends AbstractAppointment> {

   /**
    * Connect the model to the controller.
    * @param model the appointment's model.
    */
   void setModel( IAppointmentsModel<T> model );

   /**
    * Initialize the appointment editor with a new entry.
    * @param instant the date and time of the appointment.
    */
   void beginEdit( LocalDateTime instant );

   /**
    * Initialize the appointment editor with an already existing entry.
    * @param appointment the targeted appointment.
    */
   void beginEdit( T appointment );
}
