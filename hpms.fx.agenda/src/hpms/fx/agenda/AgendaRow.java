package hpms.fx.agenda;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class AgendaRow {

   private final StringProperty _time         = new SimpleStringProperty();
   private final StringProperty _monday       = new SimpleStringProperty();
   private final StringProperty _tuesday      = new SimpleStringProperty();
   private final StringProperty _wednesday    = new SimpleStringProperty();
   private final StringProperty _thursday     = new SimpleStringProperty();
   private final StringProperty _friday       = new SimpleStringProperty();
   private final StringProperty _saturday     = new SimpleStringProperty();
   private final StringProperty _sunday       = new SimpleStringProperty();
   private final Map<
      DayOfWeek, AbstractAppointment>   _appointments = new HashMap<>();
   private final LocalDateTime  _mondayDateTime;

   public AgendaRow( LocalDateTime monday ) {
      _mondayDateTime = monday;
      _time.set( String.format( "%02d:%02d",
         monday.get( ChronoField.HOUR_OF_DAY ), monday.get( ChronoField.MINUTE_OF_HOUR )));
   }

   public void reset() {
      _monday   .set( null );
      _tuesday  .set( null );
      _wednesday.set( null );
      _thursday .set( null );
      _friday   .set( null );
      _saturday .set( null );
      _sunday   .set( null );
   }

   public void setAppointment( AbstractAppointment appointment ) {
      final DayOfWeek dow = appointment.getFrom().getDayOfWeek();
      _appointments.put( dow, appointment );
      switch( dow ) {
      case MONDAY   : _monday   .set( appointment.toString()); break;
      case TUESDAY  : _tuesday  .set( appointment.toString()); break;
      case WEDNESDAY: _wednesday.set( appointment.toString()); break;
      case THURSDAY : _thursday .set( appointment.toString()); break;
      case FRIDAY   : _friday   .set( appointment.toString()); break;
      case SATURDAY : _saturday .set( appointment.toString()); break;
      case SUNDAY   : _sunday   .set( appointment.toString()); break;
      }
   }

   public LocalDateTime getMondayDateTime() {
      return _mondayDateTime;
   }

   public AbstractAppointment getAppointment( DayOfWeek dow ) {
      return _appointments.get( dow );
   }

   public StringProperty timeProperty() {
      return _time;
   }

   public StringProperty mondayProperty() {
      return _monday;
   }

   public StringProperty tuesdayProperty() {
      return _tuesday;
   }

   public StringProperty wednesdayProperty() {
      return _wednesday;
   }

   public StringProperty thursdayProperty() {
      return _thursday;
   }

   public StringProperty fridayProperty() {
      return _friday;
   }

   public StringProperty saturdayProperty() {
      return _saturday;
   }

   public StringProperty sundayProperty() {
      return _sunday;
   }
}
