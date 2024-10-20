package org.example.datafulldisplay.service;


import org.example.datafulldisplay.domain.FullCoordinate;
import org.example.datafulldisplay.result.GlobalResult;

import java.io.IOException;

public interface IFullCoordinateService {

    public GlobalResult selectFullCoordinateList(FullCoordinate fullCoordinate);

    public GlobalResult addFullCoordinate(FullCoordinate fullCoordinate) throws IOException;

}
