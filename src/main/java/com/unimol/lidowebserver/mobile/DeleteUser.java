package com.unimol.lidowebserver.mobile;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/delete")
public class DeleteUser {

    @DELETE
    @Path("/dodelete")
    @Produces(MediaType.APPLICATION_JSON)
    public String doDelete(@QueryParam("mail") String mail) throws ClassNotFoundException, SQLException {
        String response = "";

        if (this.deleteUser(mail)) {
            response = Utility.constructJSON("deleteUser", true);
        } else {
            response = Utility.constructJSON("deleteUser", false);
        }

        return response;
    }

    private boolean deleteUser(String mail) throws ClassNotFoundException, SQLException {
        boolean result = false;
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();

        if (Utility.isNotNull(mail)) {
            result = databaseConfiguration.deleteUser(mail);
        }
        return result;
    }

}
