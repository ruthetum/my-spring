package org.example.dmaker.service;

import lombok.RequiredArgsConstructor;
import org.example.dmaker.dto.CreateDeveloperDto;
import org.example.dmaker.entity.Developer;
import org.example.dmaker.repository.DeveloperRepository;
import org.example.dmaker.type.DeveloperLevel;
import org.example.dmaker.type.DeveloperSkillType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DmakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper( CreateDeveloperDto.Request request) {
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .experienceYears(2)
                .name("Olaf")
                .age(28)
                .build();

        developerRepository.save(developer);
    }
}
