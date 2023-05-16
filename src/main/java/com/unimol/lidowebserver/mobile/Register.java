package com.unimol.lidowebserver.mobile;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/register")
public class Register {

    @POST
    @Path("/doregister")
    @Produces(MediaType.APPLICATION_JSON)
    public String doRegister(@QueryParam("name") String name, @QueryParam("surname") String surname, @QueryParam("mail") String mail,
                             @QueryParam("password") String password) throws ClassNotFoundException, SQLException {
        String response = " ";
        password = AES.encrypt(password, Constants.secretKey);

        int returnCode = this.registerUser(name, surname, mail, password);

        if (returnCode == 0) {
            response = Utility.constructJSON("register", true);
        } else if (returnCode == 1) {
            response = Utility.constructJSONMessage("register", false,"Sei già registrato");
        } else if (returnCode == 2) {
            response = Utility.constructJSONMessage("register", false, "ERRORE");
        }
        return response;
    }

    private int registerUser(String name, String surname, String mail, String password) throws ClassNotFoundException, SQLException {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        int result = 2;

        if (Utility.isNotNull(name) && Utility.isNotNull(surname) && Utility.isNotNull(mail) && Utility.isNotNull(password)) {
            try {
                if (databaseConfiguration.insertUser(name, surname, mail, password)) {
                    result = 0;
                }
            } catch (SQLException sqlException) {
                 if (sqlException.getErrorCode() == Constants.primaryKeyCodeError) {
                     result = 1;
                 }
             }
        }
        return result;
    }

}
