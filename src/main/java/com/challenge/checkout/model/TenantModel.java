package com.challenge.checkout.model;

import com.challenge.checkout.gateway.MappingFormatEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tenants")
public class TenantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String baseURL;

    @OneToMany(
            mappedBy = "tenant",
            cascade  = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BasketModel> baskets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private MappingFormatModel mappingFormat;
}
