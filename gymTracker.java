import java.util.Scanner;;

public class gymTracker {
    public static void main(String[] args) {
        // Initialise the scanner
        Scanner scanner = new Scanner(System.in);
        // Will loop user until they stop logging workouts
        boolean continueLogging = true;
        // Ask user if they want to log a workout or view previous workout
        System.out.print("Do you want to log a workout (Y) or view previously logged workout (N): ");
        String logInput = scanner.nextLine().trim().toUpperCase();
        if (logInput.equals("Y") || logInput.equals("YES")) {
            do {
                // User will be asked of the workout was a PR attempt
                boolean pr = false;
                // Used to repeat the question if the user inputs an invalid answer
                boolean validInput = false;
                do {
                    System.out.print("Did you hit a PR? (Y/N): ");
                    String prInput = scanner.nextLine().trim().toUpperCase();
                    if (prInput.equals("Y") || prInput.equals("YES")) {
                        pr = true;
                        validInput = true;
                    } else if (prInput.equals("N") || prInput.equals("NO")) {
                        pr = false;
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' for yes or 'N' for no. ");
                        validInput = false;
                    }
                } while (!validInput);
                // If the workout was a PR attempt then no sets will be asked for. The user will
                // be asked for the workout name, reps, and weight. If it wasn't a PR attempt
                // then it will also ask for the number of sets.
                if (pr) {
                    System.out.print("Enter the name of the exercise: ");
                    String workoutName = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Enter the number of reps: ");
                    int reps = scanner.nextInt();
                    System.out.print("Enter the weight used (in kg): ");
                    double weight = scanner.nextDouble();
                    workoutInfo prWorkout = new workoutInfo(workoutName, reps, weight);
                    prWorkout.setPR(pr);
                    System.out.println("Thanks for logging. Here is your workout info: ");
                    prWorkout.displayWorkoutInfo();
                    scanner.nextLine(); // Consume the newLine left by nextInt() and nextDouble()
                }
                if (!pr) {
                    System.out.print("Enter the name of the exercise: ");
                    String workoutName = scanner.nextLine();
                    System.out.print("Enter the number of sets: ");
                    int sets = scanner.nextInt();
                    System.out.print("Enter the number of reps: ");
                    int reps = scanner.nextInt();
                    System.out.print("Enter the weight used (in kg): ");
                    double weight = scanner.nextDouble();
                    workoutInfo nonPRWorkout = new workoutInfo(workoutName, reps, weight);
                    nonPRWorkout.setSets(sets);
                    nonPRWorkout.setPR(pr);
                    System.out.println("Thanks for logging. Here is your workout info: ");
                    nonPRWorkout.displayWorkoutInfo();
                    scanner.nextLine(); // Consume the newLine left by nextInt() and nextDouble()
                }
                System.out.print("Would you like to log another workout? (Y/N): ");
                String continueInput = scanner.nextLine().trim().toUpperCase();
                if (continueInput.equals("N") || continueInput.equals("NO")) {
                    continueLogging = false;
                }
            } while (continueLogging);
        }
        // Ask user if they want to view previously logged workout
        if(logInput.equals("N") || logInput.equals("NO")) {
            System.out.println("Enter if the workout was a PR or not (Y/N), the date of the workout in the format dd/mm/yyyy, and the exercise name: ");
            

        }

        System.out.println("Thanks for checking in!");
        scanner.close();
    }

}
