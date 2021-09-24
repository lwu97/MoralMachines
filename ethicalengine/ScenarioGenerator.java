package ethicalengine;
import java.util.Random;

/**
 * COMP90041, Sem2, 2020: Final Project
 * @author: leon.wuuu@gmail.com
 */

public class ScenarioGenerator {

    private final Random random = new Random();
    private int minPassenger = 1;
    private int maxPassenger = 5;
    private int minPedestrian = 1;
    private int maxPedestrian = 5;

    //stores enumerated types in arrays
    Persona.Gender[] genders = Persona.Gender.values();
    Persona.BodyType[] bodyTypes = Persona.BodyType.values();
    Human.Profession[] professions = Human.Profession.values();
    Animal.Species[] speciesArray = Animal.Species.values();

    /**
     * sets random seed
     */
   public ScenarioGenerator(){
        random.setSeed(random.nextLong());
   }

    /**
     * Sets seed to parsed value
     * @param seed Seed for randomness
     */
   public ScenarioGenerator(long seed) {
       random.setSeed(seed);
   }

    /**
     * Sets seed and the minimum and maximum passengers and pedestrians
     * @param seed Seed for randomness
     * @param passengerCountMaximum Maximum passengers allowed
     * @param passengerCountMinimum Minimum passengers allowed
     * @param pedestrianCountMaximum Maximum pedestrians allowed
     * @param pedestrianCountMinimum Minimum pedestrians allowed
     */
   public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum,
                            int pedestrianCountMinimum, int pedestrianCountMaximum) {
       random.setSeed(seed);
       if (minPassenger <= maxPassenger) {
           setPassengerCountMax(passengerCountMaximum);
           setPassengerCountMin(passengerCountMinimum);
       }
       else {
           System.out.println("Error: minimum value cannot be greater than maximum value");
       }
       if (minPedestrian <= maxPedestrian) {
           setPedestrianCountMax(pedestrianCountMaximum);
           setPedestrianCountMin(pedestrianCountMinimum);
       }
       else {
           System.out.println("Error: minimum value cannot be greater than maximum value");
       }

   }

    public void setPassengerCountMin(int min) {
        this.minPassenger = min;
    }

    public void setPassengerCountMax(int max) {
        this.maxPassenger = max;
    }

    public void setPedestrianCountMin(int min) {
        this.minPedestrian = min;
    }

    public void setPedestrianCountMax(int max) {
        this.maxPedestrian = max;
    }

    /**
     * Generates random human
     * @return Human with random characteristics
     */
    public Human getRandomHuman() {
       int age = random.nextInt(100);
       Persona.Gender gender = genders[random.nextInt(genders.length)];
       Persona.BodyType bodyType = bodyTypes[random.nextInt(bodyTypes.length)];
       Human.Profession profession = professions[random.nextInt(professions.length)];
       boolean isPregnant;
       if (gender == Persona.Gender.FEMALE && age >= 18) {
           isPregnant = random.nextBoolean();
       }
       else {
           isPregnant = false;
       }
       return new Human(age, profession, gender, bodyType, isPregnant);
    }

    /**
     * Generates random animal
     * @return Animal with random characteristics
     */
    public Animal getRandomAnimal() {
        int age = random.nextInt(100);
        Persona.Gender gender = genders[random.nextInt(genders.length)];
        Persona.BodyType bodyType = bodyTypes[random.nextInt(bodyTypes.length)];
        String species = speciesArray[random.nextInt(speciesArray.length)].name();
        boolean isPet = random.nextBoolean();

        return new Animal(age, gender, bodyType, species, isPet);
    }

    /**
     * Generates random scenario
     * @return Scenario with humans and animals with random characteristics
     */
    public Scenario generate() {
        int numPassenger = minPassenger + random.nextInt(maxPassenger);
        int numPedestrian = minPedestrian + random.nextInt(maxPedestrian);
        boolean isLegalCrossing = random.nextBoolean();

        Persona[] passenger = new Persona[numPassenger];
        Persona[] pedestrian = new Persona[numPedestrian];

        int yourLocation = random.nextInt(3);    //random location of user. 0 is absent, 1 is passenger, 2 is pedestrian
        for (int i = 0; i < numPassenger; i++) {
            if (yourLocation == 1) {
                Human you = getRandomHuman();
                you.setAsYou(true);
                passenger[i] = you;
            }
            else if (random.nextBoolean()) {
                passenger[i] = getRandomHuman();
            }
            else {
                passenger[i] = getRandomAnimal();
            }
        }

        for (int i = 0; i < numPedestrian; i++) {
            if (yourLocation == 2) {
                Human you = getRandomHuman();
                you.setAsYou(true);
                pedestrian[i] = you;
            }
            else if (random.nextBoolean()){
                pedestrian[i] = getRandomHuman();
            }
            else {
                pedestrian[i] = getRandomAnimal();
            }
        }
        return new Scenario(passenger, pedestrian, isLegalCrossing);
    }

}
