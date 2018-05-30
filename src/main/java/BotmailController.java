import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class BotmailController implements Initializable {

    @FXML
    private JFXTextField emailTextField, destinoTextField, assuntoTextField, infoTextField;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private ImageView btnAnexo;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private JFXCheckBox checkBoxEmail, checkBoxSenha;

    @FXML
    private JFXButton btnComeco, btnClean, btnAddDestino, btnAjuda;

    @FXML
    private JFXSlider nEmails;

    @FXML
    private JFXListView<String> listView = new JFXListView<>();

    @FXML
    void btnAnexo(ActionEvent event) {

    }

    @FXML
    void showAjuda(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Olá");
        alert.showAndWait();
    }

    @FXML
    void adicionarDestinatarios(ActionEvent event) {

        listView.getItems().add(destinoTextField.getText());
        destinoTextField.setText(null);

    }

    @FXML
    void clean(ActionEvent event) {
        emailTextField.setText(null);
        passwordTextField.setText(null);
        assuntoTextField.setText(null);
        textArea.setText(null);
    }

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisible(true);

                for (String s : listView.getItems()) {
                    SendEmail sendEmail = new SendEmail("smtp.gmail.com", emailTextField.getText(), passwordTextField.getText(), "465", true, true);
                    sendEmail.sendSimpleEmail(s, assuntoTextField.getText(), textArea.getText());
                    System.out.println("Email sendo mandado para : " + s);
                }
                infoTextField.setDisable(false);
                infoTextField.setVisible(true);
                infoTextField.setText("Um total de " + (formatado) + " emails foram enviados com sucesso!");
                progressBar.setVisible(false);
            }
        }).start();




    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


}
