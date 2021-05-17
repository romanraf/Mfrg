package sample;

import javafx.fxml.FXML;

import java.awt.*;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class Controller {
    String OldString;
    String bufString;
    int counter = 0;
    @FXML
    TextArea mainTextArea;
    @FXML
    Button sendButton;
    @FXML
    TextField inputTextField;

    public void SendAction(ActionEvent actionEvent) {
        bufString = inputTextField.getText();
        if(!bufString.replaceAll(" ","").isEmpty()
                && counter != 3) {
            if(bufString.equals(OldString))
                counter++;
            else{
                OldString = bufString;
                counter = 1;
            }
            mainTextArea.appendText(inputTextField.getText() + "\n");
        }
        inputTextField.setText("");
        inputTextField.requestFocus();
    }
}
