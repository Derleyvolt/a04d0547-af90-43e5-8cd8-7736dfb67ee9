package com.challenge.checkout.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tenants", uniqueConstraints = { @UniqueConstraint(columnNames = {"name", "baseURL"}) })
public class TenantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Pattern(regexp="^[a-z][a-z0-9][a-zA-Z0-9]*$")
    private String baseURL;
}
