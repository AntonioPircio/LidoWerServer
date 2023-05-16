package com.unimol.lidowebserver.mobile;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/login")
public class Login {

    @POST
    @Path("/dologin")
    @Produces(MediaType.APPLICATION_JSON)
    public String doLogin(@QueryParam("mail") String mail, @QueryParam("password") String password) throws ClassNotFoundException, SQLException {

        String response = "";
        if (this.checkCredentials(mail, password)) {
            response = "true" + Utility.constructJSON("login", true);
        } else {
            response = "false" + Utility.constructJSONMessage("login", false, "Incorrect Mail or Password");
        }
        return response;
    }


    private boolean checkCredentials(String mail, String password) throws ClassNotFoundException, SQLException {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        boolean result = false;

            if (Utility.isNotNull(mail) && Utility.isNotNull(password)) {
                result = databaseConfiguration.checkLogin(mail, password);
            }
        return result;
    }
}

