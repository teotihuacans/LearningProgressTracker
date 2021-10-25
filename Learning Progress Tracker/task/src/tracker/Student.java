package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Student {
    private int UniqueID;
    private String email;
    private String firstName;
    private String lastName;

    private int ScoreJava = 0;
    private int ScoreDSA = 0;
    private int ScoreDB = 0;
    private int ScoreSpring = 0;

    public Student(int uid, String email, String firstName, String lastName) {
        this.UniqueID = uid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int uid, String email, String firstName, String lastName,
                   int scj, int sdsa, int sdb, int ss) {
        this.UniqueID = uid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;

        this.ScoreJava = scj;
        this.ScoreDSA = sdsa;
        this.ScoreDB = sdb;
        this.ScoreSpring = ss;
    }

    public int getUniqueID() {
        return UniqueID;
    }

    public int getScoreJava() {
        return ScoreJava;
    }

    public int getScoreDSA() {
        return ScoreDSA;
    }

    public int getScoreDB() {
        return ScoreDB;
    }

    public int getScoreSpring() {
        return ScoreSpring;
    }

    public int getScore(String subjectNme) {
        switch (subjectNme) {
            case "Java" : return ScoreJava;
            case "DSA" : return ScoreDSA;
            case "Databases" : return ScoreDB;
            case "Spring" : return ScoreSpring;
        }
        return 0;
    }

    public void setAllScores(String[] values) {
        ScoreJava += Integer.parseInt(values[1]);
        ScoreDSA += Integer.parseInt(values[2]);
        ScoreDB += Integer.parseInt(values[3]);
        ScoreSpring += Integer.parseInt(values[4]);
    }

    @Override
    public String toString() {
        return String.format("%d\t%d\t%d\t%d\t%d", UniqueID, ScoreJava, ScoreDSA, ScoreDB, ScoreSpring);
    }

    public int[] getAllScores() {
        return new int[] {ScoreJava, ScoreDSA, ScoreDB, ScoreSpring};
    }

    public String getInfo() {
        return UniqueID + " points: Java=" + ScoreJava + "; DSA=" + ScoreDSA + "; Databases=" + ScoreDB +
                "; Spring=" + ScoreSpring;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String pointsInfo() {
        return String.format("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d", UniqueID,
                ScoreJava, ScoreDSA, ScoreDB, ScoreSpring);
    }

    public String pointsSubjectInfo(String subjectName, Model model) {
        return String.format("%d\t%d\t%.1f",
                UniqueID,
                getScore(subjectName),
                new BigDecimal(Double.toString(getScore(subjectName) * 1.0 / model.getSubjects().stream()
                        .filter(e -> subjectName.equals(e.getName()))
                        .findAny().get().getMaxScore() * 100.0))
                        .setScale(1, RoundingMode.HALF_UP)
        ) + "%";
    }
}
