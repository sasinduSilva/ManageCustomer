package lk.ijse.dep.ManageCustomer.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep.ManageCustomer.TM.CustomerTM;
import lk.ijse.dep.ManageCustomer.db.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteController implements Initializable {
    public AnchorPane root;
    public TableView<CustomerTM> tblCstmrs;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public Button btnDelete;
    public Button btnCncl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblCstmrs.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCstmrs.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCstmrs.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));


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
            tblCstmrs.setItems(customerOBList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        tblCstmrs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                CustomerTM selectedItem = tblCstmrs.getSelectionModel().getSelectedItem();
                if (selectedItem!=null){
                    txtId.setText(selectedItem.getId());
                    txtName.setText(selectedItem.getName());
                    txtAddress.setText(selectedItem.getAddress());


                }
            }
        });


    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        CustomerTM selectedItem = tblCstmrs.getSelectionModel().getSelectedItem();
        if (selectedItem!=null){
            String id = selectedItem.getId();
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id='"+id+"'");
                int count = pstm.executeUpdate();
                if (count>0){
                    new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
                    tblCstmrs.getItems().remove(selectedItem);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }

    public void btnCncl_OnAction(ActionEvent actionEvent) {
    }


}
