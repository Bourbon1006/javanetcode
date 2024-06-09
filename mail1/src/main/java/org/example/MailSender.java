package org.example;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;

public class MailSender {
    private String smtpHost;
    private String smtpPort;
    private String username;
    private String password;

    public MailSender(String smtpHost, String smtpPort, String username, String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String toAddress, String subject, String messageContent, String attachmentPath) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);

        // 创建消息部分
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(messageContent, "text/plain; charset=UTF-8");

        // 创建多部分对象
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // 添加附件部分
        if (attachmentPath != null && !attachmentPath.isEmpty()) {
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            try {
                attachmentBodyPart.attachFile(new File(attachmentPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            multipart.addBodyPart(attachmentBodyPart);
        }

        // 设置完整的邮件内容
        message.setContent(multipart);

        // 发送邮件
        Transport.send(message);
    }
}


