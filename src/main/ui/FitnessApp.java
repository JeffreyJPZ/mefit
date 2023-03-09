package ui;

import exceptions.InvalidExerciseException;
import exceptions.InvalidInputException;
import exceptions.InvalidNameException;
import exceptions.InvalidPositionException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

// Represents a fitness application with user profiles, editing and viewing of exercises and workouts
// attributed to TellerApp from CPSC 210
public class FitnessApp {
    private static final String PATH = "./data/fitnessapp.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private ProfilesById profilesById;
    private ProfilesById profilesByIdMaster;
    private Profile profile;
    private ExercisesByName exercisesByName;
    private ExercisesByName exercisesByNameMaster;
    private Exercise exercise;
    private WorkoutsByName workoutsByName;
    private WorkoutsByName workoutsByNameMaster;
    private Workout workout;
    private Scanner scanner;

    // EFFECTS: runs the fitness application
    public FitnessApp() {
        runFitnessApp();
    }

    // MODIFIES: this
    // EFFECTS: runs the fitness application until exit key is entered
    private void runFitnessApp() {
        boolean run = true;
        String input;

        init();

        while (run) {
            mainMenu();
            input = scanner.next().toLowerCase();

            if (input.equals("/")) {
                run = false;
            } else {
                mainMenuSelection(input);
            }
        }
        exitApp();
    }

