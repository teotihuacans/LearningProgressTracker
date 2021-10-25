import java.util.stream.IntStream;

/*class Main {
    public static void main(String[] args) {
        Arrays.stream(TestHelper.primeGenerator()).distinct().forEach(System.out::println);
    }
}*/

class TestHelper {
    static int start = 10;
    static int end = 1000;

    public static int[] primeGenerator() {
        // your code here
        return IntStream.rangeClosed(start, end)
                .filter(x -> isPrime(x))
                .toArray();
    }
    private static boolean isPrime(int number) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(number)))
                .allMatch(x -> number % x != 0);
    }
}