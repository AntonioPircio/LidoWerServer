package com.unimol.lidowebserver.service;

import com.unimol.lidowebserver.mobile.AES;
import com.unimol.lidowebserver.mobile.Utility;
import com.unimol.lidowebserver.mobile.Constants;
import com.unimol.lidowebserver.mobile.DatabaseConfiguration;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/newpassword")
public class NewPassword {

    @PUT
    @Path("/donewpassword")
    @Produces(MediaType.APPLICATION_JSON)
    public String doNewPassword(@QueryParam("mail") String mail, @QueryParam("newPassword") String newPassword, @QueryParam("confPassword") String confPassword) {
            String response = " ";

            if (newPassword.equals(confPassword)) {
                newPassword = AES.encrypt(newPassword, Constants.secretKey);
                try {
                    DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
                    databaseConfiguration.updatePassword(mail, newPassword);
                    response = Utility.constructJSON("CHANGE PASSWORD", true);
                } catch (ClassNotFoundException classNotFoundException) {
                    response = Utility.constructJSON("CHANGE PASSWORD", false);
                    System.err.println(classNotFoundException.getMessage());
                } catch (SQLException sqlException) {
                    System.err.println(sqlException.getMessage());
                    response = Utility.constructJSON("CHANGE PASSWORD", false);
                }
            } else {
                System.err.println("ERRORE NELLA PASSWORD");
                response = Utility.constructJSON("CHANGE PASSWORD", false);
            }
            return response;
    }

}
