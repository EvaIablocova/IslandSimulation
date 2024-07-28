package com.javarush.island.iablocova.view.guiview.controller;

import com.javarush.island.iablocova.config.IslandConfig;
import com.javarush.island.iablocova.entity.CustomData;
import com.javarush.island.iablocova.view.guiview.IslandMainApplication;
import com.javarush.island.iablocova.view.guiview.util.GUIExchanger;
import com.javarush.island.iablocova.view.guiview.util.GUISynchronizer;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.BrokenBarrierException;

public class IslandConfigPaneController
{
    @FXML
    private Slider widthSlider;
    @FXML
    private Slider heightSlider;
    @FXML
    private Slider dayLengthSlider;
    @FXML
    private TextField widthTextField;
    @FXML
    private TextField heightTextField;
    @FXML
    private TextField dayLengthTextField;

    private IslandMainApplication islandMainApplication;

    public IslandConfigPaneController()
    {
    }

    @FXML
    private void initialize()
    {
        widthTextField.setText(String.valueOf(widthSlider.getValue()));
        heightTextField.setText(String.valueOf(heightSlider.getValue()));
        dayLengthTextField.setText(String.valueOf(dayLengthSlider.getValue()));

        HashMap<Slider, TextField> sliders = new HashMap<>();
        sliders.put(widthSlider, widthTextField);
        sliders.put(heightSlider, heightTextField);
        sliders.put(dayLengthSlider, dayLengthTextField);

        for(Slider s : sliders.keySet())
        {
            s.valueProperty().addListener((observable, oldValue, newValue) -> showSliderValue(sliders.get(s), newValue));
        }
    }

    @FXML
    private void handleExit()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(islandMainApplication.getPrimaryStage());
        alert.setTitle("Выход");
        alert.setHeaderText("Вы уверены, что хотите выйти?");
        ButtonType buttonTypeNo = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeYes = new ButtonType("Да", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes)
            System.exit(0);
    }

    @FXML
    private void handleRun()
    {
        int width = (int) widthSlider.getValue();
        int height = (int) heightSlider.getValue();
        int dayLength = (int) dayLengthSlider.getValue() * 1000;
        CustomData customData = new CustomData(width, height, dayLength);
        IslandConfig.width = width;
        IslandConfig.height = height;
        IslandConfig.dayLength = dayLength;
        try
        {
            GUIExchanger.customDataExchanger.exchange(customData);
            islandMainApplication.initIslandRootLayout();
            islandMainApplication.displayCreatures();
            GUISynchronizer.BARRIER.await();
        }
        catch (InterruptedException | BrokenBarrierException e)
        {
            e.printStackTrace();
        }
        islandMainApplication.startMonitoringStatistics();
    }

    private void showSliderValue(TextField textField, Number newValue)
    {
        textField.setText(String.valueOf(newValue));
    }

    public void setIslandMainApplication(IslandMainApplication islandMainApplication)
    {
        this.islandMainApplication = islandMainApplication;
    }
}