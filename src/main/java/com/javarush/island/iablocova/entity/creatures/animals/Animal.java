package com.javarush.island.iablocova.entity.creatures.animals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javarush.island.iablocova.config.IslandConfig;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.exception.CreateObjectException;
import com.javarush.island.iablocova.view.View;
import com.javarush.island.iablocova.util.*;
import javafx.scene.image.ImageView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Data
@NoArgsConstructor
public abstract class Animal implements Creature, Cloneable {

    private double maxWeight;
    private int speed;
    private int maxSpeed;
    private double massOfFood;
    private int matureAge;
    private int maxAge;
    private int maxEnergy;
    private int maxNumberPerCell;
    private String imagePath;

    private double weight;
    private boolean isAlive;
    private int energy;
    private int age;
    private boolean isMale;
    private boolean isBred;
    private double wantToEat;

    @JsonIgnore
    private ImageView imageView;
    @JsonIgnore
    private Cell currentCell;
    @JsonIgnore
    private final HashMap<Class<? extends Creature>, Integer> foodMap = new HashMap<>();
    @JsonIgnore
    private ReentrantLock lock = new ReentrantLock(true);
    @JsonIgnore
    private View view;

    @Override
    public void run() {
        try {
            boolean lockIsCaptured = this.lock.tryLock(IslandConfig.dayLength / 500, TimeUnit.MILLISECONDS);
            if (lockIsCaptured)
                decideWhatToDo();
            else
                System.err.println("*ProVoLokA************************************* Животное заблокировано!!! **************************************");
        } catch (CreateObjectException | InterruptedException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {
            System.err.println("*ProVoLokA************************************* Животное равно null!!! **************************************");
        } finally {
            if (this.lock.isHeldByCurrentThread())
                this.lock.unlock();
        }
    }

    private void decideWhatToDo() throws InterruptedException, CreateObjectException, IOException {
        if (!isAlive) {
            System.err.println("*ProVoLokA************************************* Мёртвое животное!!! **************************************");
            return;
        }
        if (wantToEat < massOfFood * 0.5) {
            if (Feeding.foodIsAvailable(this))
                Feeding.eat(this);
            else
                Moving.move(this);
        } else if (isMale && age > matureAge) {
            if (!Breeding.breed(this, Breeding.availablePair(this)))
                Moving.move(this);
        } else
            Moving.move(this);
    }

    @Override
    @SuppressWarnings("all")
    public Creature clone() {
        try {
            return (Creature) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
