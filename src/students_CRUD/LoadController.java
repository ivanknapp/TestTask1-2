package students_CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class LoadController implements Initializable {

    private final static String GET_ALL = "SELECT * FROM students";

    @FXML
    private TableView<StudentDetails> tableStudent;
    @FXML
    private TableColumn<StudentDetails, String> firstNameColumn;
    @FXML
    private TableColumn<StudentDetails, String> secondNameColumn;
    @FXML
    private TableColumn<StudentDetails, String> lastNameColumn;
    @FXML
    private TableColumn<StudentDetails, Date> birthdayColumn;
    @FXML
    private TableColumn<StudentDetails, Integer> groupNumberColumn;
    @FXML
    private TableColumn<StudentDetails, Integer> idColumn;
    @FXML
    private Button loadButton;

    private ObservableList<StudentDetails> data;

    private MySQLConnection mySQLConnection = MySQLConnection.getInstance();





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadDataFromDatabase(ActionEvent event){
        try(
            Connection connection = mySQLConnection.getConnection();
        ){
            data = FXCollections.observableArrayList();
            ResultSet res = connection.createStatement().executeQuery(GET_ALL);

            while (res.next()){
                String id = String.valueOf(res.getInt("id"));
                String firstName = res.getString("firstname");
                String secondName = res.getString("secondname");
                String lastName = res.getString("lastname");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date sqlDate = res.getDate("birthday");
                String birthday = sdf.format(sqlDate);

                String groupNumber = String.valueOf(res.getInt("group_number"));

                StudentDetails student = new StudentDetails(id, firstName, secondName,
                        lastName, birthday, groupNumber);
                data.add(student);
            }

            idColumn.setCellValueFactory(new PropertyValueFactory<>("uniqId"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            secondNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
            groupNumberColumn.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));

            tableStudent.setItems(data);


        }catch (SQLException e){

        }
    }
}
