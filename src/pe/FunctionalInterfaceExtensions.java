package pe;

class FunctionalInterfaceExtensions {

    // function that accepts two int arguments and returns a result
    interface IntIntFunction<R> {
        R apply(int var1, int var2);
    }

}
