package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * CustomQuestionAnsweringService.java
 * チャットボットに纏わる処理を実装するクラス
 * @author のうみそ＠overload
 */
@Service
public class CustomQuestionAnsweringService {

    @Value("${azure.cqa.endpoint}")
    private String endpoint;

    @Value("${azure.cqa.api-key}")
    private String apiKey;

    @Value("${azure.cqa.project-name}")
    private String projectName;

    @Value("${azure.cqa.deployment-name}")
    private String deploymentName;


    private final RestTemplate restTemplate;

    public CustomQuestionAnsweringService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * チャットボットへ質問を送信し、返答を受け取るためのメソッド
     * @param 送信する質問文
     * @return 送信した質問に最も近い質問と回答のペアと、信頼度スコア等の詳細情報
     */
    public Map<String, Object> getAnswer(String question) {
        String url = endpoint + "language/:query-knowledgebases?projectName=" + projectName + "&api-version=2021-10-01&deploymentName=" + deploymentName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", 1);
        requestBody.put("question", question);
        requestBody.put("confidenceScoreThreshold", 0.3);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

        return response.getBody();
    }
}
