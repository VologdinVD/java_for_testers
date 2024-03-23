import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TriangleTest {

    @Test
    @DisplayName("Вычисление периметра треугольника успешный сценарий")
    public void calcPerimeterOfTriangleSuccessTest() {

        Triangle triangle = new Triangle(3, 4, 5);

        double perimeterResult = triangle.perimeter();
        double expectedPerimeter = 12.0;

        Assertions.assertEquals(expectedPerimeter, perimeterResult);

    }

    @Test
    @DisplayName("Вычисление площади треугольника успешный сценарий")
    public void calcSquareOfTriangleSuccessTest() {

        Triangle triangle = new Triangle(3, 4, 5);

        double squareResult = triangle.square();
        double expectedSquare = 6.0;

        Assertions.assertEquals(expectedSquare, squareResult);
    }
}
