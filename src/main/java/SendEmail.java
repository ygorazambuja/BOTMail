import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

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

}