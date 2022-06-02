package com.htecgroup.skynest.model.email;


import lombok.Getter;

@Getter
public class Email {
    String to;
    String subject;
    String emailBody;
    Boolean html;
    public Email(String to, String subject, String emailBody, Boolean html) {
        this.to = to;
        this.subject = subject;
        this.emailBody = emailBody;
        this.html = html;
    }
}
