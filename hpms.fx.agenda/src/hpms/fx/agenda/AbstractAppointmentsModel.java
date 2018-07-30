package hpms.fx.agenda;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The abstract model which record the appointments.
 * @param <T> The concrete appointment's type.
 */
public abstract class AbstractAppointmentsModel<T extends AbstractAppointment>
   implements
      IAppointmentsModel<T>
{
   protected final Set<T> _appointments = new HashSet<>();
   protected final int _firstHour;
   protected final int _lastHour;

   /**
    * Build the model.
    * @param firstHour the first appointment of a day.
    * @param lastHour  the last appointment of a day.
    */
   public AbstractAppointmentsModel( int firstHour, int lastHour ) {
      _firstHour = firstHour;
      _lastHour  = lastHour;
   }

   @Override
   public int getFirstHour() {
      return _firstHour;
   }

   @Override
   public int getLastHour() {
      return _lastHour;
   }

   @Override
   public void addAppointment( T appointment ) {
      _appointments.add( appointment );
   }

   @Override
   public void removeAppointment( T appointment ) {
      _appointments.remove( appointment );
   }

   protected boolean between( T appointment, LocalDate from, LocalDate to ) {
      return appointment.getFrom().toLocalDate().equals  ( from )
         ||  appointment.getTo  ().toLocalDate().equals  ( to   )
         ||(   appointment.getFrom().toLocalDate().isAfter ( from )
            && appointment.getTo  ().toLocalDate().isBefore( to   ));
   }

   protected void addIfInWeek( T appointment, LocalDate from, LocalDate to, Set<T> appointments ) {
      if( between( appointment, from, to )) {
         appointments.add( appointment );
      }
   }

   @Override
   public void getAppointments( LocalDate from, LocalDate to, Set<T> appointments ) {
      appointments.clear();
      _appointments.stream().forEach( a -> addIfInWeek( a, from, to, appointments ));
   }
}
