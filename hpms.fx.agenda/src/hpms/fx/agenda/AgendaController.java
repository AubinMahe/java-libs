package hpms.fx.agenda;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The agenda controller connect the model to the view: a {@link TableView}
 * into a {@link BorderPane}. This clas is public for technical reason.
 * @param <T> The concrete appointment's type.
 */
public final class AgendaController<T extends AbstractAppointment> {

   private static final int TBL_HEADER_HEIGHT = 30;
   private static final int ROW_HEIGHT        = 36;

   /**
    * @param date a date.
    * @return the first day of the week of the provided day.
    */
   public static LocalDate previousMonday( LocalDate date ) {
      return
         date.minus( date.getDayOfWeek().ordinal() - DayOfWeek.MONDAY.ordinal(), ChronoUnit.DAYS );
   }

   private final Set<T>                _appointments = new HashSet<>();
   private /* */ LocalDate             _current      = previousMonday( LocalDate.now());
   private /* */ IAppointmentsModel<T> _model;
   private /* */ IAppointmentEditor<T> _editor;

   @FXML private Label                _week;
   @FXML private TableView<AgendaRow> _rows;
   @FXML private Label                _statusBar;

   @FXML
   public void initialize() {
      _rows.fixedCellSizeProperty().set( ROW_HEIGHT );
      _rows.getSelectionModel().setCellSelectionEnabled( true );
   }

   /**
    * Connect the controller to the model.
    * @param model the appointment's model.
    */
   public void setAppointmentModel( IAppointmentsModel<T> model ) {
      _model = model;
      LocalDateTime ts = _current.atStartOfDay().plus( _model.getFirstHour(), ChronoUnit.HOURS );
      for( int hour = _model.getFirstHour(); hour < _model.getLastHour(); ++hour ) {
         _rows.getItems().add( new AgendaRow( ts ));
         ts = ts.plus( 30, ChronoUnit.MINUTES );
         _rows.getItems().add( new AgendaRow( ts ));
         ts = ts.plus( 30, ChronoUnit.MINUTES );
      }
      _rows.setPrefHeight( TBL_HEADER_HEIGHT + _rows.getItems().size() * ROW_HEIGHT );
   }

   /**
    * A user defined appointment editor, often a {@link Stage} offering a form
    * of inputs.
    * @param editor a user defined appointment editor.
    * @see DefaultAppointmentEditor
    */
   public void setAppointmentEditor( IAppointmentEditor<T> editor ) {
      _editor = editor;
   }

   /**
    * Set the current showed week.
    * @param date any date in the week to show.
    */
   public void setWeek( LocalDate date ) {
      _current = previousMonday( date );
      final LocalDate sunday = _current.plus( 6, ChronoUnit.DAYS );
      _week.setText( "Semaine du " +
         _current.getDayOfMonth() + " " +
         _current.getMonth().getDisplayName( TextStyle.FULL, Locale.getDefault()) +
         " au " +
         sunday.getDayOfMonth() + " " +
         sunday.getMonth().getDisplayName( TextStyle.FULL, Locale.getDefault()));
      _rows.getItems().forEach( row -> row.reset());
      _model.getAppointments( _current, sunday, _appointments );
      for( final AbstractAppointment app : _appointments ) {
         final int slotFrom = 2*( app.getFrom().getHour() - 9) + ( app.getFrom().getMinute() / 30 );
         final int slotTo   =
            Math.min(
               _rows.getItems().size(),
               (int)Math.ceil( 2.0*( app.getTo().getHour() - 9.0) + ( app.getTo().getMinute() / 30.0 )));
         for( int slot = slotFrom; slot < slotTo; ++slot ) {
            final AgendaRow row = _rows.getItems().get( slot );
            row.setAppointment( app );
         }
      }
      _statusBar.setText( _model.getAgendaStatus( _current ));
   }

   @FXML
   public void previousWeek() {
      setWeek( _current.minus( 7, ChronoUnit.DAYS ));
   }

   @FXML
   public void nextWeek() {
      setWeek( _current.plus( 7, ChronoUnit.DAYS ));
   }

   @FXML
   public void tblClicked( @SuppressWarnings("unused") MouseEvent e ) {
      @SuppressWarnings("rawtypes")
      final ObservableList<TablePosition> cells = _rows.getSelectionModel().getSelectedCells();
      if( ! cells.isEmpty()) {
         final TablePosition<AgendaRow, String> tp  = cells.get( 0 );
         final int                              col = tp.getColumn();
         final TableColumn<AgendaRow, String>   tc  = tp.getTableColumn();
         if(( tc != null )&&( col > 0 )) {
            final int       slot = tp.getRow();
            final AgendaRow row  = _rows.getItems().get( slot );
            final DayOfWeek dow  = DayOfWeek.of( col );
            @SuppressWarnings("unchecked")
            final T         app  = (T)row.getAppointment( dow );
            if( app != null ) {
               _editor.edit( app );
            }
            else {
               final LocalDateTime instant =
                  row.getMondayDateTime().plus( col - 1, ChronoUnit.DAYS );
               _editor.edit( instant );
            }
            setWeek( _current );
         }
      }
   }
}
