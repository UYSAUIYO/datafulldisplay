package org.example.datafulldisplay.service;


import org.example.datafulldisplay.domain.FullLight;
import org.example.datafulldisplay.result.GlobalResult;

public interface IFullLightService {

    public GlobalResult selectFullLightList(FullLight fullLight);

    public GlobalResult addFullLight(FullLight fullLight);
    GlobalResult getLatestLight();

}
