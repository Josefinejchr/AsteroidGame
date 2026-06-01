package dk.sdu.cbse.scoring;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * REST client that reports scores to the external ScoringService.
 * Uses java.net.http (no Spring dependency needed in the game module).
 * Runs asynchronously so the game loop never blocks on HTTP.
 *
 * @pre  ScoringService is optionally running on localhost:8080
 * @post Score changes are reported to the external service if available;
 *       game continues normally if the service is offline
 */
public class ScoreClient implements IPostEntityProcessingService {

    private static final String SERVICE_URL = "http://localhost:8080/scores";
    private static final String PLAYER_NAME = "Player1";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private int lastReportedScore = 0;

    @Override
    public void process(GameData gameData, World world) {
        int currentScore = gameData.getScore();
        if (currentScore > lastReportedScore) {
            lastReportedScore = currentScore;
            postScore(currentScore);
        }
    }

    private void postScore(int score) {
        try {
            String url = SERVICE_URL + "?name=" + PLAYER_NAME + "&score=" + score;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(r -> System.out.println("[ScoreClient] Service responded: " + r.body()))
                    .exceptionally(e -> {
                        System.out.println("[ScoreClient] Service offline, score not reported");
                        return null;
                    });
        } catch (Exception e) {
            System.out.println("[ScoreClient] Could not reach scoring service");
        }
    }
}