package org.example;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;

public class MailReceiver {
    private String host;
    private String port;
    private String username;
    private String password;

    public MailReceiver(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void checkMail() throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", port);
        properties.put("mail.pop3.auth", "true");
        properties.put("mail.pop3.starttls.enable", "true");

        Session session = Session.getInstance(properties);

        Store store = session.getStore("pop3");
        store.connect(host, username, password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        Message[] messages = folder.getMessages();
        for (Message message : messages) {
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Text: " + message.getContent().toString());
        }

        folder.close(false);
        store.close();
    }
}
