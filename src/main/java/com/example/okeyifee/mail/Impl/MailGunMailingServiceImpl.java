package com.example.okeyifee.mail.Impl;

import com.mashape.unirest.http.HttpResponse;

import com.example.okeyifee.mail.MailingService;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("mailGun")
public class MailGunMailingServiceImpl implements MailingService{

    private final Logger logger = LoggerFactory.getLogger(MailGunMailingServiceImpl.class);

    @Value("#{environment['mailgun.api_key']}")
    private String API_KEY;

    @Value("#{environment['mailgun.domain_name']}")
    private String DOMAIN_NAME;

    /**
     * This method handles hair recommendation mailimpl, It sends recommendation link to
     * the provided user's email address to view hair profiling result.
     *
     * @param email
     * @param emailLink
     * @return
     */
    @Override
    public boolean sendEmailLink(String email, String emailLink) {
        System.out.println(email);
        System.out.println(emailLink);
        System.out.println(API_KEY);
        System.out.println(DOMAIN_NAME);
        try {
            HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                    .basicAuth("api", API_KEY)
                    .field("from", "Admin <okeyifee@gmail.com>")
                    .field("to", email)//TODO pass the emails in the variable
                    .field("template", "hair_recommendation")
                    .field("subject", "ACCOUNT ACTIVATION LINK")
                    .field("h:X-Mailgun-Variables", String.format("{\"Click the link below to activate your account\": \"%s\"}", emailLink))
                    .asString();
            logger.info(request.getStatusText());
            logger.info(String.valueOf(request.getStatus()));
            if (request.getStatus() == 200){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(e.getMessage());
            return false;
        }
    }
}




