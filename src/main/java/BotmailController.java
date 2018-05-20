import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class BotmailController implements Initializable {

    @FXML
    private JFXTextField emailTextField, destinoTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private JFXCheckBox checkBoxEmail, checkBoxSenha;

    @FXML
    private JFXButton btnComeco;

    @FXML
    private JFXSlider nEmails;

    public void setCheckBoxEmail() {
        String s = emailTextField.getText();

        if (s.contains("@gmail.com")) {
            checkBoxEmail.setVisible(true);
            checkBoxEmail.setSelected(true);
            checkBoxEmail.setDisable(true);

        } else if (!s.contains("@gmail.com") && (s.length() > 50)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();

        }
        validarComeco();
    }

    public void setCheckBoxSenha() {

        String s = passwordTextField.getText();

        if (s.length() > 6) {
            checkBoxSenha.setSelected(true);
            checkBoxSenha.setVisible(true);
            checkBoxSenha.setManaged(false);
            checkBoxSenha.setDisable(true);
        }

        validarComeco();
    }

    void validarComeco() {
        if (checkBoxEmail.isSelected() && checkBoxSenha.isSelected() && nEmails.getValue() >= 1) {
            btnComeco.setVisible(true);
        }
    }

    public void comecarBot() {


        Integer formatado = (int) nEmails.getValue();
        for (int i = 0; i < formatado; i++) {
            SendEmail sendEmail = new SendEmail("smtp.gmail.com", emailTextField.getText(), passwordTextField.getText(), "587", false, true);
            sendEmail.sendSimpleEmail(destinoTextField.getText(), "ISSO NÃO É SPAM ! ISSO NÃO É SPAM ! ", "ISSO NÃO É SPAM");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pronto!");
        alert.setContentText("Um total de " + formatado + 1 + " emails foram enviados com sucesso!");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
