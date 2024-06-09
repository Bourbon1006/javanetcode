package org.example;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmailSenderGUI extends JFrame {
    private final JTextField toField;
    private final JTextField subjectField;
    private final JTextArea messageArea;
    private final JTextField attachmentField;
    private final JTextField smtpHostField;
    private final JTextField smtpPortField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public EmailSenderGUI() {
        setTitle("邮件发送器");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建面板和布局
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // 添加组件
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("收件人："), constraints);

        constraints.gridx = 1;
        toField = new JTextField(20);
        panel.add(toField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("主题："), constraints);

        constraints.gridx = 1;
        subjectField = new JTextField(20);
        panel.add(subjectField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("消息内容："), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        messageArea = new JTextArea(5, 20);
        panel.add(new JScrollPane(messageArea), constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(new JLabel("附件路径："), constraints);

        constraints.gridx = 1;
        attachmentField = new JTextField(20);
        panel.add(attachmentField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(new JLabel("SMTP 主机："), constraints);

        constraints.gridx = 1;
        smtpHostField = new JTextField(20);
        smtpHostField.setText("smtp.qq.com");
        panel.add(smtpHostField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(new JLabel("SMTP 端口："), constraints);

        constraints.gridx = 1;
        smtpPortField = new JTextField(20);
        smtpPortField.setText("587");
        panel.add(smtpPortField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(new JLabel("邮箱账号："), constraints);

        constraints.gridx = 1;
        usernameField = new JTextField(20);
        panel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(new JLabel("邮箱密码："), constraints);

        constraints.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        JButton sendButton = new JButton("发送");
        panel.add(sendButton, constraints);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendEmail();
            }
        });

        add(panel);
    }

    private void sendEmail() {
        String to = toField.getText();
        String subject = subjectField.getText();
        String message = messageArea.getText();
        String attachment = attachmentField.getText();
        String smtpHost = smtpHostField.getText();
        String smtpPort = smtpPortField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        MailSender mailSender = new MailSender(smtpHost, smtpPort, username, password);

        try {
            mailSender.sendEmail(to, subject, message, attachment);
            JOptionPane.showMessageDialog(this, "邮件发送成功！");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "邮件发送失败：" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmailSenderGUI frame = new EmailSenderGUI();
            frame.setVisible(true);
        });
    }
}
