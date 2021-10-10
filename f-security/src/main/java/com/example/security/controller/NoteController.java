package com.example.security.controller;

import com.example.security.dto.NoteRegisterDto;
import com.example.security.entity.Note;
import com.example.security.entity.User;
import com.example.security.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public String getNote(Authentication authentication, Model model) {
        log.info("GET /note");
        User user = (User) authentication.getPrincipal();
        List<Note> notes = noteService.findByUser(user);
        model.addAttribute("notes", notes);
        return "note/index";
    }

    @PostMapping
    public String saveNote(Authentication authentication, @ModelAttribute NoteRegisterDto.Request noteDto) {
        log.info("POST /note");
        User user = (User) authentication.getPrincipal();
        noteService.saveNote(user, noteDto.getTitle(), noteDto.getContent());
        return "redirect:note";
    }

    @DeleteMapping
    public String deleteNote(Authentication authentication, @RequestParam Long id) {
        log.info("DELETE /note");
        User user = (User) authentication.getPrincipal();
        noteService.deleteNote(user, id);
        return "redirect:note";
    }
}
