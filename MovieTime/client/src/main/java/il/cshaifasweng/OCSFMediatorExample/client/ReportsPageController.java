package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

public class ReportsPageController {
	
	private
	List<Purchase> purchases;
	List<Cinema> cinemas;
	List<Complaint> complaints;

    @FXML
    private BarChart<?, ?> reportChart;

    @FXML
    private ComboBox<String> reportNameComboBox;

    @FXML
    private ComboBox<String> monthComboBox;
    
    
    @FXML
    void getReport() {
    	setData();
    	
    }
    
    
    
    @Subscribe
    public void OnMessageEvent(Message msg) {
    	EventBus.getDefault().unregister(this);
    	if(msg.getAction().equals("got cinemas and purchases and complaints")) {
    		Platform.runLater(()-> {
    			this.purchases = msg.getPurchasesList();
    			this.cinemas = msg.getCinemasArrayList();
    			this.complaints = msg.getComplaints();
    			//setData();
    		});
    	}
    	
    	
    	
    		    	
    }
    
    
    private void setData() {
    	
    	//ArrayList<Integer> count = new ArrayList<Integer>(cinemas.size());
    	
    	
    	if(reportNameComboBox.getValue().equals("Ticket Sales")){
	    	XYChart.Series set1 = new XYChart.Series<>();
	    	for(Cinema cinema : cinemas) {
	        	int count = 0;
		    	for(Purchase purchase : purchases) {
		    		if(purchase.getScreening().getCinema().getName().equals(cinema.getName())) {
			    		if(purchase.getPurchaseTime().getMonthValue() == Integer.parseInt(monthComboBox.getValue()) && purchase.isCard() == false && purchase.isLink() == false) {
							count++;
						}
		    		}
		    	}
		    	System.out.println(count);
		    	set1.getData().add(new XYChart.Data(cinema.getName(), count));
	    	}
	    	reportChart.getData().addAll(set1);
    	}
    	if(reportNameComboBox.getValue().equals("ViewingPackages and Subscription Card sales")){
	    	XYChart.Series set1 = new XYChart.Series<>();
	    	for(Cinema cinema : cinemas) {
	        	int count = 0;
		    	for(Purchase purchase : purchases) {
		    		if(purchase.getCinema().getName().equals(cinema.getName())) {
			    		if(purchase.getPurchaseTime().getDayOfMonth() == Integer.parseInt(monthComboBox.getValue()) && (purchase.isCard() == true || purchase.isLink() == true)) {
							count++;
						}
		    		}
		    	}
		    	System.out.println(count);
		    	set1.getData().add(new XYChart.Data(cinema.getName(), count));
	    	}
	    	reportChart.getData().addAll(set1);
    	}
    	if(reportNameComboBox.getValue().equals("Complaints by day")){
	    	XYChart.Series set1 = new XYChart.Series<>();
	    	for(Cinema cinema : cinemas) {
	        	int count = 0;
		    	for(Purchase purchase : purchases) {
		    		if(purchase.getCinema().getName().equals(cinema.getName())) {
			    		if(purchase.getPurchaseTime().getMonthValue() == Integer.parseInt(monthComboBox.getValue()) && purchase.isCard() == false && purchase.isLink() == false) {
							count++;
						}
		    		}
		    	}
		    	System.out.println(count);
		    	set1.getData().add(new XYChart.Data(cinema.getName(), count));
	    	}
	    	reportChart.getData().addAll(set1);
    	}
    	if(reportNameComboBox.getValue().equals("Tav Sagol Refunds")){
	    	XYChart.Series set1 = new XYChart.Series<>();
	    	YearMonth yearMonthObject = YearMonth.of(2021,Integer.parseInt(monthComboBox.getValue()) );
	    	int daysInMonth = yearMonthObject.lengthOfMonth(); //28 
	    	
	    	ArrayList<Integer> count;
	    	count = new ArrayList<Integer>(daysInMonth);
	    	for(int i = 1 ; i <= daysInMonth ; i++) {
	    		//count = new ArrayList<Integer>(daysInMonth);
	    		for(int j = 0 ; j < count.size() ; j++) {
	    			count.set(j, 0);
	    		}
		    	for(Cinema cinema : cinemas) {
		        	
			    	for(Complaint complaint : complaints) {
			    		if(complaint.getCinema().getName().equals(cinema.getName()))
			    		{
				    		if(complaint.getComplaintDate().getMonthValue() == Integer.parseInt(monthComboBox.getValue())) {
				    			count.set(complaint.getComplaintDate().getDayOfMonth(), (Integer)count.get((complaint.getComplaintDate().getDayOfMonth())) + 1 );
							}
			    		}
			    	}
		    	}
	    	}
	    	for(int i = 1 ; i <= daysInMonth ; i++) {
	    		set1.getData().add(new XYChart.Data(Integer.toString(i) + "." + monthComboBox.getValue(), count.get(i)));
	    	}
	    	reportChart.getData().addAll(set1);
    	}
    	
    	
    	
    	
    	
    	//set1.getData().add(new XYChart.Data("haifa", 1000));
    	//set1.getData().add(new XYChart.Data("tel-aviv", 500));
		
    	//reportChart.getData().addAll(set1);
    }
    
    @FXML
	public void initialize() {
    	EventBus.getDefault().register(this);
    	monthComboBox.getItems().clear();
    	reportNameComboBox.getItems().clear();
		
    	reportNameComboBox.getItems().addAll("Ticket Sales", "ViewingPackages and Subscription Card sales", "Tav Sagol Refunds", "Complaints by day");
    	
    	for(int i = 1 ; i < 13 ; i++) {
        	monthComboBox.getItems().add(Integer.toString(i));
        }
    	
    	
    	Message msg = new Message();
    	msg.setAction("get cinemas and purchases and complaints");
    	try {
			AppClient.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
		/*
		 * XYChart.Series set1 = new XYChart.Series<>(); set1.getData().add(new
		 * XYChart.Data("haifa", 1)); set1.getData().add(new XYChart.Data("haifa", 1));
		 * set1.getData().add(new XYChart.Data("haifa", 1)); set1.getData().add(new
		 * XYChart.Data("tel-aviv", 1)); reportChart.getData().addAll(set1);
		 */
	}
    
    
    
    
    
    
    

}
