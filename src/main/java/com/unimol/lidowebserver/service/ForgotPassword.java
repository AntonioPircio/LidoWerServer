package com.unimol.lidowebserver.service;

import com.unimol.lidowebserver.mobile.Utility;
import com.unimol.lidowebserver.mobile.Constants;
import com.unimol.lidowebserver.mobile.DatabaseConfiguration;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

@Path("/forgotpassword")
public class ForgotPassword {

    @POST
    @Path("/doforgotpassword")
    @Produces(MediaType.APPLICATION_JSON)
    public String doForgotPassword(@QueryParam("mail") String mail) {
        String response = " ";

        try {
            if (this.checkMail(mail)) {
                    int otpvalue = 0;
                    Random rand = new Random();
                    otpvalue = rand.nextInt(1255650);

                    Properties props = new Properties();
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host",  Constants.SMTP_HOST);
                    props.put("mail.smtp.socketFactory.port", Constants.SMTP_SFP);
                    props.put("mail.smtp.socketFactory.class", Constants.SMTP_SFC);
                    props.put("mail.smtp.auth", Constants.SMTP_AUTH);
                    props.put("mail.smtp.port", Constants.SMTP_PORT);
                    Session session = Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Constants.emailManager, Constants.emailPassword);
                        }
                    });
                    try {
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(Constants.emailManager));
                        message.addRecipient(Message.RecipientType.TO,  new InternetAddress(mail));
                        message.setSubject("Recupero Password - LidoAPP");
                        message.setText("il tuo codice OTP: " + otpvalue);

                        Transport transport = session.getTransport("smtp");
                        transport.connect(Constants.SMTP_HOST,Constants.emailManager, Constants.emailPassword);
                        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                        transport.close();

                        response = String.valueOf(otpvalue);
                    } catch (MessagingException messagingException) {
                        response = Utility.constructJSON("Sended OTP", false);
                        System.err.println(messagingException.getMessage());
                    }
            } else {
                response = Utility.constructJSON("Sended OTP", false);
                System.err.println("ERRORE IL CAMPO MAIL NON PUO' ESSERE VUOTO ");
            }
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            response = Utility.constructJSON("Sended OTP", false);
            System.err.println(classNotFoundException.getMessage());
        }
        return response;
    }



    private boolean checkMail(String mail) throws ClassNotFoundException, SQLException {
        boolean result = false;

        if (Utility.isNotNull(mail)) {
                DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
                result = databaseConfiguration.checkMail(mail);
        }
        return result;
    }

}