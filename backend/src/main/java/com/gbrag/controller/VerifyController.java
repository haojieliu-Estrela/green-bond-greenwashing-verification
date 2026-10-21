package com.gbrag.controller;

import com.gbrag.dto.VerifyRequest;
import com.gbrag.dto.VerifyResponse;
import jakarta.validation.Valid;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
@CrossOrigin(origins = "http://localhost:5173")
public class VerifyController {

    private static final String VERIFY_PROMPT = """
            你是绿色债券洗绿核验专家。请对以下内容进行核验，判断是否存在洗绿风险。
            请按以下格式输出（不要输出多余内容）：
            结论：[低风险 / 中风险 / 高风险]
            依据：[具体说明]

            待核验内容：
            %s
            """;

    private final ChatClient chatClient;

    public VerifyController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping
    public VerifyResponse verify(@Valid @RequestBody VerifyRequest request) {
        String raw = chatClient
                .prompt()
                .user(String.format(VERIFY_PROMPT, request.getContent()))
                .call()
                .content();

        String conclusion = extract(raw, "结论：", "依据：");
        String evidence = extract(raw, "依据：", null);

        return new VerifyResponse(conclusion, evidence);
    }

    private String extract(String text, String start, String end) {
        int s = text.indexOf(start);
        if (s < 0) return text.trim();
        s += start.length();
        if (end == null) return text.substring(s).trim();
        int e = text.indexOf(end, s);
        return e < 0 ? text.substring(s).trim() : text.substring(s, e).trim();
    }
}
