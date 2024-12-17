package com.azlearning.functionapp.model;

import com.azlearning.functionapp.function.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * EmailDTOData
 *
 * @author Uthiraraj Saminathan
 */
public class EmailDTOData {



    public static void main(String[] args) {


        ObjectMapper objectMapper = new ObjectMapper();

       EmailDTO emailDTO = new EmailDTO();

        emailDTO.setFrom("test");
        emailDTO.setTo("test");
        emailDTO.setSubject("test");
        emailDTO.setBody("test");

        try {
            System.out.println(objectMapper.writeValueAsString(emailDTO));
            //objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(emailDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
