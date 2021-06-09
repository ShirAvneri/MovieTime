package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;


public class BarAndGridLayoutController {
	CardContainerController gridController;
	
	@FXML
    private VBox barContainer;
	
	@FXML
    private VBox gridContainer;
	
	public BarAndGridLayoutController() {
		gridController = new CardContainerController();
	}

	public void setBarAndGrid(String namePage) throws IOException {
		FXMLLoader barFXMLLoader = new FXMLLoader();
		FXMLLoader gridFXMLLoader = new FXMLLoader();

		if(namePage.equals("MainPage")) {
			barFXMLLoader.setLocation(getClass().getResource("SearchBar.fxml"));
		}
		if(namePage.equals("ComingSoonPage")) {
			barFXMLLoader.setLocation(getClass().getResource("ComingSoonBar.fxml"));
		}
		if(namePage.equals("ViewingPackagesPage")) {
	    	barFXMLLoader.setLocation(getClass().getResource("SearchBar.fxml"));
		}

		if(namePage.equals("NetworkAdministratorMainPage")) {
	    	barFXMLLoader.setLocation(getClass().getResource("SearchBar.fxml"));

		}
		if(namePage.equals("BranchManagerMainPage")) {
	    	barFXMLLoader.setLocation(getClass().getResource("SearchBar.fxml"));
		}
		barContainer = barFXMLLoader.load();
		gridFXMLLoader.setLocation(getClass().getResource("CardContainer.fxml"));
		gridContainer = gridFXMLLoader.load();
		gridController = gridFXMLLoader.getController();
		gridController.setPurchaseType(PurchaseTypes.getType(namePage));
		gridController.setGridContent(namePage);
		CardContainerController ctrl = gridController;
		if(!namePage.equals("ComingSoonPage")) {
			SearchBarController Sctrl = barFXMLLoader.getController();
			Sctrl.SetPageType(namePage);
			Sctrl.setCardController(ctrl);
		}
		else {
			ComingSoonSearchBarController Sctrl = barFXMLLoader.getController();
			Sctrl.setCardController(ctrl);
		}
	}
	
	public VBox getTopBar() {
		return barContainer;
	}
	
	public VBox getCardContainer() {
		return gridContainer;
	}
	
	public CardContainerController getGridController() {
		return gridController;
	}
}
