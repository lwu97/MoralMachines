import ethicalengine.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * COMP90041, Sem2, 2020: Final Project
 * @author: leon.wuuu@gmail.com
 */
public class EthicalEngine {

    public enum Decision {PASSENGERS, PEDESTRIANS};
    private static final ArrayList<String[]> csvFile = new ArrayList<>();   //saves csv rows to arraylist

    /**
     * Decides whether to save the passengers or the pedestrians
     * @param  scenario: the ethical dilemma
     * @return Decision: which group to save
     */
    public static Decision decide(Scenario scenario) {
        int passengerScore = 0;
        int pedestrianScore = 0;
        int legalCrossing = 20;
        int morePassengers = 2;
        int morePedestrians = 2;
        int human = 2;
        int animal = 1;
        int pregnant = 2;
        int criminal = -5;
        int senior = -2;
        int pet = 2;
        int you = 1;
        int child = 2;
        int baby = 2;
        int doctor = 2;
        int dog = 3;

        Persona[] passengers = scenario.getPassengers();
        for (int i = 0; i < passengers.length; i++) {   //iterates through passenger and updates statistics accordingly
            if (passengers[i] instanceof Human) {
                passengerScore += human;
                if (((Human) passengers[i]).isYou()) {
                    passengerScore += you;
                }
                if (((Human) passengers[i]).isPregnant()) {
                    passengerScore += pregnant;
                }
                if (((Human) passengers[i]).getProfession() == Human.Profession.DOCTOR) {
                    passengerScore += doctor;
                }
                if (((Human) passengers[i]).getProfession() == Human.Profession.CRIMINAL) {
                    passengerScore += criminal;
                }
                if (((Human) passengers[i]).getAgeCategory() == Human.AgeCategory.SENIOR) {
                    passengerScore += senior;
                }
                if (((Human) passengers[i]).getAgeCategory() == Human.AgeCategory.BABY) {
                    passengerScore += baby;
                }
                if (((Human) passengers[i]).getAgeCategory() == Human.AgeCategory.CHILD) {
                    passengerScore += child;
                }

            }
            if (passengers[i] instanceof Animal) {
                passengerScore += animal;
                if (((Animal) passengers[i]).isPet()) {
                    passengerScore += pet;
                }
                if (((Animal) passengers[i]).getSpecies().equalsIgnoreCase("DOG")) {
                    passengerScore += dog;
                }
            }
        }

        Persona[] pedestrians = scenario.getPedestrians();
        for (int i = 0; i < pedestrians.length; i++) {   //iterates through pedestrian and updates statistics accordingly
            if (pedestrians[i] instanceof Human) {
                pedestrianScore += human;
                if (((Human) pedestrians[i]).isYou()) {
                    pedestrianScore += you;
                }
                if (((Human) pedestrians[i]).isPregnant()) {
                    pedestrianScore += pregnant;
                }
                if (((Human) pedestrians[i]).getProfession() == Human.Profession.DOCTOR) {
                    pedestrianScore += doctor;
                }
                if (((Human) pedestrians[i]).getProfession() == Human.Profession.CRIMINAL) {
                    pedestrianScore += criminal;
                }
                if (((Human) pedestrians[i]).getAgeCategory() == Human.AgeCategory.SENIOR) {
                    pedestrianScore += senior;
                }
                if (((Human) pedestrians[i]).getAgeCategory() == Human.AgeCategory.BABY) {
                    pedestrianScore += baby;
                }
                if (((Human) pedestrians[i]).getAgeCategory() == Human.AgeCategory.CHILD) {
                    pedestrianScore += child;
                }

            }
            if (pedestrians[i] instanceof Animal) {
                pedestrianScore += animal;
                if (((Animal) pedestrians[i]).isPet()) {
                    pedestrianScore += pet;
                }
                if (((Animal) pedestrians[i]).getSpecies().equalsIgnoreCase("DOG")) {
                    pedestrianScore += dog;
                }
            }
        }

        if (scenario.isLegalCrossing()) {   //checks if pedestrians were legally crossing
            pedestrianScore += legalCrossing;
        }

        if (passengers.length < pedestrians.length) {   //checks if there are more passengers or pedestrians
            pedestrianScore += morePedestrians;
        }
        else {
            passengerScore += morePassengers;
        }

        if (passengerScore > pedestrianScore) {     //makes decision based on passenger and pedestrian score
            return Decision.PASSENGERS;
        } else {
            return Decision.PEDESTRIANS;
        }
    }

