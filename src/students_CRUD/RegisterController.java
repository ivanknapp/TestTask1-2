package students_CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class RegisterController implements Initializable{

    private static final String TABLE_NAME = "students";

    private static final String INSERT_INTO ="INSERT INTO students VALUES(?,?,?,?,?,?)";
    private static final String DELETE ="DELETE FROM students WHERE id = ? ";
    private final static String GET_ALL = "SELECT * FROM students";

    private Connection connection = MySQLConnection.getInstance().getConnection();

    //заготовки
    @FXML
    private Label isConnected, firstNameLabel,secondNameLabel,
            lastNameLabel, birthDayLabel, groupNumberLabel, uniqIdLabel;
    @FXML
    private TextField firstName;
    @FXML
    private TextField secondName,lastName, birthDay,
    groupNumber, uniqId;
    @FXML
    private TextField removeId;
    @FXML
    private Label removeLabel;
    @FXML
    private TableView<StudentDetails> tableStudent;
    @FXML
    private TableColumn<StudentDetails, String> firstNameColumn;
    @FXML
    private TableColumn<StudentDetails, String> secondNameColumn;
    @FXML
    private TableColumn<StudentDetails, String> lastNameColumn;
    @FXML
    private TableColumn<StudentDetails, String> birthdayColumn;
    @FXML
    private TableColumn<StudentDetails, String> groupNumberColumn;
    @FXML
    private TableColumn<StudentDetails, String> idColumn;

    private ObservableList<StudentDetails> data;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(MySQLConnection.getInstance().isDbConnected()){
            isConnected.setText("Connected to Db");
            isConnected.setTextFill(Color.GREEN);
        }else {
            isConnected.setText("Not Connected to Db");
            isConnected.setTextFill(Color.RED);
        }
    }
    //add new row to db
    public void register(){
        try
            (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO))
        {
            preparedStatement.setInt(1,Integer.parseInt(uniqId.getText()));
            preparedStatement.setString(2,firstName.getText());
            preparedStatement.setString(3,secondName.getText());
            preparedStatement.setString(4,lastName.getText());
            //date format to sqlDate
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDay.getText());
            Date sqlDate = new Date(utilDate.getTime());

            preparedStatement.setDate(5,sqlDate);
            preparedStatement.setInt(6,Integer.parseInt(groupNumber.getText()));

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void registerStudent(ActionEvent event){
        register();
        loadData();
    }
    //delete row from table
    public void delete(){
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
        ){
            preparedStatement.setInt(1,Integer.parseInt(removeId.getText()));
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                removeLabel.setText("Successfully removed the student");
                removeLabel.setTextFill(Color.GREEN);
            }else {
                removeLabel.setText("Failed to remove the student");
                removeLabel.setTextFill(Color.RED);

            }
        }catch (SQLException e){

        }

    }

    public void deleteStudent(ActionEvent event){
        delete();
        loadData();
    }
    //load and show data from table
    private void loadData(){
        try{
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

            tableStudent.setItems(null);
            tableStudent.setItems(data);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void loadDataFromDatabase(ActionEvent event){
        loadData();
    }
}
