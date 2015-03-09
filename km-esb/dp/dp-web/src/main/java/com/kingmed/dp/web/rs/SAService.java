/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.web.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author zhengjunjie
 */
@Path("/sa/")
public class SAService {

    @GET
    @Path("/users/get/{country}/")		
    @Produces("application/json")
    public String getAllExperts(@PathParam("country") String country) {
        return null;
    }
}
