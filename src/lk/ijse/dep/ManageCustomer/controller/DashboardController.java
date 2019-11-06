package lk.ijse.dep.ManageCustomer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    public AnchorPane root;
    public Button btnAdd;
    public Button btnUpdt;
    public Button btnDlte;
    public Button btnSrch;

    public void btnAdd_OnAction(ActionEvent actionEvent) {
    }

    public void btnUpdt_OnAction(ActionEvent actionEvent) {
    }

    public void btnDlte_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/ManageCustomer/view/DeleteCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage stage1 = (Stage) this.root.getScene().getWindow();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.show();
    }

    public void btnSrch_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/ManageCustomer/view/SearchForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage1 = (Stage) this.root.getScene().getWindow();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.show();
    }
}
