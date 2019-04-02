package com.sofya.primenumbers;

import java.sql.Time;
import java.util.Comparator;
import java.util.Date;

public class Statistic  {
    private String date;
    private Integer steps;
    private Integer resultSum;

    public Statistic(String date, Integer steps, Integer resultSum)

    {
        this.date = date;
        this.steps = steps;
        this.resultSum = resultSum;
    }

    @Override
    public String toString() {
        return "" + date + " steps " + steps +
                " sum " + resultSum;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public void setResultSum(Integer resultSum) {
        this.resultSum = resultSum;
    }

    public String getDate() {
        return date;
    }

    public Integer getSteps() {
        return steps;
    }

    public Integer getResultSum() {
        return resultSum;
    }




    public static Comparator<Statistic> compareResult() {
        return new Comparator<Statistic>() {
            public int compare(Statistic o1, Statistic o2) {
                if (o1.getResultSum() > o2.getResultSum())
                    return 1; // highest value first
                if (o1.getResultSum().equals(o2.getResultSum()))
                    return 0;
                return -1;    }
        };
    }

    public static Comparator<Statistic> compareSteps() {
        return new Comparator<Statistic>() {
            public int compare(Statistic o1, Statistic o2) {
                if (o1.getSteps() > o2.getSteps())
                    return 1; // highest value first
                if (o1.getSteps().equals(o2.getSteps()))
                    return 0;
                return -1;    }
        };
    }
}
