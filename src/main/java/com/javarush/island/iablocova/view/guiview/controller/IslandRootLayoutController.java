package com.javarush.island.iablocova.view.guiview.controller;

import com.javarush.island.iablocova.view.guiview.IslandMainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class IslandRootLayoutController
{
    private IslandMainApplication islandMainApplication;

    @FXML
    public void handleStatistics()
    {
        islandMainApplication.showStatisticsDialog();
    }

    @FXML
    public void handleExit()
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

    public void setIslandMainApplication(IslandMainApplication islandMainApplication)
    {
        this.islandMainApplication = islandMainApplication;
    }
}
