package org.example.dmaker.service;

import lombok.RequiredArgsConstructor;
import org.example.dmaker.dto.CreateDeveloperDto;
import org.example.dmaker.entity.Developer;
import org.example.dmaker.exception.DmakerErrorCode;
import org.example.dmaker.exception.DmakerException;
import org.example.dmaker.repository.DeveloperRepository;
import org.example.dmaker.type.DeveloperLevel;
import org.example.dmaker.type.DeveloperSkillType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.example.dmaker.exception.DmakerErrorCode.DUPLICATED_MEMBER_ID;
import static org.example.dmaker.exception.DmakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@Service
@RequiredArgsConstructor
public class DmakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper( CreateDeveloperDto.Request request) {
        validateCreateDeveloper(request);

        // business logic
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .experienceYears(2)
                .name("Olaf")
                .age(28)
                .build();

        developerRepository.save(developer);
    }

    private void validateCreateDeveloper(CreateDeveloperDto.Request request) {
        // business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();
        if (developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DmakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DmakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DmakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DmakerException(DUPLICATED_MEMBER_ID);
                }));
    }
}