    /**
     * @param args Commandline arguments
     */

    public static void main(String[] args) {
        boolean config = false;
        boolean help = false;
        boolean results = false;
        boolean interactive = false;
        int configIndex = 0;
        int resultIndex = 0;
        String consent = "";
        String welcome = "welcome.ascii";
        String userFile;
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < args.length; i++) {     //checks command line arguments
            if (args[i].equals("--config") || args[i].equals("-c")) {
                config = true;
                configIndex = i;       //sets the index position of config flag so we can find file path if needed
            }
            if (args[i].equals("--help") || args[i].equals("-h")) {
                help = true;
            }
            if (args[i].equals("--results") || args[i].equals("-r")) {
                resultIndex = i;        //sets the index position of results flag so we can find file path if needed
                results = true;
            }
            if (args[i].equals("--interactive") || args[i].equals("-i")) {
                interactive = true;
            }

        }
        if (config && !interactive) {       //Starts non-interactive audit and loads in CSV file
            readCsv(configIndex, args);
            Audit audit = new Audit(parseCsv(csvFile));
            audit.setAuditType("Algorithm");
            audit.run();
            audit.printStatistic();
            if (results) {
                try {
                    audit.printToFile(args[resultIndex + 1]);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("ERROR: could not print results. Target directory does not exist.");
                }
            }
        }
        else if (help) {
            printHelp();
        }

