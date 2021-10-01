package org.example.dmaker.service;

import org.example.dmaker.dto.CreateDeveloperDto;
import org.example.dmaker.dto.DeveloperDetailDto;
import org.example.dmaker.dto.DeveloperDto;
import org.example.dmaker.entity.Developer;
import org.example.dmaker.exception.DmakerErrorCode;
import org.example.dmaker.exception.DmakerException;
import org.example.dmaker.repository.DeveloperRepository;
import org.example.dmaker.repository.RetiredDeveloperRepository;
import org.example.dmaker.type.DeveloperLevel;
import org.example.dmaker.type.DeveloperSkillType;
import org.example.dmaker.type.StatusCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.example.dmaker.exception.DmakerErrorCode.DUPLICATED_MEMBER_ID;
import static org.example.dmaker.type.DeveloperLevel.SENIOR;
import static org.example.dmaker.type.DeveloperSkillType.BACK_END;
import static org.example.dmaker.type.StatusCode.EMPLOYED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DmakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DmakerService dmakerService;

    private final Developer defaultDeveloper =
            Developer.builder()
                .developerLevel(SENIOR)
                .developerSkillType(BACK_END)
                .experienceYears(12)
                .statusCode(EMPLOYED)
                .name("name")
                .age(35)
                .build();

    private final CreateDeveloperDto.Request defaultCreateRequest =
            CreateDeveloperDto.Request.builder()
                    .developerLevel(SENIOR)
                    .developerSkillType(BACK_END)
                    .experienceYears(12)
                    .memberId("memberId")
                    .name("name")
                    .age(35)
                    .build();

    @Test
    public void testSomething() {
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        DeveloperDetailDto developerDetail = dmakerService.getDeveloperDetail("memberId");

        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(BACK_END, developerDetail.getDeveloperSkillType());
        assertEquals(12, developerDetail.getExperienceYears());
    }

    @Test
    void createDeveloperTest_success() {
        // given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        // when
        CreateDeveloperDto.Response developer = dmakerService.createDeveloper(defaultCreateRequest);

        // then
        verify(developerRepository, times(1))
                .save(captor.capture());
        Developer saveDeveloper = captor.getValue();
        assertEquals(SENIOR, saveDeveloper.getDeveloperLevel());
        assertEquals(BACK_END, saveDeveloper.getDeveloperSkillType());
        assertEquals(12, saveDeveloper.getExperienceYears());
    }

    @Test
    void createDeveloperTest_failed_with_duplicated() {
        // given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        // when
        // then
        DmakerException dmakerException = assertThrows(DmakerException.class,
                () -> dmakerService.createDeveloper(defaultCreateRequest));

        assertEquals(DUPLICATED_MEMBER_ID, dmakerException.getDmakerErrorCode());
    }
}