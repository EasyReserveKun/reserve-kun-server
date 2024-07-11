package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CustomQuestionAnsweringService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuestionAnsweringController {

    private final CustomQuestionAnsweringService cqaService;

    @PostMapping("/search")
    public Map<String, Object> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        return cqaService.getAnswer(question);
    }
}
