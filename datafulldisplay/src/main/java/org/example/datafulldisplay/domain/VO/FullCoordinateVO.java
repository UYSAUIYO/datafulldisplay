package org.example.datafulldisplay.domain.VO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.datafulldisplay.domain.FullCoordinate;

@EqualsAndHashCode(callSuper = true)
@Data
public class FullCoordinateVO extends FullCoordinate {
    private String imageUrl;
}
