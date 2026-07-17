package com.s_giken.training.webapp.model.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Charge {

    @Nullable
    private Long chargeid;

    @NotBlank
    @Size(min = 1, max = 127)
    private String name;

    @NotNull
    @Digits(integer = 9, fraction = 0)
    private java.math.BigDecimal amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Nullable
    private LocalDate endDate;

}
