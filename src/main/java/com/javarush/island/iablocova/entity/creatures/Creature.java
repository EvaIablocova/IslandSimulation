package com.javarush.island.iablocova.entity.creatures;

import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.view.View;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public interface Creature extends Runnable
{
    void setView(View view);

    String getImagePath();
    int getSpeed();
    void setImageView(ImageView imageView);
    ImageView getImageView();
    void setCurrentCell(Cell currentCell);
    Cell getCurrentCell();
    double getWeight();
    void setWeight(double weight);
    double getMaxWeight();
    int getMatureAge();
    int getAge();
    boolean isMale();
    boolean isBred();
    void setBred(boolean bred);
    void setAge(int age);
    boolean isAlive();
    void setWantToEat(double wantToEat);
    ReentrantLock getLock();
    int getMaxNumberPerCell();
    int getMaxSpeed();
    double getMassOfFood();
    int getMaxAge();
    int getMaxEnergy();
    int getEnergy();
    double getWantToEat();
    void setSpeed(int speed);
    void setAlive(boolean alive);
    void setEnergy(int energy);
    void setMale(boolean male);
    HashMap<Class<? extends Creature>, Integer> getFoodMap();

    View getView();
}
