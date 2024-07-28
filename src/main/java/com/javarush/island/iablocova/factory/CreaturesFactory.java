package com.javarush.island.iablocova.factory;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.creatures.animals.Animal;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.exception.CreateObjectException;
import com.javarush.island.iablocova.view.View;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class CreaturesFactory
{
    private static final YAMLMapper MAPPER = new YAMLMapper();
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static <T> T getInstance(Class<T> clazz, Cell currentCell, View view) throws IOException, CreateObjectException
    {
        String yamlFile = getYAMLPath(clazz);
        T creature = MAPPER.readValue(readYAMLContent(yamlFile), clazz);
        ImageView imageView = getImageViewForCreature(creature);
        ((Creature) creature).setImageView(imageView);
        ((Creature) creature).setCurrentCell(currentCell);
        ((Creature) creature).setView(view);
        setParameters((Creature) creature);
        return creature;
    }

    private static <T> String getYAMLPath(Class<T> clazz) throws CreateObjectException
    {
        if (!clazz.isAnnotationPresent(Config.class))
            throw new CreateObjectException(String.format("Класс %s должен иметь аннотацию @Config!", clazz.getSimpleName())); //TODO определить тип Exception

        Config config = clazz.getAnnotation(Config.class);
        return config.yamlFile();
    }

    private static String readYAMLContent(String yamlFile)
    {
        String yamlContent = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(yamlFile)))
        {
            StringBuilder builder = new StringBuilder();
            while (reader.ready())
            {
                builder.append(reader.readLine()).append("\n");
            }
            yamlContent = builder.toString();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return yamlContent;
    }

    private static <T> ImageView getImageViewForCreature(T creature)
    {
        String s = ((Creature)creature).getImagePath();
        Image image;
        try(FileInputStream fileInputStream = new FileInputStream(s))
        {
            image = new Image(fileInputStream);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return new ImageView(image);
    }

    private static void setParameters(Creature creature)
    {
        if (creature instanceof Animal)
        {
            creature.setAge(RANDOM.nextInt( creature.getMaxAge() ));
            creature.setMale(RANDOM.nextBoolean());
            creature.setWantToEat(creature.getMassOfFood() == 0 ? 0 : RANDOM.nextDouble( creature.getMassOfFood() / 2, creature.getMassOfFood() ));
        }
    }

    public static Creature clone(Creature creature)
    {
        Creature newInstance = ((Animal) creature).clone();

        ImageView imageView = getImageViewForCreature(newInstance);
        newInstance.setImageView(imageView);
        newInstance.setCurrentCell(creature.getCurrentCell());
        setParameters(newInstance);
        return newInstance;
    }
}
