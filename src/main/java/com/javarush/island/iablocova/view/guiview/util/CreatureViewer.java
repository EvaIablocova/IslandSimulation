package com.javarush.island.iablocova.view.guiview.util;

import com.javarush.island.iablocova.config.IslandConfig;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.island.Cell;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CreatureViewer
{
    private CreatureViewer()
    {
    }

    public static void showPicturesInCell(Cell cell)
    {
        ConcurrentHashMap<Class<? extends Creature>, Set<Creature>> cellCreatures = cell.getCellCreatures();

        for(Class<? extends Creature> clazz : cellCreatures.keySet())
        {
            Set<Creature> species = cellCreatures.get(clazz);
            for (Creature creature : species)
            {
                ImageView imageView = creature.getImageView();

                imageView.setFitWidth(IslandConfig.cellSize - 2);
                imageView.setFitHeight(IslandConfig.cellSize - 2);

                cell.getChildren().add(imageView);
            }
        }
    }

    public static void moveCreaturePicture(Creature creature, Cell oldCell)
    {
        ImageView imageView = creature.getImageView();
        imageView.setFitWidth(IslandConfig.cellSize - 2);
        imageView.setFitHeight(IslandConfig.cellSize - 2);

        synchronized (oldCell.getChildren())
        {
            ObservableList<Node> oldCellChildren = oldCell.getChildren();
            oldCellChildren.remove(imageView);
        }
        synchronized (creature.getCurrentCell().getChildren())
        {
            ObservableList<Node> newCellChildren = creature.getCurrentCell().getChildren();
            newCellChildren.add(imageView);
        }
    }

    public static void deleteCreaturePicture(Creature creature)
    {
        ImageView imageView = creature.getImageView();
        imageView.setFitWidth(IslandConfig.cellSize - 2);
        imageView.setFitHeight(IslandConfig.cellSize - 2);
        synchronized (creature.getCurrentCell().getChildren())
        {
            ObservableList<Node> children = creature.getCurrentCell().getChildren();
            children.remove(imageView);
        }
    }

    public static void showCreaturePicture(Creature creature)
    {
        ImageView imageView = creature.getImageView();
        imageView.setFitWidth(IslandConfig.cellSize - 2);
        imageView.setFitHeight(IslandConfig.cellSize - 2);
        synchronized (creature.getCurrentCell().getChildren())
        {
            ObservableList<Node> children = creature.getCurrentCell().getChildren();
            children.add(imageView);
        }
    }
}
