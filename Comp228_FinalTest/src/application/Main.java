package application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;



public class Main extends Application {
	
	private Connection connection;
	PreparedStatement preparedStatement;
	private TextField tfStudentid = new TextField();
	private TextField tfName = new TextField();
	private TextField tfTuitionFee = new TextField();
	private TextField tfDiscountFee = new TextField();
	private Label lblStatus = new Label();


	@Override
	public void start(Stage primaryStage) {
		
		//initialize database connection and create a statement object
				initializeDB();
				// Display Record button
				Button btnDisplay = new Button("Display Record");
				//Reset button
				Button btReset = new Button("Reset");
				//Delete button
				Button btDelete = new Button("Delete");
				//Update button
				Button btUpdate = new Button("Update");
				//Quit button
				Button btQuit = new Button("Quit");
				
		        GridPane gridPane = new GridPane();
		        gridPane.setHgap(5);
		        gridPane.setVgap(5);
		        gridPane.add(new Label("Student ID"), 0, 0);
		        gridPane.add(tfStudentid, 1, 0);
		        gridPane.add(new Label("Student Name"), 0, 1);
		        gridPane.add(tfName, 1, 1);
		        gridPane.add(new Label("Student's Tuition Fee"),0,2);
		        gridPane.add(tfTuitionFee, 1, 2);
		        gridPane.add(new Label("Student's Discounted fee"), 0, 3);
		        gridPane.add(tfDiscountFee, 1, 3);
		        gridPane.add(btnDisplay, 0, 4);
		        gridPane.add(btReset, 0, 5);
		        gridPane.add(btDelete, 1, 4);
		        gridPane.add(btUpdate, 1, 5);
		        gridPane.add(btQuit, 1, 6);
		        gridPane.add(lblStatus, 0, 7);

		        gridPane.setAlignment(Pos.CENTER);
		        tfStudentid.setAlignment(Pos.BOTTOM_RIGHT);
		        tfName.setAlignment(Pos.BOTTOM_RIGHT);
		        tfTuitionFee.setAlignment(Pos.BOTTOM_RIGHT);
		        tfDiscountFee.setAlignment(Pos.BOTTOM_RIGHT);
		        GridPane.setHalignment(btnDisplay, HPos.RIGHT);
		        GridPane.setHalignment(btReset, HPos.RIGHT);
		        GridPane.setHalignment(btDelete, HPos.RIGHT);
		        GridPane.setHalignment(btUpdate, HPos.RIGHT);
		        GridPane.setHalignment(btQuit, HPos.RIGHT);
		        
		        //Button events
		        btnDisplay.setOnAction(e -> {
		        	btnDisplayFunc();
		        });
		        btReset.setOnAction(e -> {
		        	resetFunc();           
						        });
		        btDelete.setOnAction(e -> {
		            deleteFunc();
		        });
		        btUpdate.setOnAction(e -> {
		        	updateRecord();        
						        });
		        btQuit.setOnAction(e -> {
		            quitFunc();
		        });
		        
		        
		        Scene scene = new Scene(gridPane,400,300);
		        primaryStage.setTitle("Student Information System");
		        primaryStage.setScene(scene);
		        primaryStage.show();

	}
	
	//Method for initializing database
    private void initializeDB()
	{
		try
		{
			//load JDBC driver
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Driver loaded....");			
			//establish a connection
			connection = DriverManager.getConnection("jdbc:oracle:thin:@199.212.26.208:1521:SQLD","COMP228_W21_sy_76", "password");
			if(connection != null)
			{
			System.out.println("Database connected.....");
			}
		}
		catch(Exception ex)
		{			
			ex.printStackTrace();			
		}				
	}
    private void btnDisplayFunc() {
	   	 
    	String StudentId = tfStudentid.getText();
    	String query = ("SELECT * FROM Student WHERE stID = '" + StudentId + "'");
    	try
    	{    		
    		Statement statement = connection.createStatement();
    		ResultSet rset = statement.executeQuery(query);   
    		if (rset.next())
    		{   			
    	         String stuName = rset.getString(2);
    			 Double stuTuitionFee = rset.getDouble(3);
    			tfName.setText(stuName); 
    			tfName.setEditable(false);
    			tfTuitionFee.setText(String.valueOf(stuTuitionFee));
    			tfTuitionFee.setEditable(false);
    			double discountFee=(stuTuitionFee-(stuTuitionFee*0.3));
    			tfDiscountFee.setText(String.valueOf(discountFee));
    			tfDiscountFee.setEditable(false);   			
    		}
			else
			{
				lblStatus.setText("Invalid Student ID");

			}	
    	}		
    	catch(SQLException ex)
    	{
    		ex.printStackTrace();
    	}		  

    }
    
    private void resetFunc() {
    
    	tfStudentid.setText("");
    	tfName.setText("");
    	tfTuitionFee.setText("");
    	tfDiscountFee.setText("");
    	lblStatus.setText("");
    	tfStudentid.setEditable(true);
    	tfName.setEditable(true);
    	tfTuitionFee.setEditable(true);
    	

    }
    
    private void deleteFunc() {
    	
    	
    	String stuId = tfStudentid.getText();
    	try
    	{  
    		String stringQuery = "DELETE FROM Student WHERE stId= ?";
    		preparedStatement = connection.prepareStatement(stringQuery);
    		preparedStatement.setString(1, stuId);
            int result_set = preparedStatement.executeUpdate();                	
         if (result_set > 0)
         {
        	 tfStudentid.setEditable(false);
    	   	 tfName.setEditable(false);      	 
    	   	 tfTuitionFee.setEditable(false);       	        	 
    	   	 tfDiscountFee.setEditable(false);
        	 lblStatus.setText("Record Deleted Successfully!");
         }
         else
         {
        	 lblStatus.setText("Cannot Delete. Invalid Student ID");
         }
    	}
    	catch(SQLException ex)
    	{
    		ex.printStackTrace();
    	}

    }

    private void updateRecord()
    {
	   	 
    	String stuID = tfStudentid.getText();
    	double tuitionFee = Double.parseDouble(tfTuitionFee.getText());
    	try
    	{
    		//load JDBC driver
    		Class.forName("oracle.jdbc.OracleDriver");
    		
    		//establish a connection
    		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@199.212.26.208:1521:SQLD","COMP228_W21_sy_76", "password");
    		
    		String updateQuery = "UPDATE Student SET stFees= "+tuitionFee+" where stID ="+stuID+" ";    		
    		preparedStatement = connection.prepareStatement(updateQuery);
    		
    		int rowsUpdated = preparedStatement.executeUpdate();
    		if (rowsUpdated > 0) 
    		{
    			 tfStudentid.setEditable(false);
    		   	 tfName.setEditable(false);      	 
    		   	 tfTuitionFee.setEditable(false);       	        	 
    		   	 tfDiscountFee.setEditable(false);
    			lblStatus.setText("Updated successfully!");
    		}
    		else
    		{
    			lblStatus.setText("Cannot update. Invalid Student ID");
    		}
    	}
    	catch(Exception ex)
    	{
    		
    		ex.printStackTrace();
    		
    	}		
    }
    
    private void quitFunc() {
    	try
		{
    		connection.close();
    		System.out.println("Connection close");
    		//pane.dispose();
    		String message = "Thank You! Come Back Again";
    	        JOptionPane.showMessageDialog(null, message);
    	        Platform.exit();
    		    
		}
		catch(Exception ex)
		{			
			ex.printStackTrace();			
		}		 

    }




	public static void main(String[] args) {
		launch(args);
	}
}
