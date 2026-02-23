
//import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.*;

public class WorkoutInfo {
    private String workoutName;
    private int sets;
    private int reps;
    private double weight;
    private boolean pr = false;
    private LocalDateTime dateOfWorkout = LocalDateTime.now();
    private String workoutDetails = "";
    private String prWorkoutDetails = "";
    private String nonPRWorkoutDetails = "";
    private boolean foundDataMatch = false;
    // ArrayList<workoutInfo> workoutHistory = new ArrayList<>();

    /**
     * Constructor for the workoutInfo class that the user will input the workout
     * name, sets, reps, and weight.
     * 
     * @param workoutName the name of the workout
     * @param sets        the number of sets performed
     * @param reps        the number of reps performed
     * @param weight      the weight used for the workout
     */
    public WorkoutInfo(String workoutName, int reps, double weight) {
        setWorkoutName(workoutName);
        setReps(reps);
        setWeight(weight);
    }

    // Set getters for return information about the workout
    public String getWorkoutName() {
        return workoutName;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public String isPR() {
        if (pr) {
            return "PR attempt";
        } else {
            return "Normal working sets";
        }
    }

    public String getDateOfWorkout() {
        return dateOfWorkout.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // Setters to set the data for the workout and return to the constructor
    public void setWorkoutName(String workoutName) {
        try {
            if (workoutName == null || workoutName.trim().isEmpty()) {
                throw new IllegalArgumentException("Workout name cannot be null or empty.");
            }
            this.workoutName = workoutName;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Sets will only be asked for non-PR workouts, so it is not included in the
    // constructor, but is included as a setter so that the user can input the
    // number of sets for non-PR workouts.
    public void setSets(int sets) {
        try {
            if (sets < 0) {
                throw new IllegalArgumentException("Sets cannot be less than 0. Eneter a valid number of sets.");
            }
            this.sets = sets;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setReps(int reps) {
        try {
            if (reps < 0) {
                throw new IllegalArgumentException("Reps cannot be less than 0. Eneter a valid number of reps.");
            }
            this.reps = reps;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setWeight(double weight) {
        try {
            if (weight < 0) {
                throw new IllegalArgumentException("Weight cannot be less than 0. Eneter a valid weight.");
            }
            this.weight = weight;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setPR(boolean pr) {
        this.pr = pr;
    }

    // Method to display the current workout information
    public void displayWorkoutInfo() {
        if (pr) {
            System.out.println(isPR());
        }
        System.out.println("Workout Name: " + getWorkoutName());
        if (!pr) {
            System.out.println("Sets: " + getSets());
        }
        System.out.println("Reps: " + getReps());
        System.out.println("Weight: " + getWeight() + " kg");
        System.out.println("Date of Workout: " + getDateOfWorkout());
    }

    // Method to write the current workout information to array list to store the
    // workout history
    /**
     * public void logWorkout() {
     * // String workoutDetails = isPR() + ", " + "Date: " + getDateOfWorkout() + ",
     * // Workout Name: " + getWorkoutName() + ", Sets: " + getSets() + ", Reps: " +
     * // getReps() + ", Weight: " + getWeight() + " kg";
     * 
     * }
     */

    // Method to write the current workout to a file to store the workout
    public void writeWorkoutToFile() {
        // The workout data is passed to the method, it then searches for the file
        // (creates one if it doesn't exist) and adds the workout data to the file.
        // Formatted so that can be searched by PR then data the workoutName
        try {
            String dataToAdd = "";
            if (pr) {
                dataToAdd = isPR() + ", " + "Date: " + getDateOfWorkout() + ", Workout Name: " + getWorkoutName()
                        + ", Reps: " + getReps() + ", Weight: " + getWeight() + " kg";
            }
            if (!pr) {
                dataToAdd = isPR() + ", " + "Date: " + getDateOfWorkout() + ", Workout Name: " + getWorkoutName()
                        + ", Sets: " + getSets() + ", Reps: " + getReps() + ", Weight: " + getWeight() + " kg";
            }
            File file = new File("WorkoutHistory.txt");

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(dataToAdd);
            bw.write("\n");
            bw.close();
            System.out.println("Workout successfully saved to file.");
        } catch (IOException e) {
            System.out.println("An error occured while writing to the file: " + e.getMessage());
        }
    }

    // Method to read the previously logged workout from the file, individual
    // methods for PR and non-PR used when user enters a new log.
    public String readWorkoutFromFile(String prStatus, String dateOfWorkout, String workoutName) {
        try {
            File file = new File("WorkoutHistory.txt");
            if (!file.exists()) {
                return "No previous workouts available.";
            }
            FileReader fr = new FileReader(file.getName());
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] workoutData = line.split(", ");
                if (workoutData[0].equals(prStatus) && workoutData[1].equals("Date: " + dateOfWorkout)
                        && workoutData[2].equals("Workout Name: " + workoutName)) {
                    workoutDetails = line;
                    foundDataMatch = true;
                    break;
                }
            }
            br.close();
            fr.close();
            if (line == null) {
                return "No matching data found or file does not exist.";
            }
            System.out.println("Workout successfully read from file.");
        } catch (IOException e) {
            System.out.println("An error occured while reading from the file: " + e.getMessage());
        }
        return workoutDetails;
    }

    public String readPRWorkoutFromFile(String prStatus, String workoutName) {
        try {
            File file = new File("WorkoutHistory.txt");
            if (!file.exists()) {
                return "No previous workouts available.";
            }
            FileReader fr = new FileReader(file.getName());
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] workoutData = line.split(", ");
                if (workoutData[0].equals(prStatus) && workoutData[2].equals("Workout Name: " + workoutName)) {
                    prWorkoutDetails = line;
                    foundDataMatch = true;
                    break;
                }
            }
            br.close();
            fr.close();
            if (line == null) {
                return "No matching data found or file does not exist.";
            }
            System.out.println("Workout successfully read from file.");
        } catch (IOException e) {
            System.out.println("An error occured while reading from the file: " + e.getMessage());
        }
        return prWorkoutDetails;
    }

    public String readNonPRWorkoutFromFile(String prStatus, String workoutName) {
        try {
            File file = new File("WorkoutHistory.txt");
            if (!file.exists()) {
                return "No previous workouts available.";
            }
            FileReader fr = new FileReader(file.getName());
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] workoutData = line.split(", ");
                if (workoutData[0].equals(prStatus) && workoutData[2].equals("Workout Name: " + workoutName)) {
                    nonPRWorkoutDetails = line;
                    foundDataMatch = true;
                    break;
                }
            }
            br.close();
            fr.close();
            if (line == null) {
                return "No matching data found or file does not exist.";
            }
            System.out.println("Workout successfully read from file.");
        } catch (IOException e) {
            System.out.println("An error occured while reading from the file: " + e.getMessage());
        }
        return nonPRWorkoutDetails;
    }

    // Method to compare the current workout to the previous one and calculate the %
    // increase in PR weight (and reps if necessary) and increase in sets, calucated
    // if it's grater than, less than, or gives a statement if equal than the
    // previous workout.
    public void compareWorkouts() {
        if (pr) {
            String[] parts = prWorkoutDetails.split(", ");
            double previousWeight = Double.parseDouble(parts[4].split(" ")[1]);
            int previousReps = Integer.parseInt(parts[3].split(" ")[1]);
            if (getWeight() > previousWeight) {
                double weightIncrease = getWeight() - previousWeight;
                double weightIncreasePercent = (weightIncrease / previousWeight) * 100;
                System.out.printf("Weight increase by: %.2f kg\nWeight percentage increase: %.2f%%\n", weightIncrease, weightIncreasePercent);;
            } else if (getWeight() < previousWeight) {
                double weightDecrease = previousWeight - getWeight();
                double weightDecreasepercent = (weightDecrease / previousWeight) * 100;
                System.out.printf("Weight decreased by: %.2f kg\nWeight percentage decrease: %.2f%%\n", weightDecrease, weightDecreasepercent);
            } else if (getWeight() == previousWeight) {
                System.out.println("No increase in weight. Previous weight: " + previousWeight + " kg");
            } else {
                System.out.println("Error comparing weight.");
            }
            if (getReps() > previousReps) {
                int repsIncrease = getReps() - previousReps;
                System.out.println("Reps increase by: " + repsIncrease);
            } else if (getReps() < previousReps) {
                int repsDecrease = previousReps - getReps();
                System.out.println("Reps decreased by: " + repsDecrease);
            } else if (getReps() == previousReps) {
                System.out.println("No increase in reps. Previous reps: " + previousReps);
            } else {
                System.out.println("Error comparing reps.");
            }
        }
        if (!pr) {
            String[] parts = nonPRWorkoutDetails.split(", ");
            double previousWeight = Double.parseDouble(parts[5].split(" ")[1]);
            int previousReps = Integer.parseInt(parts[4].split(" ")[1]);
            int previousSets = Integer.parseInt(parts[3].split(" ")[1]);
            if (getWeight() > previousWeight) {
                double weightIncrease = getWeight() - previousWeight;
                double weightIncreasePercent = (weightIncrease / previousWeight) * 100;
                System.out.printf("Weight increase by: %.2f kg\nWeight percentage increase: %.2f%%\n", weightIncrease, weightIncreasePercent);
            } else if (getWeight() < previousWeight) {
                double weightDecrease = previousWeight - getWeight();
                double weightDecreasepercent = (weightDecrease / previousWeight) * 100;
                System.out.printf("Weight decreased by: %.2f kg\nWeight percentage decrease: %.2f%%\n", weightDecrease, weightDecreasepercent);
            } else if (getWeight() == previousWeight) {
                System.out.println("No increase in weight. Previous weight: " + previousWeight + " kg");
            } else {
                System.out.println("Error comparing weight.");
            }
            if (getSets() > previousSets) {
                int setsIncrease = getSets() - previousSets;
                System.out.println("Set increase: " + setsIncrease);
            } else if (getSets() < previousSets) {
                int setsDecrease = previousSets - getSets();
                System.out.println("Set decrease: " + setsDecrease);
            } else if (getSets() == previousSets) {
                System.out.println("No increase in sets. Previous sets: " + previousSets);

            } else {
                System.out.println("Error comparing reps.");
            }
            if (getReps() > previousReps) {
                int repsIncrease = getReps() - previousReps;
                System.out.println("Reps increase by: " + repsIncrease);
            } else if (getReps() < previousReps) {
                int repsDecrease = previousReps - getReps();
                System.out.println("Reps decreased by: " + repsDecrease);
            } else if (getReps() == previousReps) {
                System.out.println("No increase in reps. Previous reps: " + previousReps);
            } else {
                System.out.println("Error comparing reps.");
            }

        }
    }

    // Methods to print the previous workout details, used to show the user the
    // previous workout
    public void displayPreviousWorkout() {
        if(foundDataMatch) {
        System.out.println(workoutDetails);
        } else {
            System.out.println("No previous workout data found");
        }
    }

    public void displayPreviousPRWorkout() {
        if(foundDataMatch) {
        System.out.println(prWorkoutDetails);
        } else {
            System.out.println("No previous workout data found");
        }
    }

    public void displayPreviousNonPRWorkout() {
        if(foundDataMatch) {
        System.out.println(nonPRWorkoutDetails);
        } else {
            System.out.println("No previous workout data found");
        }
    }
}
