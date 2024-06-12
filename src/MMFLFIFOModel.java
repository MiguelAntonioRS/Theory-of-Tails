public class MMFLFIFOModel extends Model {
    private int servers;
    private int capacity;

    public MMFLFIFOModel(double lambda, double mu, int servers, int capacity) {
        super(lambda, mu);
        this.servers = servers;
        this.capacity = capacity;
    }

    @Override
    public String calculate() {
        double rho = lambda / (servers * mu);
        double P0 = calculateP0(lambda, mu, servers, capacity);
        double L = calculateL(lambda, mu, servers, capacity, P0);
        double Lq = calculateLq(lambda, mu, servers, capacity, P0);
        double W = L / lambda;
        double Wq = Lq / lambda;
        double Pwait = calculatePwait(lambda, mu, servers, capacity, P0);
        double PnoWait = 1 - Pwait;

        StringBuilder result = new StringBuilder();
        result.append(String.format("Probabilidad de que NO haya unidades en el sistema: P0 = %.4f\n", P0));
        result.append("Probabilidad de que haya n unidades en el sistema:\n");
        for (int n = 0; n <= capacity; n++) {
            double Pn = calculatePn(lambda, mu, servers, capacity, P0, n);
            result.append(String.format("P%d = %.4f\n", n, Pn));
        }
        result.append(String.format("Cantidad media de unidades en el sistema: L = %.2f\n", L));
        result.append(String.format("Cantidad media de unidades en cola: Lq = %.2f\n", Lq));
        result.append(String.format("Tiempo medio de estancia de una unidad en el sistema: W = %.2f\n", W));
        result.append(String.format("Tiempo medio de estancia de una unidad en la cola: Wq = %.2f\n", Wq));
        result.append(String.format("Probabilidad de que una unidad llegue al sistema y NO tenga que esperar: P(no wait) = %.4f\n", PnoWait));
        result.append(String.format("Probabilidad de que una unidad arribe al sistema y tenga que esperar: P(wait) = %.4f\n", Pwait));

        return result.toString();
    }

    private double calculateP0(double lambda, double mu, int servers, int capacity) {
        double sum = 0;
        for (int n = 0; n < servers; n++) {
            sum += Math.pow(lambda / mu, n) / factorial(n);
        }
        double C = calculateC(lambda, mu, servers, capacity);
        double additionalTerm = Math.pow(lambda / mu, servers) / (factorial(servers) * C);
        return 1 / (sum + additionalTerm);
    }

    private double calculateC(double lambda, double mu, int servers, int capacity) {
        double sum = 0;
        for (int n = 0; n <= capacity; n++) {
            sum += Math.pow(lambda / mu, n) / factorial(n);
        }
        return 1 / (1 - sum);
    }

    private double calculateL(double lambda, double mu, int servers, int capacity, double P0) {
        double sum = 0;
        for (int n = servers; n <= capacity; n++) {
            sum += n * calculatePn(lambda, mu, servers, capacity, P0, n);
        }
        return sum + servers * (1 - P0);
    }

    private double calculateLq(double lambda, double mu, int servers, int capacity, double P0) {
        double L = calculateL(lambda, mu, servers, capacity, P0);
        return L - lambda / mu * (1 - P0);
    }

    private double calculatePn(double lambda, double mu, int servers, int capacity, double P0, int n) {
        double C = calculateC(lambda, mu, servers, capacity);
        if (n <= servers) {
            return Math.pow(lambda / mu, n) / factorial(n) * P0;
        } else {
            return Math.pow(lambda / mu, n) / (factorial(servers) * Math.pow(servers, n - servers) * C) * P0;
        }
    }

    private double calculatePwait(double lambda, double mu, int servers, int capacity, double P0) {
        double sum = 0;
        for (int n = servers; n <= capacity; n++) {
            sum += calculatePn(lambda, mu, servers, capacity, P0, n);
        }
        return sum;
    }
}
