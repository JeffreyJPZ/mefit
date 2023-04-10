# Fitness Application

###  Improve your gains
This application serves as a fitness hub for users. Whether you are:

- a new gym goer
- an experienced gym rat
- currently regretting wasting money on that gym membership in January
- making excuses for not going to the gym (possibly me)

There is something for everyone. For example, it might be difficult sometimes especially for new gym goers to
build a balanced or consistent schedule. Or, one might be doing the same *boring* schedule
continuously and dreading going to the gym, but lazy enough to not want to spend time reorganizing their workout,
including myself.

The application will allow you to make your **own personal profile** by entering basic information such as your name,
age, gender, and weight. For each profile, you can enter exercises with a number of sets,
repetitions, time per set, muscle groups worked, and a personal difficulty rating. 
You can view saved exercises by various filters, and edit them.
You will also be able to generate a workout schedule with your list of exercises. 
These workouts can be named and saved.

Event logging classes and their tests were derived from AlarmSystem from CPSC 210.

Inspiration for the console application was taken from TellerApp and JsonSerializationDemo from CPSC 210.

Additional inspiration for the GUI was from:
- SimpleDrawingPlayer and SpaceInvadersStarter from CPSC 210
- https://stackoverflow.com/questions/6175899/how-to-change-card-layout-panels-from-another-panel
- https://stackoverflow.com/questions/47841447/how-to-refresh-jtable-after-click-the-jbutton
- https://stackoverflow.com/questions/14393112/jtable-in-jscrollpane
- https://stackoverflow.com/questions/13264907/why-is-windowclosed-not-being-called

## User Stories
- As a user, I want to be able to make a new exercise and add it to a list of exercises for my profile.
- As a user, I want to be able to view and delete exercises from my profile.
- As a user, I want to be able to select an exercise and edit its information,
  including its name, muscle group, difficulty, and time.
- As a user, I want to be able to generate a workout schedule with the list of exercises for my profile.
- As a user, I want to be able to save all the profiles in the fitness application to file from the main menu.
- As a user, I want to be able to be able to load saved profiles from file from the main menu.

# Instructions for Grader

- You can generate the first required action related to adding an exercises to the exercises on profile by:
  - Pressing the "Profiles" button on the home screen
  - In the profiles screen, either add a new profile by pressing the "Add Profile" button and follow the example inputs or
  load the sample saved profiles from file (recommended) by pressing the "Load Profiles" button
  - Click on a profile from the table and press the "View Selected Profile" button
  - Press the "Exercises" button in the profile screen
  - Press the "Add Exercise" button in the exercises screen and follow the example inputs
  - Press the "Add Exercise" button after you are done inputting to return to the exercises screen
  - New exercise should appear in the exercises screen
  
- You can generate the second required action related to adding Xs to a Y by:
  - Pressing the "Profiles" button on the home screen
  - In the profiles screen, either add a new profile by pressing the "Add Profile" button and follow the example inputs or
    load the sample saved profiles from file (recommended) by pressing the "Load Profiles" button
  - Click on a profile from the table and press the "View Selected Profile" button
  - Press the "Exercises" button in the profile screen
  - Click on the exercise(s) you want to delete in the exercises screen and press the "Delete Exercise" button
  - Exercise(s) should disappear from the exercises screen
  - **OR**
  - Once in the exercises screen, select a filter from the dropdown menu titled "Filters"
  - Match the input types in the exercises table when typing in your filter in the textbox
  - Exercise(s) should be filtered

- You can locate my visual component by looking at the beautiful picture on the home screen when the application starts

- You can save the state of my application by:
  - Pressing the "Profiles" button on the home screen
  - Pressing the "Save Profiles" button on the profiles screen (after adding or removing some profile/exercise)

- You can reload the state of my application by:
  - Pressing the "Profiles" button on the home screen
  - Pressing the "Load Profiles" button on the profiles screen (after adding or removing some profile/exercise)

### Phase 4: Task 2
**NOTE: First 7 events are from loading profiles from JSON** 

Tue Apr 04 17:56:25 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:56:25 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:56:25 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:56:25 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:56:25 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:56:25 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:56:25 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:57:03 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:57:10 PDT 2023 \
Added an exercise to a profile's exercises \
Tue Apr 04 17:57:20 PDT 2023 \
Filtered a profile's exercises \
Tue Apr 04 17:57:29 PDT 2023 \
Removed an exercise from a profile's exercises \
Tue Apr 04 17:57:29 PDT 2023 \
Removed an exercise from a profile's exercises

### Phase 4: Task 3
From the project's UML diagram, it could make sense to apply the Singleton pattern to the FitnessApp class.
There should really only be one instance of the FitnessApp class, as it the frame that contains the different panels
of the application. All the panels need to have a reference to FitnessApp, as FitnessApp needs to switch between panels
when certain events are handled (such as a button press), thus it should be accessible to all classes in the package.
Static methods would not really be applicable, as the fields of FitnessApp inherit from supertypes
and therefore cannot be static. To refactor FitnessApp,
I would have a static field that instantiates the FitnessApp at runtime (since main will need the instance)
and references the single FitnessApp instance. I would also make the constructor private,
and have a public static method that returns the single instance.

I could also increase the cohesion of the panel classes, as right now they are responsible for storing the model
and event handling in addition to displaying the model (using buttons and other components). This can be seen in
the ProfilesPanel and ProfilePanel classes in the UML diagram, which have associations to classes in the model package
and to other panels. Refactoring would include separating each panel into a class that contains the model and a
class that updates the display, respectively. However, this might introduce coupling as the methods in the
current panel classes modify the same fields. One way to reduce this could be to have the model class extend JPanel, 
while having the display class extend the model class to inherit the necessary fields. 
While this might not violate Liskov Substitution Principle as the display class would only add
behaviour that the model class would not have, it would defeat the point of separating the classes in the first place, 
so this might not be a good solution.

I could also apply the observer pattern to the GUI to reduce coupling, comprising the FitnessApp class
and many panels that are associated with it. For example, the UML diagram shows that all panels are associated with 
FitnessApp as FitnessApp needs to change state when events are handled. However, some panels such as
ExercisesPanel need to update their state when an event is handled in the ProfilePanel class. Currently, I need
to explicitly update FitnessApp and any additional panels when events are handled, which could get confusing if I decide
to add more features and panels in the future. To refactor, I could first separate each panel into
model and display classes as mentioned above and have the model class extend an abstract Subject class.
Each subject would maintain a list of some observers (FitnessApp, the display class, other model classes).
Each observer would implement the Observer interface, implementing their own update method that takes in the necessary
parameter(s) that is called when an event is handled. However, some subjects would also need to be observers to other 
subjects as well. Therefore, those panels must also implement the Observer interface.

If I implement the observer pattern, I could improve cohesion by making event handling its own class. 
However, event handling will need implementation details of the model and display classes.
Seeing as each display has different components and therefore will handle different events,
it may be worthwhile to make event handling an inner class of each display class. This way, it is only used by its
parent display class.