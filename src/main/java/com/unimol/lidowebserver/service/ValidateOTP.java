package com.unimol.lidowebserver.service;

import com.unimol.lidowebserver.mobile.Utility;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/validateotp")
public class ValidateOTP {

    @POST
    @Path("/dovalidateotp")
    @Produces(MediaType.APPLICATION_JSON)
    public String doValidateOtp(@QueryParam("insertedotp") String insertedOtp, @QueryParam("sendedotp") String sendedOtp) {
        String response = " ";

        if (this.checkOTP(insertedOtp)) {
            try {
                if (insertedOtp.trim().equals(sendedOtp.trim())) {
                    response = Utility.constructJSON("OTP VALIDATED", true);
                } else {
                    response = Utility.constructJSON("OTP VALIDATED", false);
                }
            } catch (NumberFormatException numberFormatException) {
                response = Utility.constructJSON("OTP VALIDATED", false);
                System.err.println(numberFormatException.getMessage());
            }
        } else {
            response = Utility.constructJSON("OTP VALIDATED", false);
        }
        return response;
    }

    private boolean checkOTP(String otp) {
        return !otp.isEmpty();
    }
}




