package mk.ukim.finki.np.vezbanjekol1;

import java.util.Scanner;

public class ApplicantEvaluationTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int creditScore = scanner.nextInt();
        int employmentYears = scanner.nextInt();
        boolean hasCriminalRecord = scanner.nextBoolean();
        int choice = scanner.nextInt();
        Applicant applicant = new Applicant(name, creditScore, employmentYears, hasCriminalRecord);
        Evaluator.TYPE type = Evaluator.TYPE.values()[choice];
        Evaluator evaluator;
        try {
            evaluator = EvaluatorBuilder.build(type);
            System.out.println("Applicant");
            System.out.println(applicant);
            System.out.println("Evaluation type: " + type.name());
            if (evaluator.evaluate(applicant)) {
                System.out.println("Applicant is ACCEPTED");
            } else {
                System.out.println("Applicant is REJECTED");
            }
        } catch (InvalidEvaluation invalidEvaluation) {
            System.out.println("Invalid evaluation");
        }
    }
}

class Applicant {
    private String name;

    private int creditScore;
    private int employmentYears;
    private boolean hasCriminalRecord;

    Applicant(String name, int creditScore, int employmentYears, boolean hasCriminalRecord) {
        this.name = name;
        this.creditScore = creditScore;
        this.employmentYears = employmentYears;
        this.hasCriminalRecord = hasCriminalRecord;
    }

    int getCreditScore() {
        return creditScore;
    }

    int getEmploymentYears() {
        return employmentYears;
    }

    boolean hasCriminalRecord() {
        return !hasCriminalRecord;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nCredit score: %d\nExperience: %d\nCriminal record: %s\n",
                name, creditScore, employmentYears, hasCriminalRecord ? "Yes" : "No");
    }
}

interface Evaluator {
    enum TYPE {
        NO_CRIMINAL_RECORD,
        MORE_EXPERIENCE,
        MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE,
        MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE,
        INVALID // should throw exception
    }

    boolean evaluate(Applicant applicant);
}

class EvaluatorBuilder {
    static Evaluator build(Evaluator.TYPE type) throws InvalidEvaluation {
        switch (type) {
            case NO_CRIMINAL_RECORD:
                return (Applicant::hasCriminalRecord);
            case MORE_EXPERIENCE:
                return applicant -> applicant.getEmploymentYears() >= 10;
            case MORE_CREDIT_SCORE:
                return (applicant -> applicant.getCreditScore() > 500);
            case NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE:
                return new ComboEvaluator((Applicant::hasCriminalRecord), (applicant -> applicant.getCreditScore() > 500));
            case MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE:
                return new ComboEvaluator(applicant -> applicant.getEmploymentYears() >= 10, (applicant -> applicant.getCreditScore() > 500));
            case NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE:
                return new ComboEvaluator((Applicant::hasCriminalRecord), applicant -> applicant.getEmploymentYears() >= 10);
            case INVALID:
            default:
                throw new InvalidEvaluation();
        }
    }
}

class ComboEvaluator implements Evaluator {
    private Evaluator evaluator1;
    private Evaluator evaluator2;

    ComboEvaluator(Evaluator evaluator1, Evaluator evaluator2) {
        this.evaluator1 = evaluator1;
        this.evaluator2 = evaluator2;
    }

    @Override
    public boolean evaluate(Applicant applicant) {
        return evaluator1.evaluate(applicant) && evaluator2.evaluate(applicant);
    }
}

class InvalidEvaluation extends Exception {
    InvalidEvaluation() {
        super("Invalid Evaluation");
    }
}