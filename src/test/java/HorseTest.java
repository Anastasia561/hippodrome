import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    @Test
    public void HorseConstructorFirstParamNullException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 5);
        });
    }

    @Test
    public void HorseConstructorFirstParamNullExceptionMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 5);
        });
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    public void HorseConstructorFirstParamOtherSymbolsException(String s) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(s, 5);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  ", "\n"})
    public void HorseConstructorFirstParamOtherSymbolsExceptionMessage(String s) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(s, 5);
        });
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void HorseConstructorSecondParamNegativeNumberException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Tom", -3);
        });
    }

    @Test
    public void HorseConstructorSecondParamNegativeNumberExceptionMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Tom", -3);
        });
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void HorseConstructorThirdParamNegativeNumberException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Tom", 3, -5);
        });
    }

    @Test
    public void HorseConstructorThirdParamNegativeNumberExceptionMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Tom", 3, -5);
        });
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() {
        String name = "Tom";
        assertEquals(name, new Horse(name, 5).getName());
    }

    @Test
    public void getSpeed() {
        int speed = 5;
        assertEquals(speed, new Horse("Tom", speed).getSpeed());
    }

    @Test
    public void getDistanceShouldReturnParamFromConstructor() {
        int distance = 10;
        assertEquals(distance, new Horse("Tom", 5, distance).getDistance());
    }

    @Test
    public void getDistanceShouldReturnZero() {
        assertEquals(0, new Horse("Tom", 5).getDistance());
    }

    @Test
    public void moveCallsGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Tom", 3);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.6, 0.8, 0.85})
    public void moveCalculatesCorrectDistance(double fakeRandomValue) {
        double distance = 3;
        double speed = 5;
        Horse horse = new Horse("Tom", speed, distance);
        double expectedDistance = distance + speed * fakeRandomValue;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeRandomValue);
            horse.move();

            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}
