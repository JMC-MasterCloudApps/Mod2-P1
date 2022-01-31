package es.codeurjc.mastercloudapps.planner.service;

import es.codeurjc.mastercloudapps.planner.clients.TopoClient;
import es.codeurjc.mastercloudapps.planner.models.EoloplantCreationRequest;
import es.codeurjc.mastercloudapps.planner.models.Eoloplant;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlannerService {

	private final TopoClient topoClient;

	@RabbitListener(queues = "eoloplantCreationRequests", ackMode = "AUTO")
	public void createNewEoloplant(EoloplantCreationRequest request) {

		Eoloplant eoloplant = new Eoloplant(request.getId(), request.getCity());

		CompletableFuture<String> landscape = topoClient.getLandscape(request.getCity());

		CompletableFuture<Void> allFutures = CompletableFuture.allOf(landscape);

		eoloplant.advanceProgress();

		landscape.thenAccept(eoloplant::addPlanning);

		allFutures.thenRun(() -> {
			simulateProcessWaiting();
			eoloplant.processPlanning();
		});
	}

	private void simulateProcessWaiting() {
		try {
			Thread.sleep((long) (Math.random() * 2000 + 1000));
		} catch (InterruptedException e) {}
	}
}
