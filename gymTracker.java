import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;;

public class gymTracker {
    public static void main(String[] args) {
        // Initialise the scanner
        Scanner scanner = new Scanner(System.in);
        // Will loop user until they stop logging workouts and if to quite the program
        // or not
        boolean continueLogging = true;
        boolean quit = false;
        do {
            // User will be asked of the workout was a PR attempt
            boolean pr = false;
            // Ask user if they want to log a workout or view previous workout
            System.out.print("Do you want to log a workout (Y) or view previously logged workout (N): ");
            String logInput = scanner.nextLine().trim().toUpperCase();
            if (logInput.equals("Y") || logInput.equals("YES")) {
                do {
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
                        // Writes the logged workout to file saving it for future use
                        prWorkout.writeWorkoutToFile();

                        // Retrieve previous PR attempt with the same name and display it
                        prWorkout.readPRWorkoutFromFile("PR attempt", workoutName);
                        prWorkout.displayPreviousPRWorkout();

                        // Compare the logged workout vs the previous workout
                        prWorkout.compareWorkouts();
                    }
                    if (!pr) {
                        System.out.print("Enter the name of the exercise: ");
                        String workoutName = scanner.nextLine().trim().toUpperCase();
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
                        // Writes the logged workouut to file saving it for future use
                        nonPRWorkout.writeWorkoutToFile();

                        // Retrieve previous non-PR attempt with the same name and display it
                        nonPRWorkout.readNonPRWorkoutFromFile("Normal working sets", workoutName);
                        nonPRWorkout.displayPreviousNonPRWorkout();

                        // Compare the logged workout vs the previous workout
                        nonPRWorkout.compareWorkouts();
                    }
                    System.out.print("Would you like to log another workout? (Y/N): ");
                    String continueInput = scanner.nextLine().trim().toUpperCase();
                    if (continueInput.equals("N") || continueInput.equals("NO")) {
                        continueLogging = false;
                    }
                } while (continueLogging);
            }
            // Code to view previously logged workout, will ask user for workout details
            if (logInput.equals("N") || logInput.equals("NO")) {
                String prStatus = "";
                LocalDate date;
                DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                        .withResolverStyle(ResolverStyle.STRICT);
                System.out.println("You will need to enter your previous exercise details to view them.");
                System.out.print("Did you hit a PR? (Y/N): ");
                String prInput = scanner.nextLine().trim().toUpperCase();
                if (prInput.equals("Y") || prInput.equals("YES")) {
                    pr = true;
                    prStatus = "PR attempt";
                } else if (prInput.equals("N") || prInput.equals("NO")) {
                    pr = false;
                    prStatus = "Normal working sets";
                } else {
                    System.out.println("Invalid input. Please enter 'Y' for yes or 'N' for no. ");
                }
                System.out.print("What was the name of the exercise? ");
                String exerciseName = scanner.nextLine().trim().toUpperCase();
                System.out.print("Enter the date of the workout (dd/mm/yyyy): ");
                while (true) {
                    String dateInput = scanner.nextLine().trim();
                    try {
                        date = LocalDate.parse(dateInput, inputFmt);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.print("Invalid date. Example: 21/02/2026. Enter the date again: ");
                    }
                }
                workoutInfo workoutHistory = new workoutInfo(exerciseName, 0, 0);
                // Convert to the exact format stored in WorkoutHistory.txt
                String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Read the workout details from the file using the values the user inputted
                workoutHistory.readWorkoutFromFile(prStatus, formattedDate, exerciseName);
                workoutHistory.displayPreviousWorkout();

            }
            System.out.println("Do you want to quit the program? (Y/N): ");
            boolean validInput = false;
            do {
                String prInput = scanner.nextLine().trim().toUpperCase();
                if (prInput.equals("Y") || prInput.equals("YES")) {
                    validInput = true;
                    quit = true;
                } else if (prInput.equals("N") || prInput.equals("NO")) {
                    validInput = true;
                    quit = false;
                } else {
                    System.out.println("Invalid input. Please enter 'Y' for yes or 'N' for no. ");
                    validInput = false;
                }
            } while (!validInput);
        } while (!quit);
        System.out.println("Thanks for checking in!");
        scanner.close();
    }
}