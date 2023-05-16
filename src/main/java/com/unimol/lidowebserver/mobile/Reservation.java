package com.unimol.lidowebserver.mobile;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/reservation")
public class Reservation {

    @POST
    @Path("/doreservation")
    @Produces(MediaType.APPLICATION_JSON)
    public String doReservation(@QueryParam("mail") String mail, @QueryParam("ID_Umbrella") String ID_Umbrella) throws ClassNotFoundException, SQLException {
        String response = " " ;

        if (this.reserveUmbrella(mail, ID_Umbrella)) {
            response = Utility.constructJSON("reservation", true);
        } else {
            response = Utility.constructJSON("reservation", false);
        }
         return response;
    }

    private boolean reserveUmbrella(String mail, String ID_Umbrella) throws ClassNotFoundException, SQLException {
        boolean madeReservation = false;
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();

        if (Utility.isNotNull(mail) && Utility.isNotNull(ID_Umbrella)) {

                if (databaseConfiguration.makeReservation(mail, ID_Umbrella)) {
                    madeReservation = true;
            }
        }
        return madeReservation;
    }
}
