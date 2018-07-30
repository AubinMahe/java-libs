package hpms.fx.agenda.tests;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person implements Comparable<Person> {

   private final StringProperty            _forname    = new SimpleStringProperty();
   private final StringProperty            _name       = new SimpleStringProperty();
   private final StringProperty            _gender     = new SimpleStringProperty();
   private final ObjectProperty<LocalDate> _birthdate  = new SimpleObjectProperty<>();
   private final IntegerProperty           _weight     = new SimpleIntegerProperty();
   private final IntegerProperty           _size       = new SimpleIntegerProperty();
   private final StringProperty            _occupation = new SimpleStringProperty();
   private final StringProperty            _hobbies    = new SimpleStringProperty();
   private final StringProperty            _email      = new SimpleStringProperty();
   private final StringProperty            _phone      = new SimpleStringProperty();
   private final StringProperty            _mobile     = new SimpleStringProperty();
   private final StringProperty            _notoriety  = new SimpleStringProperty();
   private final StringProperty            _history    = new SimpleStringProperty();
   private final BooleanProperty           _smoker     = new SimpleBooleanProperty();

   private int _saveID;

   public Person() {
      _forname  .set( "<à saisir>" );
      _name     .set( "<à saisir>" );
      _birthdate.set( LocalDate.ofYearDay( 1980, 365/2 ));
   }

   void setSaveID( int id ) {
      _saveID = id;
   }

   int getSaveID() {
      return _saveID;
   }

   @Override
   public int compareTo( Person right ) {
      int diff;
      diff = _name.get().compareTo( right._name.get());
      if( diff != 0 ) {
         return diff;
      }
      diff = _forname.get().compareTo( right._forname.get());
      if( diff != 0 ) {
         return diff;
      }
      diff = _birthdate.get().compareTo( right._birthdate.get());
      if( diff != 0 ) {
         return diff;
      }
      return 0;
   }

   public StringProperty            fornameProperty   () { return _forname;    }
   public StringProperty            nameProperty      () { return _name;       }
   public StringProperty            genderProperty    () { return _gender;     }
   public ObjectProperty<LocalDate> birthdateProperty () { return _birthdate;  }
   public IntegerProperty           weightProperty    () { return _weight;     }
   public IntegerProperty           sizeProperty      () { return _size;       }
   public StringProperty            occupationProperty() { return _occupation; }
   public StringProperty            hobbiesProperty   () { return _hobbies;    }
   public StringProperty            emailProperty     () { return _email;      }
   public StringProperty            phoneProperty     () { return _phone;      }
   public StringProperty            mobileProperty    () { return _mobile;     }
   public StringProperty            notorietyProperty () { return _notoriety;  }
   public StringProperty            historyProperty   () { return _history;    }
   public BooleanProperty           smokerProperty    () { return _smoker;     }
}
