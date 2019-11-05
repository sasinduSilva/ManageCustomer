package lk.ijse.dep.ManageCustomer.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep.ManageCustomer.db.DBConnection;
import lk.ijse.dep.ManageCustomer.util.CustomerTM;

import java.sql.*;

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


    private void loadAllCustomers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

        ObservableList<CustomerTM> customers = tblCustome.getItems();
        customers.clear();

        while (rst.next()) {
            customers.add(new CustomerTM(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)));
        }
    }
}
