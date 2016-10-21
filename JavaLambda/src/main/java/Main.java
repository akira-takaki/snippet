import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        runnable1();
        runnable1Lambda();
        System.out.println();
        runnable2();
        runnable2Lambda();
        System.out.println();
        function();
        functionLambda();
    }

    private static void runnable1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("abc");
            }
        }).start();
    }

    /**
     * Runnable は関数型インタフェース @FunctionalInterface アノテーションが指定されているので、
     * 引数無し、戻り値無しのラムダ式として使用できる。
     */
    private static void runnable1Lambda() {
        new Thread(() -> System.out.println("abc")).start();
    }

    private static void runnable2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++) {
                    System.out.println("abc" + i);
                }
            }
        }).start();
    }

    /**
     * Runnable は関数型インタフェース @FunctionalInterface アノテーションが指定されているので、
     * 引数無し、戻り値無しのラムダ式として使用できる。
     *
     * int の Stream を生成し、forEachOrdered(Consumer<? super T> action) メソッドを使う。
     * Consumer<T> は関数型インタフェース @FunctionalInterface アノテーションが指定されているので、
     * 引数が1つ、戻り値無しのラムダ式として使用できる。この場合 Consumer<T> の T は IntStream が生成した int になる。
     */
    private static void runnable2Lambda() {
        new Thread(() -> IntStream.range(0, 10).forEachOrdered(i -> System.out.println("abc" + i))).start();
    }

    private static void function() {
        Function<String, Integer> functionObject = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };

        System.out.println(functionObject.apply("123"));
    }

    /**
     * Function<T, R> は関数型インタフェース @FunctionalInterface アノテーションが指定されているので、
     * 引数が1つ、戻り値有りのラムダ式として使用できる。
     */
    private static void functionLambda() {
        Function<String, Integer> functionObject = s -> Integer.parseInt(s);

        System.out.println(functionObject.apply("123"));
    }

}
