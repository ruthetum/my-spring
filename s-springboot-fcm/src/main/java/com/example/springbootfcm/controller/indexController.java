package com.example.springbootfcm.controller;

import com.example.springbootfcm.dto.MessageDto;
import com.example.springbootfcm.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class indexController {

    private final FirebaseCloudMessageService fcmService;

    @PostMapping("/")
    public void sendMessage(
            @RequestBody MessageDto request
            ) {
        fcmService.sendMessageTo(
                request.getTargetToken(),
                request.getTitle(),
                request.getBody(),
                request.getPath()
        );
    }
}
