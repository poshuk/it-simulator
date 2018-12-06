package org.itsimulator.germes.app.rest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.math.NumberUtils;
import org.itsimulator.germes.app.model.entity.geography.City;
import org.itsimulator.germes.app.model.entity.transport.TransportType;
import org.itsimulator.germes.app.rest.dto.CityDTO;
import org.itsimulator.germes.app.rest.service.base.BaseResource;
import org.itsimulator.germes.app.service.GeographicService;
import org.itsimulator.germes.app.service.impl.GeographicServiceImpl;
import org.itsimulator.germes.app.service.transform.Transformer;
import org.itsimulator.germes.app.service.transform.impl.SimpleDTOTransformer;

@Path("cities")
/**
 * {@link CityResource} is REST web-service that handles city-related requests  
 * @author Morenets
 *
 */
public class CityResource extends BaseResource{
    private final GeographicService service;
    private final Transformer transformer;
    public CityResource(){
        transformer = new SimpleDTOTransformer();
        service = new GeographicServiceImpl();
        City city = new City("Odessa");
        city.addStation(TransportType.AUTO);
        service.saveCity(city);
    }


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CityDTO> findCities() {
		return service.findCities().stream().map((city) -> transformer.transform(city, CityDTO.class)).collect(Collectors.toList());
	}

	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveCity(CityDTO cityDTO){
        service.saveCity(transformer.untransform(cityDTO, City.class));
    }

    @Path("/{cityId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCityById(@PathParam("cityId") final String cityId){
        if (!NumberUtils.isCreatable(cityId)){
            return BAD_REQUEST;
        }

        Optional<City> city = service.findCityById(NumberUtils.toInt(cityId));
        if (!city.isPresent()){
            return NOT_FOUND;
        }

        return ok(transformer.transform(city.get(), CityDTO.class));
    }


}
