import java.util.HashMap;

/**
 * COMP90041, Sem2, 2020: Final Project
 * @author: leon.wuuu@gmail.com
 */

public class Traits {

    /**
     * Stores data for characteristics of survivors
     */
    public HashMap<String, Integer> survive = new HashMap<>() {
        {
            put("BABY", 0);
            put("CHILD", 0);
            put("ADULT", 0);
            put("SENIOR", 0);
            put("AVERAGE", 0);
            put("ATHLETIC", 0);
            put("OVERWEIGHT", 0);
            put("MALE", 0);
            put("FEMALE", 0);
            put("DOCTOR", 0);
            put("CEO", 0);
            put("CRIMINAL", 0);
            put("HOMELESS", 0);
            put("UNEMPLOYED", 0);
            put("TEACHER", 0);
            put("POLICE", 0);
            put("PREGNANT", 0);
            put("HUMAN", 0);
            put("ANIMAL", 0);
            put("DOG", 0);
            put("CAT", 0);
            put("BIRD", 0);
            put("DINOSAUR", 0);
            put("FERRET", 0);
            put("PET", 0);
            put("RED", 0);
            put("GREEN", 0);
            put("PEDESTRIANS", 0);
            put("PASSENGERS", 0);
            put("YOU", 0);

        }
    };

    /**
     * Stores data for characteristics of all persona in the scenario
     */
    public HashMap<String, Integer> total = new HashMap<>() {
        {
            put("BABY", 0);
            put("CHILD", 0);
            put("ADULT", 0);
            put("SENIOR", 0);
            put("AVERAGE", 0);
            put("ATHLETIC", 0);
            put("OVERWEIGHT", 0);
            put("MALE", 0);
            put("FEMALE", 0);
            put("DOCTOR", 0);
            put("CEO", 0);
            put("CRIMINAL", 0);
            put("HOMELESS", 0);
            put("UNEMPLOYED", 0);
            put("TEACHER", 0);
            put("POLICE", 0);
            put("PREGNANT", 0);
            put("HUMAN", 0);
            put("ANIMAL", 0);
            put("DOG", 0);
            put("CAT", 0);
            put("BIRD", 0);
            put("DINOSAUR", 0);
            put("FERRET", 0);
            put("PET", 0);
            put("RED", 0);
            put("GREEN", 0);
            put("PEDESTRIANS", 0);
            put("PASSENGERS", 0);
            put("YOU", 0);

        }
    };

    /**
     * Updates hash map of characteristics for survivors
     * @param s String that is parsed
     */
    public void updateSurviveMap(String s) {
        if (survive.containsKey(s.toUpperCase())) {
            survive.put(s.toUpperCase(), survive.get(s.toUpperCase()) + 1);
        }
    }

    /**
     * Updates hash map of total characteristics
     * @param s String that is parsed
     */
    public void updateTotalMap(String s) {
        if (total.containsKey(s.toUpperCase())) {
            total.put(s.toUpperCase(), total.get(s.toUpperCase()) + 1);
        }
    }

}
