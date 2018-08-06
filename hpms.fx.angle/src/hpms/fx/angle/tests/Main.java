package hpms.fx.angle.tests;

import hpms.fx.angle.AngleControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

   @Override
   public void start( Stage main ) throws Exception {
      main.setTitle( "AngleControl tests main window" );
      final AngleControl angleCtrl  = new AngleControl();
      final BorderPane   bp         = new BorderPane( angleCtrl );
      final Slider       angleSlide = new Slider( 0, 179.0,  90.0 );
      angleCtrl.setPrefWidth( 200 );
      angleCtrl.setHasText( true );
      angleCtrl.angleProperty().bind( angleSlide.valueProperty());
      bp.setBottom( angleSlide );
      main.setScene( new Scene( bp ));
      main.setOnCloseRequest( e -> System.exit( 0 ));
      main.show();
   }

   public static void main( String[] args ) {
      launch( args );
   }
}