        if (interactive) {      //starts interactive mode
            try {       //prints welcome message
                BufferedReader inputStream = new BufferedReader(new FileReader(welcome));
                String line;
                while ((line = inputStream.readLine()) != null) {
                    System.out.println(line);
                }
                inputStream.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("ERROR: could not find file.");
            } catch (IOException e) {
                System.out.println("ERROR: I/O Exception");
            }
            System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
            boolean validConsent = false;
            while (!validConsent) {     //loop until valid response
                try {
                    consent = scan.nextLine();
                    if (!consent.equals("yes") && !consent.equals("no")) {
                        throw new InvalidInputException("Invalid response. Do you consent to have your " +
                                "decisions saved to a file? (yes/no)");
                    }
                    else {
                        validConsent = true;
                    }
                }
                catch(InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (config) {   //if config file is provided
                readCsv(configIndex, args);
                Audit userAudit = new Audit(parseCsv(csvFile));
                userAudit.setAuditType("User");
                userAudit.userRun(scan);
                if (consent.equals("yes")) {
                    userFile = "user.log";
                    userAudit.printToFile(userFile);
                }
            }

            else {      //generates random scenarios
                Audit audit = new Audit();
                audit.setAuditType("User");
                audit.userRunRandom(scan);
                if (consent.equals("yes")) {
                    userFile = "user.log";
                    audit.printToFile(userFile);
                }
            }
        }

        if(results && !interactive && !config) {        //if no config, interactive is provided
            Audit audit = new Audit();
            audit.run(100);
            audit.printStatistic();
            audit.printToFile(args[resultIndex + 1]);
        }

        if (args.length == 0) {     //runs audit will 100 scenarios if no arguments are provided
            Audit audit = new Audit();
            audit.run(100);
            audit.printStatistic();
            audit.printToFile("results.log");
        }

    }

    /**
     * Prints help messages
     */
    public static void printHelp() {
        System.out.println("EthicalEngine - COMP90041 - Final Project\n");
        System.out.println("Usage: java EthicalEngine [arguments]\n");
        System.out.println("Arguments:");
        System.out.println("    -c or --config      Optional: path to config file");
        System.out.println("    -h or --help        Print Help (this message) and exit");
        System.out.println("    -r or --results     Optional: path to result log file");
        System.out.println("    -i or --interactive Optional: launches interactive mode");
    }

    /** reads CSV file line by line and saves to arraylist
     * @param args Array of commandline arguments
     * @param configIndex Index of -c or --config
     */
    public static void readCsv(int configIndex, String[] args) {
        String line;
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(args[configIndex + 1]));
            line = inputStream.readLine();
            while (line != null) {
                csvFile.add(line.split(","));
                line = inputStream.readLine();
            }
            inputStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: could not find config file.");
            System.exit(0);
        }
        catch(IOException ignored) {
        }
        catch(ArrayIndexOutOfBoundsException e) {
            printHelp();
            System.exit(0);
        }
    }

    /** Reads CSV file and creates a Scenario array containing the data
     * @param csvFile Imported CSV file
     * @return Scenario[] Returns Scenario array containing the data of the file
     */
    public static Scenario[] parseCsv(ArrayList<String[]> csvFile) {
        ArrayList<Scenario> scenarioList = new ArrayList<>();
        ArrayList<Persona> passengersList = new ArrayList<>();
        ArrayList<Persona> pedestriansList = new ArrayList<>();
        boolean isLegalCrossing = false;
        int lineCount = 0;

        for (String[] line : csvFile) {     //scans through every line of CSV file
            lineCount++;
            try {
                if (line.length != 10 && !line[0].contains("scenario")) {
                    throw new InvalidDataFormatException("WARNING: invalid data format in config file in line "
                            + lineCount);
                }
            }
            catch(InvalidDataFormatException e) {
                System.out.println(e.getMessage());
            }

            if (line[0].equalsIgnoreCase("Human")) {
                Human human = new Human();
                try {
                    if (line[1].equalsIgnoreCase(Persona.Gender.FEMALE.name())) {
                        human.setGender(Persona.Gender.FEMALE);
                    } else if (line[1].equalsIgnoreCase(Persona.Gender.MALE.name())) {
                        human.setGender(Persona.Gender.MALE);
                    } else if (line[1].equalsIgnoreCase(Persona.Gender.UNKNOWN.name())) {
                        human.setGender(Persona.Gender.UNKNOWN);
                    } else {
                        human.setGender(Persona.Gender.UNKNOWN);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    if (Integer.parseInt(line[2]) < 0) {
                        throw new NumberFormatException();
                    }
                    human.setAge(Integer.parseInt(line[2]));
                } catch (NumberFormatException e) {
                    System.out.println("WARNING: invalid number format in config file in line " +
                            lineCount);
                }

                try {
                    boolean validBodyType = false;
                    for (Persona.BodyType bodyType : Persona.BodyType.values()) {
                        if (line[3].equalsIgnoreCase(bodyType.name())) {
                            human.setBodyType(bodyType);
                            validBodyType = true;
                        }
                    }
                    if (!validBodyType || line[3].equals("")) {
                        human.setBodyType(Persona.BodyType.UNSPECIFIED);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    boolean validProfession = false;
                    for (Human.Profession profession : Human.Profession.values()) {
                        if (line[4].equalsIgnoreCase(profession.name())) {
                            human.setProfession(profession);
                            validProfession = true;
                        }
                    }
                    if (!validProfession || line[4].equals("")) {
                        human.setProfession(Human.Profession.NONE);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    if (line[5].equalsIgnoreCase("true") || line[5].equalsIgnoreCase("false")) {
                        human.setPregnant(Boolean.parseBoolean(line[5]));
                    } else {
                        human.setPregnant(false);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    if (line[6].equalsIgnoreCase("true") || line[6].equalsIgnoreCase("false")) {
                        human.setAsYou(Boolean.parseBoolean(line[6]));
                    } else {
                        human.setAsYou(false);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    if (line[9].equalsIgnoreCase("passenger")) {
                        passengersList.add(human);
                    } else if (line[9].equalsIgnoreCase("pedestrian")) {
                        pedestriansList.add(human);
                    } else {
                        pedestriansList.add(human);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (line[0].equalsIgnoreCase("animal")) {
                Animal animal = new Animal();
                try {
                    if (line[1].equalsIgnoreCase(Persona.Gender.FEMALE.name())) {
                        animal.setGender(Persona.Gender.FEMALE);
                    } else if (line[1].equalsIgnoreCase(Persona.Gender.MALE.name())) {
                        animal.setGender(Persona.Gender.MALE);
                    } else if (line[1].equalsIgnoreCase(Persona.Gender.UNKNOWN.name())) {
                        animal.setGender(Persona.Gender.UNKNOWN);
                    } else {
                        animal.setGender(Persona.Gender.UNKNOWN);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    boolean validSpecies = false;
                    for (Animal.Species species : Animal.Species.values()) {
                        if (line[7].equalsIgnoreCase(species.name())) {
                            animal.setSpecies(species.toString().toLowerCase());
                            validSpecies = true;
                        }
                    }
                    if (!validSpecies || line[7].equals("")) {
                        animal.setSpecies(Animal.Species.DOG.name());
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }


                try {
                    if (line[8].equalsIgnoreCase("true") || line[8].equalsIgnoreCase("false")) {
                        animal.setPet(Boolean.parseBoolean(line[8]));
                    }
                    else {
                        animal.setPet(false);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                } catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    if (line[9].equalsIgnoreCase("passenger")) {
                        passengersList.add(animal);
                    }
                    else if (line[9].equalsIgnoreCase("pedestrian")){
                        pedestriansList.add(animal);
                    }
                    else {
                        pedestriansList.add(animal);
                        throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line "
                                + lineCount);
                    }
                }
            catch (InvalidCharacteristicException e) {
                    System.out.println(e.getMessage());
                }

            }
            if ((line[0].equalsIgnoreCase("scenario:green") ||
                    line[0].equalsIgnoreCase("scenario:red")) && lineCount != 2) {      //checks if end of scenario

                Persona[] passengers = new Persona[passengersList.size()];
                for (int i = 0; i < passengersList.size(); i++) {       //copies arraylist to array
                    passengers[i] = passengersList.get(i);
                }
                Persona[] pedestrians = new Persona[pedestriansList.size()];
                for (int i = 0; i < pedestriansList.size(); i++) {       //copies arraylist to array
                    pedestrians[i] = pedestriansList.get(i);
                }
                passengersList.clear();
                pedestriansList.clear();
                Scenario scenario = new Scenario(passengers, pedestrians, isLegalCrossing);
                scenarioList.add(scenario);
            }

            if (line[0].equalsIgnoreCase("scenario:green")) {
               isLegalCrossing = true;
            }
            if (line[0].equalsIgnoreCase("scenario:red")) {
                isLegalCrossing = false;
            }
        }
        Persona[] passengers = new Persona[passengersList.size()];
        for (int i = 0; i < passengersList.size(); i++) {       //copies arraylist to array
            passengers[i] = passengersList.get(i);
        }
        Persona[] pedestrians = new Persona[pedestriansList.size()];
        for (int i = 0; i < pedestriansList.size(); i++) {       //copies arraylist to array
            pedestrians[i] = pedestriansList.get(i);
        }
        Scenario scenario = new Scenario(passengers, pedestrians, isLegalCrossing);
        scenarioList.add(scenario);
        Scenario[] scenarios = new Scenario[scenarioList.size()];
        for (int i = 0; i < scenarioList.size(); i++) {       //copies arraylist to array
            scenarios[i] = scenarioList.get(i);
        }
        return scenarios;
    }

}