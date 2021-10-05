package org.example.dmaker.controller;

import org.example.dmaker.dto.DeveloperDto;
import org.example.dmaker.service.DmakerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.example.dmaker.type.DeveloperLevel.JUNIOR;
import static org.example.dmaker.type.DeveloperLevel.SENIOR;
import static org.example.dmaker.type.DeveloperSkillType.BACK_END;
import static org.example.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DmakerController.class)
class DmakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DmakerService dmakerService;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void getAllDevelopers() throws Exception {
        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(SENIOR)
                .developerSkillType(BACK_END)
                .memberId("member1").build();
        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(JUNIOR)
                .developerSkillType(FRONT_END)
                .memberId("member2").build();

        given(dmakerService.getAllEmployedDevelopers())
                .willReturn(Arrays.asList(seniorDeveloperDto, juniorDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.[0].developerSkillType",
                                is(BACK_END.name()))
                ).andExpect(
                        jsonPath("$.[0].developerLevel",
                                is(SENIOR.name()))
                ).andExpect(
                        jsonPath("$.[1].developerSkillType",
                                is(FRONT_END.name()))
                ).andExpect(
                        jsonPath("$.[1].developerLevel",
                                is(JUNIOR.name()))
                );
    }
}