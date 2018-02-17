package students_CRUD;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentDetails {
    private final StringProperty uniqId;
    private final StringProperty firstName;
    private final StringProperty secondName;
    private final StringProperty lastName;
    private final StringProperty birthDay;
    private final StringProperty groupNumber;


    public StudentDetails(String uniqId, String firstName, String secondName,
                          String lastName, String birthDay, String groupNumber) {

        this.uniqId =  new SimpleStringProperty(uniqId);
        this.firstName = new SimpleStringProperty(firstName);
        this.secondName = new SimpleStringProperty(secondName);
        this.lastName = new SimpleStringProperty(lastName);
        this.birthDay = new SimpleStringProperty(birthDay);
        this.groupNumber = new SimpleStringProperty(groupNumber);
    }

    public String getUniqId() {
        return uniqId.get();
    }

    public StringProperty uniqIdProperty() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId.set(uniqId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSecondName() {
        return secondName.get();
    }

    public StringProperty secondNameProperty() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getBirthDay() {
        return birthDay.get();
    }

    public StringProperty birthDayProperty() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay.set(birthDay);
    }

    public String getGroupNumber() {
        return groupNumber.get();
    }

    public StringProperty groupNumberProperty() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber.set(groupNumber);
    }
}
