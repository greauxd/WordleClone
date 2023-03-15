import java.io.*;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;



public class WordleClone extends Application {

    //Initialize variable guesses
    private int guesses = 0;

    @Override

    public void start(Stage primaryStage) {

        //Create an array of TextFields
        TextField[] letters = new TextField[5];

        //Declare hBox for TextField and vBox for the opening message
        HBox hBox = new HBox();
        VBox vBox = new VBox();

        //Create padding for hBox and vBox
        hBox.setPadding(new Insets(100, 100, 100, 200));
        vBox.setPadding(new Insets(10, 100, 10, 150));

        //Spacing for TextField boxes
        hBox.setSpacing(5);

        //Creates 5 TextField boxes to fill array, sets sizes/fonts for each TextField box
        for (int i = 0; i < letters.length; i++) {
            letters[i] = new TextField();
            letters[i].setPrefColumnCount(1);
            letters[i].setMinSize(50, 50);
            letters[i].setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            hBox.getChildren().addAll(letters[i]);
        }

        //Declare BorderPane
        BorderPane pane = new BorderPane();

        //Creates text for introduction and description messages
        Text introduction = new Text(50, -150, "Welcome to my Wordle Clone!");
        Text description = new Text(30, -100, "The goal of the game is to guess the correct word, \nyou will have 6 attempts to guess, whether you win or \nlose you get to try again with a new word!\nUse arrows to navigate letters \n\n                                 GOOD LUCK!!!");

        //Changes font for introduction and description
        introduction.setFont(Font.font("Verdana", 20));
        description.setFont(Font.font("Verdana", 12));

        vBox.getChildren().addAll(introduction, description);



        //Fills pane with vBox and hBox, vBox, which contains introduction and description is at the top, and the hBox, which has the TextFields is in the center
        pane.setCenter(hBox);
        pane.setTop(vBox);

        // Calls function getAnswer to get random word from text file
        String answer = getAnswer();

        System.out.println(answer);


        //Creates scene and sets it
        Scene scene = new Scene(pane, 650, 400);
        primaryStage.setTitle("WordleClone");
        primaryStage.setScene(scene);
        primaryStage.show();

    //ActionEvents that will be triggered when keys are released to navigate TextFields
        letters[0].setOnKeyReleased(e -> {
            if(letters[0].getText().length() >= 1){
                letters[1].requestFocus();
            }
            if(e.getCode() == KeyCode.ENTER){
                letters[0].requestFocus();
            }
            if(e.getCode() == KeyCode.LEFT) {
                letters[4].requestFocus();
            }
        });

        letters[1].setOnKeyReleased(e -> {
            if(letters[1].getText().length() >= 1){
                letters[2].requestFocus();
            }
            if(e.getCode() == KeyCode.LEFT) {
                letters[0].requestFocus();
            }
        });

        letters[2].setOnKeyReleased(e -> {
            if(letters[2].getText().length() >= 1){
                letters[3].requestFocus();
            }
            if(e.getCode() == KeyCode.LEFT) {
                letters[1].requestFocus();
            }
        });

        letters[3].setOnKeyReleased(e -> {
            if(letters[3].getText().length() >= 1){
                letters[4].requestFocus();
            }
            if(e.getCode() == KeyCode.LEFT) {
                letters[2].requestFocus();
            }
        });
        letters[4].setOnKeyReleased(e ->{
            if(e.getCode() == KeyCode.LEFT) {
                letters[3].requestFocus();
            }
            if(e.getCode() == KeyCode.RIGHT){
                letters[0].requestFocus();
            }
        });


            scene.setOnKeyPressed(e -> {
                //Initializes variable guess
                String guess = "";

                if(e.getCode() == KeyCode.ENTER) {
                    //Increment guesses by 1 every time enter is pressed
                    guesses++;
                    //Concatenates first letter of TextField to guess
                    for (int i = 0; i < 5; i++)
                        guess = guess.concat(letters[i].getText(0, 1));
                }
                    //Calls checkGuess function
                    checkGuess(guess, letters, answer, pane, guesses);
                    letters[0].requestFocus();
            });

            }

    private static String getAnswer() {

        //Function that will return a random word from dictionary txt file, and assigns it to variable answer

        try {
            BufferedReader file = new BufferedReader(new FileReader("E:/IntelliJ/WordleClone/src/dictionary.txt"));

            Random rand = new Random();

            int line = rand.nextInt(5757);
            int index = 1;

            while ((file.readLine()) != null) {
                if (index == line) {
                    String answer = (file.readLine());
                    return answer;

                }
                index++;
            }
        }
        //Exception thrown when file cannot be found
        catch(Exception noFile){
            System.out.println("That file cannot be found");
        }
        return("Answer cannot be found");
    }

    private static String checkGuess(String guess, TextField[] letters, String answer, BorderPane pane, int guesses) {
        //Function that will compare guess to answer

        HBox guessesBox = new HBox();
        Popup popup = new Popup();
        Stage stage = new Stage();
        Text correctAnswer = new Text("The correct answer is " + answer);
        correctAnswer.setFont(Font.font("Verdana", 14));

        System.out.println(guess);
        for (int i = 0; i < guess.length(); i++) {

            //Compares ith character of guess to ith character in answer, will change TextField to green and sets editable to false if correct letter is in the correct place in the word,
            //yellow if the letter is in the word, but not in the right place and red if the letter is not in the word at all
            if (guess.charAt(i) == answer.charAt(i)) {
                letters[i].setStyle("-fx-background-color: green");
                letters[i].setEditable(false);
            } else if (answer.indexOf(guess.charAt(i)) != -1) {
                letters[i].setStyle("-fx-background-color: gray");
            } else {
                letters[i].setStyle("-fx-background-color: red");
            }
        }
        Text guessText = new Text("Guesses: " + guesses);
        if(guesses >= 6){
            popup.show(stage);
        }
       guessesBox.getChildren().add(guessText);

        pane.setBottom(guessesBox);

        return guess;

    }
}