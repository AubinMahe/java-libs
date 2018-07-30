package hpms.fx.agenda;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A {@link Stage} which show a table of seven days as columns, two buttons to
 * show the next/previous week and a status bar which resume the week in a
 * few words.
 * @param <T> The concrete appointment's type.
 */
public class Agenda<T extends AbstractAppointment> extends Stage {

   /**
    * Build an agenda from it's model and a user defined appointment editor.
    * @param model the model of this agenda, often inherited from
    * {@link AbstractAppointmentsModel}.
    * @param editor a user defined appointment editor.
    * @throws IOException thrown by {@link FXMLLoader#load()} when the
    * resource isn't found.
    */
   public Agenda( IAppointmentsModel<T> model, IAppointmentEditor<T> editor ) throws IOException {
      final FXMLLoader loader = new FXMLLoader( getClass().getResource( "Agenda.fxml" ));
      final Parent rootPane = loader.load();
      final AgendaController<T> ctrl = loader.getController();
      ctrl.setAppointmentModel( model  );
      ctrl.setAppointmentEditor  ( editor );
      final Scene agendaScene = new Scene( rootPane );
      setScene( agendaScene );
      ctrl.setWeek( LocalDate.now());
   }

   /**
    * Build an agenda from it's model and a default appointment editor.
    * @param model the model of this agenda, often inherited from
    * {@link AbstractAppointmentsModel}.
    * @param fxmlEditor an URL to a FXML definition of an appointment editor.
    * @throws IOException thrown by {@link FXMLLoader#load()} when the
    * resource isn't found.
    * @see DefaultAppointmentEditor
    */
   public Agenda( IAppointmentsModel<T> model, URL fxmlEditor ) throws IOException {
      this( model, new DefaultAppointmentEditor<>( model, fxmlEditor ));
   }
}
