package com.example.maxledlightingproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PanelController implements Initializable {
    @FXML
    HBox left_hbox;
    @FXML
    HBox right_hbox;
    @FXML
    Pane pane;


    @FXML
    AnchorPane anchor;
    @FXML
    private ScrollPane scrollPane;
    private static int[] A;
    private static int[] B;
    private static String lightedLeds;




    public void setAandB(int[] a,int[] b) {
        A=new int[a.length];
        B=new int[b.length];
        for(int i=0;i<A.length;i++){
            B[i]=i+1;
            A[i]=a[i];
        }
    }
    public void setLightedLeds(String leds){
        this.lightedLeds=leds;
    }

        public void setImages() {
            Image lighted = new Image("file:C:/Users/Lenovo/IdeaProjects/maxLedsLighting/src/main/resources/com/example/maxledslighting/lighted.png");
            Image unlighted = new Image("file:C:/Users/Lenovo/IdeaProjects/maxLedsLighting/src/main/resources/com/example/maxledslighting/unlighted.png");
            Image power = new Image("file:C:/Users/Lenovo/IdeaProjects/maxLedsLighting/src/main/resources/com/example/maxledslighting/power.png");
            Font smallFont = new Font(5);
            List<Node> leftNodes = new ArrayList<>();
            List<Node> rightNodes = new ArrayList<>();

            for (int i = 0; i < A.length; i++) {
                VBox vbox = new VBox();
                vbox.setPrefWidth(40);
                vbox.setPrefHeight(40);

                Label label = new Label();
                label.setPrefWidth(10);
                label.setPrefHeight(10);
                Label label2 = new Label();
                label2.setPrefHeight(10);
                label2.setPrefWidth(10);
                label.setFont(smallFont);
                label2.setFont(smallFont);

                label.setTextFill(Color.WHITE);
                label2.setTextFill(Color.WHITE);
                ImageView iv = new ImageView();
                iv.setFitWidth(30);
                iv.setFitHeight(30);

                if (lightedLeds.contains("Led : " + A[i] + ".")) {
                    iv.setImage(lighted);
                } else {
                    iv.setImage(unlighted);
                }

                label.setText(String.valueOf(A[i]));
                vbox.getChildren().addAll(label, iv);
                leftNodes.add(vbox);

                ImageView powerImageView = new ImageView();
                powerImageView.setFitWidth(30);
                powerImageView.setFitHeight(30);
                powerImageView.setImage(power);

                Label powerLabel = new Label();
                powerLabel.setPrefHeight(10);
                powerLabel.setPrefWidth(10);

                powerLabel.setFont(smallFont);
                powerLabel.setTextFill(Color.WHITE);
                powerLabel.setText(String.valueOf(B[i]));

                VBox powerVBox = new VBox();
                powerVBox.setPrefWidth(40);
                powerVBox.setPrefHeight(40);
                powerVBox.getChildren().addAll(powerLabel, powerImageView);
                rightNodes.add(powerVBox);
            }

            // Add all leftNodes to left_hbox in a single update
            left_hbox.getChildren().addAll(leftNodes);

            // Add all rightNodes to right_hbox in a single update
            right_hbox.getChildren().addAll(rightNodes);
        }



    public void setWires(){
        Line line ;


        for (int i=0;i<A.length;i++){


            if (lightedLeds.contains("Led : "+A[i]+".")) {



                line=new Line();
                line.setStyle("-fx-stroke: orange;");

                line.setStartX(((i * 40) + 15));
                line.setStartY(40);
                line.setEndX((((A[i] - 1) * 40)+ 15));
                line.setEndY(200);
                pane.getChildren().add(line);


            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setVvalue(1.0);
        //if(A.length<=30){
        setImages();
        setWires();
        // else{
      //  drawCircles();
        //setWiresForCircles();
        //  }



    }
    public void drawCircles(){
        for (int i = 0; i < A.length; i++) {

            VBox vbox = new VBox();
            VBox vbox2 = new VBox();

            vbox2.setPrefWidth(15);
            vbox2.setPrefHeight(15);

            vbox.setPrefHeight(15);
            vbox.setPrefWidth(15);

            Font smallFont = new Font(5);

            // Set the Font for the Label


            Label label = new Label();
            label.setPrefWidth(10);
            label.setPrefHeight(10);
            Label label2 = new Label();
            label2.setPrefHeight(10);
            label2.setPrefWidth(10);
            label.setFont(smallFont);
            label2.setFont(smallFont);

            label.setTextFill(Color.WHITE);
            label2.setTextFill(Color.WHITE);

            Circle circle = new Circle(5);

            if (lightedLeds.contains("Led : " + A[i]+".")) {
                circle.setFill(Color.YELLOW);
                label.setText(String.valueOf(A[i]));


                vbox.getChildren().addAll(label, circle);

                left_hbox.getChildren().addAll(vbox);

            } else {

                circle.setFill(Color.WHITE);

                label.setText(String.valueOf(A[i]));
                vbox.getChildren().addAll(label, circle);

                left_hbox.getChildren().addAll(vbox);

            }
            Polygon triangle = new Polygon();

            // Set the coordinates of the vertices to create a triangle of size 10x10
            triangle.getPoints().addAll(
                    0.0, 0.0,         // Vertex 1
                    10.0, 0.0,        // Vertex 2
                    5.0, 10.0         // Vertex 3
            );

            // Set the fill color of the triangle
            triangle.setFill(Color.GREEN);


            label2.setText(String.valueOf(B[i]));
            vbox2.getChildren().addAll(label2, triangle);


            right_hbox.getChildren().addAll(vbox2);



        }
    }
    public void setWiresForCircles(){

        for (int i=0;i<A.length;i++){

            if (lightedLeds.contains("Led : "+A[i]+".")) {



                Line line = new Line();
                line.setStyle("-fx-stroke: orange;");
                line.setStrokeWidth(0.5);
                line.setStartX((((i) * 15)+5 ));
                line.setStartY(25);
                line.setEndX((((A[i] ) * 15) ));
                line.setEndY(200);
                anchor.getChildren().add(line);


            }

        }
    }
}
