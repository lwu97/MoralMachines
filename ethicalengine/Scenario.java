package ethicalengine;

/**
 * Represents a scenario to decide on
 * COMP90041, Sem2, 2020: Final Project
 * @author: leon.wuuu@gmail.com
 */
public class Scenario {
	private final Persona[] passengers;
    private final Persona[] pedestrians;
	private boolean legalCrossing;

    /**
     * Sets passengers and pedestrian array and whether or no the scenario is a legal crossing
     * @param passengers Array of passengers
     * @param pedestrians Array of pedestrians
     * @param isLegalCrossing Boolean on whether the scenario is a legal crossing
     */
    public Scenario(Persona[] passengers, Persona[] pedestrians, boolean isLegalCrossing) {
        this.passengers = passengers;
        this.pedestrians = pedestrians;
        this.legalCrossing = isLegalCrossing;
    }

    /**
     * Scans through passenger array and returns true is you are in it
     * @return Returns boolean
     */
    public boolean hasYouInCar() {
        for (Persona persona : this.passengers) {
            if (((Human) persona).isYou()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Scans through pedestrian array and returns true is you are in it
     * @return Returns boolean
     */
    public boolean hasYouInLane() {
        for (Persona persona : this.pedestrians) {
            if (((Human) persona).isYou()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Returns Persona array containing passengers
     */
    public Persona[] getPassengers() {
        return passengers;
    }

    /**
     * @return Returns Persona array containing pedestrians
     */
    public Persona[] getPedestrians() {
        return pedestrians;
    }

    /**
     * @return Returns boolean of if the scenario is a legal crossing
     */
    public boolean isLegalCrossing() {
        return legalCrossing;
    }

    public String isLegalCrossingString() {
        if (legalCrossing) {
            return "Green";
        }
        return "Red";
    }
    /**
     * Sets boolean of if the scenario is a legal crossing
     * @param isLegalCrossing
     */
    public void setLegalCrossing(boolean isLegalCrossing) {
        this.legalCrossing = isLegalCrossing;
    }

    /**
     * @return Returns passenger count
     */
    public int getPassengerCount() {
        return passengers.length;
    }

    /**
     * @return Returns pedestrian count
     */
    public int getPedestrianCount() {
        return pedestrians.length;
    }

    /**
     * @return Returns string containing characteristics of passengers and pedestrians
     */
    public String toString() {
        String string = "";
        string += "======================================\n";
        string += "# Scenario\n";
        string += "======================================\n";
        string += "Legal Crossing: " + (legalCrossing ? "yes\n" : "no\n");
        string += "Passengers (" + getPassengerCount() + ")\n";
        for (Persona persona : passengers) {
            string += "- " + persona.toString() + "\n";
        }
        string += "Pedestrians (" + getPedestrianCount() + ")\n";
        for (int i = 0; i < pedestrians.length; i++) {
            string += "- " + pedestrians[i].toString();
            if (i < pedestrians.length -1) {
                string += "\n";
            }
        }
        return string;
    }

}