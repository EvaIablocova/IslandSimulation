package com.javarush.island.iablocova.view.guiview;

import com.javarush.island.iablocova.config.IslandConfig;
import com.javarush.island.iablocova.entity.CustomData;
import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.island.Cell;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;

public class IslandPane extends Pane
{
    private Pane root;
    private final int width;
    private final int height;
    private int cellSize;
    private StackPane[][] stackPanes;
    private final Island island;

    public IslandPane(Island island)
    {
        this.island = island;
        CustomData customData = island.getCustomData();
        width = customData.getWidth();
        height = customData.getHeight();
    }

    /**
     * Вызывается в IslandMainApplication для установки сцены острова
     * @return Возвращает объект типа Pane готовой главной сцены острова с заполненным контентом.
     */
    public Pane getIslandPane()
    {
        initialize();
        return createAndFormatPane();
    }

    /**
     * Проверяет поступившие значения и записывает количество ячеек по вертикали и горизонтали. Вызывается при установке
     * главной сцены острова. Устанавливает размеры ячейки в зависимости от количества ячеек. Для каждой ячейки создаёт
     * прямоугольник, передаёт соответствующий объект типа Cell в каждую ячейку, который унаследован от класса GridPane.
     * Каждому объекту StackPane соответствует свой объект Cell, индексы совпадают.
     * Таким образом, ячейка представляет собой объект-стек StackPane, внутри которого имеется прямоугольник и объект
     * Cell, унаследованный от GridPane.
     */
    public void initialize()
    {
        cellSize = Math.min(1400 / width, 700 / height);
        IslandConfig.cellSize = cellSize;
        IslandConfig.sceneHeight = height * cellSize + 50;
        IslandConfig.sceneWidth = width * cellSize + 50;
        stackPanes = new StackPane[height][width];
        Cell[][] cells = island.getCells();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                this.stackPanes[y][x] = new StackPane(new Rectangle(), cells[y][x]);         // Инициализация каждой отдельной ячейки, размещение в объекте StackPane созданного прямоугольника и соответствующего объекта Cell ячейки, унаследованного от класса GridPane.
            }
        }
    }

    /**
     * Инициализирует главную сцену, устанавливает её размеры. Устанавливает для главной сцены изображение на заднем
     * плане. Для каждой ячейки устанавливает размер прямоугольника, равный размеру ячейки, и его цвета. Размещает все
     * ячейки на сцене острова каждую в своих координатах.
     * @return Возвращает отформатированную основную сцену.
     */
    private Pane createAndFormatPane()
    {
        root = new Pane();

        root.setPrefSize(IslandConfig.sceneWidth, IslandConfig.sceneHeight);
        root.setMaxSize(IslandConfig.sceneWidth, IslandConfig.sceneHeight);
        createBackgroundImage();

        for (int y = 0; y < height; ++y)
        {
            for (int x = 0; x < width; ++x)
            {
                ObservableList<Node> children = stackPanes[y][x].getChildren();
                Rectangle rectangle;
                Cell cell;
                if (children.size() > 0)
                {
                    rectangle = (Rectangle) children.get(0);
                    rectangle.setWidth(cellSize - 0.1);
                    rectangle.setHeight(cellSize - 0.1);
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setFill(Color.LIGHTGREEN);
                    cell = (Cell) children.get(1);
                    cell.setPrefWidth(cellSize - 1);
                    cell.setPrefHeight(cellSize - 1);
                    stackPanes[y][x].setLayoutX(x * cellSize + 25);
                    stackPanes[y][x].setLayoutY(y * cellSize + 25);
                    root.getChildren().add(stackPanes[y][x]);
                }
            }
        }
        return root;
    }

    /**
     * Устанавливает изображение заднего фона для острова.
     */
    private void createBackgroundImage()
    {
        InputStream inputStream = IslandPane.class.getResourceAsStream("/img/screen.png");
        assert inputStream != null : "Ошибка! Фоновый рисунок острова не загружен!";
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(IslandConfig.sceneWidth);
        imageView.setFitHeight(IslandConfig.sceneHeight);
        root.getChildren().add(imageView);
    }
}
