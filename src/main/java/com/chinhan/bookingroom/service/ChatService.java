package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.request.ChatRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
//@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {
    ChatClient chatClient;
    public ChatService(ChatClient.Builder builder){
        chatClient = builder.build();
    }

    public String chat(ChatRequest request){

        SystemMessage systemMessage = new SystemMessage("""
                You are DCN AI
                You should response with a funny
                """);

        UserMessage userMessage = new UserMessage(request.message());

        Prompt prompt = new Prompt(userMessage,systemMessage);

        return  chatClient
                .prompt(prompt)
                .call()
                .content();

    }

    public String chatWithImage(MultipartFile file, String message){
        Media media = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
                .data(file.getResource())
                .build();

        return chatClient
                .prompt()
                .system("You are DCN AI")
                .user(promptUserSpec -> promptUserSpec.media(media)
                        .text(message))
                .call()
                .content();
    }
}
