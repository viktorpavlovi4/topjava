package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).stream().forEach(s -> System.out.println(s.toString()));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList,
                                                                   LocalTime startTime,
                                                                   LocalTime endTime,
                                                                   int caloriesPerDay) {
        //return filtered list with correctly exceeded field
        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

        for (UserMeal meal : mealList) {
            LocalDateTime mealTime = meal.getDateTime();
            Integer calories = meal.getCalories();

            caloriesPerDayMap.merge(mealTime.toLocalDate(), meal.getCalories(), Integer::sum);

            if (TimeUtil.isBetween(mealTime.toLocalTime(), startTime, endTime))
                mealWithExceedList.add(new UserMealWithExceed(mealTime, meal.getDescription(), calories, false));
        }

        for (UserMealWithExceed mealWithExceed : mealWithExceedList) {
            LocalDate mealWithExceedDate = mealWithExceed.getDateTime().toLocalDate();
            mealWithExceed.setExceed(caloriesPerDayMap.get(mealWithExceedDate) > caloriesPerDay);
        }

        return mealWithExceedList;
        /*   Map<LocalDate, Integer> mealMap = mealList
                .stream()
                .collect(Collectors.toMap(m -> m.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return mealList
                .stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), mealMap.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        */
    }
}