package com.javarush.island.iablocova.view.guiview.util;

import com.javarush.island.iablocova.view.guiview.IslandMainApplication;

import static javafx.application.Application.launch;

public class GUIStarter implements Runnable
{
    @Override
    public void run()
    {
        launch(IslandMainApplication.class);
    }
}

