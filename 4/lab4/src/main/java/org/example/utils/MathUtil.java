package org.example.utils;

import java.util.HashMap;
import java.util.Map;

public class MathUtil {
    public static double getMean(Map<Integer, Integer> valueCountMap) {
        Map<Integer, Double> valueProbability = getProbabilityMap(valueCountMap);
        double mean = valueProbability.entrySet().stream()
                .mapToDouble(entry -> entry.getKey() * entry.getValue())
                .sum();
        return mean;
    }

    public static double getVariance(Map<Integer, Integer> valueCountMap) {
        Map<Integer, Double> valueProbability = getProbabilityMap(valueCountMap);
        double variance = valueProbability.entrySet().stream()
                .mapToDouble(entry -> Math.pow(entry.getKey(), 2) * entry.getValue())
                .sum();
        variance -= Math.pow(getMean(valueCountMap), 2);
        return variance;
    }

    private static Map<Integer, Double> getProbabilityMap(Map<Integer, Integer> valueCountMap) {
        int sum = valueCountMap.values().stream()
                .mapToInt(x -> x)
                .sum();
        Map<Integer, Double> valueProbability = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : valueCountMap.entrySet()) {
            valueProbability.put(entry.getKey(), 1.0 * entry.getValue() / sum);
        }
        return valueProbability;
    }

    public static double getDeviation(Map<Integer, Integer> valueCountMap) {
        return Math.sqrt(getVariance(valueCountMap));
    }
}
