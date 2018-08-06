package hpms.fx.angle;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public final class AngleControl extends BorderPane {

   private final BooleanProperty _hasTextProperty = new SimpleBooleanProperty();
   private final DoubleProperty  _angleProperty   = new SimpleDoubleProperty();
   private final double[]        _x = {
         4*cos( toRadians( 180 )),
         4*cos( toRadians( 165 )),
         4*cos( toRadians( 150 )),
         4*cos( toRadians( 135 )),
         4*cos( toRadians( 120 )),
         4*cos( toRadians( 105 )),
         4*cos( toRadians( 110 )),
         4*cos( toRadians(  85 )),
      16+2*cos( toRadians(  85 )),
      16+2*cos( toRadians(  75 )),
      16+2*cos( toRadians(  60 )),
      16+2*cos( toRadians(  45 )),
      16+2*cos( toRadians(  30 )),
      16+2*cos( toRadians(  15 )),
      16+2*cos( toRadians(   0 )),
      16+2*cos( toRadians( -15 )),
      16+2*cos( toRadians( -30 )),
      16+2*cos( toRadians( -45 )),
      16+2*cos( toRadians( -60 )),
      16+2*cos( toRadians( -75 )),
      16+2*cos( toRadians( -85 )),
         4*cos( toRadians( -85 )),
         4*cos( toRadians( -90 )),
         4*cos( toRadians(-105 )),
         4*cos( toRadians(-120 )),
         4*cos( toRadians(-135 )),
         4*cos( toRadians(-150 )),
         4*cos( toRadians(-165 )),
         4*cos( toRadians( 180 )),
   };
   private final double[]        _y = {
       4*sin( toRadians( 180 )),
       4*sin( toRadians( 165 )),
       4*sin( toRadians( 150 )),
       4*sin( toRadians( 135 )),
       4*sin( toRadians( 120 )),
       4*sin( toRadians( 105 )),
       4*sin( toRadians( 110 )),
       4*sin( toRadians(  85 )),
       2*sin( toRadians(  85 )),
       2*sin( toRadians(  75 )),
       2*sin( toRadians(  60 )),
       2*sin( toRadians(  45 )),
       2*sin( toRadians(  30 )),
       2*sin( toRadians(  15 )),
       2*sin( toRadians(   0 )),
       2*sin( toRadians( -15 )),
       2*sin( toRadians( -30 )),
       2*sin( toRadians( -45 )),
       2*sin( toRadians( -60 )),
       2*sin( toRadians( -75 )),
       2*sin( toRadians( -85 )),
       4*sin( toRadians( -85 )),
       4*sin( toRadians( -90 )),
       4*sin( toRadians(-105 )),
       4*sin( toRadians(-120 )),
       4*sin( toRadians(-135 )),
       4*sin( toRadians(-150 )),
       4*sin( toRadians(-165 )),
       4*sin( toRadians( 180 )),
   };
   private /* */ Canvas          _canvas;

   public AngleControl() {
      _angleProperty.addListener((o,b,a) -> draw());
      prefWidthProperty().addListener((o,b,a) -> reInitCanvas());
      setPrefWidth( 100 );
      Platform.runLater(() -> draw());
   }

   private void reInitCanvas() {
      final double w = getPrefWidth();
      _canvas = new Canvas( w, 0.62 * w );
      setCenter( _canvas );
   }

   public void draw() {
      if( _canvas == null ) {
         reInitCanvas();
      }
      if( _angleProperty.get() > 179.0 ) {
         _angleProperty.set( 179.0 );
      }
      else if( _angleProperty.get() < 0.0 ) {
         _angleProperty.set( 0.0 );
      }
      final double          width = _canvas.getWidth();
      final double          scale = width / 38.0;
      final GraphicsContext gc    = _canvas.getGraphicsContext2D();
      gc.clearRect( 0, 0, width, _canvas.getHeight());
      gc.save();
      gc.scale( scale, -scale );
      gc.translate( 19.0, -19.0 );
      gc.rotate( angleProperty().get());
      gc.setLineWidth( 0.25 );
      gc.strokePolygon( _x, _y, _x.length );
      gc.strokeArc( -1.8, -1.8, 3.6, 3.6, 0, 360.0, ArcType.CHORD );
      gc.strokeArc( 14, -0.2, 0.4, 0.4, 0, 360.0, ArcType.CHORD );
      gc.strokeArc( 11, -0.2, 0.4, 0.4, 0, 360.0, ArcType.CHORD );
      gc.strokeArc(  8, -0.2, 0.4, 0.4, 0, 360.0, ArcType.CHORD );
      gc.restore();
      if( _hasTextProperty.get()) {
         gc.setTextAlign( TextAlignment.LEFT );
         gc.setTextBaseline( VPos.TOP );
         gc.setFont( Font.font( "monospace", FontWeight.EXTRA_LIGHT, 11 ));
         gc.strokeText( String.format( "%3.0f°", _angleProperty.get()), 4, 4 );
      }
   }

   public boolean hasText() {
      return _hasTextProperty.get();
   }

   public void hasText( boolean value ) {
      _hasTextProperty.set( value );
   }

   public boolean getHasText() {
      return _hasTextProperty.get();
   }

   public void setHasText( boolean value ) {
      _hasTextProperty.set( value );
   }

   public BooleanProperty hasTextProperty() {
      return _hasTextProperty;
   }

   public double getAngle() {
      return _angleProperty.get();
   }

   public void setAngle( double value ) {
      _angleProperty.set( value % 180.0 );
   }

   public DoubleProperty angleProperty() {
      return _angleProperty;
   }
}
