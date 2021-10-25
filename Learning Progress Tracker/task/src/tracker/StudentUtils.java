package tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentUtils {
    Model model;

    public StudentUtils(Model model) {
        this.model = model;
    }

    public boolean checkIdIfExists(String inputNonSplitArray) {
        String id = parsePointsFormat(inputNonSplitArray)[0];
        return model.getAccounts().stream().noneMatch(e -> id.equals(String.valueOf(e.getUniqueID())));
    }

    public Student getStudent(String id) {
        return model.getAccounts().stream().filter(e -> id.equals(String.valueOf(e.getUniqueID()))).findFirst().get();
    }

    public boolean checkPointsFormat(String input) {
        return parseOnlyPointsFormat(input).length != 4 ||
                Arrays.stream(parseOnlyPointsFormat(input)).anyMatch(e -> !e.matches("[0-9]+")) ||
                Arrays.stream(parseOnlyPointsFormat(input)).anyMatch(e -> Integer.parseInt(e) < 0);
    }

    public String[] parsePointsFormat(String input) {
        return input.split("\\s+");
    }

    public String[] parseOnlyPointsFormat(String input) {
        String[] temp = input.split("\\s+");
        String[] result = new String[temp.length - 1];
        for(int i = 1; i < temp.length; i++) {
            result[i - 1] = temp[i];
        }
        return result;
    }

    public String generalStatistics() {

        model.getSubjects().forEach(e -> e.setPopular(
                model.getAccounts().stream().mapToInt(v -> v.getScore(e.getName()) > 0 ? 1 : 0).sum()
        ));

        model.getSubjects().forEach(e -> e.setActivity(
                model.getAccounts().stream().mapToInt(v -> v.getScore(e.getName())).sum()
        ));

        model.getSubjects().forEach(Subject::setAverageGrade);

        int maxPopular = model.getSubjects().stream().mapToInt(Subject::getPopular).max().orElse(0);
        int minPopular = model.getSubjects().stream().mapToInt(Subject::getPopular).
                filter(e -> e < maxPopular && e > 0).min().orElse(0);
        int maxActivity = model.getSubjects().stream().mapToInt(Subject::getActivity).max().orElse(0);
        int minActivity = model.getSubjects().stream().mapToInt(Subject::getActivity).
                filter(e -> e < maxActivity && e > 0).min().orElse(0);
        double maxGrade = model.getSubjects().stream().mapToDouble(Subject::getAverageGrade).max().orElse(0);
        double minGrade = model.getSubjects().stream().mapToDouble(Subject::getAverageGrade).
                filter(e -> e < maxGrade && e > 0).min().orElse(0);


        return String.format("Most popular: %s\n" +
                    "Least popular: %s\n" +
                    "Highest activity: %s\n" +
                    "Lowest activity: %s\n" +
                    "Easiest course: %s\n" +
                    "Hardest course: %s",
                    checkPopular(maxPopular),
                    checkPopular(minPopular),
                    checkActivity(maxActivity),
                    checkActivity(minActivity),
                    checkGrade(maxGrade),
                    checkGrade(minGrade)
                    );
    }
    private String checkPopular(int value) {
        if (value == 0) {
            return "n/a";
        } else {
            return model.getSubjects().stream().filter(e -> e.getPopular() == value).map(Subject::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    private String checkActivity(int value) {
        if (value == 0) {
            return "n/a";
        } else {
            return model.getSubjects().stream().filter(e -> e.getActivity() == value).map(Subject::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    private String checkGrade(double value) {
        if (value == 0) {
            return "n/a";
        } else {
            return model.getSubjects().stream().filter(e -> e.getAverageGrade() == value).map(Subject::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    public void checkNotify(Student student, String[] points) {
        model.getSubjects().stream().forEach(s -> {
            if (Integer.parseInt(points[s.getIndex()]) >= s.getMaxScore() &&
                    student.getScore(s.getName()) <
                            Integer.parseInt(points[s.getIndex()])) {
                model.addNotify(student, s);
            }
        });
    }

    public String getNotifications() {
        String result = "";
        for (Notify n : model.getNotifies()) {
            result += String.format("To: %s\n", n.getStudent().getEmail());
            result += "Re: Your Learning Progress\n";
            result += String.format("Hello, %s %s! You have accomplished our %s course!\n",
                    n.getStudent().getFirstName(), n.getStudent().getLastName(),
                    n.getSubject().getName());
        }

        result += String.format("Total %d students have been notified.",
                model.getNotifies().stream().map(Notify::getStudent).distinct().count());
        model.deleteNotify();
        return result;
    }

    public String subjectInfo(String subjectName) {
        String result = subjectName + "\nid\tpoints\tcompleted\n";
        switch (subjectName) {
            case "Java" : result += model.getAccounts().stream().filter(e -> e.getScore(subjectName) > 0)
                    .sorted(Comparator.comparingInt(Student::getScoreJava)
                            .reversed().thenComparingInt(Student::getUniqueID))
                    .map(e -> e.pointsSubjectInfo(subjectName, model))
                    .collect(Collectors.joining("\n")); break;

            case "DSA" : result += model.getAccounts().stream().filter(e -> e.getScore(subjectName) > 0)
                    .sorted(Comparator.comparingInt(Student::getScoreDSA)
                            .reversed().thenComparingInt(Student::getUniqueID))
                    .map(e -> e.pointsSubjectInfo(subjectName, model))
                    .collect(Collectors.joining("\n")); break;

            case "Databases" : result += model.getAccounts().stream().filter(e -> e.getScore(subjectName) > 0)
                    .sorted(Comparator.comparingInt(Student::getScoreDB)
                            .reversed().thenComparingInt(Student::getUniqueID))
                    .map(e -> e.pointsSubjectInfo(subjectName, model))
                    .collect(Collectors.joining("\n")); break;

            case "Spring" : result += model.getAccounts().stream().filter(e -> e.getScore(subjectName) > 0)
                    .sorted(Comparator.comparingInt(Student::getScoreSpring)
                            .reversed().thenComparingInt(Student::getUniqueID))
                    .map(e -> e.pointsSubjectInfo(subjectName, model))
                    .collect(Collectors.joining("\n")); break;
        }

        return result;
    }

}
