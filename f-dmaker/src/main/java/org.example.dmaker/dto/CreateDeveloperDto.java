package org.example.dmaker.dto;

import lombok.*;
import org.example.dmaker.type.DeveloperLevel;
import org.example.dmaker.type.DeveloperSkillType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateDeveloperDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "memberId min 3 max 50")
        private String memberId;
        @NotNull
        @Size(min = 3, max = 20, message = "name min 3 max 20")
        private String name;
        @Min(18)
        private String age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;

        private String memberId;
        private String name;
        private String age;
    }
}
