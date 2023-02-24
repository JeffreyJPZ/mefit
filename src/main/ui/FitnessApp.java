package ui;

import model.*;

import java.util.*;

import static java.lang.Integer.parseInt;

// Represents a fitness application with user profiles, editing and viewing of exercises and workouts
public class FitnessApp {
    private ProfilesById profilesById;
    private ProfilesById profilesByIdSaved;
    private Profile profile;
    private ExercisesByName exercisesByName;
    private ExercisesByName exercisesByNameSaved;
    private Exercise exercise;
    private WorkoutsByName workoutsByName;
    private WorkoutsByName workoutsByNameSaved;
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
            System.out.println(profilesById.toString());
            input = scanner.nextLine().toLowerCase();

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
        profilesByIdSaved = profilesById;
        scanner = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: prints the key options for main profiles menu
    private void mainMenu() {
        System.out.println("Fitness App\n");
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to access a profile by id");
        System.out.println("\t\"2\" to filter for matching profiles by name");
        System.out.println("\t\"3\" to reset filters");
        System.out.println("\t\"4\" to create a profile");
        System.out.println("\t\"5\" to delete a profile");
        System.out.println("\t\"/\" to exit the application\n");
    }

    // MODIFIES: this
    // EFFECTS: processes the key options for main profiles menu and resets any filters
    private void mainMenuSelection(String input) {
        switch (input) {
            case "1":
                resetProfiles();
                accessProfile();
            case "2":
                filterProfiles();
                break;
            case "3":
                resetProfiles();
                break;
            case "4":
                resetProfiles();
                createProfile();
                break;
            case "5":
                resetProfiles();
                deleteProfile();
                break;
            case "/":
                break;
            default:
                System.out.println("Please select a valid option.\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: views the profile if given profile id matches a profile, otherwise indicates failure to find profile
    private void accessProfile() {
        String input;

        System.out.println("Type the id number of the profile you wish to access:");
        input = scanner.next();

        if (profilesByIdSaved.contains(parseInt(input))) {
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
        profilesById = profilesByIdSaved;
    }

    // MODIFIES: this
    // EFFECTS: makes a profile with user inputted name, gender, age, and weight and adds it to the profiles
    private void createProfile() {
        Map<String, String> profileData = new LinkedHashMap<>();
        String input;

        profileData.put("name", null);
        profileData.put("gender", null);
        profileData.put("age", null);
        profileData.put("weight", null);

        System.out.println("Profile Creation\n");

        for (String data : profileData.keySet()) {
            System.out.println("Enter your " + data + ":");
            input = scanner.nextLine();
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
    // EFFECTS: deletes the profile with a given id if found, otherwise prints failure
    private void profileMenu(String id) {
        profile = profilesByIdSaved.getProfile(parseInt(id));
        String input = "";
        boolean profileMenuSelection;

        while (!input.equals("<")) {
            displayProfileMenu();

            input = scanner.nextLine().toLowerCase();

            profileMenuSelection = profileMenuSelection(input);

            if (!profileMenuSelection) {
                menuNavigation(input);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the profile with a given id if found, otherwise prints failure
    private boolean profileMenuSelection(String input) {
        switch (input) {
            case "1":
                profileInfoMenu();
                return true;
            case "2":
                exercisesMenuForProfile();
                return true;
            case "3":
                workoutsMenuForProfile();
                return true;
        }
        return false;
    }

    private void displayProfileMenu() {
        System.out.println("Welcome, " + profile.getName());
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to view your profile information");
        System.out.println("\t\"2\" to view your exercises");
        System.out.println("\t\"3\" to view your workouts");
        System.out.println("\t\"<\" to return to the main menu");
        System.out.println("\t\"/\" to exit the application\n");
    }

    private void profileInfoMenu() {
        String input = "";
        boolean profileInfoSelection;

        while (!input.equals("<")) {
            displayProfileInfoMenu();
            input = scanner.nextLine().toLowerCase();

            profileInfoSelection = profileInfoSelection(input);

            if (!profileInfoSelection) {
                menuNavigation(input);
            }
        }
    }

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

    private boolean profileInfoSelection(String input) {
        switch (input) {
            case "1":
                System.out.println("Enter your new name:");
                input = scanner.nextLine();
                profile.setName(input);
                return true;
            case "2":
                System.out.println("Enter your new gender:");
                input = scanner.nextLine();
                profile.setGender(input);
                return true;
            case "3":
                System.out.println("Enter your new age:");
                input = scanner.next();
                profile.setAge(parseInt(input));
                return true;
            case "4":
                System.out.println("Enter your new weight:");
                input = scanner.next();
                profile.setAge(parseInt(input));
                return true;
        }
        return false;
    }

    private void exercisesMenuForProfile() {
        exercisesByName = profile.getExercises();
        exercisesByNameSaved = exercisesByName;

        exercisesMenu();
    }

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

    private void exercisesMenu() {
        String input = "";
        boolean exercisesMenuSelection;

        while (!input.equals("<")) {
            displayExercisesMenu();
            input = scanner.nextLine().toLowerCase();

            exercisesMenuSelection = exercisesMenuSelection(input);

            if (!exercisesMenuSelection) {
                menuNavigation(input);
            }
        }
    }

    private boolean exercisesMenuSelection(String input) {
        switch (input) {
            case "1":
                resetExercises();
                viewExercise();
                return true;
            case "2":
                filterExercises();
                return true;
            case "3":
                resetExercises();
                return true;
            case "4":
                resetExercises();
                createExercise();
                return true;
            case "5":
                resetExercises();
                deleteExercise();
                return true;
        }
        return false;
    }

    private void viewExercise() {
        String input;

        System.out.println("Enter the name of the exercise you wish to view:");
        input = scanner.nextLine();

        if (exercisesByName.contains(input)) {
            exerciseMenu(input);
        } else {
            System.out.println("Exercise cannot be found \n");
        }
    }

    private void exerciseMenu(String name) {
        exercise = exercisesByName.getExercise(name);
        String input = "";

        while (!input.equals("<")) {
            displayExerciseMenu();
            input = scanner.nextLine().toLowerCase();
            exerciseMenuSelection(input);
        }
    }

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

    private void exerciseMenuSelection(String input) {
        boolean exerciseOptions = false;
        boolean additionalExerciseOptions = false;

        exerciseOptions = exerciseOptions(input);
        additionalExerciseOptions = additionalExerciseOptions(input);

        // Ensures default "select a valid option" message only printed if input is not handled already
        if (!(exerciseOptions || additionalExerciseOptions)) {
            menuNavigation(input);
        }
    }

    // MODIFIES: this
    // EFFECTS: returns true if input matches an exercise option, otherwise returns false
    private boolean exerciseOptions(String input) {
        switch (input) {
            case "1":
                System.out.println("Enter the new exercise name:");
                input = scanner.nextLine();
                exercise.setName(input);
                return true;
            case "2":
                exercise.setMuscleGroup(editMuscleGroup());
                return true;
            case "3":
                exercise.setDifficulty(editDifficulty());
                return true;
            case "4":
                System.out.println("Enter the new exercise time:");
                input = scanner.next();
                exercise.setTime(parseInt(input));
                return true;
            case "5":
                exercise.setFavourite(!exercise.isFavourite());
                return true;
        }
        return false;
    }

    private MuscleGroup editMuscleGroup() {
        int input;
        MuscleGroup retMuscleGroup = null;

        System.out.println("Select muscle group:");
        // Assigns muscle group position in enum to selection number beginning with 1
        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            int position = muscleGroup.ordinal() + 1;
            System.out.println("\t" + "\"" + position + "\"" + " for " + muscleGroup.getMuscleGroup());
        }

        input = scanner.nextInt();

        // Checks if input matches muscle group position, sets muscle group if matching and breaks out of loop
        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            int position = muscleGroup.ordinal() + 1;
            if (input == position) {
                retMuscleGroup = muscleGroup;
                break;
            }
        }

        return retMuscleGroup;
    }

    private Difficulty editDifficulty() {
        int input;
        Difficulty retDifficulty = null;

        System.out.println("Select difficulty from:");
        // Assigns difficulty position in enum to selection number beginning with 1
        for (Difficulty difficulty : Difficulty.values()) {
            int position = difficulty.ordinal() + 1;
            System.out.println("\t" + "\"" + position + "\"" + " for " + difficulty.getDifficulty());
        }

        input = scanner.nextInt();

        // Checks if input matches muscle group position, sets muscle group if matching and breaks out of loop
        for (Difficulty difficulty : Difficulty.values()) {
            int position = difficulty.ordinal() + 1; // UPDATE COUNTER
            if (input == position) {
                retDifficulty = difficulty;
                break;
            }
        }

        return retDifficulty;
    }

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

    private boolean editWeightsExercise(WeightsExercise weightsExercise, String input) {
        switch (input) {
            case "6":
                System.out.println("Enter the new exercise weight:");
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

    private boolean editCardioExercise(CardioExercise cardioExercise, String input) {
        if (input.equals("6")) {
            System.out.println("Enter the new exercise distance:");
            input = scanner.next();
            cardioExercise.setDistance(parseInt(input));
            return true;
        }
        return false;
    }

    private void displayFilterExercisesMenu() {
        System.out.println("Select from the following filters:");
        System.out.println("\t\"1\" by name");
        System.out.println("\t\"2\" by muscle group");
        System.out.println("\t\"3\" by difficulty");
        System.out.println("\t\"4\" by time");
        System.out.println("\t\"5\" by favourite");
    }

    private void filterExercises() {
        String input;

        displayFilterExercisesMenu();
        input = scanner.next();

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
        }
    }

    private void filterExercisesByName() {
        String input;

        System.out.println("Enter the name of the exercise(s) you wish to filter for:");
        input = scanner.nextLine();

        exercisesByName = exercisesByName.filter(input);
    }

    private void filterExercisesByMuscleGroup() {
        exercisesByName = exercisesByName.filterMuscleGroup(editMuscleGroup());
    }

    private void filterExercisesByDifficulty() {
        exercisesByName = exercisesByName.filterDifficulty(editDifficulty());
    }

    private void filterExercisesByTime() {
        int input;
        System.out.println("Enter the exercise time you wish to filter by (all times <= given)");

        input = scanner.nextInt();

        exercisesByName = exercisesByName.filterTime(input);
    }

    private void filterExercisesByFavourite() {
        exercisesByName = exercisesByName.filterFavourite();
    }

    private void resetExercises() {
        exercisesByName = exercisesByNameSaved;
    }

    private void createExercise() {
        Map<String, String> exerciseData = new LinkedHashMap<>();

        System.out.println("Exercise Creation\n");

        additionalExerciseInfo(exerciseData);
    }

    private void additionalExerciseInfo(Map<String, String> exerciseData) {
        String input;

        System.out.println("Enter the name of the exercise:");
        input = scanner.nextLine();
        scanner.nextLine();
        exerciseData.put("name", input);
        System.out.println("Enter the time of the exercise:");
        input = scanner.next();
        exerciseData.put("time", input);

        createExerciseSelection(exerciseData);
    }

    private void createExerciseSelection(Map<String, String> exerciseData) {
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
    }

    private Exercise createWeightsExercise(Map<String, String> exerciseData) {
        String input;

        System.out.println("Enter the weight of the exercise:"); // TODO: FOR LOOP
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

    private Exercise createBodyWeightsExercise(Map<String, String> exerciseData) {
        String input;

        System.out.println("Enter the sets of the exercise:"); // TODO: FOR LOOP
        input = scanner.next();
        exerciseData.put("sets", input);
        System.out.println("Enter the reps of the exercise:");
        input = scanner.next();
        exerciseData.put("reps", input);

        return new BodyWeightsExercise(exerciseData.get("name"), editMuscleGroup(),
                parseInt(exerciseData.get("sets")), parseInt(exerciseData.get("reps")),
                editDifficulty(), parseInt(exerciseData.get("time")));
    }

    private Exercise createCardioExercise(Map<String, String> exerciseData) {
        String input;

        System.out.println("Enter the distance of the exercise:");
        input = scanner.next();
        exerciseData.put("distance", input);

        return new CardioExercise(exerciseData.get("name"), editMuscleGroup(),
                parseInt(exerciseData.get("distance")),
                editDifficulty(), parseInt(exerciseData.get("time")));
    }

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

    private void workoutsMenuForProfile() {
        workoutsByName = profile.getWorkouts();
        workoutsByNameSaved = workoutsByName;

        workoutsMenu();
    }

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

    private void workoutsMenu() {
        String input = "";
        boolean workoutsMenuSelection;

        while (!input.equals("<")) {
            displayWorkoutsMenu();
            input = scanner.nextLine().toLowerCase();

            workoutsMenuSelection = workoutsMenuSelection(input);

            if (!workoutsMenuSelection) {
                menuNavigation(input);
            }
        }
    }

    private boolean workoutsMenuSelection(String input) {
        switch (input) {
            case "1":
                resetWorkouts();
                viewWorkout();
                return true;
            case "2":
                filterWorkouts();
                return true;
            case "3":
                resetWorkouts();
                return true;
            case "4":
                resetWorkouts();
                createWorkout();
                return true;
            case "5":
                resetWorkouts();
                deleteWorkout();
                return true;
        }
        return false;
    }

    private void viewWorkout() {
        String input;

        System.out.println("Enter the name of the workout you wish to view:");
        input = scanner.nextLine();

        if (workoutsByName.contains(input)) {
            workout = workoutsByName.getWorkout(input);
            workoutMenu();
        } else {
            System.out.println("Workout cannot be found \n");
        }
    }

    private void displayWorkoutMenu() {
        System.out.println("Workout Information");
        System.out.println(workout.toString());
        System.out.println(workout.getName());

        System.out.println("\nSelect from the following options:");
        System.out.println("\t\"1\" to edit the name");
        System.out.println("\t\"2\" to edit the difficulty");
        System.out.println("\t\"3\" to mark favourite/unfavourite");
        System.out.println("\t\"4\" to view options for adding an exercise");
        System.out.println("\t\"5\" to clear all exercises");
        System.out.println("\t\"<\" to return to the workouts menu");
        System.out.println("\t\"/\" to exit the application");
    }

    private void workoutMenu() {
        String input = "";
        boolean workoutMenuSelection;

        while (!input.equals("<")) {
            displayWorkoutMenu();
            input = scanner.nextLine().toLowerCase();
            workoutMenuSelection = workoutMenuSelection(input);

            if (!workoutMenuSelection) {
                menuNavigation(input);
            }
        }
    }

    private boolean workoutMenuSelection(String input) {
        switch (input) {
            case "1":
                System.out.println("Enter the new name of the workout:");
                input = scanner.nextLine();
                workout.setName(input); // TODO: MUST UPDATE KEY FOR WORKOUT IN WORKOUTS AND EXERCISE IN EXERCISES
                return true;
            case "2":
                workout.setDifficulty(editDifficulty());
                return true;
            case "3":
                workout.setFavourite(!workout.isFavourite());
                return true;
            case "4":
                workoutExerciseOptions();
                return true;
            case "5":
                workout.clear();
                return true;
        }
        return false;
    }

    private void displayWorkoutExerciseOptions() {
        System.out.println("Select from the following options:");
        System.out.println("\t\"1\" to add an exercise by name");
        System.out.println("\t\"2\" to insert an exercise at a position");
        System.out.println("\t\"3\" to replace an exercise at a position");
        System.out.println("\t\"4\" to remove an exercise by name");
        System.out.println("\t\"5\" to remove an exercise at a position");
    }

    private void workoutExerciseOptions() {
        displayWorkoutExerciseOptions();

        String input = scanner.nextLine();

        workoutExerciseOptionsSelection(input);
    }

    private void workoutExerciseOptionsSelection(String input) {
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

    private void addExercise() {
        Exercise exercise = getExerciseFromExercisesByName();
        workout.addExercise(exercise);
    }

    private void insertExercise() {
        System.out.println("Enter the position where you wish to insert the exercise:");
        String input = scanner.next();
        workout.insertExercise(exercise, parseInt(input));
    }

    private void replaceExercise() {
        System.out.println("Enter the position of the exercise you wish to replace:");
        String input = scanner.next();
        Exercise exercise = getExerciseFromExercisesByName();
        workout.setExercise(exercise, parseInt(input));
    }

    private void removeExerciseByName() {
        System.out.println("Enter the name of the exercise:");
        String input = scanner.nextLine();
        workout.removeExercise(input);
    }

    private void removeExerciseByPosition() {
        System.out.println("Enter the position of the exercise you wish to remove:");
        String input = scanner.next();
        workout.removeExercise(parseInt(input));
    }

    private Exercise getExerciseFromExercisesByName() {
        String input;

        System.out.println("Enter the name of the exercise:");
        input = scanner.nextLine();

        return exercisesByName.getExercise(input);
    }

    private void displayFilterWorkoutsMenu() {
        System.out.println("Select from the following filters:");
        System.out.println("\t\"1\" by name");
        System.out.println("\t\"2\" by difficulty");
        System.out.println("\t\"3\" by time");
        System.out.println("\t\"4\" by # of exercises");
        System.out.println("\t\"5\" by favourite");
    }

    private void filterWorkouts() {
        String input;

        displayFilterWorkoutsMenu();

        input = scanner.next();

        filterWorkouts(input);
    }

    private void filterWorkouts(String input) {
        switch (input) {
            case "1":
                System.out.println("Enter the name of the workout(s) you wish to filter for");
                input = scanner.nextLine();
                workoutsByName = workoutsByName.filter(input);
                break;
            case "2":
                workoutsByName = workoutsByName.filterDifficulty(editDifficulty());
                break;
            case "3":
                System.out.println("Enter the time of the workout(s) you wish to filter for (all <= given)");
                input = scanner.next();
                workoutsByName = workoutsByName.filterTime(parseInt(input));
                break;
            case "4":
                System.out.println("Enter the # of exercises of the workout(s) you wish to filter for (all <= given)");
                input = scanner.next();
                workoutsByName = workoutsByName.filterNumberOfExercises(parseInt(input));
                break;
            case "5":
                workoutsByName = workoutsByName.filterFavourite();
        }
    }

    private void resetWorkouts() {
        workoutsByName = workoutsByNameSaved;
    }

    private void createWorkout() {
        Map<String, String> workoutData = new LinkedHashMap<>();
        String input;

        System.out.println("Workout Creation\n");

        workoutData.put("name", null);

        for (String data : workoutData.keySet()) {
            System.out.println("Enter the " + data + " of the workout:");
            input = scanner.nextLine();
            workoutData.replace(data, input);
        }

        Workout workout = new Workout(workoutData.get("name"), editDifficulty());

        workoutsByName.addWorkout(workout);
    }

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

    // EFFECTS: Checks if input matches a key to backtrack to a previous menu, exit the application, or indicates
    //          an invalid input
    private void menuNavigation(String input) {
        switch (input) {
            case "<":
                break;
            case "/":
                exitApp();
            default:
                System.out.println("Please select a valid option.\n");
                // PRINTS WHEN MAKING NEW EXERCISE/ACCESSING PROFILE
                // TODO: FIX BY CHECKING INPUTS AGAINST LIST OF INTEGER INPUTS?
        }
    }
}
