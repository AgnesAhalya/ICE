/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ice;

import javafx.application.Application;
import javafx.scene.image.Image ;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.stage.*;

/**
 *
 * @author Agnes
 */
public class ICE extends Application {
    String[] str=new String[5];
    @Override
    public void start(Stage primaryStage) {
        try{
        
       GridPane grid = new GridPane();
       grid.setAlignment(Pos.CENTER);
       grid.setHgap(10);
       grid.setVgap(10);
       FileChooser fileChooser = new FileChooser();
       Button btn1 = new Button("Select Org/Enc File");
       TextField tfoe=new TextField();
       btn1.setOnAction(e -> {
            str[0]=fileChooser.showOpenDialog(primaryStage).getAbsolutePath();
            tfoe.setText(str[0]);
       });
       Button btn2 = new Button("Select Cover Image File");
       TextField tfc=new TextField();
       btn2.setOnAction(e -> {
            str[1]=fileChooser.showOpenDialog(primaryStage).getAbsolutePath();
            tfc.setText(str[1]);
       });
       Button btn3= new Button("Select Public/Private key");
       TextField tfpp=new TextField();
       btn3.setOnAction(e -> {
            str[2]=fileChooser.showOpenDialog(primaryStage).getAbsolutePath();
            tfpp.setText(str[2]);
       });
       Label l1=new Label("Enter user key");
       TextField tf1=new TextField();
       Label l2=new Label("Dynamic key");
       TextField tf2=new TextField();
       
       Button btn4 = new Button();
       btn4.setText("Encrypt");
       btn4.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
              Encrypt obj=new Encrypt();
              str[3]=tf1.getText();
              str[4]=obj.encrypt(str);
              tf2.setText(str[4]);
              tfoe.clear();
              tfc.clear();
              tfpp.clear();
              tf1.clear();
            
            }
        });
        Button btn5 = new Button();
        btn5.setText("Decrypt");
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
             Decrypt obj=new Decrypt();
             str[3]=tf1.getText();
             str[4]=tf2.getText();
             System.out.println(obj.decrypt(str));
             tfoe.clear();
             tfc.clear();
             tfpp.clear();
             tf1.clear();
             tf2.clear();
            }
        });
       grid.add(btn1,0,1);
       grid.add(tfoe,1,1);
       grid.add(btn2,0,2);
       grid.add(tfc,1,2);
       grid.add(btn3,0,3);
       grid.add(tfpp,1,3);
       grid.add(l1,0,4);
       grid.add(tf1,1,4);
       grid.add(l2,0,5);
       grid.add(tf2,1,5);
       grid.add(btn4,0,6);
       grid.add(btn5,1,6);
       Scene scene = new Scene(grid, 700, 250);
        
        primaryStage.setTitle("ICE");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("ICE.png")));
        primaryStage.show();
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }
    
}
