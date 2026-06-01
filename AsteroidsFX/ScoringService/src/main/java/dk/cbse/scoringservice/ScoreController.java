package dk.sdu.cbse.scoringservice;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final List<ScoreEntry> scores = new ArrayList<>();

    //POST /scores?name=Player1&score=150
    //Submit a new score entry.
    @PostMapping
    public String addScore(@RequestParam String name, @RequestParam int score) {
        scores.add(new ScoreEntry(name, score));
        System.out.println("[ScoringService] Score received: " + name + " = " + score);
        return "Score saved: " + name + " = " + score;
    }


    //GET /scores/highest
    //Returns the highest score entry.
    @GetMapping("/highest")
    public ScoreEntry getHighest() {
        return scores.stream()
                .max(Comparator.comparingInt(ScoreEntry::getScore))
                .orElse(new ScoreEntry("Nobody", 0));
    }

    //GET /scores
    //Returns all score entries.
    @GetMapping
    public List<ScoreEntry> getAllScores() {
        return scores;
    }
}