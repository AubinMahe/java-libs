package hpms.fx.agenda.tests;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import hpms.fx.agenda.AbstractAppointment;

final class MyAppointment extends AbstractAppointment {

   public Person person;
   public String forname;
   public String name;
   public String work;
   public String comment;

   public MyAppointment( LocalDateTime instant ) {
      super( instant, instant.plus( 45, ChronoUnit.MINUTES ));
   }

   public MyAppointment( LocalDateTime instant, Person p ) {
      super( instant, instant.plus( 45, ChronoUnit.MINUTES ));
      person  = p;
      forname = person.fornameProperty().get();
      name    = person.   nameProperty().get();
   }

   @Override
   public String toString() {
      return      ( forname == null ? "" : forname )
         + ' '  + ( name    == null ? "" : name    )
         + '\n' + ( work    == null ? "" : work    );
   }
}
