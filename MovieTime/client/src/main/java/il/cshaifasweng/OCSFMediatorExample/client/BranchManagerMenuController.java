package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class BranchManagerMenuController {

    @FXML
    private AnchorPane menuContainer;

    @FXML
    private Button cinemaMoviesMenuBtn;

    @FXML
    private Button reportsMenuBtn;

    @FXML
    private Button logoutMenuBtn;
    
    @FXML
    private Button PriceRequestBtn;

    @FXML
    void PriceRequest(ActionEvent event) throws IOException {
    	App.setWindowTitle(PageTitles.PriceChangePage);
    	App.setContent("Price Change Page");
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	App.logout(true);
    	App.setWindowTitle(PageTitles.MainPage);
    	App.setBarAndGridLayout("MainPage");
    	App.setMenu("SystemMenu");
    }
    
    @FXML
    void reports(ActionEvent event) throws IOException {
    	App.setWindowTitle(PageTitles.ReportsPage);
    	App.setContent("ReportsPage");
    }
    
    
    

}
