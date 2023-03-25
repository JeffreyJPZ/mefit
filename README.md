# Fitness Application

###  Improve your gains
This application serves as an fitness hub for users. Whether you are:

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

Inspiration was taken from TellerApp and JsonSerializationDemo from CPSC 210.

## User Stories
- As a user, I want to be able to make a new exercise and add it to a list of exercises for my profile.
- As a user, I want to be able to view and delete exercises from my profile.
- As a user, I want to be able to select an exercise and edit its information,
  including its name, muscle group, difficulty, and time.
- As a user, I want to be able to generate a workout schedule with the list of exercises for my profile.

- As a user, I want to be able to save all the profiles in the fitness application to file from the main menu.
- As a user, I want to be able to be able to load saved profiles from file from the main menu.
- 
# Instructions for Grader

- You can generate the first required action related to adding an exercises to the exercises on profile by:
  - Pressing the "Profiles" button on the home screen
  - In the profiles screen, either add a new profile by pressing the "Add Profile" and follow the example inputs or
  load the sample saved profiles from file (recommended) by pressing the "Load Profiles" button
  - Click on a profile from the table and press the "View Selected Profile" button
  - Press the "Exercises" button in the profile screen
  - Press the "Add Exercise" button in the exercises screen and follow the example inputs
  - Press the "Add Exercise" button after you are done inputting to return to the exercises screen
  - New exercise should appear in the exercises screen
  
- You can generate the second required action related to adding Xs to a Y by:
  - Pressing the "Profiles" button on the home screen
  - In the profiles screen, either add a new profile by pressing the "Add Profile" and follow the example inputs or
    load the sample saved profiles from file (recommended) by pressing the "Load Profiles" button
  - Click on a profile from the table and press the "View Selected Profile" button
  - Press the "Exercises" button in the profile screen
  - Click on the exercise(s) you want to delete in the exercises screen and press the "Delete Exercise" button
  - Exercise(s) should disappear from the exercises screen
  - **OR**
  - Once in the exercises screen, select a filter from the dropdown menu titled "Filters"
  - Match the input types in the exercises table when typing in your filter in the textbox
  - Exercise(s) should be filtered

- You can locate my visual component by looking at the beautiful picture on the home screen

- You can save the state of my application by:
  - Pressing the "Profiles" button on the home screen
  - Pressing the "Save Profiles" button on profiles screen (after adding or removing some profile/exercise)

- You can reload the state of my application by:
  - Pressing the "Profiles" button on the home screen
  - Pressing the "Load Profiles" button on profiles screen (after adding or removing some profile/exercise)