package hpms.fx.agenda;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A default appointment editor which connect a IAppointmentsModel, a
 * IAppointmentEditor and a IAppointmentController.
 * @param <T> The concrete appointment's type.
 */
public class DefaultAppointmentEditor<T extends AbstractAppointment>
   implements
      IAppointmentEditor<T>
{
   private final Stage                     _view = new Stage( StageStyle.UTILITY );
   private final IAppointmentController<T> _ctrl;

   /**
    * Creates an editor from a FXML file which uses a controller inherited
    * from {@link AbstractAppointmentsModel}.
    * @param model a model inherited from {@link AbstractAppointmentsModel}
    * @param fxml the url of the FXML resource
    * @throws IOException thrown by {@link FXMLLoader#load()}
    */
   public DefaultAppointmentEditor( IAppointmentsModel<T> model, URL fxml ) throws IOException {
      final FXMLLoader loader = new FXMLLoader( fxml );
      final Parent rootPane = loader.load();
      _ctrl = loader.getController();
      _ctrl.setModel( model );
      _view.setScene( new Scene( rootPane ));
      _view.initModality( Modality.APPLICATION_MODAL );
   }

   @Override
   public void edit( LocalDateTime instant ) {
      _ctrl.beginEdit( instant );
      _view.showAndWait();
   }

   @Override
   public void edit( T appointment ) {
      _ctrl.beginEdit( appointment );
      _view.showAndWait();
   }
}
