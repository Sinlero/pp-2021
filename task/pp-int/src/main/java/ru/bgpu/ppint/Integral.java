package ru.bgpu.ppint;

public class Integral {

    private final Double a;
    private final Double b;
    private final Double width;
    private final Function function;

    public Integral(Double a, Double b, Double width, Function function) {
        this.a = a;
        this.b = b;
        this.width = width;
        this.function = function;
    }

    public Double calc() {
        double x = a;
        double sum = function.call(x);
        for(x += width; x < b; x+=width) {
            sum += 2*function.call(x);
        }
        sum += function.call(x);
        return sum * 0.5 * width;
    }
}
