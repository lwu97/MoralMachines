package ethicalengine;

/**
 * COMP90041, Sem2, 2020: Final Project
 * @author: leon.wuuu@gmail.com
 */

public abstract class Persona {

    public enum Gender {
        FEMALE,
        MALE,
        UNKNOWN,
    }

    public enum BodyType {
        AVERAGE,
        ATHLETIC,
        OVERWEIGHT,
        UNSPECIFIED
    }

    private int age;
    private BodyType bodyType;
    private Gender gender;
    private final int DEFAULT_AGE = 0;

    /**
     * No-argument constructor
     */
    public Persona() {
        age = DEFAULT_AGE;
        gender = Gender.UNKNOWN;
        bodyType = BodyType.UNSPECIFIED;

    }

    /**
     * Sets age gender and body type of persona
     * @param age Age of persona
     * @param gender Gender of persona
     * @param bodytype Body type of persona
     */
    public Persona(int age, Gender gender, BodyType bodytype) {
        if (age < 0) {
            this.age = DEFAULT_AGE;
        }
        else {
            this.age = age;
        }
        this.gender = gender;
        this.bodyType = bodytype;
    }

    /**
     * Copy constructor
     * @param otherPersona The persona that is parsed
     */
    public Persona(Persona otherPersona) {
        this.age = otherPersona.age;
        this.gender = otherPersona.gender;
        this.bodyType = otherPersona.bodyType;
    }

    /**
     * @return Returns age of Persona
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age of persona
     * @param age Age that is being parsed
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return Returns Gender of persona
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets gender of Human
     * @param gender Gender of persona
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @return Returns body type of persona
     */
    public BodyType getBodyType() {
        return bodyType;
    }

    /**
     * Sets body type of Human
     * @param bodyType bodytype that is being parsed
     */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }
}
