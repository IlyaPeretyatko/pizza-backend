package ru.nsu.assjohns.service;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.nsu.assjohns.config.type.MailType;
import ru.nsu.assjohns.entity.User;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final Configuration configuration;

    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendEmail(User user, MailType mailType) {
        switch (mailType) {
            case REGISTER -> sendRegistrationEmail(user);
            case RESET_PASSWORD -> sendResetPasswordEmail(user);
            default -> {}
        }
    }

    private void sendRegistrationEmail(User user) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Thank you for your registration, " + user.getUsername());
        helper.setTo(user.getEmail());
        String emailContent = getRegistrationEmailContent(user);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }


    private String getRegistrationEmailContent(User user) throws Exception {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getUsername());
        model.put("domain", "localhost:8880");
        model.put("token", user.getVerificationCode().toString());
        configuration.getTemplate("register.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }


    private void sendResetPasswordEmail(User user) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Request to Reset Your Password");
        helper.setTo(user.getEmail());
        String emailContent = getResetPasswordEmailContent(user);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    private String getResetPasswordEmailContent(User user) throws Exception {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getUsername());
        model.put("domain", "localhost:8880");
        model.put("token", user.getVerificationCode().toString());
        configuration.getTemplate("reset.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
