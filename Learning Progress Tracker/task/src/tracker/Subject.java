package tracker;

import java.math.RoundingMode;

public class Subject {
    private String name;
    private int index;
    private int maxScore;
    private int popular = 0;
    private int activity = 0; //sum of all the points per subject
    private double averageGrade = 0.0;

    public Subject(int index, String name, int maxScore) {
        this.name = name;
        this.maxScore = maxScore;
        this.index = index;
    }

    public void setPopular(int popular) {
        this.popular = popular; //только при первом наборе очков инкрементируем
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public void setAverageGrade() {
        if (popular > 0 && activity > 0) {
            averageGrade =  activity / popular;
        }
    }

    public String getName() {
        return name;
    }

    public int getActivity() {
        return activity;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getPopular() {
        return popular;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("name = %s, maxScore = %d, popular = %d, activity = %d, averageGrade = %.2f;\n",
                name, maxScore, popular, activity, averageGrade);
    }
}
