package com.coderman.worldgdp.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryLanguage {
    private Country country;
    @NotNull private Stringg countryCode;
    @NotNull @Size(max = 30)private String language;
    @NotNull @Size(max = 1,min = 1) private String isOfficial;
    @NotNull private Double percentage;
}
