package es.codeurjc.mastercloudapps.planner.clients;

import es.codeurjc.mastercloudapps.planner.models.LandscapeResponse;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class TopoClient {

    static final String TOPO_HOST = "localhost";
    static final int TOPO_PORT = 8080;

    private RestTemplate rest;

    public TopoClient(RestTemplate template) {
        rest = template;
    }

    @Async
    public CompletableFuture<String> getLandscape(String city) {

        String url = "http://"+TOPO_HOST+":"+TOPO_PORT+"/api/topographicdetails/" + city;
        log.info(url);
        LandscapeResponse response = rest.getForObject(url, LandscapeResponse.class);
        
        return CompletableFuture.completedFuture(response.getLandscape());
    }
}
