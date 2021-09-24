import ethicalengine.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * COMP90041, Sem2, 2020: Final Project
 * @author: leon.wuuu@gmail.com
 */

public class Audit {

    private String auditType = "Unspecified";
    private int runs = 0;
    private Scenario[] scenarios;
    private double totalAge = 0;
    private double humans = 0;
    Traits trait = new Traits();

    /**
     * No-argument constructor
     */
    public Audit(){}

    /**
     * Sets scenarios array
     * @param scenarios Scenario array that is being called
     */
    public Audit(Scenario[] scenarios) {
        this.scenarios = scenarios;
    }

    /**
     * Runs audit based on CSV file
     */

    public void run() {
        this.runs = scenarios.length;
        for (int i = 0; i < scenarios.length; i++) {
            EthicalEngine.Decision outcome = EthicalEngine.decide(scenarios[i]);
            updateTotal(scenarios[i]);
            updateStatistic(scenarios[i], outcome);
        }
    }

    /**
     * Performs audit on number of runs parsed
     * @param runs Number of runs that are performed
     */

    public void run(int runs) {
        this.runs += runs;
        ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
        for (int i = 0; i < runs; i++) {
            Scenario scenario = scenarioGenerator.generate();
            EthicalEngine.Decision outcome = EthicalEngine.decide(scenario);
            updateTotal(scenario);
            updateStatistic(scenario, outcome);
        }
    }

