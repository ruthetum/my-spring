package com.example.security.service;

import com.example.security.entity.Note;
import com.example.security.entity.User;
import com.example.security.exception.UserException;
import com.example.security.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.security.exception.UserErrorCode.NO_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional(readOnly = true)
    public List<Note> findByUser(User user) {
        if (user == null) {
            throw new UserException(NO_USER);
        }
        if (user.isAdmin()) {
            return noteRepository.findAll(Sort.by(Direction.DESC, "id"));
        }
        return noteRepository.findByUserOrderByIdDesc(user);
    }

    public Note saveNote(User user, String title, String content) {
        if (user == null) {
            throw new UserException(NO_USER);
        }

        Note note = Note.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();

        return noteRepository.save(note);
    }

    public void deleteNote(User user, Long noteId) {
        if (user == null) {
            throw new UserException(NO_USER);
        }

        Note note = noteRepository.findByIdAndUser(noteId, user);
        if (note != null) {
            noteRepository.delete(note);
        }
    }
}
