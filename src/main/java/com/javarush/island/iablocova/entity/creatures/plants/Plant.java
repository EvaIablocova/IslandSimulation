package com.javarush.island.iablocova.entity.creatures.plants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.exception.ParametersException;
import com.javarush.island.iablocova.view.View;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Plant implements Creature, Runnable {
    public double weight;
    public boolean isAlive;
    public int maxAge;
    public int maxNumberPerCell;
    public String imagePath;

    @JsonIgnore
    protected ImageView imageView;
    @JsonIgnore
    protected Cell currentCell;
    @JsonIgnore
    View view;

    public ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run()
    {
        this.grow();
    }

    private void grow()
    {
        if(weight < 1024)
            weight *= 2;
    }

    @Override
    public void setView(View view)
    {
        this.view = view;
    }

    @Override
    public String getImagePath()
    {
        return imagePath;
    }

    @Override
    public int getSpeed()
    {
        return 0;
    }

    @Override
    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }

    @Override
    public ImageView getImageView()
    {
        return imageView;
    }

    @Override
    public void setCurrentCell(Cell currentCell)
    {
        this.currentCell = currentCell;
    }

    @Override
    public double getWeight()
    {
        return weight;
    }

    @Override
    public double getMaxWeight()
    {
        return weight;
    }

    @Override
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    @Override
    public int getMatureAge()
    {
        throw new ParametersException("Ошибка! Параметры конфигурации размножения к растениям не применимы!");
    }

    @Override
    public int getAge()
    {
        throw new ParametersException("Ошибка! Параметры конфигурации возраста к растениям не применимы!");
    }

    @Override
    public boolean isMale()
    {
        throw new ParametersException("Ошибка! Параметры конфигурации пола к растениям не применимы!");
    }

    @Override
    public boolean isBred()
    {
        throw new ParametersException("Ошибка! Параметры конфигурации размножения к растениям не применимы!");
    }

    @Override
    public void setBred(boolean bred)
    {
        throw new ParametersException("Ошибка! Параметры конфигурации размножения к растениям не применимы!");
    }

    @Override
    public void setAge(int age)
    {
        throw new ParametersException("Ошибка! Нельзя устанавливать растениям возраст!");
    }

    @Override
    public boolean isAlive()
    {
        return isAlive;
    }

    @Override
    public void setWantToEat(double wantToEat)
    {
        throw new ParametersException("Ошибка! Параметры голода неприменимы к растениям");
    }

    @Override
    public ReentrantLock getLock()
    {
        return lock;
    }

    @Override
    public int getMaxNumberPerCell()
    {
        return maxNumberPerCell;
    }

    @Override
    public int getMaxSpeed()
    {
        throw new ParametersException("Ошибка! Параметры скорости неприменимы к растениям!");
    }

    @Override
    public double getMassOfFood()
    {
        throw new ParametersException("Ошибка! Параметры съедаемой еды неприменимы к растениям!");
    }

    @Override
    public int getMaxAge()
    {
        throw new ParametersException("Ошибка! Параметры возраста неприменимы к растениям!");
    }

    @Override
    public int getMaxEnergy()
    {
        throw new ParametersException("Ошибка! Параметры энергии неприменимы к растениям!");
    }

    @Override
    public int getEnergy()
    {
        throw new ParametersException("Ошибка! Параметры энергии неприменимы к растениям!");
    }

    @Override
    public double getWantToEat()
    {
        throw new ParametersException("Ошибка! Параметры съедаемой еды неприменимы к растениям!");
    }

    @Override
    public void setSpeed(int speed)
    {
        throw new ParametersException("Ошибка! Параметры скорости неприменимы к растениям!");
    }

    @Override
    public void setAlive(boolean isAlive)
    {
        this.isAlive = isAlive;
    }

    @Override
    public void setEnergy(int energy)
    {
        throw new ParametersException("Ошибка! Параметры энергии неприменимы к растениям!");
    }

    @Override
    public void setMale(boolean male)
    {
        throw new ParametersException("Ошибка! Параметры пола неприменимы к растениям!");
    }

    @Override
    public HashMap<Class<? extends Creature>, Integer> getFoodMap()
    {
        throw new ParametersException("Ошибка! Параметры съедаемой еды неприменимы к растениям!");
    }

    @Override
    public View getView()
    {
        return view;
    }

    @Override
    public Cell getCurrentCell()
    {
        return currentCell;
    }
}
