package com.example.security.repository;

import com.example.security.entity.Note;
import com.example.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserOrderByIdDesc(User user);

    Note findByIdAndUser(Long id, User user);
}