package hpms.fx.agenda.tests;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;

import hpms.fx.agenda.IAppointmentController;
import hpms.fx.agenda.IAppointmentsModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AppointmentController implements IAppointmentController<MyAppointment> {

   private final List<Person>      _completions = new LinkedList<>();
   private /* */ AppointmentsModel _model;
   private /* */ Person            _person;
   private /* */ MyAppointment     _appointment;

   @FXML private DatePicker       _date;
   @FXML private TextField        _fromHour;
   @FXML private TextField        _fromMinute;
   @FXML private TextField        _toHour;
   @FXML private TextField        _toMinute;
   @FXML private TextField        _forname;
   @FXML private TextField        _name;
   @FXML private Button           _proposalBtn;
   @FXML private ComboBox<String> _work;
   @FXML private TextField        _comment;
   @FXML private Button           _addOrUpdateBtn;
   @FXML private Button           _deleteBtn;

   @Override
   public void setModel( IAppointmentsModel<MyAppointment> model ) {
      _model = (AppointmentsModel)model;
      _work.getItems().setAll( AppointmentsModel.getWorks());
   }

   private void setDateTime( TextField hour, TextField minute, LocalDateTime instant ) {
      _date .setValue( instant.toLocalDate());
      hour  .setText( Integer.toString( instant.get( ChronoField.HOUR_OF_DAY    )));
      minute.setText( Integer.toString( instant.get( ChronoField.MINUTE_OF_HOUR )));
   }

   private void initFromModel() {
      setDateTime( _fromHour, _fromMinute, _appointment.getFrom());
      setDateTime( _toHour  , _toMinute  , _appointment.getTo  ());
      _person = _appointment.person;
      if( _person != null ) {
         _forname.setText( _person.fornameProperty().get());
         _name   .setText( _person.nameProperty   ().get());
      }
      else {
         _forname.setText( _appointment.forname );
         _name   .setText( _appointment.name    );
      }
      _work.getSelectionModel().select( _appointment.work );
      _comment.setText( _appointment.comment );
      _completions.clear();
      _proposalBtn.setText( "Entrez nom ou prénom" );
   }

   private LocalDateTime buildTimestamp( TextField fromHour, TextField fromMinute ) {
      final int hour = Math.min(
         _model.getLastHour(),
         Math.max(
            _model.getFirstHour(),
            Integer.parseInt( fromHour.getText())));
      final int minute = Math.min(
         59,
         Math.max(
            0,
            Integer.parseInt( fromMinute.getText())));
      return LocalDateTime.from( _date.getValue().atTime( LocalTime.of( hour, minute )));
   }

   private void initFromView() {
      _appointment.setFrom   ( buildTimestamp( _fromHour, _fromMinute ));
      _appointment.setTo     ( buildTimestamp( _toHour  , _toMinute   ));
      _appointment.person  = _person;
      _appointment.forname = _forname.getText();
      _appointment.name    = _name   .getText();
      _appointment.work    = _work   .getValue();
      _appointment.comment = _comment.getText();
   }

   @Override
   public void beginEdit( LocalDateTime instant ) {
      _appointment = new MyAppointment( instant );
      _completions.clear();
      _addOrUpdateBtn.setText( "Ajouter" );
      _deleteBtn     .setVisible( false );
      initFromModel();
   }

   @Override
   public void beginEdit( MyAppointment appointment ) {
      _appointment = appointment;
      _completions.clear();
      _addOrUpdateBtn.setText( "Enregistrer" );
      _deleteBtn     .setVisible( true );
      initFromModel();
   }

   @FXML
   public void useCompletion() {
      _person = _completions.get( 0 );
      _forname.setText( _person.fornameProperty().get());
      _name   .setText( _person.   nameProperty().get());
   }

   private void proposal() {
      _model.searchPersons( _forname.getText(), _name.getText(), _completions );
      if( _completions.isEmpty()) {
         _person = null;
         _proposalBtn.setText( "Entrez nom ou prénom" );
         _proposalBtn.setDisable( true );
      }
      else if( _completions.size() == 1 ) {
         final Person choice = _completions.get( 0 );
         _proposalBtn.setText(
            choice.fornameProperty().get() + "\n" +
            choice.   nameProperty().get()          );
         _proposalBtn.setDisable( false );
      }
      else {
         _proposalBtn.setText( _completions.size() + " personnes correspondent" );
         _proposalBtn.setDisable( true );
      }
   }

   @FXML
   public void initialize() {
      _forname.setOnKeyReleased( e -> proposal());
      _name   .setOnKeyReleased( e -> proposal());
   }

   @FXML
   public void add() {
      _name.getScene().getWindow().hide();
      initFromView();
      _model.addAppointment( _appointment );
   }

   @FXML
   public void remove() {
      _name.getScene().getWindow().hide();
      initFromView();
      _model.removeAppointment( _appointment );
   }

   @FXML
   public void cancel() {
      _name.getScene().getWindow().hide();
      _appointment = null;
   }
}
