package com.javarush.island.iablocova.util;

import com.javarush.island.iablocova.constants.Text;
import com.javarush.island.iablocova.entity.Coordinates;
import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.Result;
import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.creatures.animals.Animal;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.repository.SetOfCreaturesTypes;
import com.javarush.island.iablocova.repository.ResultCode;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public class IslandScheduler implements Timeable
{
    private final Island island;
    private final int width;
    private final int height;
    private long dayLength;
    private Exchanger<Statistics> exchanger;
    private ScheduledExecutorService dailyExecutor;


    public IslandScheduler(Island island, Exchanger<Statistics> exchanger)
    {
        this.island = island;
        this.width = island.getCustomData().getWidth();
        this.height = island.getCustomData().getHeight();
        this.dayLength = island.getCustomData().getDayLength();
        this.exchanger = exchanger;
    }

    public void startActivity()
    {
        dailyExecutor = Executors.newScheduledThreadPool(2);
        dailyExecutor.scheduleAtFixedRate(task, 0, dayLength, TimeUnit.MILLISECONDS);						// Пока ещё Main-thread
    }

    Runnable task = () -> {																								// Вот отсюда уже не Main-thread.
        Instant startDay = Instant.now();
        Instant stopDayLoad;
        long dayLoadTimeMillis;

        try
        {
            manageCreatureLife();
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
            downloadActionsOfTheDay(executor);																			// Запуск потоков, ответственных за действия животных

            stopDayLoad = Instant.now();																				// Но здесь первый каскад, поток, ответственный за день
            dayLoadTimeMillis = getMillis(startDay, stopDayLoad);

            if (dayLoadTimeMillis < dayLength)
            {
                Thread.sleep((long) (dayLength * 0.95 - dayLoadTimeMillis));

            }
            else
            {
                System.err.println("Не хватило времени для запуска дня!");
            }

            stopActionsOfTheDay(executor);

            Statistics statistics = countStatistics();																	// В самом конце дня выполняются сервисные функции

            Instant stopDay = Instant.now();
            long dayLengthMillis = getMillis(startDay, stopDay);
            String dayLoadTime = String.format(Text.DAY_LOAD, dayLoadTimeMillis);
            String dayLength = String.format(Text.DAY_LENGTH, dayLengthMillis);
            String statisticsTime = String.format(Text.STATISTICS_LENGTH, statistics.getTime());
            Result result = new Result(ResultCode.OK, dayLength, dayLoadTime, statisticsTime);
            statistics.setResult(result);
            exchanger.exchange(statistics);																				// В конце каждого дня отдельный Thread высчитывает и возвращает статистику

            if (statistics.getPopulation() == 0)
                dailyExecutor.shutdown();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
    };

    private void manageCreatureLife() throws InterruptedException
    {
        manageCreatureLifeParameters();
        collectDeadCreatures();
    }

    private void manageCreatureLifeParameters() throws InterruptedException
    {
        CreatureParametersManager parametersManager = new CreatureParametersManager(island);
        Thread parametersManagerThread = new Thread(parametersManager, "CreatureParametersManager");
        parametersManagerThread.start();
        parametersManagerThread.join();
    }

    private void collectDeadCreatures() throws InterruptedException
    {
        DeadsCollector deadsCollector = new DeadsCollector(island);
        Thread deadsCollectorThread = new Thread(deadsCollector, "DeadsCollector");
        deadsCollectorThread.start();
        deadsCollectorThread.join();
    }

    private void downloadActionsOfTheDay(ScheduledThreadPoolExecutor executor)
    {
        Cell[][] cells = island.getCells();
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        ArrayList<Class<? extends Creature>> listOfCreatureTypes = new ArrayList<>(SetOfCreaturesTypes.SET_OF_CREATURES_TYPES);

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                coordinates.add(new Coordinates(x, y));
            }
        }

        Collections.shuffle(coordinates);
        Collections.shuffle(listOfCreatureTypes);

        for(Coordinates coordinate : coordinates)
        {
            for(Class<? extends Creature> clazz : listOfCreatureTypes)
            {
                ConcurrentHashMap<Class<? extends Creature>, Set<Creature>> cellCreatures = cells[coordinate.getY()][coordinate.getX()].getCellCreatures();
                Set<Creature> cellCreaturesOfSpecies = cellCreatures.get(clazz);
                for (Creature creature : cellCreaturesOfSpecies)
                {
                    if (creature instanceof Animal)																		// Вот отсюда второй каскад потоков, в которых животные непосредственно выполняют свои действия. Действия совершают только животные
                    {
                        long initialDelay = creature.getSpeed() == 0 ? (long) ( dayLength * 0.3 ) : (long) (( dayLength / creature.getSpeed()) * 0.3);
                        long period = creature.getSpeed() == 0 ? dayLength : dayLength / creature.getSpeed();
                        executor.scheduleAtFixedRate(creature, initialDelay, period, TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }

    private void stopActionsOfTheDay(ScheduledThreadPoolExecutor executor)
    {
        try
        {
            executor.shutdown();
            boolean activitiesIsFinished = executor.awaitTermination((long)(dayLength * 0.02), TimeUnit.MILLISECONDS);
            if (!activitiesIsFinished)
                System.err.println("Не хватило длины дня для выполнения всех действий животных. Пожалуйста, увеличьте значение длины дня");
        }
        catch (InterruptedException e)
        {
            System.err.println("Задания были прерваны.");
            e.printStackTrace();
        }
        finally
        {
            if (!executor.isTerminated())
            {
                System.err.println("Принудительное завершение всех действий животных.");
                executor.shutdownNow();
                System.err.println("Действия животных принудительно завершены.");
            }
            else
                System.out.println("Все действия животных успешно завершились до окончания дня");
        }
    }

    private Statistics countStatistics() throws ExecutionException, InterruptedException
    {
        MonitoringStatistics monitoringStatistics = new MonitoringStatistics(island);
        FutureTask<Statistics> futureTask = new FutureTask<>(monitoringStatistics);
        Thread countStatistics = new Thread(futureTask, "StatisticsCounter");
        countStatistics.start();
        return futureTask.get();
    }
}
