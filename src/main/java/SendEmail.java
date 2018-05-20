
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

class SendEmail {

    private String smtp; //Simple Mail Transfer Protocol
    private String email_from;
    private String email_password;
    private String email_to;
    private String subject;
    private String msg;
    private String port;
    private boolean ssl; // Security Socket Layer
    private boolean tls; // Transport Layer Security

    SendEmail(String smtp, String email_from, String email_password, String port, boolean ssl, boolean tls) {
        this.smtp = smtp;
        this.email_from = email_from;
        this.email_password = email_password;
        this.port = port;
        this.ssl = ssl;
        this.tls = tls;
    }

    void sendSimpleEmail(String email_to, String subject, String msg) {
        SimpleEmail email = new SimpleEmail();
        try {
            email.setHostName(smtp);
            email.addTo(email_to);
            email.setFrom(email_from);
            email.setAuthentication(email_from, email_password);
            email.setSubject(subject);
            email.setMsg(msg);
            email.setSSL(ssl);
            email.setTLS(tls);
            email.send();
        } catch (EmailException e) {
            System.out.println(e.getMessage());
        }
    }
}