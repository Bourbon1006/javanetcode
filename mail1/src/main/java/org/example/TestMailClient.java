package org.example;

import javax.mail.MessagingException;
import java.io.IOException;

public class TestMailClient {
    public static void main(String[] args) {
        String host = "smtp.qq.com";
        String port = "587";
        String username = "@qq.com";
        String password = "";

        MailSender mailSender = new MailSender(host, port, username, password);

        try {
            mailSender.sendEmail("@gmail.com", "测试主题", "测试消息内容", ""); // 发送带附件的邮件
            System.out.println("Email sent successfully.");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    MailReceiver mailReceiver = new MailReceiver("pop.qq.com", "110", username, password);

        try {
            mailReceiver.checkMail();
        } catch (MessagingException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
