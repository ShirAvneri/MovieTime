package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ComingSoonInfoPageController {
	private int purchaseType;

    @FXML
    private ImageView movieLargeImageSrc;

    @FXML
    private ImageView movieImageSrc;

    @FXML
    private Label movieName;

    @FXML
    private Label movieGenre;

    @FXML
    private Label movieDescription;

    @FXML
    private Label movieDuration;

    @FXML
    private Label movieLaunchDate;

    @FXML
    private Label movieMainActors;

    @FXML
    private Label movieNameSecond;

    @FXML
    private Label movieGenreSecond;
    
    void setComingSoonInfo(Movie movie) {
    	movieDescription.setWrapText(true);
    	movieName.setText(movie.getName());
    	movieDescription.setText(movie.getDescription());
    	movieDuration.setText(movie.getDuration());
    	movieGenre.setText(movie.getGenre());
    	movieLaunchDate.setText(movie.getLaunchDate().toString());
    	movieMainActors.setText(movie.getMainActors());
    	movieNameSecond.setText(movie.getName());
    	movieGenreSecond.setText(movie.getGenre());
    	movieImageSrc.setImage(movie.getImage());
    	movieLargeImageSrc.setImage(movie.getLargeImage());
    }
    
    public void setPurchaseType(int type) {
    	this.purchaseType = type;
    }
    
    public int getPurchaseType() {
    	return this.purchaseType;
    }

}
