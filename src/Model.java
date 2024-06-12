public abstract class Model {
    protected double lambda;
    protected double mu;

    public Model(double lambda, double mu) {
        this.lambda = lambda;
        this.mu = mu;
    }

    public abstract String calculate();

    protected double factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}
