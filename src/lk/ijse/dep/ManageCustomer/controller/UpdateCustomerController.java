package lk.ijse.dep.ManageCustomer.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep.ManageCustomer.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCustomerController {
    public AnchorPane window1;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TableView tblCustome;
    public TableColumn clmId;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public Button btnUpdate;




    public void updateOnAction(ActionEvent actionEvent) {
    }


    public String getLastCustomerId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT Id FROM Customer ORDER BY Id DESC LIMIT 1");
        ResultSet rst = pstm.executeQuery();
        if (rst.next()){
            return rst.getString(1);
        }else{
            return null;
        }
    }
}
