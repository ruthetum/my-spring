package com.example.security.service;

import com.example.security.entity.Notice;
import com.example.security.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<Notice> findAll() {
        return noticeRepository.findAll(Sort.by(Direction.DESC, "id"));
    }

    public Notice saveNotice(String title, String content) {
        Notice notice = Notice.builder()
                .title(title)
                .content(content)
                .build();

        return noticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        noticeRepository.findById(id).ifPresent(noticeRepository::delete);
    }
}
