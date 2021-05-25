package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.ResourceBundle;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;

public class MainPageController implements Initializable {
	int NUM_ROWS = 2, NUM_COLS = 3, currentlyDisplayedFrom = 0, moviesNumber = 0;
    
	@FXML
	private Button btn_update_movie_time;
	
    @FXML
    private TextField mainSearchBar;

    @FXML
    private GridPane movieContainer;

    @FXML
    private VBox cell1;

    @FXML
    private VBox cell2;

    @FXML
    private VBox cell3;

    @FXML
    private VBox cell4;

    @FXML
    private VBox cell5;

    @FXML
    private VBox cell6;

    @FXML
    private ComboBox<String> cb_genere;

    @FXML
    private ComboBox<String> cb_theatre;

    @FXML
    private ComboBox<String> cb_type;
    
    @FXML
    private Button loadMoreBtn;

	private List<Movie> recentlyAdded;
	
	public MainPageController() {
		currentlyDisplayedFrom = 0;
		moviesNumber = 0;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initializing main page");
		EventBus.getDefault().register(this);
		Message msg = new Message();
		msg.setAction("pull screening movies");
		try {
			System.out.println("trying to send msg to server");
			AppClient.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("failed to send msg to server from recentlyAdded");
			e.printStackTrace();
		}
		
	}
	
	public void InitializeCB() {
		cb_genere.getItems().clear();
		cb_type.getItems().clear();
		cb_theatre.getItems().clear();
	}

	public void SetMovies(int displayFrom) {

    	int index;
		if(moviesNumber < NUM_ROWS * NUM_COLS) 
			index = 0;
		else
			index = displayFrom;
		try {
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
					if(index > moviesNumber - 1) {
						if(moviesNumber < NUM_ROWS * NUM_COLS) 
							return;
						index -= moviesNumber;
					}

					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("card.fxml"));
					Button cardBox = fxmlLoader.load();
					CardController cardController = fxmlLoader.getController();
					cardController.SetData(recentlyAdded.get(index), "ticket");
					movieContainer.add(cardBox, j, i);
					index++;
               }
            }
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

    @FXML
    void loadMoreMovies() {
    	if(moviesNumber < NUM_ROWS * NUM_COLS)
			return;
    	
    	int nextIndex = currentlyDisplayedFrom + NUM_ROWS * NUM_COLS;
    	if(nextIndex > moviesNumber - 1)
    		currentlyDisplayedFrom = nextIndex - moviesNumber;
    	else
    		currentlyDisplayedFrom = nextIndex;
    	SetMovies(currentlyDisplayedFrom);
    }

	@Subscribe
	public void onMessageEvent(Message msg) {
		System.out.println("reveived message!!");
		System.out.println(msg.getAction());
    	if(msg.getAction().equals("got screening movies")) {
    		Platform.runLater(()-> {
    			recentlyAdded = msg.getMovies();
    			moviesNumber = recentlyAdded.size();
    			currentlyDisplayedFrom = 0;
    			SetMovies(currentlyDisplayedFrom);
    		});
    	}
	}


	@FXML
	private void sendData(ActionEvent event)
	{
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("UpdateMoviesPage.fxml"));
		try {
			Loader.load();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		EventBus.getDefault().unregister(this);

		Stage stage = (Stage) btn_update_movie_time.getScene().getWindow();
		Parent p =Loader.getRoot();
		stage.setScene(new Scene(p));
		
		stage.show();

	}
}
/*
 * @FXML void GoToUpdateMovieTime(ActionEvent event) {
 * //calculatorTF.appendText(((Button)event.getSource()).getText());
 * 
 * }
 */

