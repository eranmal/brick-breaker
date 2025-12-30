package tools;

/**
 * The {@code Tools} class provides utility methods for performing various operations.
 * It includes methods for comparing floating point numbers with a small tolerance to handle precision errors.
 */
public class Tools {
    // A constant threshold used for comparison of floating-point numbers.
    static final double COMPARISON_THRESHOLD = 0.0000001;

    /**
     * Compares two double values for equality within a fixed threshold.
     * This method checks if the absolute difference between the two values is less than a small predefined threshold.
     *
     * @param a The first value to compare.
     * @param b The second value to compare.
     * @return {@code true} if the absolute difference between {@code a} and {@code b}
     * is less than the comparison threshold, otherwise {@code false}.
     */
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < COMPARISON_THRESHOLD;
    }

    /**
     * Compares two double values for equality within a specified epsilon value.
     * This method checks if the absolute difference between the two values is less than or equal
     * to the provided epsilon.
     *
     * @param a The first value to compare.
     * @param b The second value to compare.
     * @param epsilon The maximum allowed difference between {@code a} and {@code b} for them to be considered equal.
     * @return {@code true} if the absolute difference between {@code a} and {@code b} is less than
     * or equal to {@code epsilon}, otherwise {@code false}.
     */
    public static boolean doubleEquals(double a, double b, double epsilon) {
        return Math.abs(a - b) <= epsilon;
    }
}
