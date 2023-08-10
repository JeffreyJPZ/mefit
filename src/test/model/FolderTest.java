package model;

import exceptions.AlreadyContainedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FolderTest {
    private Folder folder;

    @BeforeEach
    public void runBefore() {
        folder = new Folder("f1");
    }

    @Test
    public void testConstructor() {
        assertEquals("f1", folder.getName());
        assertEquals(0, folder.getTime());
        assertEquals(0, folder.getSize());
        assertEquals(ExerciseComponentTypes.FOLDER, folder.getType());
    }

    @Test
    public void testAddComponentNotAlreadyContained() {
        Exercise e1 = new CardioExercise("1", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);

        try {
            folder.addComponent(e1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        assertTrue(folder.getComponents().containsValue(e1));
        assertEquals(1, folder.getSize());
        assertEquals(1, folder.getTime());
    }

    @Test
    public void testAddComponentAlreadyContained() {
        Exercise e1 = new CardioExercise("1", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);

        try {
            folder.addComponent(e1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        assertTrue(folder.getComponents().containsValue(e1));
        assertEquals(1, folder.getSize());

        try {
            folder.addComponent(e1);
            fail("AlreadyContainedException not thrown when expected");
        } catch (AlreadyContainedException e) {
            // success
        }

        assertTrue(folder.getComponents().containsValue(e1));
        assertEquals(1, folder.getSize());
        assertEquals(1, folder.getTime());
    }

    @Test
    public void testAddComponentMultiple() {
        Exercise e1 = new CardioExercise("e1", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);
        Exercise e2 = new CardioExercise("e2", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);
        Workout w1 = new Workout("w1", Difficulty.LIGHT);

        w1.addExercise(e2);

        try {
            folder.addComponent(e1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        try {
            folder.addComponent(w1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        assertTrue(folder.getComponents().containsValue(e1));
        assertTrue(folder.getComponents().containsValue(w1));
        assertEquals(2, folder.getSize());
        assertEquals(2, folder.getTime());
    }

    @Test
    public void testRemoveComponent() {
        Exercise e1 = new CardioExercise("1", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);

        try {
            folder.addComponent(e1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        assertTrue(folder.getComponents().containsValue(e1));
        assertEquals(1, folder.getSize());
        assertEquals(1, folder.getTime());

        folder.removeComponent(e1);

        assertFalse(folder.getComponents().containsValue(e1));
        assertEquals(0, folder.getSize());
        assertEquals(0, folder.getTime());
    }

    @Test
    public void testRemoveComponentMultiple() {
        Exercise e1 = new CardioExercise("e1", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);
        Exercise e2 = new CardioExercise("e2", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);
        Workout w1 = new Workout("w1", Difficulty.LIGHT);

        w1.addExercise(e2);

        try {
            folder.addComponent(e1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        try {
            folder.addComponent(w1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        assertTrue(folder.getComponents().containsValue(e1));
        assertTrue(folder.getComponents().containsValue(w1));
        assertEquals(2, folder.getSize());
        assertEquals(2, folder.getTime());

        folder.removeComponent(e1);
        folder.removeComponent(w1);

        assertFalse(folder.getComponents().containsValue(e1));
        assertFalse(folder.getComponents().containsValue(w1));
        assertEquals(0, folder.getSize());
        assertEquals(0, folder.getTime());
    }

    @Test
    public void testGetComponent() {
        Exercise e1 = new CardioExercise("e1", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);
        Exercise e2 = new CardioExercise("e2", MuscleGroup.BACK, 1, Difficulty.MODERATE, 1);
        Workout w1 = new Workout("w1", Difficulty.LIGHT);

        w1.addExercise(e2);

        try {
            folder.addComponent(e1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        try {
            folder.addComponent(w1);
        } catch (AlreadyContainedException e) {
            fail("AlreadyContainedException thrown, expected none");
        }

        assertEquals(e1, folder.getComponent("e1"));
        assertEquals(w1, folder.getComponent("w1"));
    }
}
