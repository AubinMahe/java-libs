package hpms.fx.agenda.tests;

import hpms.fx.agenda.Agenda;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

   @Override
   public void start( Stage main ) throws Exception {
      final Button showAgendaBtn = new Button( "Show agenda" );
      final Agenda<MyAppointment> agenda = new Agenda<>(
         new AppointmentsModel(),
         getClass().getResource( "Appointment.fxml" ));
      agenda.setTitle( "FxAgenda dialog" );
      showAgendaBtn.setOnAction( e -> agenda.show());
      main.setTitle( "FxAgenda tests main window" );
      main.setScene( new Scene( new BorderPane( showAgendaBtn )));
      main.setOnCloseRequest( e -> System.exit( 0 ));
      main.show();
   }

   public static void main( String[] args ) {
      launch( args );
   }
}
