package com.azlearning.functionapp.function;

import com.azlearning.functionapp.model.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * EmailSender
 *
 * This class is responsible for sending emails using the JavaMailSender.
 * It implements the Function interface to process EmailDTO objects and send emails.
 *
 * @author Uthiraraj Saminathan
 */
@Component
public class EmailSender implements Function<EmailDTO,String> {

    private final static Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private final JavaMailSender javaMailSender;

    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String apply(EmailDTO emailDTO) {
        logger.info("Email Sender Function starts");
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailDTO.getFrom());
        message.setTo(emailDTO.getTo());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getBody());

        try{
            javaMailSender.send(message);
        }catch (MailException exp) {
            logger.error("sendEmail() exception occured");
            exp.printStackTrace();
            return "Email sending failed" + exp.getMessage();
        } catch (Exception exp){
            logger.error("Unexpected exception occurred when sending email");
            exp.printStackTrace();
            return "Unexpected exception occurred when sending email" + exp.getMessage();
        }
        logger.info("Email is delivered Successfully");
        return "SUCCESS";
    }
}
