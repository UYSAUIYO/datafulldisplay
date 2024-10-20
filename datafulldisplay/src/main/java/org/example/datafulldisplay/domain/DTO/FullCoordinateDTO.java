package org.example.datafulldisplay.domain.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.datafulldisplay.domain.FullCoordinate;

@EqualsAndHashCode(callSuper = true)
@Data
public class FullCoordinateDTO extends FullCoordinate {
    private String type;
}
