package com.example.utils;

import java.util.ArrayList;
import java.util.List;

public class ScheduleUtils {

    public static String generateScheduleString(int date, int time) {
        // 星期几的映射
        String dayOfWeek = getDayOfWeek(date);

        // 时间段的映射
        String timeRange = getTimeRange(time);

        return dayOfWeek + " " + timeRange;
    }

    public static String generateScheduleTable(int date, int time) {
        // 星期几的映射
        String dayOfWeek = getDayOfWeekSchedule(date);

        // 时间段的映射
        String timeRange = getTimeRangeSchedule(time);

        return dayOfWeek + "-" + timeRange + "-2";
    }

    public static String schedule(Integer d, Integer t) {
        List<String> schedules = new ArrayList<>();
        List<Integer> dates = splitNumber(d);
        List<Integer> times = splitNumber(t);

        // 遍历每个日期和时间组合，生成对应的字符串
        for (int i = 0; i < Math.min(dates.size(), times.size()); i++) {
            int date = dates.get(i);
            int time = times.get(i);

            String schedule = generateScheduleString(date, time);
            schedules.add(schedule);
        }

        // 使用逗号拼接所有字符串
        return String.join("，", schedules);
    }

    public static String scheduleTable(Integer d, Integer t) {
        List<String> schedules = new ArrayList<>();
        List<Integer> dates = splitNumber(d);
        List<Integer> times = splitNumber(t);

        // 遍历每个日期和时间组合，生成对应的字符串
        for (int i = 0; i < Math.min(dates.size(), times.size()); i++) {
            int date = dates.get(i);
            int time = times.get(i);

            String schedule = generateScheduleTable(date, time);
            schedules.add(schedule);
        }

        // 使用逗号拼接所有字符串
        return String.join("，", schedules);
    }

    private static String getDayOfWeek(int date) {
        // 星期几的映射关系
        String[] daysOfWeek = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

        // 映射为实际的星期几，注意处理越界
        int dayIndex = (date - 1) % daysOfWeek.length;
        return daysOfWeek[dayIndex];
    }

    private static String getTimeRange(int time) {
        // 时间段的映射关系
        String[] timeRanges = {"8:00 ~ 9:40", "10:10 ~ 11:50", "14:00 ~ 15:40", "16:10 ~ 17:50", "18:30 ~ 20:00"};

        // 映射为实际的时间段，注意处理越界
        int timeIndex = (time - 1) % timeRanges.length;
        return timeRanges[timeIndex];
    }

    private static String getDayOfWeekSchedule(int date) {
        // 星期几的映射关系
        String[] daysOfWeek = {"1", "2", "3", "4", "5", "6", "7"};

        // 映射为实际的星期几，注意处理越界
        int dayIndex = (date - 1) % daysOfWeek.length;
        return daysOfWeek[dayIndex];
    }

    private static String getTimeRangeSchedule(int time) {
        // 时间段的映射关系
        String[] timeRanges = {"1", "3", "5", "7", "9"};

        // 映射为实际的时间段，注意处理越界
        int timeIndex = (time - 1) % timeRanges.length;
        return timeRanges[timeIndex];
    }

    public static List<Integer> splitNumber(int number) {
        List<Integer> digits = new ArrayList<>();

        while (number > 0) {
            // 取数字的最后一位
            int digit = number % 10;

            // 添加到列表
            digits.add(0, digit);

            // 去掉数字的最后一位
            number /= 10;
        }

        return digits;
    }
}

