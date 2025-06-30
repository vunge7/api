package com.dvml.api.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhatsAppService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage() {
        String url = "https://rpz82e.api.infobip.com/whatsapp/1/message/template";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "App b9ccb7f95b5ac8368c2f96c9c22ddb43-9d0bd5c2-6a66-4236-acf2-54691eb04c38");

        String json = """
        {
          "messages": [
            {
              "from": "244955820597",
              "to": "244924326551",
              "messageId": "03143c95-3e0f-47bb-92d1-0a4830752224",
              "content": {
                "templateName": "test_whatsapp_template_en",
                "templateData": {
                  "body": {
                    "placeholders": ["Martinho"]
                  }
                },
                "language": "en"
              }
            }
          ]
        }
        """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println("Response: " + response.getBody());
    }
}
