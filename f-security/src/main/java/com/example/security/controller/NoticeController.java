package com.example.security.controller;

import com.example.security.dto.NoteRegisterDto;
import com.example.security.entity.Notice;
import com.example.security.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String getNotice(Model model) {
        log.info("GET /notice");
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices", notices);
        return "notice/index";
    }

    @PostMapping
    public String postNotice(@ModelAttribute NoteRegisterDto.Request noteDto) {
        log.info("POST /notice");
        noticeService.saveNotice(noteDto.getTitle(), noteDto.getContent());
        return "redirect:notice";
    }

    @DeleteMapping
    public String deleteNotice(@RequestParam Long id) {
        log.info("DELETE /notice");
        noticeService.deleteNotice(id);
        return "redirect:notice";
    }
}