    // MODIFIES: this
    // EFFECTS: initializes the profiles for the application and input
    private void init() {
        profilesById = new ProfilesById();
        profilesByIdMaster = profilesById;
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(PATH);
        jsonReader = new JsonReader(PATH);
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for main profiles menu and string representations of profiles
    private void mainMenu() {
        System.out.println("Fitness App\n");
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to access a profile by id");
        System.out.println("\t\"2\" for profile filtering options");
        System.out.println("\t\"3\" for profile creation/deletion options");
        System.out.println("\t\"4\" to save/load profiles to/from file");
        System.out.println("\t\"/\" to exit the application\n");
        System.out.println(profilesById.toString());
    }

    // MODIFIES: this
    // EFFECTS: selects the appropriate profiles menu option and resets any filters
    private void mainMenuSelection(String input) {
        switch (input) {
            case "1":
                resetProfiles();
                accessProfile();
                break;
            case "2":
                filterOrResetFilters();
                break;
            case "3":
                resetProfiles();
                createOrDeleteProfile();
                break;
            case "4":
                resetProfiles();
                saveOrLoadProfile();
                break;
            case "/":
                break;
            default:
                invalidSelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: enters the menu for profile filtering/resetting filtering and prompts user
    public void filterOrResetFilters() {
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to filter profiles by name");
        System.out.println("\t\"2\" to reset filters");

        String input = scanner.next();
        scanner.nextLine();

        switch (input) {
            case "1":
                filterProfiles();
                break;
            case "2":
                resetProfiles();
                break;
            default:
                invalidSelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: enters the menu for profile creation/deletion and prompts user
    public void createOrDeleteProfile() {
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to create a profile");
        System.out.println("\t\"2\" to delete a profile");

        String input = scanner.next();
        scanner.nextLine();

        switch (input) {
            case "1":
                createProfile();
                break;
            case "2":
                deleteProfile();
                break;
            default:
                invalidSelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: enters the menu for profile saving/loading and prompts user
    public void saveOrLoadProfile() {
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to save profiles to file");
        System.out.println("\t\"2\" to load saved profiles from file");

        String input = scanner.next();
        scanner.nextLine();

        switch (input) {
            case "1":
                saveProfiles();
                break;
            case "2":
                loadProfiles();
                break;
            default:
                invalidSelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: writes profiles to file with given path
    //          and indicates success if successfully written, otherwise indicates failure
    public void saveProfiles() {
        try {
            jsonWriter.open();
            jsonWriter.write(profilesById);
            jsonWriter.close();
            System.out.println("Successfully saved to: " + PATH + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Could not save to: " + PATH + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: reads profiles from file and indicates success if successfully read,
    //          otherwise indicates failure
    public void loadProfiles() {
        try {
            profilesById = jsonReader.read();
            profilesByIdMaster = profilesById;
            System.out.println("Successfully loaded from: " + PATH + "\n");
        } catch (InvalidExerciseException e) {
            System.out.println("Invalid exercise type encountered in: " + PATH + "\n");
        } catch (IOException e) {
            System.out.println("Could not load from: " + PATH + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: enters the menu for the profile with the given id if found, otherwise indicates failure to find profile
    public void accessProfile() {
        String input;

        System.out.println("Type the id number of the profile you wish to access:");
        input = scanner.next();

        if (profilesByIdMaster.contains(parseInt(input))) {
            profileMenu(input);
        } else {
            System.out.println("Profile cannot be found");
        }
    }

    // MODIFIES: this
    // EFFECTS: filters the profiles by name inputted by user
    private void filterProfiles() {
        String input;

        System.out.println("Enter the name of the profile(s) you wish to filter for:");

        input = scanner.next();

        profilesById = profilesById.filter(input);
    }

    // MODIFIES: this
    // EFFECTS: resets profiles to unfiltered state
    private void resetProfiles() {
        profilesById = profilesByIdMaster;
    }

    // MODIFIES: this
    // EFFECTS: makes a profile with user inputted name, gender, age, and weight and adds it to the profiles
    private void createProfile() {
        Map<String, String> profileData = new LinkedHashMap<>();

        profileData.put("name", null);
        profileData.put("gender", null);
        profileData.put("age", null);
        profileData.put("weight", null);

        System.out.println("Profile Creation\n");

        for (String data : profileData.keySet()) {
            System.out.println("Enter your " + data + ":");
            String input = scanner.nextLine();
            profileData.replace(data, input);
        }

        Profile profile = new Profile(profileData.get("name"),
                                        profileData.get("gender"),
                                        parseInt(profileData.get("age")),
                                        parseInt(profileData.get("weight")));

        profilesById.addProfile(profile);
    }

    // MODIFIES: this
    // EFFECTS: deletes the profile with the given id if found, otherwise prints failure
    private void deleteProfile() {
        int input;

        System.out.println("Enter the id number of the profile you wish to delete:");
        input = scanner.nextInt();

        if (profilesById.contains(input)) {
            profilesById.removeProfile(input);
        } else {
            System.out.println("Profile cannot be found\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if input is a valid input, if input is not accepted then indicates an invalid
    //          input; keeps user in profile menu until exit key is pressed
    private void profileMenu(String id) {
        profile = profilesByIdMaster.getProfile(parseInt(id));
        String input = "";
        String[] inputs = {"1", "2", "3", "<", "/"};
        boolean isInputInvalid;

        scanner.nextLine();

        while (!input.equals("<")) {
            displayProfileMenu();

            input = scanner.nextLine().toLowerCase();

            isInputInvalid = true;

            for (String possibleInput : inputs) {
                if (possibleInput.equals(input)) {
                    profileMenuSelection(possibleInput);
                    isInputInvalid = false;
                    break;
                }
            }

            if (isInputInvalid) {
                invalidSelection();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: selects the appropriate profile option given user input
    private void profileMenuSelection(String input) {
        switch (input) {
            case "1":
                profileInfoMenu();
                break;
            case "2":
                exercisesMenuForProfile();
                break;
            case "3":
                workoutsMenuForProfile();
                break;
        }
        menuNavigation(input);
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for a profile
    private void displayProfileMenu() {
        System.out.println("Welcome, " + profile.getName());
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to view your profile information");
        System.out.println("\t\"2\" to view your exercises");
        System.out.println("\t\"3\" to view your workouts");
        System.out.println("\t\"<\" to return to the main menu");
        System.out.println("\t\"/\" to exit the application\n");
    }

    // MODIFIES: this
    // EFFECTS: checks if given input is valid for profile info menu, if invalid then indicates an invalid input
    //          and keeps user in profile info menu until exit key is pressed
    private void profileInfoMenu() {
        String input = "";
        String[] inputs = {"1", "2", "3", "4", "<", "/"};
        boolean isInputInvalid;

        while (!input.equals("<")) {
            displayProfileInfoMenu();
            input = scanner.nextLine().toLowerCase();
            isInputInvalid = true;

            for (String possibleInput : inputs) {
                if (possibleInput.equals(input)) {
                    profileInfoSelection(possibleInput);
                    isInputInvalid = false;
                    break;
                }
            }

            if (isInputInvalid) {
                invalidSelection();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for profile info menu and a string representation of a profile
    private void displayProfileInfoMenu() {
        System.out.println("Profile Information");
        System.out.println(profile.toString());

        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to edit your name");
        System.out.println("\t\"2\" to edit your gender");
        System.out.println("\t\"3\" to edit your age");
        System.out.println("\t\"4\" to edit your weight");
        System.out.println("\t\"<\" to return to your profile menu");
        System.out.println("\t\"/\" to exit the application\n");
    }

    // MODIFIES: this
    // EFFECTS: selects the appropriate profile info option given user input
    private void profileInfoSelection(String input) {
        switch (input) {
            case "1":
                setProfileName();
                break;
            case "2":
                setProfileGender();
                break;
            case "3":
                setProfileAge();
                break;
            case "4":
                setProfileWeight();
        }
        menuNavigation(input);
    }

    // MODIFIES: this
    // EFFECTS: sets the name for profile given user input
    private void setProfileName() {
        String input;

        System.out.println("Enter your new name:");
        input = scanner.nextLine();
        profile.setName(input);
    }

    // MODIFIES: this
    // EFFECTS: sets the gender for profile given user input
    private void setProfileGender() {
        String input;

        System.out.println("Enter your new gender:");
        input = scanner.nextLine();
        profile.setGender(input);
    }

    // MODIFIES: this
    // EFFECTS: sets the age for profile given user input
    private void setProfileAge() {
        String input;

        System.out.println("Enter your new age:");
        input = scanner.next();
        profile.setAge(parseInt(input));
    }

    // MODIFIES: this
    // EFFECTS: sets the weight for profile given user input
    private void setProfileWeight() {
        String input;

        System.out.println("Enter your new weight:");
        input = scanner.next();
        profile.setWeight(parseInt(input));
    }

    // MODIFIES: this
    // EFFECTS: processes the exercises for a profile
    private void exercisesMenuForProfile() {
        exercisesByName = profile.getExercises();
        exercisesByNameMaster = exercisesByName;

        exercisesMenu();
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for the exercises menu for a profile and a string representation for exercises
    private void displayExercisesMenu() {
        System.out.println("Exercises");
        System.out.println(exercisesByName.toString());

        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to view an exercise by name");
        System.out.println("\t\"2\" to filter the exercises");
        System.out.println("\t\"3\" to reset filters");
        System.out.println("\t\"4\" to create a new exercise");
        System.out.println("\t\"5\" to delete an exercise");
        System.out.println("\t\"<\" to return to your profile menu");
        System.out.println("\t\"/\" to exit the application\n");
    }

    // MODIFIES: this
    // EFFECTS: checks if user input is valid for the exercises menu, if invalid then indicates an invalid input;
    //          keeps user in exercises menu until exit key is pressed
    private void exercisesMenu() {
        String input = "";
        String[] inputs = {"1", "2", "3", "4", "5", "<", "/"};
        boolean isInputInvalid;

        while (!input.equals("<")) {
            displayExercisesMenu();

            input = scanner.nextLine().toLowerCase();

            isInputInvalid = true;

            for (String possibleInput : inputs) {
                if (possibleInput.equals(input)) {
                    handleExercisesMenu(possibleInput);
                    isInputInvalid = false;
                    break;
                }
            }

            if (isInputInvalid) {
                invalidSelection();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: enters exercises menu selection
    //          prints invalid input message if input to muscle group or difficulty when filtering exercises
    //          does not match an option
    private void handleExercisesMenu(String possibleInput) {
        try {
            exercisesMenuSelection(possibleInput);
        } catch (InvalidInputException e) {
            System.out.println("Please select one of the options.\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes any filters and selects the appropriate exercises menu option based on user input
    //          throws InvalidInputException if filter by time receives a non-integer input
    private void exercisesMenuSelection(String input) throws InvalidInputException {
        switch (input) {
            case "1":
                resetExercises();
                viewExercise();
                break;
            case "2":
                filterExercises();
                break;
            case "3":
                resetExercises();
                break;
            case "4":
                resetExercises();
                createExercise();
                break;
            case "5":
                resetExercises();
                deleteExercise();
        }
        menuNavigation(input);
    }

    // MODIFIES: this
    // EFFECTS: enters the menu for the exercise with the given name if found, otherwise indicates failure
    private void viewExercise() {
        String input;

        System.out.println("Enter the name of the exercise you wish to view:");
        input = scanner.nextLine();

        if (exercisesByName.contains(input)) {
            exerciseMenu(input);
        } else {
            System.out.println("Exercise cannot be found\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: accesses the exercise from the given input, keeps user in exercise menu until exit key is pressed
    private void exerciseMenu(String name) {
        exercise = exercisesByName.getExercise(name);
        String input = "";

        while (!input.equals("<")) {
            displayExerciseMenu();
            input = scanner.nextLine().toLowerCase();

            exerciseMenuSelection(input);
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for the exercise menu and additional options based on exercise type,
    //          and a string representation of the exercise
    private void displayExerciseMenu() {
        System.out.println("Exercise Information");
        System.out.println(exercise.toString());

        System.out.println("\nSelect from the following options:");
        System.out.println("\t\"1\" to edit the name");
        System.out.println("\t\"2\" to edit the muscle group");
        System.out.println("\t\"3\" to edit the difficulty");
        System.out.println("\t\"4\" to edit the time");
        System.out.println("\t\"5\" to mark favourite/unfavourite");

        // Determines additional options based on actual type of exercise

        if (exercise instanceof WeightsExercise) {
            System.out.println("\t\"6\" to edit the weight");
            System.out.println("\t\"7\" to edit the sets");
            System.out.println("\t\"8\" to edit the reps");
        } else if (exercise instanceof BodyWeightsExercise) {
            System.out.println("\t\"6\" to edit the sets");
            System.out.println("\t\"7\" to edit the reps");
        } else if (exercise instanceof CardioExercise) {
            System.out.println("\t\"6\" to edit the distance");
        }

        System.out.println("\t\"<\" to return to your profile menu");
        System.out.println("\t\"/\" to exit the application\n");
    }

    // MODIFIES: this
    // EFFECTS: checks if input is valid for the exercise menu, if invalid indicates an invalid input
    //          prints an invalid name message if exercises contains an exercise with the inputted name
    //          prints an invalid input message if input to muscle group or difficulty does not match an option
    private void exerciseMenuSelection(String input) {
        boolean exerciseOptions;
        boolean additionalExerciseOptions;

        try {
            exerciseOptions = exerciseOptions(input);
        } catch (InvalidNameException e) {
            System.out.println("Name already exists in exercises. Try another name.\n");
            exerciseOptions = true;
        } catch (InvalidInputException e) {
            System.out.println("Please select one of the given options.\n");
            exerciseOptions = true;
        }
        additionalExerciseOptions = additionalExerciseOptions(input);

        // Ensures invalid input message only printed if input is not handled already
        if (!(exerciseOptions || additionalExerciseOptions)) {
            invalidSelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: selects appropriate exercise option and returns true if input matches an exercise option,
    //          otherwise returns false
    //          throws InvalidNameException if exercises contains an exercise with an inputted name
    //          throws InvalidInputException if input to muscle group or difficulty does not match an option
    private boolean exerciseOptions(String input) throws InvalidNameException, InvalidInputException {
        switch (input) {
            case "1":
                renameExercise();
                return true;
            case "2":
                setExerciseMuscleGroup();
                return true;
            case "3":
                setExerciseDifficulty();
                return true;
            case "4":
                setExerciseTime();
                return true;
            case "5":
                setExerciseFavourite();
                return true;
            case "<":
                return true;
            case "/":
                exitApp();
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: renames exercise given user input, if exercises has an exercise with the same name,
    //          throws InvalidNameException
    private void renameExercise() throws InvalidNameException {
        String input;

        System.out.println("Enter the new exercise name:");
        input = scanner.nextLine();

        if (exercisesByName.contains(input)) {
            throw new InvalidNameException();
        }

        exercisesByName.removeExercise(exercise.getName());
        exercise.setName(input);
        exercisesByName.addExercise(exercise);
    }

    // MODIFIES: this
    // EFFECTS: sets exercise muscle group given user input
    //          prints an invalid input message if input to muscle group or difficulty does not match an option
    private void setExerciseMuscleGroup() throws InvalidInputException {
        MuscleGroup muscleGroup = editMuscleGroup();
        exercise.setMuscleGroup(muscleGroup);
    }

    // MODIFIES: this
    // EFFECTS: sets exercise difficulty given user input
    //          prints an invalid input message if input to muscle group or difficulty does not match an option
    private void setExerciseDifficulty() throws InvalidInputException {
        Difficulty difficulty = editDifficulty();
        exercise.setDifficulty(difficulty);
    }

    // MODIFIES: this
    // EFFECTS: sets exercise time given user input
    private void setExerciseTime() {
        String input;

        System.out.println("Enter the new exercise time (min):");
        input = scanner.next();

        exercise.setTime(parseInt(input));
    }

    // MODIFIES: this
    // EFFECTS: unfavourites or favourites exercise given user input
    private void setExerciseFavourite() {
        exercise.setFavourite(!exercise.isFavourite());
    }

    // MODIFIES: this
    // EFFECTS: returns muscle group given user input; if input does not match an option throws InvalidInputException
    private MuscleGroup editMuscleGroup() throws InvalidInputException {
        int input;
        MuscleGroup retMuscleGroup = null;

        System.out.println("Select muscle group:");
        // Assigns muscle group position in enum to selection number beginning with 1
        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            int position = muscleGroup.ordinal() + 1;
            System.out.println("\t" + "\"" + position + "\"" + " for " + muscleGroup.getMuscleGroup());
        }

        if (scanner.hasNextInt()) {
            input = scanner.nextInt();
            scanner.nextLine(); // Clears scanner

            // Checks if input matches muscle group position, sets muscle group if matching and breaks out of loop
            for (MuscleGroup muscleGroup : MuscleGroup.values()) {
                int position = muscleGroup.ordinal() + 1;
                if (input == position) {
                    retMuscleGroup = muscleGroup;
                    return retMuscleGroup;
                }
            }
        }
        
        throw new InvalidInputException();
    }

    // MODIFIES: this
    // EFFECTS: returns difficulty given user input; if input does not match an option throws InvalidInputException
    private Difficulty editDifficulty() throws InvalidInputException {
        int input;
        Difficulty retDifficulty = null;

        System.out.println("Select difficulty from:");
        
        // Assigns difficulty position in enum to selection number beginning with 1
        for (Difficulty difficulty : Difficulty.values()) {
            int position = difficulty.ordinal() + 1;
            System.out.println("\t" + "\"" + position + "\"" + " for " + difficulty.getDifficulty());
        }

        if (scanner.hasNextInt()) {
            input = scanner.nextInt();
            scanner.nextLine(); // Clears scanner

            // Checks if input matches muscle group position, sets muscle group if matching and breaks out of loop
            for (Difficulty difficulty : Difficulty.values()) {
                int position = difficulty.ordinal() + 1;
                if (input == position) {
                    retDifficulty = difficulty;
                    return retDifficulty;
                }
            }
        }

        throw new InvalidInputException();
    }

    // MODIFIES: this
    // EFFECTS: selects appropriate additional exercise option based on exercise type and returns true if
    //          input matches an option, otherwise returns false
    private boolean additionalExerciseOptions(String input) {
        boolean retBoolean = false;

        // Determines additional exercise options based on actual type of exercise
        if (exercise instanceof WeightsExercise) {
            WeightsExercise weightsExercise = (WeightsExercise) exercise;
            retBoolean = editWeightsExercise(weightsExercise, input);
        } else if (exercise instanceof BodyWeightsExercise) {
            BodyWeightsExercise bodyWeightsExercise = (BodyWeightsExercise) exercise;
            retBoolean = editBodyWeightsExercise(bodyWeightsExercise, input);
        } else if (exercise instanceof  CardioExercise) {
            CardioExercise cardioExercise = (CardioExercise) exercise;
            retBoolean = editCardioExercise(cardioExercise, input);
        }
        return retBoolean;
    }

    // MODIFIES: this
    // EFFECTS: selects appropriate additional exercise option for weights exercise and returns true if
    //          input matches an option, otherwise returns false
    private boolean editWeightsExercise(WeightsExercise weightsExercise, String input) {
        switch (input) {
            case "6":
                System.out.println("Enter the new exercise weight (lbs):");
                input = scanner.next();
                weightsExercise.setWeight(parseInt(input));
                return true;
            case "7":
                System.out.println("Enter the new exercise # of sets:");
                input = scanner.next();
                weightsExercise.setSets(parseInt(input));
                return true;
            case "8":
                System.out.println("Enter the new exercise # of reps:");
                input = scanner.next();
                weightsExercise.setReps(parseInt(input));
                return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: selects appropriate additional exercise option for bodyweights exercise and returns true if
    //          input matches an option, otherwise returns false
    private boolean editBodyWeightsExercise(BodyWeightsExercise bodyWeightsExercise, String input) {
        switch (input) {
            case "6":
                System.out.println("Enter the new exercise # of sets:");
                input = scanner.next();
                bodyWeightsExercise.setSets(parseInt(input));
                return true;
            case "7":
                System.out.println("Enter the new exercise # of reps:");
                input = scanner.next();
                bodyWeightsExercise.setReps(parseInt(input));
                return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: selects appropriate additional exercise option for cardio exercise and returns true if
    //          input matches an option, otherwise returns false
    private boolean editCardioExercise(CardioExercise cardioExercise, String input) {
        if (input.equals("6")) {
            System.out.println("Enter the new exercise distance:");
            input = scanner.next();
            cardioExercise.setDistance(parseInt(input));
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: prints key options for filtering exercises menu
    private void displayFilterExercisesMenu() {
        System.out.println("Select from the following filters:");
        System.out.println("\t\"1\" by name");
        System.out.println("\t\"2\" by muscle group");
        System.out.println("\t\"3\" by difficulty");
        System.out.println("\t\"4\" by time");
        System.out.println("\t\"5\" by favourite");
    }

    // MODIFIES: this
    // EFFECTS: selects the appropriate filtering option based on user input
    //          throws InvalidInputException if input to time is not an integer
    private void filterExercises() throws InvalidInputException {
        String input;

        displayFilterExercisesMenu();
        input = scanner.nextLine();

        switch (input) {
            case "1":
                filterExercisesByName();
                break;
            case "2":
                filterExercisesByMuscleGroup();
                break;
            case "3":
                filterExercisesByDifficulty();
                break;
            case "4":
                filterExercisesByTime();
                break;
            case "5":
                filterExercisesByFavourite();
                break;
            default:
                invalidSelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: filters exercises by name given user input
    private void filterExercisesByName() {
        System.out.println("Enter the name of the exercise(s) you wish to filter for:");

        String input = scanner.nextLine();

        exercisesByName = exercisesByName.filter(input);
    }

    // MODIFIES: this
    // EFFECTS: filters exercises by muscle group given user input
    //          throws InvalidInputException if input to muscle group does not match an option
    private void filterExercisesByMuscleGroup() throws InvalidInputException {
        MuscleGroup muscleGroup = editMuscleGroup();
        exercisesByName = exercisesByName.filterMuscleGroup(muscleGroup);

        scanner.nextLine(); // clears scanner
    }

    // MODIFIES: this
    // EFFECTS: filters exercises by difficulty given user input
    //          throws InvalidInputException if input to difficulty does not match an option
    private void filterExercisesByDifficulty() throws InvalidInputException {
        Difficulty difficulty = editDifficulty();
        exercisesByName = exercisesByName.filterDifficulty(difficulty);

        scanner.nextLine(); // clears scanner
    }

    // MODIFIES: this
    // EFFECTS: filters exercises with time <= user inputted time
    //          throws InvalidInputException if input to time is not an integer
    private void filterExercisesByTime() throws InvalidInputException {
        String input;

        System.out.println("Enter the exercise time you wish to filter by (all times <= given)");

        if (scanner.hasNextInt()) {
            input = scanner.next();
            scanner.nextLine(); // Clears scanner

            exercisesByName = exercisesByName.filterTime(parseInt(input));
        } else {
            scanner.nextLine(); // Clears scanner
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: filters exercises by favourited status
    private void filterExercisesByFavourite() {
        exercisesByName = exercisesByName.filterFavourite();
    }

    // MODIFIES: this
    // EFFECTS: resets exercise filters
    private void resetExercises() {
        exercisesByName = exercisesByNameMaster;
    }

    // MODIFIES: this
    // EFFECTS: creates a new exercise with name, muscle group, difficulty, time and additional exercise information
    //          based on exercise type
    //          throws InvalidInputException if input to muscle group or difficulty does not match an option
    private void createExercise() {
        Map<String, String> exerciseData = new LinkedHashMap<>();

        System.out.println("Exercise Creation\n");

        try {
            additionalExerciseInfo(exerciseData);
        } catch (InvalidNameException e) {
            System.out.println("Name already exists in exercises. Try another name.\n");
        } catch (InvalidInputException e) {
            System.out.println("Input must be an appropriate integer.\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: gets user input for exercise name and time and additional exercise information, if exercises has an
    //          exercise with the same name, throws InvalidNameException
    //          throws InvalidInputException if input to muscle group or difficulty does not match an option
    private void additionalExerciseInfo(Map<String, String> exerciseData) throws InvalidNameException,
                                                                                    InvalidInputException {
        String input;

        System.out.println("Enter the name of the exercise:");
        input = scanner.nextLine();
        exerciseData.put("name", input);

        if (exercisesByName.contains(input)) {
            throw new InvalidNameException();
        }

        System.out.println("Enter the time of the exercise (min):");
        input = scanner.next();
        exerciseData.put("time", input);

        createExerciseSelection(exerciseData);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter appropriate information based on exercise type
    //          throws InvalidInputException if input to muscle group or difficulty does not match an option
    private void createExerciseSelection(Map<String, String> exerciseData) throws InvalidInputException {
        Exercise exercise = null;
        String input;

        System.out.println("Select the exercise type:");
        System.out.println("\"1\" for weights");
        System.out.println("\"2\" for bodyweights");
        System.out.println("\"3\" for cardio");

        input = scanner.next();

        switch (input) {
            case "1":
                exercise = createWeightsExercise(exerciseData);
                break;
            case "2":
                exercise = createBodyWeightsExercise(exerciseData);
                break;
            case "3":
                exercise = createCardioExercise(exerciseData);
        }

        exercisesByName.addExercise(exercise);

        scanner.nextLine(); // clears scanner so that next cycle does not result in invalid input
    }

    // MODIFIES: this
    // EFFECTS: gets user input for weight, sets, and reps for a weights exercise
    //          throws InvalidInputException if input to muscle group or difficulty does not match an option
    private Exercise createWeightsExercise(Map<String, String> exerciseData) throws InvalidInputException {
        String input;

        System.out.println("Enter the weight of the exercise (lbs):");
        input = scanner.next();
        exerciseData.put("weight", input);
        System.out.println("Enter the sets of the exercise:");
        input = scanner.next();
        exerciseData.put("sets", input);
        System.out.println("Enter the reps of the exercise:");
        input = scanner.next();
        exerciseData.put("reps", input);

        return new WeightsExercise(exerciseData.get("name"), editMuscleGroup(),
                parseInt(exerciseData.get("weight")), parseInt(exerciseData.get("sets")),
                parseInt(exerciseData.get("reps")), editDifficulty(),
                parseInt(exerciseData.get("time")));
    }

    // MODIFIES: this
    // EFFECTS: gets user input for sets and reps for a bodyweights exercise
    //          throws InvalidInputException if input to muscle group or difficulty does not match an option
    private Exercise createBodyWeightsExercise(Map<String, String> exerciseData) throws InvalidInputException {
        String input;

        System.out.println("Enter the sets of the exercise:");
        input = scanner.next();
        exerciseData.put("sets", input);
        System.out.println("Enter the reps of the exercise:");
        input = scanner.next();
        exerciseData.put("reps", input);

        return new BodyWeightsExercise(exerciseData.get("name"), editMuscleGroup(),
                parseInt(exerciseData.get("sets")), parseInt(exerciseData.get("reps")),
                editDifficulty(), parseInt(exerciseData.get("time")));
    }

    // MODIFIES: this
    // EFFECTS: gets user input for distance for a cardio exercise
    //          throws InvalidInputException if input to muscle group or difficulty does not match an option
    private Exercise createCardioExercise(Map<String, String> exerciseData) throws InvalidInputException {
        String input;

        System.out.println("Enter the distance of the exercise (m):");
        input = scanner.next();
        exerciseData.put("distance", input);

        return new CardioExercise(exerciseData.get("name"), editMuscleGroup(),
                parseInt(exerciseData.get("distance")),
                editDifficulty(), parseInt(exerciseData.get("time")));
    }

    // MODIFIES: this
    // EFFECTS: deletes the exercise with the given name if found, otherwise indicates failure
    private void deleteExercise() {
        String input;

        System.out.println("Enter the name of the exercise you wish to delete:");
        input = scanner.nextLine();

        if (exercisesByName.contains(input)) {
            exercisesByName.removeExercise(input);
        } else {
            System.out.println("Exercise cannot be found\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: accesses the workouts for a profile and enters the menu for workouts
    private void workoutsMenuForProfile() {
        workoutsByName = profile.getWorkouts();
        workoutsByNameMaster = workoutsByName;

        workoutsMenu();
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for workouts menu
    private void displayWorkoutsMenu() {
        System.out.println("Workouts");
        System.out.println(workoutsByName.toString());

        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to view a workout by name");
        System.out.println("\t\"2\" to filter the workouts");
        System.out.println("\t\"3\" to reset filters");
        System.out.println("\t\"4\" to create a new workout");
        System.out.println("\t\"5\" to delete a workout");
        System.out.println("\t\"<\" to return to your profile menu");
        System.out.println("\t\"/\" to exit the application\n");
    }

    // MODIFIES: this
    // EFFECTS: checks if given input is valid for the workouts menu, if invalid then indicates an invalid selection;
    //          keeps user in exercises menu until exit key is pressed

    private void workoutsMenu() {
        String input = "";
        String[] inputs = {"1", "2", "3", "4", "5", "<", "/"};
        boolean isInputInvalid;

        while (!input.equals("<")) {
            displayWorkoutsMenu();
            input = scanner.nextLine().toLowerCase();

            isInputInvalid = true;

            for (String possibleInput : inputs) {
                if (possibleInput.equals(input)) {
                    handleWorkoutsMenu(possibleInput);
                    isInputInvalid = false;
                    break;
                }
            }

            if (isInputInvalid) {
                invalidSelection();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: enters workouts menu selection
    //          prints invalid name message if workouts contains a workout with the same name
    //          prints invalid input message if input to difficulty when creating a workout
    //          does not match an option
    private void handleWorkoutsMenu(String possibleInput) {
        try {
            workoutsMenuSelection(possibleInput);
        } catch (InvalidNameException e) {
            System.out.println("Name already exists in workouts. Try another name.\n");
        } catch (InvalidInputException e) {
            System.out.println("Please select one of the options.\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes any filters and selects the appropriate workout menu option given user input
    //          throws InvalidNameException if workouts contains a workout with the inputted name
    //          throws InvalidInputException if input to difficulty when creating a workout does not match an option
    private void workoutsMenuSelection(String input) throws InvalidInputException, InvalidNameException {
        switch (input) {
            case "1":
                resetWorkouts();
                viewWorkout();
                break;
            case "2":
                filterWorkouts();
                break;
            case "3":
                resetWorkouts();
                break;
            case "4":
                resetWorkouts();
                createWorkout();
                break;
            case "5":
                resetWorkouts();
                deleteWorkout();
        }
        menuNavigation(input);
    }

    // MODIFIES: this
    // EFFECTS: accesses the workout menu with the appropriate workout matching user input if found,
    //          otherwise indicates failure
    private void viewWorkout() {
        String input;

        System.out.println("Enter the name of the workout you wish to view:");
        input = scanner.nextLine();

        if (workoutsByName.contains(input)) {
            workoutMenu(input);
        } else {
            System.out.println("Workout cannot be found \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for workout menu and a string representation of the workout
    private void displayWorkoutMenu() {
        System.out.println("Workout Information");
        System.out.println(workout.toString());

        System.out.println("\nSelect from the following options:");
        System.out.println("\t\"1\" to edit the name");
        System.out.println("\t\"2\" to edit the difficulty");
        System.out.println("\t\"3\" to mark favourite/unfavourite");
        System.out.println("\t\"4\" to view options for adding/removing an exercise");
        System.out.println("\t\"5\" to clear all exercises");
        System.out.println("\t\"<\" to return to the workouts menu");
        System.out.println("\t\"/\" to exit the application");
    }

    // MODIFIES: this
    // EFFECTS: checks if user input matches a workout menu option, if invalid indicates an invalid option;
    //          keeps user in workout menu until exit key is pressed
    private void workoutMenu(String name) {
        workout = workoutsByName.getWorkout(name);
        String input = "";
        String[] inputs = {"1", "2", "3", "4", "5", "<", "/"};
        boolean isInputInvalid;

        while (!input.equals("<")) {
            displayWorkoutMenu();
            input = scanner.nextLine().toLowerCase();
            isInputInvalid = true;

            for (String possibleInput : inputs) {
                if (possibleInput.equals(input)) {
                    handleWorkoutMenu(possibleInput);
                    isInputInvalid = false;
                    break;
                }
            }

            if (isInputInvalid) {
                invalidSelection();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: enters the workout menu selection
    //          prints invalid name message if workouts contains a workout with the same name
    //          prints invalid input message if input to difficulty when setting workout difficulty
    //          does not match an option
    private void handleWorkoutMenu(String possibleInput) {
        try {
            workoutMenuSelection(possibleInput);
        } catch (InvalidNameException e) {
            System.out.println("Name already exists in workouts. Try another name.\n");
        } catch (InvalidInputException e) {
            System.out.println("Please select one of the options.\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: selects appropriate workout menu option given user input
    //          throws InvalidNameException if workouts contains a workout with the same name
    //          throws InvalidInputException if input to difficulty when setting workout difficulty
    //          does not match an option
    private void workoutMenuSelection(String input) throws InvalidNameException, InvalidInputException {
        switch (input) {
            case "1":
                renameWorkout();
                break;
            case "2":
                setWorkoutDifficulty();
                break;
            case "3":
                setWorkoutFavourite();
                break;
            case "4":
                workoutExerciseOptions();
                break;
            case "5":
                clearWorkout();
        }
        menuNavigation(input);
    }

    // MODIFIES: this
    // EFFECTS: renames workout given name inputted by user, if workouts has a workout with the same name, throws
    //          InvalidNameException
    private void renameWorkout() throws InvalidNameException {
        String input;

        System.out.println("Enter the new name of the workout:");
        input = scanner.nextLine();

        if (workoutsByName.contains(input)) {
            throw new InvalidNameException();
        }

        workoutsByName.removeWorkout(workout.getName());
        workout.setName(input);
        workoutsByName.addWorkout(workout);
    }

    // MODIFIES: this
    // EFFECTS: sets workout difficulty given user input
    private void setWorkoutDifficulty() throws InvalidInputException {
        Difficulty difficulty = editDifficulty();
        workout.setDifficulty(difficulty);
    }

    // MODIFIES: this
    // EFFECTS: if workout is favourited, unfavourites workout; if workout is unfavourited, favourites workout
    private void setWorkoutFavourite() {
        workout.setFavourite(!workout.isFavourite());
    }

    // MODIFIES: this
    // EFFECTS: removes all exercises from workout
    private void clearWorkout() {
        workout.clear();
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for editing the exercises of a workout
    private void displayWorkoutExerciseOptions() {
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to add an exercise by name");
        System.out.println("\t\"2\" to insert an exercise at a position");
        System.out.println("\t\"3\" to replace an exercise at a position");
        System.out.println("\t\"4\" to remove an exercise by name");
        System.out.println("\t\"5\" to remove an exercise at a position");
    }

    // MODIFIES: this
    // EFFECTS: processes the user input for editing the exercises of a workout
    private void workoutExerciseOptions() {
        displayWorkoutExerciseOptions();

        String input = scanner.nextLine();

        try {
            workoutExerciseOptionsSelection(input);
        } catch (InvalidPositionException e) {
            System.out.println("Position is invalid. Try another position.\n");
        } catch (InvalidNameException e) {
            System.out.println("No exercise found with the given name. Try another name.\n");
        } catch (InvalidInputException e) {
            System.out.println("Input must be an appropriate integer.\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: selects the appropriate workout exercise option given user input;
    //          throws InvalidNameException if workout exercises does not have an exercise with the given name,
    //          throws InvalidPositionException if a position is inputted greater than number of exercises
    //          throws InvalidInputException if input to position is not an integer
    private void workoutExerciseOptionsSelection(String input) throws InvalidNameException, InvalidPositionException,
                                                                        InvalidInputException {
        switch (input) {
            case "1":
                addExercise();
                break;
            case "2":
                insertExercise();
                break;
            case "3":
                replaceExercise();
                break;
            case "4":
                removeExerciseByName();
                break;
            case "5":
                removeExerciseByPosition();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an exercise from the user profile to workout given user input
    //          throws InvalidNameException if workout exercises does not have an exercise with the given name
    private void addExercise() throws InvalidNameException {
        Exercise exercise = getExerciseFromProfile();
        workout.addExercise(exercise);
    }

    // MODIFIES: this
    // EFFECTS: inserts an exercise from the user exercises to a given position in workout
    //          throws InvalidNameException if exercises does not have an exercise with the given name,
    //          throws InvalidPositionException if a position is inputted greater than number of workout exercises
    //          throws InvalidInputException if input to position is not an integer
    private void insertExercise() throws InvalidNameException, InvalidPositionException, InvalidInputException {
        Exercise exercise = getExerciseFromProfile();
        System.out.println("Enter the position where you wish to insert the exercise:");
        String input;

        if (scanner.hasNextInt()) {
            input = scanner.next();
            scanner.nextLine();

            workout.insertExercise(exercise, parseInt(input));
        } else {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: replaces an exercise in workout at a given position by an exercise from the user exercises
    //          throws InvalidNameException if exercises does not have an exercise with the given name,
    //          throws InvalidPositionException if a position is inputted greater than number of workout exercises
    //          throws InvalidInputException if input to position is not an integer
    private void replaceExercise() throws InvalidNameException, InvalidPositionException, InvalidInputException {
        Exercise exercise = getExerciseFromProfile();
        System.out.println("Enter the position of the exercise you wish to replace:");
        String input;

        if (scanner.hasNextInt()) {
            input = scanner.next();
            scanner.nextLine();

            workout.setExercise(exercise, parseInt(input));
        } else {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an exercise from the workout given a name inputted by user
    //          throws InvalidNameException if workout exercises does not have an exercise with the given name
    private void removeExerciseByName() throws InvalidNameException {
        System.out.println("Enter the name of the exercise:");
        String input = scanner.nextLine();

        try {
            workout.removeExercise(input);
        } catch (NullPointerException e) {
            throw new InvalidNameException();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an exercise from the workout given its position inputted by user
    //          throws InvalidPositionException if a position is inputted greater than number of workout exercises
    //          throws InvalidInputException if input to position is not an integer
    private void removeExerciseByPosition() throws InvalidPositionException, InvalidInputException {
        System.out.println("Enter the position of the exercise you wish to remove:");
        String input;

        if (scanner.hasNextInt()) {
            input = scanner.next();
            scanner.nextLine();

            workout.removeExercise(parseInt(input));
        } else {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: returns an exercise from the user exercises matching name inputted by user if found, otherwise
    //          throws InvalidNameException
    private Exercise getExerciseFromProfile() throws InvalidNameException {
        String input;

        System.out.println("Enter the name of the exercise:");
        input = scanner.nextLine();

        if (profile.getExercises().contains(input)) {
            return profile.getExercises().getExercise(input);
        } else {
            throw new InvalidNameException();
        }
    }

    // MODIFIES: this
    // EFFECTS: prints key options for filtering workouts
    private void displayFilterWorkoutsMenu() {
        System.out.println("Select from the following filters:");
        System.out.println("\t\"1\" by name");
        System.out.println("\t\"2\" by difficulty");
        System.out.println("\t\"3\" by time");
        System.out.println("\t\"4\" by # of exercises");
        System.out.println("\t\"5\" by favourite");
    }

    // MODIFIES: this
    // EFFECTS: processes user input to filter workouts
    private void filterWorkouts() {
        String input;

        displayFilterWorkoutsMenu();

        input = scanner.nextLine();

        try {
            filterWorkouts(input);
        } catch (InvalidInputException e) {
            System.out.println("Input must be an appropriate integer.\n");
        }

        scanner.nextLine(); // clears scanner
    }

    // MODIFIES: this
    // EFFECTS: selects the appropriate filtering option for workouts given user input
    //          throws InvalidInputException if input to time or # of exercises is not an integer
    private void filterWorkouts(String input) throws InvalidInputException {
        switch (input) {
            case "1":
                filterWorkoutsByName();
                break;
            case "2":
                filterWorkoutsByDifficulty();
                break;
            case "3":
                filterWorkoutsByTime();
                break;
            case "4":
                filterWorkoutsByNumberOfExercises();
                break;
            case "5":
                filterWorkoutsByFavourite();
                break;
            default:
                invalidSelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: filters workouts by name given user input
    private void filterWorkoutsByName() {
        String input;

        System.out.println("Enter the name of the workout(s) you wish to filter for");
        input = scanner.nextLine();

        workoutsByName = workoutsByName.filter(input);
    }

    // MODIFIES: this
    // EFFECTS: filters workouts by difficulty matching user input
    //          throws InvalidInputException if input to difficulty does not match an option
    private void filterWorkoutsByDifficulty() throws InvalidInputException {
        Difficulty difficulty = editDifficulty();
        workoutsByName = workoutsByName.filterDifficulty(difficulty);
    }

    // MODIFIES: this
    // EFFECTS: filters workouts with time <= user inputted time
    //          throws InvalidInputException if input to time is not an integer
    private void filterWorkoutsByTime() throws InvalidInputException {
        int input;

        System.out.println("Enter the time of the workout(s) you wish to filter for (all <= given)");

        if (scanner.hasNextInt()) {
            input = scanner.nextInt();
            scanner.nextLine(); // Clears scanner

            workoutsByName = workoutsByName.filterTime(input);
        } else {
            scanner.nextLine(); // Clears scanner
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: filters workouts with number of exercises <= user inputted number
    //          throws InvalidInputException if input to time is not an integer
    private void filterWorkoutsByNumberOfExercises() throws InvalidInputException {
        int input;

        System.out.println("Enter the # of exercises of the workout(s) you wish to filter for (all <= given)");

        if (scanner.hasNextInt()) {
            input = scanner.nextInt();
            scanner.nextLine(); // Clears scanner

            workoutsByName = workoutsByName.filterNumberOfExercises(input);
        } else {
            scanner.nextLine(); // Clears scanner
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: filters workouts by favourited status
    private void filterWorkoutsByFavourite() {
        workoutsByName = workoutsByName.filterFavourite();
    }

    // MODIFIES: this
    // EFFECTS: removes filters from the user workouts
    private void resetWorkouts() {
        workoutsByName = workoutsByNameMaster;
    }

    // MODIFIES: this
    // EFFECTS: creates a new workout with user inputted name and difficulty and adds it to the user workouts
    //          if workouts has a workout with the same name, throws an InvalidNameException
    //          throws an InvalidInputException if input to difficulty does not match an option
    private void createWorkout() throws InvalidNameException, InvalidInputException {
        Map<String, String> workoutData = new LinkedHashMap<>();
        String input;

        System.out.println("Workout Creation\n");

        workoutData.put("name", null);

        System.out.println("Enter the name of the workout:");
        input = scanner.nextLine();

        if (workoutsByName.contains(input)) {
            throw new InvalidNameException();
        }

        workoutData.replace("name", input);

        Workout workout = new Workout(workoutData.get("name"), editDifficulty());

        workoutsByName.addWorkout(workout);

        scanner.nextLine(); // clears scanner so that next cycle does not result in invalid input
    }

    // MODIFIES: this
    // EFFECTS: removes a workout from user workouts if found, otherwise indicates failure
    private void deleteWorkout() {
        String input;

        System.out.println("Enter the name of the workout you wish to delete:");
        input = scanner.nextLine();

        if (workoutsByName.contains(input)) {
            workoutsByName.removeWorkout(input);
        } else {
            System.out.println("Workout cannot be found\n");
        }
    }

    // EFFECTS: exits the application
    private void exitApp() {
        System.out.println("Application was exited.");
        System.exit(0);
    }

    // EFFECTS: Checks if input matches a key to exit to a previous menu or to exit the application
    private void menuNavigation(String input) {
        switch (input) {
            case "<":
                break;
            case "/":
                exitApp();
        }
    }

    // EFFECTS: prints a statement indicating an invalid input
    private void invalidSelection() {
        System.out.println("Please select a valid option.\n");
    }
}
