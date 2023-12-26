package com.example.maxledlightingproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.security.auth.callback.LanguageCallback;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    private  int[] B;
    static LinkedHashSet<Integer> autoGenerate;
    private Set<Integer> uniqueIntegers = new HashSet<>();
    @FXML
    private Button fileBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Button autoBtn;
    @FXML
    private Button inputBtn;
    @FXML
    private TextField numberTf;
    @FXML
    private TextField textField;
    @FXML
    private Button addBtn;
    @FXML
    private Button submitInputBtn;
    @FXML
    private Label resultLbl;
    private static int numberOfLeds;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberTf.setVisible(false);
        submitBtn.setVisible(false);
        textField.setVisible(false);
        addBtn.setVisible(false);
        submitInputBtn.setVisible(false);

        numberTf.setStyle("-fx-text-fill: red;");
        fileBtn.setOnMouseEntered(e -> {

            fileBtn.setStyle("-fx-background-color: #3d3b3b;"); // Remove effect on hover
            fileBtn.setStyle("-fx-background-radius: 15;");
            fileBtn.setTextFill(Color.BLACK);
        });

        fileBtn.setOnMouseExited(e -> {
            // Restore button width on exit
            // file_btn.setPrefWidth(214);
            fileBtn.setStyle("-fx-background-color: #464444;");
            fileBtn.setTextFill(Color.WHITE);
        });
        autoBtn.setOnMouseEntered(e -> {

            autoBtn.setStyle("-fx-background-color: #3d3b3b;"); // Remove effect on hover
            autoBtn.setStyle("-fx-background-radius: 15;");
            autoBtn.setTextFill(Color.BLACK);
        });

        autoBtn.setOnMouseExited(e -> {
            // Restore button width on exit
            // file_btn.setPrefWidth(214);
            autoBtn.setStyle("-fx-background-radius: 15;");
            autoBtn.setStyle("-fx-background-color: #464444;");
            autoBtn.setTextFill(Color.WHITE);
        });
        inputBtn.setOnMouseEntered(e -> {


            inputBtn.setStyle("-fx-background-color: #464444;");
            inputBtn.setTextFill(Color.BLACK);
            inputBtn.setStyle("-fx-background-radius: 15;");

        });

        inputBtn.setOnMouseExited(e -> {
            // Restore button width on exit
            // file_btn.setPrefWidth(214);
            inputBtn.setStyle("-fx-background-radius: 15;");
            inputBtn.setStyle("-fx-background-color: #3d3b3b;"); // Remove effect on hover
            inputBtn.setTextFill(Color.WHITE);
        });
        fileBtn.setOnAction(e -> {
            openFileChooser();

        });
        autoBtn.setOnAction(e -> {
            numberTf.setVisible(true);
            submitBtn.setVisible(true);
            autoBtnClicked();
        });
        inputBtn.setOnAction(e -> {
            submitInputBtn.setVisible(true);
            numberTf.setVisible(true);
            submitBtn.setVisible(true);
            textField.setVisible(true);
            addBtn.setVisible(true);
            inputBtnClicked();
        });

    }
    public boolean isNumeric(String str) {
        try {
            // Attempt to parse the string as a number
            Double.parseDouble(str);
            return true;  // If successful, it's a valid number
        } catch (NumberFormatException e) {
            return false; // If an exception is caught, it's not a valid number
        }
    }

    @FXML
    private void inputBtnClicked() {
        textField.clear();
        numberTf.clear();
        textField.setEditable(true);


        submitBtn.setOnAction(e -> {

            if (numberTf.getText()!=null && isNumeric(numberTf.getText())) {
                numberOfLeds = Integer.parseInt(numberTf.getText());
                uniqueIntegers = new LinkedHashSet<>();
                B = new int[numberOfLeds];
                for (int i = 0; i < numberOfLeds; i++) {
                    B[i] = i + 1;
                }
                submitBtn.setVisible(false);
                numberTf.setVisible(false);
                resultLbl.setText("expected "+numberTf.getText()+" leds");




            }
            else{
                resultLbl.setText("Numeric Value only");
            }
        });

        addBtn.setOnAction(ed -> {
            if (uniqueIntegers.size()>=numberOfLeds){
                resultLbl.setText("Numeric Values exceeded " + numberOfLeds);
                textField.clear();
                textField.setVisible(false);


                numberTf.setVisible(false);
                addBtn.setVisible(false);
            }
            else {
                if (textField.getText() != null && isNumeric(textField.getText()) && Integer.parseInt(textField.getText()) <= numberOfLeds) {
                    int userInput = Integer.parseInt(textField.getText());
                    if (uniqueIntegers.contains(userInput)) {
                        resultLbl.setText("Numeric Value Dublicated");
                        textField.clear();

                    } else {
                        uniqueIntegers.add(userInput);
                        textField.clear();
                    }

                } else {
                    resultLbl.setText("Numeric Value only and less than " + numberOfLeds);
                }
            }

        });
            submitInputBtn.setOnAction(ex -> {
                System.out.println(uniqueIntegers.size());

                if(uniqueIntegers.size()!=numberOfLeds){
                    resultLbl.setText("Not Enough Leds ");
                    textField.setEditable(false);
                    submitInputBtn.setVisible(false);
                    uniqueIntegers.clear();
                }
                else{
                    GridPaneController controller = new GridPaneController();
                    controller.setAandB(uniqueIntegers.stream().mapToInt(Integer::intValue).toArray(), B);
                    PanelController panelC = new PanelController();
                    panelC.setAandB(uniqueIntegers.stream().mapToInt(Integer::intValue).toArray(), B);
                    FXMLLoader fxmlLoader2 = new FXMLLoader(GridPaneController.class.getResource("display-view.fxml"));

                    Scene scene2 = null;
                    try {
                        scene2 = new Scene(fxmlLoader2.load(), 1100, 800);
                    } catch (IOException exs) {
                        throw new RuntimeException(exs);
                    }
                    Stage stage2=new Stage();
                    stage2.setTitle("Hello!");
                    stage2.setScene(scene2);
                    stage2.show();
                    uniqueIntegers = null; // kill the object
                    B = null;
                    submitInputBtn.setVisible(false);
                    textField.clear();
                    textField.setVisible(false);
                    resultLbl.setText("");

                }



            });


    }


    @FXML
    private void autoBtnClicked() {
        submitBtn.setOnAction(e -> {
            if (numberTf.getText()!=null && isNumeric(numberTf.getText())) {
                int numberOfLeds = Integer.parseInt(numberTf.getText());
                autoGenerate = new LinkedHashSet<>();
                B = new int[numberOfLeds];
                for (int i = 0; i < numberOfLeds; i++) {
                    B[i] = i + 1;
                }
                autoGenerate(numberOfLeds);
                GridPaneController controller = new GridPaneController();
                controller.setAandB(autoGenerate.stream().mapToInt(Integer::intValue).toArray(), B);
                PanelController panelC = new PanelController();
                panelC.setAandB(autoGenerate.stream().mapToInt(Integer::intValue).toArray(), B);
                FXMLLoader fxmlLoader2 = new FXMLLoader(GridPaneController.class.getResource("display-view.fxml"));

                Scene scene2 = null;
                try {
                    scene2 = new Scene(fxmlLoader2.load(), 1100, 800);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Stage stage2=new Stage();
                stage2.setTitle("Hello!");
                stage2.setScene(scene2);
                stage2.show();
                autoGenerate = null; // kill the object
                B = null;
            }
            resultLbl.setText("Numeric value only");


        });



    }
    private static void autoGenerate(int numberOfLeds) {
        Random random = new Random();
        int randomInt;
        for (int i = 0; i < numberOfLeds; i++) {
            randomInt = random.nextInt(numberOfLeds) + 1;
            while (autoGenerate.contains(randomInt)) {
                randomInt = random.nextInt(numberOfLeds + 1);
            }
            autoGenerate.add(randomInt);
        }
        System.out.println("auto generation succeeded");

    }

    @FXML
    private void openFileChooser() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);


        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                // Read the first line (number of integers)
                String line = reader.readLine();
                int numberOfIntegers = Integer.parseInt(line.trim());

                // Read the second line (integers)
                line = reader.readLine();
                String[] integerStrings = line.split("\\s+");

                if (integerStrings.length != numberOfIntegers) {
                    System.out.println("Error: The number of integers specified does not match the actual count.");
                    return;
                }

                // Parse and check for duplicates
                Set<Integer> integerSet = new HashSet<>();
                int[] integers = new int[numberOfIntegers];

                for (int i = 0; i < numberOfIntegers; i++) {
                    int num = Integer.parseInt(integerStrings[i].trim());

                    if (integerSet.contains(num)) {
                        System.out.println("Error: Duplicate integer found - " + num);
                        return;
                    }

                    integerSet.add(num);
                    integers[i] = num;
                }
                B = new int[numberOfIntegers];
                for (int i = 0; i < numberOfIntegers; i++) {
                    B[i] = i + 1;
                }


                // Now, 'integers' array contains the validated integers
                System.out.println("Validated integers: " + Arrays.toString(integers));
                GridPaneController controller = new GridPaneController();
                PanelController panelC = new PanelController();
                panelC.setAandB(integers, B);
                controller.setAandB(integers, B);
                FXMLLoader fxmlLoader2 = new FXMLLoader(GridPaneController.class.getResource("display-view.fxml"));

                Scene scene2 = null;
                try {
                    scene2 = new Scene(fxmlLoader2.load(), 1100, 800);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage2=new Stage();
                stage2.setTitle("Hello!");
                stage2.setScene(scene2);
                stage2.show();


                integers = null; // kill the object .
              //  B = null;


            } catch (IOException | NumberFormatException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }

        }
    }
}