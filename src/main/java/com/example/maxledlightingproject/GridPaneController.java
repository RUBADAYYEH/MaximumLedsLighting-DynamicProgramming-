package com.example.maxledlightingproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*
THIS CLAS INITIALIZES LCS TABLE AND THE GRID PANE
 */

public class GridPaneController implements Initializable {
    private int [][] result;

    //A is changing
    private static int[] A;

    //B is always ordered
    private static int[] B;
   private char[][] paths;
    private static  String resultLeds;

    @FXML
    Button exportButton;

    @FXML
    private GridPane dynamicGridPane;

    @FXML Label maxLabel;
    @FXML
    TextArea textArea;
    public void setAandB(int[] a,int[] b) {
        A=new int[a.length];
        B=new int[b.length];
        for(int i=0;i<A.length;i++){
            B[i]=i+1;
            A[i]=a[i];
        }
    }



    public  void calculateResult()   {

        result=new int[B.length+1][B.length+1];
        paths=new char[B.length+1][B.length+1];
        for (int i=0,j=0;i<A.length&&j<A.length;j++,i++){
            result[0][i]=0;
            result[j][0]=0;
            paths[0][i]='n';
            paths[j][0]='n';

        }
        for (int i=1;i<=A.length;i++){
            for (int j=1;j<=A.length;j++){
                if (A[i-1]==B[j-1]) {         //NOTICE THAT B IS ORDERED A IS NOT .
                    result[i][j]=result[i-1][j-1]+1;
                    paths[i][j]='c';
                }
                else if (result[i][j-1]>result[i-1][j]){

                    result[i][j]=result[i][j-1];
                    paths[i][j]='u';

                }
                else{
                    result[i][j]=result[i-1][j];
                    paths[i][j]='r';
                }
            }


        }
        //extra code to check the array in console
      /*  for (int i=0;i<A.length+1;i++){
            for (int j=0;j<A.length+1;j++){
                System.out.print(result[i][j]+" ,  ");
            }
            System.out.println();
        }*/
        String labelResult=("Maximum leds Lighting : "+result[result.length-1][result.length-1]);
        maxLabel.setText(labelResult);

        //DISPLAYING LEDS
        String[] reverseResult=printLamps(paths,A, result.length-1, result.length-1).split("\n");
        StringBuilder sb=new StringBuilder();

        for (int i=reverseResult.length-1;i>=0;i--){
            sb.append(reverseResult[i]+"\n");
        }
        resultLeds= sb.toString();
        PanelController pc=new PanelController();
        pc.setLightedLeds(resultLeds);

        System.out.println(resultLeds);
        textArea.setText(resultLeds);
        ////////////////////////////////////




        // System.out.println(textArea.getText());
        updateGridPane();
        //result=null; // kill the object

        //B=null;




    }

    public void updateGridPane() {

        // Clear existing content
        dynamicGridPane.getChildren().clear();
        dynamicGridPane.setMinSize(result.length/2,result.length/2);


        // dynamicGridPane.getChildren().clear(); // Clear existing content
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                Label label = new Label(String.valueOf(result[i][j]));
                label.setMinSize(50,50);
                label.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-alignment: CENTER;-fx-border-width: 0.5; -fx-border-color: black;");

                dynamicGridPane.add(label, j, i);

            }
        }


    }


    public  String printLamps(char[][]paths,int[] k,int h,int v){  //NOTICE K IS NOT ORDERED / ITS A
        StringBuilder sb = new StringBuilder();
        if (v == 0 || h == 0) {
            return "";
        } else {
            if (paths[h][v] =='c') {

                sb.append("Led : ").append(k[h - 1]).append(".");
                sb.append('\n');
                //   System.out.print(k[h - 1] + " ");
                return sb.append(printLamps(paths, k, h - 1, v - 1)).toString();
            } else {
                if (paths[h][v] == 'u') {
                    sb.append(printLamps(paths, k, h, v - 1)).toString();

                } else {
                    sb.append(printLamps(paths, k, h - 1, v)).toString();
                }
            }
        }

        // System.out.println(sb.toString());


        return sb.toString();


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calculateResult();
        exportButton.setOnAction(e->{
            FXMLLoader fxmlLoader2 = new FXMLLoader(GridPaneController.class.getResource("panel-view.fxml"));

            Scene scene2 = null;
            try {
                scene2 = new Scene(fxmlLoader2.load(), 1100, 700);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage stage2=new Stage();
            stage2.setTitle("Hello!");
            stage2.setScene(scene2);
            stage2.show();
        });


    }
}
