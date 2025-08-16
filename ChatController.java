package com.example.demo.ChatController;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private static final Map<String, List<String>> JOKES = Map.of(
        "general", List.of(
            "Why did the computer go to therapy? Too many tabs open.",
            "I told my laptop I needed a break—it said, 'Great, I'll update.'",
            "Why don't programmers like nature? Too many bugs."
        ),
        "sad", List.of(
            "Cheer up! Even the darkest night will end and the sun will rise.",
            "Don’t be sad, pizza still exists! 🍕"
        ),
        "angry", List.of(
            "What do you call a fake noodle? An Impasta.",
            "Why did the scarecrow win an award? Because he was outstanding in his field."
        ),
        "normal", List.of(
            "What do you call a fish with no eyes? Fsh.",
            "Why was the math book sad? It had too many problems."
        )
    );

    private static final Map<String, List<String>> QUOTES = Map.of(
        "general", List.of(
            "Small steps count—today's inch is tomorrow's mile.",
            "You've survived 100% of your hard days so far."
        ),
        "sad", List.of(
            "When it rains, look for rainbows. When it's dark, look for stars."
        ),
        "angry", List.of(
            "Anger is one letter short of danger.",
            "Stay calm. Time heals everything."
        ),
        "normal", List.of(
            "The best way to predict the future is to create it.",
            "Life is what happens when you're busy making other plans."
        )
    );

    @GetMapping("/item")
    public Map<String, String> getItem(@RequestParam String mood, @RequestParam String type) {
        // normalize inputs
        String m = mood == null ? "general" : mood.toLowerCase(Locale.ROOT).trim();
        String t = normalizeType(type);

        List<String> list;
        switch (t) {
            case "jokes" -> list = JOKES.getOrDefault(m, JOKES.get("general"));
            case "quotes" -> list = QUOTES.getOrDefault(m, QUOTES.get("general"));
            default -> {
                return Map.of("result", "Invalid type. Use jokes or quotes.");
            }
        }

        if (list == null || list.isEmpty()) {
            return Map.of("result", "No items available");
        }

        String item = list.get(ThreadLocalRandom.current().nextInt(list.size()));
        return Map.of("result", item);
    }

    private String normalizeType(String type) {
        if (type == null) return "jokes";
        String t = type.toLowerCase(Locale.ROOT).trim();
        if (t.equals("joke")) t = "jokes";
        if (t.equals("quote")) t = "quotes";
        return t;
    }
}