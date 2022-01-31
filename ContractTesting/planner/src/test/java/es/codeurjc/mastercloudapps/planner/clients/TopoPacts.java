package es.codeurjc.mastercloudapps.planner.clients;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static es.codeurjc.mastercloudapps.planner.clients.TopoClient.TOPO_HOST;
import static es.codeurjc.mastercloudapps.planner.clients.TopoClient.TOPO_PORT;
import static java.lang.String.format;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Slf4j
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "topo-provider", port = "8080")
class TopoPacts {

  private static final String TOPO_PATH = "/api/topographicdetails/";
  private static final String MADRID = "Madrid";
  private static final String LANDSCAPE = "Flat";
  private static final String GIVEN = format("City '%s' exists", MADRID);
  private static final String DESCRIPTION = format("get city with name '%s'", MADRID);

  @Pact(consumer = "planner-consumer-rest")
  public RequestResponsePact getCity(PactDslWithProvider builder) {

    return builder.given(GIVEN)
        .uponReceiving(DESCRIPTION)
        .path(TOPO_PATH + MADRID)
        .method("GET")
        .willRespondWith()
        .status(200)
        .headers(Map.of("Content-Type", "application/json"))
        .body(newJsonBody(object -> {
          object.stringType("id", MADRID);
          object.stringType("landscape", LANDSCAPE);
        }).build())
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "getCity")
  void verifyGetCity(MockServer mockServer) {

    RestTemplate restTemplate = new RestTemplateBuilder().rootUri(mockServer.getUrl() + TOPO_PATH + MADRID).build();
    var client = new TopoClient(restTemplate);
    var response = client.getLandscape(MADRID);

    assertEquals(LANDSCAPE, response.join());
  }
}
