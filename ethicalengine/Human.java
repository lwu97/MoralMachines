package ethicalengine;

/**
 * COMP90041, Sem2, 2020: Final Project
 * @author: leon.wuuu@gmail.com
 */

public class Human extends Persona {

    public enum Profession {
        DOCTOR,
        CEO,
        CRIMINAL,
        HOMELESS,
        UNEMPLOYED,
        TEACHER,
        POLICE,
        NONE
    }

    public enum AgeCategory {
        BABY,
        CHILD,
        ADULT,
        SENIOR
    }

    private Profession profession;
    private boolean isPregnant;
    private boolean isYou;

    /**
     * No-argument constructor
     */
    public Human() {
        super();
    }

    /**
     * Sets age, gender and bodyType of Human
     * @param age Age of human
     * @param gender Gender of Human
     * @param bodyType Body type of Human
     */
    public Human(int age, Gender gender, BodyType bodyType) {
        super(age, gender, bodyType);
        this.profession = Profession.NONE;
        this.isPregnant = false;
    }

    /**
     * Sets age, gender, profession,bodyType and whether or no a human is pregnant
     * @param age Age of human
     * @param gender Gender of Human
     * @param bodyType Body type of Human
     * @param isPregnant Whether human is pregnant
     * @param profession Profession of human
     */
    public Human(int age, Profession profession, Gender gender, BodyType bodyType, boolean isPregnant) {
        super(age, gender, bodyType);
        setProfession(profession);
        setPregnant(isPregnant);
    }

    /**
     * Copy constructor
     * @param otherHuman Human that is being parsed
     */
    public Human(Human otherHuman) {
        super(otherHuman.getAge(), otherHuman.getGender(), otherHuman.getBodyType());
        this.profession = otherHuman.profession;
        this.isPregnant = otherHuman.isPregnant;
        this.isYou = otherHuman.isYou;
    }


    /**
     * @return Returns age category of Human
     */
    public AgeCategory getAgeCategory() {
        int age = getAge();
        if (0 <= age && age <= 4) {
            return AgeCategory.BABY;
        }
        if (5 <= age && age <= 16) {
            return AgeCategory.CHILD;
        }
        if (17 <= age && age <= 68) {
            return AgeCategory.ADULT;
        }
        if (age > 68) {
            return AgeCategory.SENIOR;
        }
        else {
            return AgeCategory.ADULT;
        }
    }

    /**
     * @return Returns Profession of human
     */
    public Profession getProfession() {
        return profession;
    }

    /**
     * Sets profession of Human
     * @param profession Profession of human
     */
    public void setProfession(Profession profession) {
        if (getAgeCategory() == AgeCategory.ADULT) {
            this.profession = profession;
        }
        else {
            this.profession = Profession.NONE;
        }
    }

    /**
     * @return Returns whether or not human is pregnant
     */
    public boolean isPregnant() {
        return isPregnant;
    }

    /**
     * @return Returns string of whether or not a human is pregnant
     */
    public String isPregnantString() {
        if (isPregnant) {
            return "Pregnant";
        }
        return "Not Pregnant";
    }

    /**
     * Sets whether or not a human is pregnant
     * @param isPregnant Whether or not a human is pregnant
     */
    public void setPregnant(boolean isPregnant) {
        if (getGender() == Gender.FEMALE) {
            this.isPregnant = isPregnant;
        }
        else {
            this.isPregnant = false;
        }
    }
    /**
     * @return Returns if the human is you
     */
    public boolean isYou() {
        return isYou;
    }

    public String isYouString() {
        if (isYou) {
            return "You";
        }
        return "Not You";
    }

    /**
     * Sets if human is you
     * @param isYou Whether the human is you or not
     */
    public void setAsYou(boolean isYou) {
        this.isYou = isYou;
    }

    /**
     * Prints human characteristics
     * @return Returns string containing characteristics
     */
    public String toString() {
        String string = "";
        if (isYou) {
            string += string + "you ";
        }
        if (getBodyType() != BodyType.UNSPECIFIED) {
            string += getBodyType() + " ";
        }
        if (this.getAgeCategory() != null) {
            string += getAgeCategory() + " ";
        }
        if (getAgeCategory() == AgeCategory.ADULT && getProfession() != Profession.NONE) {
            string += getProfession() + " ";
        }
        if (this.getGender() != Gender.UNKNOWN) {
            string += getGender();
        }
        if (isPregnant) {
            string += " pregnant";
        }
        string = string.toLowerCase();
        return string;
    }
}
