package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for CardioExercise
public class CardioExerciseTest {
    private CardioExercise cardioExerciseTest1;
    private CardioExercise cardioExerciseTest2;
    private CardioExercise cardioExerciseTest3;

    @BeforeEach
    public void runBefore() {
        cardioExerciseTest1 = new CardioExercise("Treadmill", MuscleGroup.LEGS,
                5000, Difficulty.MODERATE, 45);
        cardioExerciseTest2 = new CardioExercise("Rowing", MuscleGroup.BACK,
                2000, Difficulty.INTENSE, 10);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Treadmill", cardioExerciseTest1.getName());
        assertEquals(MuscleGroup.LEGS, cardioExerciseTest1.getMuscleGroup());
        assertEquals(5000, cardioExerciseTest1.getDistance());
        assertEquals(Difficulty.MODERATE, cardioExerciseTest1.getDifficulty());
        assertEquals(45, cardioExerciseTest1.getTime());
        assertFalse(cardioExerciseTest1.isFavourite());

        assertEquals("Rowing", cardioExerciseTest2.getName());
        assertEquals(MuscleGroup.BACK, cardioExerciseTest2.getMuscleGroup());
        assertEquals(2000, cardioExerciseTest2.getDistance());
        assertEquals(Difficulty.INTENSE, cardioExerciseTest2.getDifficulty());
        assertEquals(10, cardioExerciseTest2.getTime());
        assertFalse(cardioExerciseTest2.isFavourite());
    }

    @Test
    public void testConstructorBoundary() {
        cardioExerciseTest3 = new CardioExercise("Bike", MuscleGroup.LEGS, 1, Difficulty.LIGHT, 1);

        assertEquals("Bike", cardioExerciseTest3.getName());
        assertEquals(MuscleGroup.LEGS, cardioExerciseTest3.getMuscleGroup());
        assertEquals(1, cardioExerciseTest3.getDistance());
        assertEquals(Difficulty.LIGHT, cardioExerciseTest3.getDifficulty());
        assertEquals(1, cardioExerciseTest3.getTime());
        assertFalse(cardioExerciseTest3.isFavourite());
    }

    @Test
    public void testToString() {
        assertEquals("Exercise Name: " + cardioExerciseTest1.getName() + "\n"
                        + "Muscle Group: " + cardioExerciseTest1.getMuscleGroup().getMuscleGroup() + "\n"
                        + "Difficulty: " + cardioExerciseTest1.getDifficulty().getDifficulty() + "\n"
                        + "Time: " + cardioExerciseTest1.getTime() + "\n"
                        + "Favourite?: " + cardioExerciseTest1.isFavourite() + "\n"
                        + "Distance: " + cardioExerciseTest1.getDistance(),
                cardioExerciseTest1.toString());

        assertEquals("Exercise Name: " + cardioExerciseTest2.getName() + "\n"
                        + "Muscle Group: " + cardioExerciseTest2.getMuscleGroup().getMuscleGroup() + "\n"
                        + "Difficulty: " + cardioExerciseTest2.getDifficulty().getDifficulty() + "\n"
                        + "Time: " + cardioExerciseTest2.getTime() + "\n"
                        + "Favourite?: " + cardioExerciseTest2.isFavourite() + "\n"
                        + "Distance: " + cardioExerciseTest2.getDistance(),
                cardioExerciseTest2.toString());
    }

    @Test
    public void testSetDistance() {
        cardioExerciseTest1.setDistance(1);
        cardioExerciseTest2.setDistance(100);

        assertEquals(1, cardioExerciseTest1.getDistance());
        assertEquals(100, cardioExerciseTest2.getDistance());
    }
}
