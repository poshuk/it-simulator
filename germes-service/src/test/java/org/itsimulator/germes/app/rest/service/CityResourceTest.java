package org.itsimulator.germes.app.rest.service;

import org.glassfish.jersey.test.JerseyTest;
import org.itsimulator.germes.app.rest.dto.CityDTO;
import org.itsimulator.germes.app.rest.service.config.JerseyConfig;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CityResourceTest extends JerseyTest {
    @Override
    protected Application configure(){
        return new JerseyConfig();
    }

    @Test
    public void testFindCitiesSuccess(){
        List<Map<String, String>> cities = target("cities").request().get(List.class);

        assertNotNull(cities);
        assertEquals(cities.size(), 1);

        Map<String, String> city = cities.get(0);
        assertEquals(city.get("name"), "Odessa");
    }

    @Test
    public void testFindCityByIdSuccess(){
        CityDTO city = target("cities/1").request().get(CityDTO.class);
        assertNotNull(city);
        assertEquals(city.getId(), 1);
        assertEquals(city.getName(), "Odessa");
    }

    @Test
    public void testFindCityByIdNotFound(){
        Response response = target("cities/2").request().get(Response.class);
        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testFindCityByIdInvalid(){
        Response response = target("cities/aaab").request().get(Response.class);
        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }


    @Test
    public void testSaveCitySuccess(){
        CityDTO city = new CityDTO();
        city.setName("Kiev");

        Response response = target("cities").request().post(Entity.entity(city, MediaType.APPLICATION_JSON));
        assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
    }



}
