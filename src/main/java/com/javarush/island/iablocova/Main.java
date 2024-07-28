package com.javarush.island.iablocova;

import com.javarush.island.iablocova.app.Application;
import com.javarush.island.iablocova.controller.Controller;
import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.exception.CreateObjectException;
import com.javarush.island.iablocova.view.View;
import com.javarush.island.iablocova.view.consoleview.ConsoleView;
import com.javarush.island.iablocova.view.guiview.GUIView;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws InterruptedException, CreateObjectException, IOException {
        View view = new GUIView();
        Controller controller = new Controller(view);
        Application application = new Application(controller);
        application.run();

        while (true) {
            try {
                Statistics statistics = application.getStatistics();
                application.printStatistics(statistics);
                if (statistics.getPopulation() == 0)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}