package lk.ijse.dep.ManageCustomer.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep.ManageCustomer.db.DBConnection;
import lk.ijse.dep.ManageCustomer.util.CustomerTM;

import java.sql.*;

public class UpdateCustomerController {
    public AnchorPane window1;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TableView <CustomerTM>tblCustome;
    public TableColumn clmId;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public Button btnUpdate;

    public void initialize() {

        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        tblCustome.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            {
                CustomerTM selectedItem = tblCustome.getSelectionModel().getSelectedItem();

                txtId.setText(selectedItem.getId());
                txtName.setText(selectedItem.getName());
                txtAddress.setText(selectedItem.getAddress());
            }
        });




        try {
            loadAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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
    public void updateCustomer(CustomerTM customer) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?)");
        pstm.setString(1, customer.getName());
        pstm.setString(2, customer.getAddress());
        if (pstm.executeUpdate() == 0){
            throw new RuntimeException("Something went wrong");
        }
    }
}
