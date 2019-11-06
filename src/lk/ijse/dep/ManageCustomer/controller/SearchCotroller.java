package lk.ijse.dep.ManageCustomer.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.ManageCustomer.TM.CustomerTM;
import lk.ijse.dep.ManageCustomer.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchCotroller implements Initializable {
    public AnchorPane root;
    public TableView<CustomerTM> tblCustoemers;
    public JFXTextField txtSearch;
    public Button btnHome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblCustoemers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustoemers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustoemers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        List<CustomerTM> customerTMS = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()){
                customerTMS.add(new CustomerTM(rst.getString(1),
                        rst.getString(2),
                        rst.getString(3)));

            }

            ObservableList<CustomerTM> customerOBList = FXCollections.observableList(customerTMS);
            tblCustoemers.setItems(customerOBList);

        } catch (Exception e) {
            e.printStackTrace();
        }


        ObservableList<CustomerTM> tblArray = tblCustoemers.getItems();
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if (newValue!=null){
                   tblArray.clear();

                   Connection connection = DBConnection.getInstance().getConnection();
                   try {
                       PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id LIKE ? OR name LIKE ? ");
                       pstm.setObject(1,newValue+"%");
                       pstm.setObject(2,newValue+"%");
                       ResultSet rst = pstm.executeQuery();

                       while (rst.next()){
                           tblArray.add(new CustomerTM(rst.getString(1),
                                   rst.getString(2),
                                   rst.getString(3)));
                       }



                   } catch (SQLException e) {
                       e.printStackTrace();
                   }


               }
            }
        });
    }

    public void btnHome_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/ManageCustomer/view/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
}
