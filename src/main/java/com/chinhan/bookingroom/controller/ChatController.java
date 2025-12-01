package com.chinhan.bookingroom.controller;

import com.chinhan.bookingroom.dto.ApiResponse;
import com.chinhan.bookingroom.dto.request.ChatRequest;
import com.chinhan.bookingroom.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/chat")
public class ChatController {
    ChatService chatService;

    @PostMapping
    public ApiResponse<String> chat(@RequestBody ChatRequest request){
        return ApiResponse.<String>builder()
                .result(chatService.chat(request))
                .build();
    }

    @PostMapping("/chat-with-image")
    public ApiResponse<String> chatWithImage(@RequestParam("file") MultipartFile file,
                                             @RequestParam("message") String message){
        return ApiResponse.<String>builder()
                .result(chatService.chatWithImage(file,message))
                .build();
    }

}
