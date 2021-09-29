package org.example.dmaker.dto;

import lombok.*;
import org.example.dmaker.exception.DmakerErrorCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DmakerErrorResponse {
    private DmakerErrorCode errorCode;
    private String errorMessage;
}
