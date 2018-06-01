import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BotmailController implements Initializable {

    @FXML
    public JFXButton btnAnexo;

    @FXML
    private JFXTextField emailTextField, destinoTextField, assuntoTextField, infoTextField, anexoTextField;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXProgressBar progressBar;

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

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            anexoTextField.setText(selectedFile.getAbsolutePath());
        }
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

    private void validarComeco() {
        if (checkBoxEmail.isSelected() && checkBoxSenha.isSelected() && nEmails.getValue() >= 1) {
            btnComeco.setVisible(true);
        }
    }

    public void comecarBot() {

        Integer formatado = (int) nEmails.getValue();

        if (anexoTextField.getText() == null) {
            // então não tem anexo

            new Thread(() -> {
                progressBar.setVisible(true);

                for (int i = 0; i < formatado; i++) {


                    for (String destinatarios : listView.getItems()) {
                        SendEmail sendEmail = new SendEmail("smtp.gmail.com", emailTextField.getText(), passwordTextField.getText(), "465", true, true);
                        sendEmail.sendSimpleEmail(destinatarios, assuntoTextField.getText(), textArea.getText());
                        System.out.println("Email sendo mandado para : " + destinatarios);
                    }
                }
                infoTextField.setDisable(false);
                infoTextField.setVisible(true);

                infoTextField.setText("Um total de " + (formatado) + " emails foram enviados com sucesso!");
                progressBar.setVisible(false);

            }).start();

        } else {
            // tem anexo
            new Thread(() -> {
                progressBar.setVisible(true);
                for (int i = 0; i < formatado; i++) {


                    for (String destinatarios : listView.getItems()) {

                        EmailAttachment anexo = new EmailAttachment();
                        anexo.setPath(anexoTextField.getText());
                        anexo.setDisposition(EmailAttachment.ATTACHMENT);
                        anexo.setDescription("");
                        anexo.setName("");

                        MultiPartEmail email = new MultiPartEmail();

                        email.setHostName("smtp.gmail.com");
                        email.setSmtpPort(465);
                        email.setAuthenticator(new DefaultAuthenticator(emailTextField.getText(), passwordTextField.getText()));
                        email.setSSLOnConnect(true);

                        try {
                            email.setFrom(emailTextField.getText());
                        } catch (EmailException e) {
                            e.printStackTrace();
                        }

                        email.setSubject(assuntoTextField.getText());

                        try {
                            email.setFrom(emailTextField.getText());
                        } catch (EmailException e) {
                            e.printStackTrace();
                        }

                        try {
                            email.addTo(destinatarios);
                        } catch (EmailException e) {
                            e.printStackTrace();
                        }


                        try {
                            email.setMsg(textArea.getText());
                            try {
                                email.attach(anexo);
                                email.send();
                            } catch (EmailException e) {
                                e.printStackTrace();
                            }
                        } catch (EmailException e) {
                            e.printStackTrace();
                        }
                    }
                }
                progressBar.setVisible(false);
            }).start();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


}
