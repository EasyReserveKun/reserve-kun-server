package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CustomQuestionAnsweringService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuestionAnsweringController {

    private final CustomQuestionAnsweringService cqaService;

    @CrossOrigin
    @PostMapping("/search")
    public Map<String, Object> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        return cqaService.getAnswer(question);
    }

    @GetMapping("/ask")
    public Map<String, Object> askQuestion(@RequestParam String question) {
        return cqaService.getAnswer(question);
    }
}