    /**
     * Controls flow interactive scenario if CSV is provided
     * @param scan Scanner Object
     */
    public void userRun(Scanner scan) {
        EthicalEngine.Decision outcome = null;
        String decision;
        int count = 0;
        for (int i = 0; i < scenarios.length; i++) {
            System.out.println(scenarios[i].toString());
            System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
            boolean validResponse = false;
            while (!validResponse) {
                decision = scan.nextLine();
                if (decision.equals("passenger") || decision.equals("passengers") || decision.equals("1")) {
                    outcome = EthicalEngine.Decision.PASSENGERS;
                    validResponse = true;
                }
                if (decision.equals("pedestrian") || decision.equals("pedestrians") || decision.equals("2")) {
                    outcome = EthicalEngine.Decision.PEDESTRIANS;
                    validResponse = true;
                }
            }
            updateTotal(scenarios[i]);
            updateStatistic(scenarios[i], outcome);
            count++;
            if (count % 3 == 0) {       //checks if 3 scenarios have been presented
                this.runs = count;
                String continueAudit = "";
                if (count == scenarios.length) {
                    break;
                }
                printStatistic();
                System.out.println("Would you like to continue? (yes/no)");
                boolean validContinue = false;
                while (!validContinue) {
                    try {
                        continueAudit = scan.nextLine();
                        if (!continueAudit.equals("yes") && !continueAudit.equals("no")) {
                            throw new InvalidInputException("Invalid response.");
                        }
                        else {
                            validContinue = true;
                        }
                    }
                    catch(InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (continueAudit.equals("no")) {
                    System.out.println("That's all. Press Enter to quit.");
                    scan.nextLine();
                    return;
                }
            }
        }
        this.runs = count;
        printStatistic();
        System.out.println("That's all. Press Enter to quit.");
        scan.nextLine();
    }

    /**
     * Controls flow interactive scenario and generates random Scenarios
     * @param scan Scanner Object
     */
    public void userRunRandom(Scanner scan) {
        EthicalEngine.Decision outcome = null;
        ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
        String decision;
        String continueAudit = "";
        boolean done = false;
        int count = 0;
        do {

            Scenario scenario = scenarioGenerator.generate();
            System.out.println(scenario.toString());
            System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
            boolean validResponse = false;
            while (!validResponse) {
                decision = scan.nextLine();
                if (decision.equals("passenger") || decision.equals("passengers") || decision.equals("1")) {
                    outcome = EthicalEngine.Decision.PASSENGERS;
                    validResponse = true;
                }
                if (decision.equals("pedestrian") || decision.equals("pedestrians") || decision.equals("2")) {
                    outcome = EthicalEngine.Decision.PEDESTRIANS;
                    validResponse = true;
                }
            }
            updateTotal(scenario);
            updateStatistic(scenario, outcome);
            count++;
            if (count % 3 == 0) {       //checks if 3 Scenarios have been presented
                this.runs = count;
                printStatistic();
                System.out.println("Would you like to continue? (yes/no)");
                boolean validContinue = false;
                while (!validContinue) {
                    try {
                        continueAudit = scan.nextLine();
                        if (!continueAudit.equals("yes") && !continueAudit.equals("no")) {
                            throw new InvalidInputException("Invalid response.");
                        } else {
                            validContinue = true;
                        }
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (continueAudit.equals("no")) {
                    done = true;
                    System.out.println("That's all. Press Enter to quit.");
                    scan.nextLine();
                    return;
                }
            }
        }
        while (!done);

        this.runs = count;
        printStatistic();
        System.out.println("That's all. Press Enter to quit.");
        scan.nextLine();
    }

    /**
     * Iterates through passengers and pedestrians arrays to update the total statistics
     * @param scenario Scenario that is being read from
     */
    public void updateTotal(Scenario scenario) {
        Persona[] totalList = scenario.getPassengers();
        for (int i = 0; i < totalList.length; i++) {
            if (totalList[i] instanceof Human) {
                trait.updateTotalMap(((Human) totalList[i]).getAgeCategory().name());
                trait.updateTotalMap((totalList[i]).getGender().name());
                trait.updateTotalMap(((Human) totalList[i]).getProfession().name());
                trait.updateTotalMap((totalList[i]).getBodyType().name());
                trait.updateTotalMap(((Human) totalList[i]).isPregnantString());
                trait.updateTotalMap(((Human) totalList[i]).isYouString());
                trait.updateTotalMap(scenario.isLegalCrossingString());
                trait.updateTotalMap("Passengers");
                trait.updateTotalMap("Human");
            }
            if (totalList[i] instanceof Animal) {
                trait.updateTotalMap("Animal");
                trait.updateTotalMap("Passengers");
                trait.updateTotalMap(((Animal) totalList[i]).getSpecies());

                trait.updateTotalMap(((Animal) totalList[i]).isPetString());
                trait.updateTotalMap(scenario.isLegalCrossingString());
            }
        }
        totalList = scenario.getPedestrians();
        for (int i = 0; i < totalList.length; i++) {
            if (totalList[i] instanceof Human) {
                trait.updateTotalMap(((Human) totalList[i]).getAgeCategory().name());
                trait.updateTotalMap((totalList[i]).getGender().name());
                trait.updateTotalMap(((Human) totalList[i]).getProfession().name());
                trait.updateTotalMap((totalList[i]).getBodyType().name());
                trait.updateTotalMap(((Human) totalList[i]).isPregnantString());
                trait.updateTotalMap(((Human) totalList[i]).isYouString());
                trait.updateTotalMap(scenario.isLegalCrossingString());
                trait.updateTotalMap("Pedestrians");
                trait.updateTotalMap("Human");
            }
            if (totalList[i] instanceof Animal) {
                trait.updateTotalMap("Animal");
                trait.updateTotalMap("Pedestrians");
                trait.updateTotalMap(((Animal) totalList[i]).getSpecies());
                trait.updateTotalMap(((Animal) totalList[i]).isPetString());
                trait.updateTotalMap(scenario.isLegalCrossingString());
            }
        }
    }

    /**
     * Updates values of survival hashmap based on who survives
     * @param scenario Scenario that is being read from
     */
    public void updateStatistic(Scenario scenario, EthicalEngine.Decision decision) {
        Persona[] survivors;
        if (decision == EthicalEngine.Decision.PASSENGERS) {
            survivors = scenario.getPassengers();
            for (int i = 0; i < survivors.length; i++) {
                if (survivors[i] instanceof Human) {
                    trait.updateSurviveMap(((Human) survivors[i]).getAgeCategory().name());
                    trait.updateSurviveMap((survivors[i]).getGender().name());
                    trait.updateSurviveMap(((Human) survivors[i]).getProfession().name());
                    trait.updateSurviveMap((survivors[i]).getBodyType().name());
                    trait.updateSurviveMap(((Human) survivors[i]).isPregnantString());
                    trait.updateSurviveMap(((Human) survivors[i]).isYouString());
                    trait.updateSurviveMap(scenario.isLegalCrossingString());
                    trait.updateSurviveMap("passengers");
                    trait.updateSurviveMap("Human");
                    totalAge += survivors[i].getAge();
                    humans += 1;

                }
                if (survivors[i] instanceof Animal) {
                    trait.updateSurviveMap("Animal");
                    trait.updateSurviveMap("passengers");
                    trait.updateSurviveMap(((Animal) survivors[i]).getSpecies());
                    trait.updateSurviveMap(((Animal) survivors[i]).isPetString());
                    trait.updateSurviveMap(scenario.isLegalCrossingString());


                }
            }
        }
        if (decision == EthicalEngine.Decision.PEDESTRIANS) {
            survivors = scenario.getPedestrians();
            for (int i = 0; i < survivors.length; i++) {
                if (survivors[i] instanceof Human) {
                    trait.updateSurviveMap(((Human) survivors[i]).getAgeCategory().name());
                    trait.updateSurviveMap((survivors[i]).getGender().name());
                    trait.updateSurviveMap(((Human) survivors[i]).getProfession().name());
                    trait.updateSurviveMap((survivors[i]).getBodyType().name());
                    trait.updateSurviveMap(((Human) survivors[i]).isPregnantString());
                    trait.updateSurviveMap(((Human) survivors[i]).isYouString());
                    trait.updateSurviveMap(scenario.isLegalCrossingString());
                    trait.updateSurviveMap("pedestrians");
                    trait.updateSurviveMap("Human");
                    totalAge += survivors[i].getAge();
                    humans += 1;
                }
                if (survivors[i] instanceof Animal) {
                    trait.updateSurviveMap("Animal");
                    trait.updateSurviveMap("pedestrians");
                    trait.updateSurviveMap(((Animal) survivors[i]).getSpecies());
                    trait.updateSurviveMap(((Animal) survivors[i]).isPetString());
                    trait.updateSurviveMap(scenario.isLegalCrossingString());

                }
            }
        }
    }

    /**
     * Sets audit type
     * @param name String that is parsed
     */
    public void setAuditType(String name) {
        auditType = name;
    }

    /**
     * returns audit type
     * @return Returns auditType
     */
    public String getAuditType() {
        return auditType;
    }

    /**
     * Sorts characteristics and appends them to create statistics
     * @return Returns String
     */
    public String toString() {
        ArrayList<String[]> sorted = new ArrayList<>();
        for (String key : trait.total.keySet()) {       //copies items from hashmaps to an arraylist
            int entry = trait.total.get(key);
            if (entry > 0) {
                double survivalPercentage = (double) trait.survive.get(key) / (double) entry;
                sorted.add(new String[]{key, String.valueOf(survivalPercentage)});
            }
        }
        Collections.sort(sorted, new Comparator<String[]>() {       //sorts array based on survival percentage
            @Override
            public int compare(String[] o1, String[] o2) {
                int c;
                c = Double.compare(Double.parseDouble(o2[1]), Double.parseDouble(o1[1]));
                if (c == 0) {
                    c = o1[0].compareTo(o2[0]);
                }
                return c;
            }
        }) ;

        String string = "";
        if (runs == 0) {
            return "no audit available";
        }
        string += "======================================\n";
        string += "# " + getAuditType() + " Audit\n";
        string += "======================================\n";
        string += "- % SAVED AFTER " + runs + " RUNS\n";
        for (String[] stats : sorted) {
            string += stats[0].toLowerCase() + ": " +
                    String.format("%.2f", Math.ceil(Double.parseDouble(stats[1]) * 100) / 100) + "\n";      //rounds up to second decimal
        }
        string += "--\n";
        string += "average age: " + String.format("%.2f", Math.ceil(totalAge/humans * 100) / 100);
        return string;
    }

    /**
     * Prints toString method
     */
    public void printStatistic() {
        System.out.println(toString());
    }

    /**
     * Prints toString to file
     * @param filepath File path that is parsed
     */
    public void printToFile(String filepath) {
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter((new FileOutputStream(filepath, true)));
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: could not print results. Target directory does not exist.");
            System.exit(0);
        }
        outputStream.println(toString());
        outputStream.close();
    }

}
