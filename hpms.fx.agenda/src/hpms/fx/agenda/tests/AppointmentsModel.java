package hpms.fx.agenda.tests;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import hpms.fx.agenda.AbstractAppointmentsModel;
import hpms.fx.agenda.AgendaController;

final class AppointmentsModel extends AbstractAppointmentsModel<MyAppointment> {

   private static final String   ORM   = "Osteo-Rebouteux-Magnétisme";
   private static final String   TNH   = "Tipi-Nerti-Hypno";
   private static final String   AA    = "Amma-assis";
   private static final String   VOY   = "Voyance";
   private static final String[] WORKS = { ORM, TNH, AA, VOY };

   private final Set<Person> _persons = new HashSet<>();

   AppointmentsModel() {
      super( 9, 17 );
      final Person aubin = new Person();
      aubin.fornameProperty().set( "Aubin" );
      aubin.   nameProperty().set( "Mahé"  );
      final Person muriel = new Person();
      muriel.fornameProperty().set( "Muriel" );
      muriel.   nameProperty().set( "Mahé"  );
      final Person eve = new Person();
      eve.fornameProperty().set( "Eve" );
      eve.   nameProperty().set( "Mahé"  );
      _persons.add( aubin );
      _persons.add( muriel );
      _persons.add( eve );
      final LocalDate     monday = AgendaController.previousMonday( LocalDate.now());
      final LocalDateTime appTs1 =
         monday.plus( 1, ChronoUnit.DAYS ).atTime( LocalTime.parse( "10:30" ));
      final LocalDateTime appTs2 =
         monday.plus( 2, ChronoUnit.DAYS ).atTime( LocalTime.parse( "11:00" ));
      final LocalDateTime appTs3 =
         monday.plus( 3, ChronoUnit.DAYS ).atTime( LocalTime.parse( "14:00" ));
      _appointments.add( new MyAppointment( appTs1, aubin  ));
      _appointments.add( new MyAppointment( appTs2, muriel ));
      _appointments.add( new MyAppointment( appTs3, eve    ));
   }

   public static String[] getWorks() {
      return WORKS;
   }

   public void searchPersons( String forname, String name, List<Person> persons ) {
      persons.clear();
      final boolean fornameEmpty = ( forname == null || forname.isEmpty());
      final boolean    nameEmpty = (    name == null ||    name.isEmpty());
      if( fornameEmpty && nameEmpty ) {
         return;
      }
      final Pattern fp = Pattern.compile( "(?i).*" + forname + ".*" );
      final Pattern np = Pattern.compile( "(?i).*" +    name + ".*" );
      _persons.stream()
         .filter( person ->
               ( fornameEmpty || fp.matcher( person.fornameProperty().get()).matches())
            && (    nameEmpty || np.matcher( person.   nameProperty().get()).matches()))
         .forEach( person -> persons.add( person ));
   }

   @Override
   public String getAgendaStatus( LocalDate monday ) {
      final LocalDate sunday = monday.plus( 6, ChronoUnit.DAYS );
      final Set<DayOfWeek> days = new HashSet<>();
      for( final MyAppointment app : _appointments ) {
         if( between( app, monday, sunday )){
            days.add( app.getFrom().getDayOfWeek());
         }
      }
      return String.format( "%d rendez-vous sur %d jours", _appointments.size(), days.size());
   }
}
