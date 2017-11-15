import java.util.function.Function;

/*
* Functional interface for use in calculus test
*/
public class Functions {
    //TODO: possibly make linked list for stack operations

//    public static Function<Double, Double> sq() {
//        // Outside the scope of the returned function:
//        Function<Double, Double> f = new Function<Double, Double>() {
//            @Override
//            public Double apply(Double x) {
//                return x*x;
//            }
//        };
//        return f;
//    }

    public static class sq {
        public double apply(double x) {
            return x * x;
        }
    }

    public static class sqrt {
        public double apply(double x) {
            return Math.sqrt(x);
        }
    }

    public static class log {
        public double apply(double x) {
            return Math.log(x);
        }
    }

    public static class exp {
        public double apply(double x) {
            return Math.exp(x);
        }
    }

    public static class sin {
        public double apply(double x) {
            return Math.sin(x);
        }
    }

    public static class cos {
        public double apply(double x) {
            return Math.cos(x);
        }
    }

    public static class tan {
        public double apply(double x) {
            return Math.tan(x);
        }
    }

    public static class arctan {
        public double apply(double x) {
            return Math.atan(x);
        }
    }
}
