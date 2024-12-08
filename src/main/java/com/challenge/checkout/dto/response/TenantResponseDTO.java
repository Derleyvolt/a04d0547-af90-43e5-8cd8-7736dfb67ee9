package com.challenge.checkout.dto.response;

import com.challenge.checkout.gateway.MappingFormatEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponseDTO {
    @NotNull
    private Long id;

    @NotBlank
    @Size(min=2, max=20)
    private String name;

    @NotBlank
    @Size(min=5, max=55)
    private String baseURL;

    @NotNull
    private String mappingFormat;
}
