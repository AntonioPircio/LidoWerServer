package com.unimol.lidowebserver.mobile;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/deletereservation")
public class DeleteReservation {

    @DELETE
    @Path("/dodeletereservation")
    @Produces(MediaType.APPLICATION_JSON)
    public String doDeleteReservation(@QueryParam("mail") String mail) throws ClassNotFoundException, SQLException {
        String response = "";

        if (this.deleteReservation(mail)) {
            response = Utility.constructJSON("deleteReservation", true);

        } else {
            response = Utility.constructJSON("deleteResevation", false);

        }
        return response;
    }

    private boolean deleteReservation(String mail) throws ClassNotFoundException, SQLException {
        boolean result = false;
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();

        if (Utility.isNotNull(mail)) {
            result = databaseConfiguration.deleteReservation(mail);
        }
        return result;
    }
}
