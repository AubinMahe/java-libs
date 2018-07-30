package hpms.fx.agenda;

import java.time.LocalDateTime;

public interface IAppointmentController<T extends AbstractAppointment> {

   void setModel( IAppointmentsModel<T> model );

   void beginEdit( LocalDateTime instant );

   void beginEdit( T appointment );
}
