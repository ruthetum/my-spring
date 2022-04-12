package com.example.housebatch.core.service;

import com.example.housebatch.core.entity.Lawd;
import com.example.housebatch.core.repository.LawdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LawdService {

    private final LawdRepository lawdRepository;

    @Transactional
    public void upsert(Lawd lawd) {
        // 데이터가 존재하면 수정, 없으면 생성
        Lawd saved = lawdRepository.findByLawdCd(lawd.getLawdCd())
                .orElseGet(() -> Lawd.create(lawd.getLawdCd(), lawd.getLawdDong(), lawd.getExist()));

        saved.update(lawd.getLawdCd(), lawd.getLawdDong(), lawd.getExist());
        lawdRepository.save(saved);
    }
}
