package com.example.RestDocs.member.contoller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void member_get() throws Exception {
        mockMvc.perform(
                get("/api/members/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void member_page_get() throws Exception {
        mockMvc.perform(
                get("/api/members")
                    .param("size", "10")
                    .param("page", "0")
                    .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void member_create() throws Exception {
        mockMvc.perform(
                post("/api/members/")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "   \"name\": \"name5\",\n" +
                        "   \"email\": \"member5@naver.com\"\n" +
                        "}")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void member_edit() throws Exception {
        mockMvc.perform(
                patch("/api/members/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"3name\"\n" +
                                "}")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void member_delete() throws Exception {
        mockMvc.perform(
                delete("/api/members/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}