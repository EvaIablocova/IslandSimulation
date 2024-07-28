package com.javarush.island.iablocova.view.guiview.controller;

import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.creatures.plants.Plant;
import com.javarush.island.iablocova.repository.SetOfCreaturesTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;

public class IslandStatisticsLayoutController
{
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;

    private final ObservableList<String> creaturesNames = FXCollections.observableArrayList();
    private Stage statisticsStage;

    @FXML
    public void initialize()
    {
        for(Class<? extends Creature> clazz : SetOfCreaturesTypes.SET_OF_CREATURES_TYPES)
        {
            if (clazz != Plant.class)
                creaturesNames.add(clazz.getSimpleName());
        }
        xAxis.setCategories(creaturesNames);
    }

    public void setCreaturesData(Statistics statistics)
    {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        HashMap<Class<? extends Creature>, Integer> allCreaturesPopulation = statistics.getAllCreaturesPopulation();
        for (Class<? extends Creature> clazz : allCreaturesPopulation.keySet())
        {
            series.getData().add(new XYChart.Data<>(clazz.getSimpleName(), allCreaturesPopulation.get(clazz)));
        }
        barChart.getData().add(series);
    }

    @FXML
    public void handleCloseStatistics()
    {
        statisticsStage.close();
    }

    public void setStatisticsStage(Stage statisticsStage)
    {
        this.statisticsStage = statisticsStage;
    }
}