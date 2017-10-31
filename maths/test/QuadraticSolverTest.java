import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuadraticSolverTest {

    @Test
    void testGetRoots() {
        QuadraticSolver qe1 = new QuadraticSolver(1, -7, 12);
        double[] expected1 = {3, 4};
        assertArrayEquals(expected1, qe1.getRoots());

        QuadraticSolver qe2 = new QuadraticSolver(1, -3, 2);
        double[] expected2 = {1, 2};
        assertArrayEquals(expected2, qe2.getRoots());

        QuadraticSolver qe3 = new QuadraticSolver(2, -4, -3);
        double[] expected3 = {-0.58, 2.58};
        assertArrayEquals(expected3, qe3.getRoots());
}

    @Test
    void testFindRoots() {
        QuadraticSolver qs = new QuadraticSolver();

        double[] expected1 = {-0.58, 2.58};
        assertArrayEquals(expected1, qs.findRoots(2, -4, -3));

        double[] expected2 = {1, 2};
        assertArrayEquals(expected2, qs.findRoots(1, -3, 2));
    }

}
