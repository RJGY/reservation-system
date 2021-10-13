package com.reservation_system.model;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AmenityDTO {

    private Long id;

    @NotNull
    private Integer amenityType;

}
