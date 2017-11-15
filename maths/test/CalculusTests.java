import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

class CalculusTests {

    public static Function<Double, Double> square() {
        // Outside the scope of the returned function:
        Function<Double, Double> f = new Function<Double, Double>() {
            @Override
            public Double apply(Double x) {
                return x*x;
            }
        };
        return f;
    }

    @Test
    void firstXSquaredDyDxTest() {
        // x^2 -> 2x -> 2(3) = 6.0
        assertEquals(6.0, Derivative.differentiate(square(), 3, 0.5));
    }

}
