import javafx.animation.KeyFrame;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Date;

/* this class is named time because of some formating problem i was getting when trying to switch the name.
 it should be called SliderAppView.*/

public class Time extends Pane{
    public Time(){
        //String[] puzzles = {"Pets", "Scenery","Lego","Numbers"};
        String[] puzzles = {"Lego"};
        String[][] buttonsLabels = {
                {"Lego_00.png","Lego_01.png","Lego_02.png","Lego_03.png"}
                ,{"Lego_10.png","Lego_11.png", "Lego_12.png","Lego_13.png"}
                ,{"Lego_20.png","Lego_21.png", "Lego_22.png","Lego_23.png"},
                {"Lego_30.png","Lego_31.png","Lego_32.png","Lego_33.png"}};
        final String[][] solvedPuzzle = {
            {"Lego_00.png","Lego_01.png","Lego_02.png","Lego_03.png"}
                ,{"Lego_10.png","Lego_11.png", "Lego_12.png","Lego_13.png"}
                ,{"Lego_20.png","Lego_21.png", "Lego_22.png","Lego_23.png"},
            {"Lego_30.png","Lego_31.png","Lego_32.png","Lego_33.png"}};




        // ListVeiw containing puzzle names

        ListView<String> puzzleNames = new ListView<String>();
        puzzleNames.setItems(FXCollections.observableArrayList(puzzles));
        puzzleNames.relocate(771,207);
        puzzleNames.setPrefSize(187,130);


        // textfield showing time elapsed

        TextField timeElapsed = new TextField();
        timeElapsed.setText("0:00");
        timeElapsed.relocate(828,396);
        timeElapsed.setPrefSize(130,30);



        Label timeElapsedLabel = new Label();
        timeElapsedLabel.setText("Time:");
        timeElapsedLabel.relocate(771,405);





        // all buttons below,
        Button[][] buttons;
        buttons = new Button[4][4];
        for(int row=0; row<4; row++){
            for(int col=0; col<4; col++){
                buttons[row][col]=new Button();
                buttons[row][col].relocate(10+col*188,10+row*188);
                buttons[row][col].setPrefSize(187,187);
                buttons[row][col].setPadding(new Insets(0,0,0,0));
                buttons[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("BLANK.png"))));

                getChildren().add(buttons[row][col]);


            }
        }


        Label thumbnail = new Label();
        thumbnail.relocate(771,10);
        thumbnail.setPrefSize(187,187);
        thumbnail.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Lego_Thumbnail.png"))));



        // this event handler used to swithc thumbnail picture
        puzzleNames.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(puzzleNames.getSelectionModel().getSelectedItem().equals("Lego")){
                    thumbnail.setGraphic(new ImageView(new Image(getClass()
                            .getResourceAsStream("Lego_Thumbnail.png"))));


                }
                else if(puzzleNames.getSelectionModel().getSelectedItem().equals("Scenery")){
                    thumbnail.setGraphic(new ImageView(new Image(getClass()
                            .getResourceAsStream("Scenery_Thumbnail.png"))));

                }
                else if(puzzleNames.getSelectionModel().getSelectedItem().equals("Pets")){
                    thumbnail.setGraphic(new ImageView(new Image(getClass()
                            .getResourceAsStream("Pets_Thumbnail.png"))));

                }
                else if(puzzleNames.getSelectionModel().getSelectedItem().equals("Numbers")){
                    thumbnail.setGraphic(new ImageView(new Image(getClass()
                            .getResourceAsStream("Numbers_Thumbnail.png"))));

                }


            }
        });

        Button startStopButton =new Button();
        startStopButton.setText("Start");
        startStopButton.setPrefSize(187,30);
        startStopButton.relocate(771,355);
        startStopButton.setStyle("-fx-base: rgb(0,170,0)");
        startStopButton.setPadding(new Insets(0,0,0,0));
        startStopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Date buttonClick = new Date();
                startStopButton.setText("Stop");
                startStopButton.setStyle("-fx-base: rgb(170,0,0);");
                thumbnail.setDisable(true);

                Timeline updateTimer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
                    int minuteCounter = 0;

                    @Override
                    public void handle(ActionEvent event) {
                        Date currentTime = new Date();


                        int totalTime   = (int)(currentTime.getTime()-buttonClick.getTime())/1000;

                        if ((totalTime%60)==0){
                            minuteCounter+=1;
                            System.out.println("MinuteCounter: "+minuteCounter);

                        }
                        totalTime = totalTime-minuteCounter*60;
                        timeElapsed.setText(minuteCounter+":"+String.format("%02d",totalTime));

                    }
                }));

                updateTimer.setCycleCount(Timeline.INDEFINITE);
                updateTimer.play();


                SliderAppModel model = new SliderAppModel(buttonsLabels);


                model.randomize(buttonsLabels); // ranomizes array
                int[] index = model.setRandomBlank(buttonsLabels); // switches one of the index pos in buttons to blank.

                System.out.println("This is where the blank is :"+" Index1: "+index[0]+" Index2: "+index[1]);


                model.formButtons(buttonsLabels,buttons);
                /* i was going to make a function that deals with button switches but was runnning out of time
                so i just did them manualy, they are all essentially the same so you dont have to check them all*/
                for(int row=0; row<4; row++){
                    for(int col=0; col<4; col++){
                        buttons[row][col].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                if(event.getSource()==buttons[0][0]){

                                    if (buttonsLabels[0][1].equals("Blank.png")){
                                        String temp = buttonsLabels[0][0];
                                        buttonsLabels[0][0] = "Blank.png";
                                        buttonsLabels[0][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();


                                        }

                                    }
                                    else if(buttonsLabels[1][0].equals("Blank.png")){
                                        String temp = buttonsLabels[0][0];
                                        buttonsLabels[0][0] = "Blank.png";
                                        buttonsLabels[1][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[1][1].equals("Blank.png")){
                                        String temp = buttonsLabels[0][0];
                                        buttonsLabels[0][0] = "Blank.png";
                                        buttonsLabels[1][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }

                                }
                                else if(event.getSource()==buttons[1][0]){

                                    if (buttonsLabels[1][1].equals("Blank.png")){
                                        String temp = buttonsLabels[1][0];
                                        buttonsLabels[1][0] = "Blank.png";
                                        buttonsLabels[1][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][0].equals("Blank.png")){
                                        String temp = buttonsLabels[1][0];
                                        buttonsLabels[1][0] = "Blank.png";
                                        buttonsLabels[2][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[0][0].equals("Blank.png")){
                                        String temp = buttonsLabels[1][0];
                                        buttonsLabels[1][0] = "Blank.png";
                                        buttonsLabels[0][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }

                                }
                                else if(event.getSource()==buttons[0][1]){

                                    if (buttonsLabels[1][1].equals("Blank.png")){
                                        String temp = buttonsLabels[0][1];
                                        buttonsLabels[0][1] = "Blank.png";
                                        buttonsLabels[1][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[0][2].equals("Blank.png")){
                                        String temp = buttonsLabels[0][1];
                                        buttonsLabels[0][1] = "Blank.png";
                                        buttonsLabels[0][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[0][0].equals("Blank.png")){
                                        String temp = buttonsLabels[0][1];
                                        buttonsLabels[0][1] = "Blank.png";
                                        buttonsLabels[0][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }

                                }
                                else if(event.getSource()==buttons[1][1]){

                                    if (buttonsLabels[1][0].equals("Blank.png")){
                                        String temp = buttonsLabels[1][1];
                                        buttonsLabels[1][1] = "Blank.png";
                                        buttonsLabels[1][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[0][1].equals("Blank.png")){
                                        String temp = buttonsLabels[1][1];
                                        buttonsLabels[1][1] = "Blank.png";
                                        buttonsLabels[0][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[2][1].equals("Blank.png")){
                                        String temp = buttonsLabels[1][1];
                                        buttonsLabels[1][1] = "Blank.png";
                                        buttonsLabels[2][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[1][2].equals("Blank.png")){
                                        String temp = buttonsLabels[1][1];
                                        buttonsLabels[1][1]="Blank.png";
                                        buttonsLabels[1][2]=temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }

                                }
                                else if(event.getSource()==buttons[0][2]){

                                    if (buttonsLabels[0][1].equals("Blank.png")){
                                        String temp = buttonsLabels[0][2];
                                        buttonsLabels[0][2] = "Blank.png";
                                        buttonsLabels[0][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[0][3].equals("Blank.png")){
                                        String temp = buttonsLabels[0][2];
                                        buttonsLabels[0][2] = "Blank.png";
                                        buttonsLabels[0][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[1][2].equals("Blank.png")){
                                        String temp = buttonsLabels[0][2];
                                        buttonsLabels[0][2] = "Blank.png";
                                        buttonsLabels[1][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }

                                }
                                else if(event.getSource()==buttons[0][3]){

                                    if (buttonsLabels[0][2].equals("Blank.png")){
                                        String temp = buttonsLabels[0][3];
                                        buttonsLabels[0][3] = "Blank.png";
                                        buttonsLabels[0][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[1][2].equals("Blank.png")){
                                        String temp = buttonsLabels[0][3];
                                        buttonsLabels[0][3] = "Blank.png";
                                        buttonsLabels[1][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[1][3].equals("Blank.png")){
                                        String temp = buttonsLabels[0][3];
                                        buttonsLabels[0][3] = "Blank.png";
                                        buttonsLabels[1][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }

                                }
                                else if(event.getSource()==buttons[1][2]){

                                    if (buttonsLabels[1][1].equals("Blank.png")){
                                        String temp = buttonsLabels[1][2];
                                        buttonsLabels[1][2] = "Blank.png";
                                        buttonsLabels[1][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[0][2].equals("Blank.png")){
                                        String temp = buttonsLabels[1][2];
                                        buttonsLabels[1][2] = "Blank.png";
                                        buttonsLabels[0][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[1][3].equals("Blank.png")){
                                        String temp = buttonsLabels[1][2];
                                        buttonsLabels[1][2] = "Blank.png";
                                        buttonsLabels[1][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[2][2].equals("Blank.png")){
                                        String temp = buttonsLabels[1][2];
                                        buttonsLabels[1][2]="Blank.png";
                                        buttonsLabels[2][2]=temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }

                                }
                                else if(event.getSource()==buttons[1][3]){

                                    if (buttonsLabels[1][2].equals("Blank.png")){
                                        String temp = buttonsLabels[1][3];
                                        buttonsLabels[1][3] = "Blank.png";
                                        buttonsLabels[1][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][3].equals("Blank.png")){
                                        String temp = buttonsLabels[1][3];
                                        buttonsLabels[1][3] = "Blank.png";
                                        buttonsLabels[2][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[0][3].equals("Blank.png")){
                                        String temp = buttonsLabels[1][3];
                                        buttonsLabels[1][3] = "Blank.png";
                                        buttonsLabels[0][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }


                                }
                                else if(event.getSource()==buttons[2][0]){

                                    if (buttonsLabels[1][0].equals("Blank.png")){
                                        String temp = buttonsLabels[2][0];
                                        buttonsLabels[2][0] = "Blank.png";
                                        buttonsLabels[1][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][1].equals("Blank.png")){
                                        String temp = buttonsLabels[2][0];
                                        buttonsLabels[2][0] = "Blank.png";
                                        buttonsLabels[2][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[3][0].equals("Blank.png")){
                                        String temp = buttonsLabels[2][0];
                                        buttonsLabels[2][0] = "Blank.png";
                                        buttonsLabels[3][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }


                                }
                                else if(event.getSource()==buttons[2][3]){

                                    if (buttonsLabels[1][3].equals("Blank.png")){
                                        String temp = buttonsLabels[2][3];
                                        buttonsLabels[2][3] = "Blank.png";
                                        buttonsLabels[1][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][2].equals("Blank.png")){
                                        String temp = buttonsLabels[2][3];
                                        buttonsLabels[2][3] = "Blank.png";
                                        buttonsLabels[2][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[3][3].equals("Blank.png")){
                                        String temp = buttonsLabels[2][3];
                                        buttonsLabels[2][3] = "Blank.png";
                                        buttonsLabels[3][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }


                                }
                                else if(event.getSource()==buttons[3][3]){

                                    if (buttonsLabels[2][2].equals("Blank.png")){
                                        String temp = buttonsLabels[3][3];
                                        buttonsLabels[3][3] = "Blank.png";
                                        buttonsLabels[2][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][3].equals("Blank.png")){
                                        String temp = buttonsLabels[3][3];
                                        buttonsLabels[3][3] = "Blank.png";
                                        buttonsLabels[2][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[3][2].equals("Blank.png")){
                                        String temp = buttonsLabels[3][3];
                                        buttonsLabels[3][3] = "Blank.png";
                                        buttonsLabels[3][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }


                                }
                                else if(event.getSource()==buttons[3][0]){

                                    if (buttonsLabels[2][0].equals("Blank.png")){
                                        String temp = buttonsLabels[3][0];
                                        buttonsLabels[3][0] = "Blank.png";
                                        buttonsLabels[2][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][1].equals("Blank.png")){
                                        String temp = buttonsLabels[3][0];
                                        buttonsLabels[3][0] = "Blank.png";
                                        buttonsLabels[2][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[3][1].equals("Blank.png")){
                                        String temp = buttonsLabels[3][0];
                                        buttonsLabels[3][0] = "Blank.png";
                                        buttonsLabels[3][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }


                                }
                                else if(event.getSource()==buttons[2][1]){

                                    if (buttonsLabels[1][1].equals("Blank.png")){
                                        String temp = buttonsLabels[2][1];
                                        buttonsLabels[2][1] = "Blank.png";
                                        buttonsLabels[1][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][0].equals("Blank.png")){
                                        String temp = buttonsLabels[2][1];
                                        buttonsLabels[2][1] = "Blank.png";
                                        buttonsLabels[2][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[3][1].equals("Blank.png")){
                                        String temp = buttonsLabels[2][1];
                                        buttonsLabels[2][1] = "Blank.png";
                                        buttonsLabels[3][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[2][2].equals("Blank.png")){
                                        String temp = buttonsLabels[2][1];
                                        buttonsLabels[2][1]="Blank.png";
                                        buttonsLabels[2][2]=temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }


                                }
                                else if(event.getSource()==buttons[2][2]){

                                    if (buttonsLabels[1][2].equals("Blank.png")){
                                        String temp = buttonsLabels[2][2];
                                        buttonsLabels[2][2] = "Blank.png";
                                        buttonsLabels[1][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][3].equals("Blank.png")){
                                        String temp = buttonsLabels[2][2];
                                        buttonsLabels[2][2] = "Blank.png";
                                        buttonsLabels[2][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[3][2].equals("Blank.png")){
                                        String temp = buttonsLabels[2][2];
                                        buttonsLabels[2][2] = "Blank.png";
                                        buttonsLabels[3][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[2][1].equals("Blank.png")){
                                        String temp = buttonsLabels[2][2];
                                        buttonsLabels[2][2]="Blank.png";
                                        buttonsLabels[2][1]=temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }

                                }
                                else if(event.getSource()==buttons[3][1]){

                                    if (buttonsLabels[3][0].equals("Blank.png")){
                                        String temp = buttonsLabels[3][1];
                                        buttonsLabels[3][1] = "Blank.png";
                                        buttonsLabels[3][0] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][1].equals("Blank.png")){
                                        String temp = buttonsLabels[3][1];
                                        buttonsLabels[3][1] = "Blank.png";
                                        buttonsLabels[2][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[3][2].equals("Blank.png")){
                                        String temp = buttonsLabels[3][1];
                                        buttonsLabels[3][1] = "Blank.png";
                                        buttonsLabels[3][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }


                                }
                                else if(event.getSource()==buttons[3][2]){

                                    if (buttonsLabels[3][1].equals("Blank.png")){
                                        String temp = buttonsLabels[3][2];
                                        buttonsLabels[3][2] = "Blank.png";
                                        buttonsLabels[3][1] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }

                                    }
                                    else if(buttonsLabels[2][2].equals("Blank.png")){
                                        String temp = buttonsLabels[3][2];
                                        buttonsLabels[3][2] = "Blank.png";
                                        buttonsLabels[2][2] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }
                                    else if(buttonsLabels[3][3].equals("Blank.png")){
                                        String temp = buttonsLabels[3][2];
                                        buttonsLabels[3][2] = "Blank.png";
                                        buttonsLabels[3][3] = temp;
                                        model.formButtons(buttonsLabels,buttons);
                                        if(model.checkPuzzle(buttonsLabels)){
                                            buttonsLabels[index[0]][index[1]] = solvedPuzzle[index[0]][index[1]];
                                            model.formButtons(buttonsLabels,buttons);
                                            model.endGame(buttons);
                                            updateTimer.stop();

                                        }
                                    }


                                }





                            }
                        });

                    }
                }

                startStopButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        startStopButton.setStyle("-fx-base: rgb(0,170,0)");
                        startStopButton.setText("Start");
                        updateTimer.stop();
                        for(int row=0; row<4; row++){
                            for(int col=0; col<4; col++){
                                buttons[row][col].setGraphic(new ImageView(new Image(getClass()
                                        .getResourceAsStream("BLANK.png"))));

                            }
                        }

                        thumbnail.setDisable(false);
                    }
                });





            }

        });

        getChildren().addAll(timeElapsedLabel,startStopButton,timeElapsed,thumbnail,puzzleNames);
    }
}
