package com.azlearning.functionapp.handler;

import com.azlearning.functionapp.function.EmailSender;
import com.azlearning.functionapp.model.EmailDTO;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * EmailSenderFunctionHandler
 *
 * @author Uthiraraj Saminathan
 */
@Component
public class EmailSenderFunctionHandler {

    private final EmailSender emailSender;

    public EmailSenderFunctionHandler(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @FunctionName("emailSender")
    public String execute(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<EmailDTO>> request,
            ExecutionContext context) {

        context.getLogger().info("emailSender function is invoked");

        context.getLogger().info("Headers : " + request.getHeaders());
        context.getLogger().info("URL : " + request.getUri());
        context.getLogger().info("Get Body value : " + request.getBody());

        EmailDTO emailDTO = request.getBody().orElseThrow(() -> new IllegalArgumentException("EmailDTO is required"));

        context.getLogger().info("From : " + emailDTO.getFrom());
        context.getLogger().info("To : " + emailDTO.getTo());
        context.getLogger().info("Subject : " + emailDTO.getSubject());
        context.getLogger().info("Body : " + emailDTO.getBody());

        String status = emailSender.apply(emailDTO);
        context.getLogger().info("emailSender function is completed "+status);
        return emailSender.apply(emailDTO);

    }
}
