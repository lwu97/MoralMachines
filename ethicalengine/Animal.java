package ethicalengine;

/**
 * COMP90041, Sem2, 2020: Final Project
 * @author: leon.wuuu@gmail.com
 */

public class Animal extends Persona{

    public enum Species {
        DOG,
        CAT,
        BIRD,
        DINOSAUR,
        FERRET
    }
    private String species;
    private boolean isPet;

    /**
     * No-argument constructor
     */
    public Animal() {
        super();
    }

    /**
     * Takes string argument and sets it as species
     */
    public Animal(String species) {
        setSpecies(species);
    }

    /**
     * Sets age, gender, bodyType, species and if it is a pet
     */
    public Animal(int age, Gender gender, BodyType bodyType, String species, boolean isPet) {
        super(age, gender, bodyType);
        this.species = species;
        this.isPet = isPet;
    }

    /**
     * Copy constructor
     */
    public Animal (Animal otherAnimal) {
        this.species = otherAnimal.species;
        this.isPet = otherAnimal.isPet;
    }

    /**
     * @return Returns species
     */
    public String getSpecies() {
        return species;
    }

    /**
     * @param species Sets species
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * @return Returns whether animal is a pet
     */
    public boolean isPet() {
        return isPet;
    }

    /**
     * Converts boolean into string
     * @return Returns string of whether animal is pet
     */
    public String isPetString() {
        if (isPet) {
            return "Pet";
        }
        return "Not Pet";
    }

    /**
     * @param isPet Pet that is parsed
     */
    public void setPet(boolean isPet) {
        this.isPet = isPet;
    }

    /**
     * @return Returns string of species and whether or not they are a pet
     */
    public String toString() {
        if (isPet) {
            return species.toLowerCase() + " is pet";
        }
        return species.toLowerCase();
    }


}
