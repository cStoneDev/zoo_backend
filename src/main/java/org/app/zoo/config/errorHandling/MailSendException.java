package org.app.zoo.config.errorHandling;

public class MailSendException extends RuntimeException {
    public MailSendException(String message) {
        super(message);
    }
    
}
