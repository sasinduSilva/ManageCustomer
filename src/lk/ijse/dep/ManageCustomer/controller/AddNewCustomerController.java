package lk.ijse.dep.ManageCustomer.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep.ManageCustomer.db.DBConnection;
import lk.ijse.dep.ManageCustomer.util.CustomerTM;

import java.sql.*;

public class AddNewCustomerController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TableView <CustomerTM>tblCustome;
    public TableColumn clmId;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public Button btnNew;
    public AnchorPane window1;
    public Button btnSave;


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
        public void addNewOnAction(ActionEvent actionEvent) {
            txtId.clear();
            txtName.clear();
            txtAddress.clear();
        //    tblCustome.getSelectionModel().clearSelection();
            txtName.setDisable(false);
            txtAddress.setDisable(false);
            txtName.requestFocus();
            btnSave.setDisable(false);

            // Generate a new id
            int maxId = 0;

            try {
                String lastCustomerId = getLastCustomerId();

                if (lastCustomerId == null){
                    maxId = 0;
                }else{
                    maxId = Integer.parseInt(lastCustomerId.replace("C",""));
                }

                maxId = maxId + 1;
                String id = "";
                if (maxId < 10) {
                    id = "C00" + maxId;
                } else if (maxId < 100) {
                    id = "C0" + maxId;
                } else {
                    id = "C" + maxId;
                }
                txtId.setText(id);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    public void saveOnAction(ActionEvent actionEvent) {

        if (!txtName.getText().matches("[A-Za-z][A-Za-z. ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return;
        }
        if (btnSave.getText().equals("Save")) {
            ObservableList<CustomerTM> customers = tblCustome.getItems();
            CustomerTM newCustomer = new CustomerTM(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText()
            );
            try {
                saveCustomer(newCustomer);
                customers.add(newCustomer);
                addNewOnAction(actionEvent);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getLastCustomerId() throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT Id FROM Customer ORDER BY Id DESC LIMIT 1");
        ResultSet rst = pstm.executeQuery();
        if (rst.next()){
            return rst.getString(1);
        }else{
            return null;
        }
    }

    public void saveCustomer(CustomerTM customer) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?)");
        pstm.setString(1, customer.getId());
        pstm.setString(2, customer.getName());
        pstm.setString(3, customer.getAddress());
        if (pstm.executeUpdate() == 0){
            throw new RuntimeException("Something went wrong");
        }
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

