package com.challenge.checkout.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantRequestDTO {
    @NotBlank
    @Size(min=2, max=20)
    private String name;

    @NotBlank
    @Size(min=5, max=55)
    private String baseURL;

    @NotBlank
    private String mappingFormat;
}
