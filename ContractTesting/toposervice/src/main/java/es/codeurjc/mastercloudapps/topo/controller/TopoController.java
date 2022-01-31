package es.codeurjc.mastercloudapps.topo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import es.codeurjc.mastercloudapps.topo.model.City;
import es.codeurjc.mastercloudapps.topo.model.CityDTO;
import es.codeurjc.mastercloudapps.topo.service.TopoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/topographicdetails")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopoController {

    private final TopoService topoService;

    @GetMapping(value = "/{city}")
    public ResponseEntity<CityDTO> getCity(@PathVariable String city) {
        log.info(city);

        return ResponseEntity.ok(toCityDTO(topoService.getCity(city)));
    }

    private CityDTO toCityDTO(City city) {

        log.info(city.toString());
        return new CityDTO(city.id(), city.landscape());
    }

}
