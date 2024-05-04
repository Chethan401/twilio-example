package com.twilioexample.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilioexample.payload.SMSRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

    // Twilio Account SID and Auth Token
    @Value("${twilio.account_sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth_token}")
    private String twilioAuthToken;

    // Twilio phone number
    @Value("${twilio.phone_number}")
    private String twilioPhoneNumber;

    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSMS(@RequestBody SMSRequest smsRequest) {
        try {
            Twilio.init(twilioAccountSid, twilioAuthToken);
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(smsRequest.getTo()),
                            new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                            smsRequest.getMessage())
                    .create();

            return ResponseEntity.ok("sms sent successfully. SID : " + message.getSid());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to send SMS: " + e.getMessage());
        }
    }
}
