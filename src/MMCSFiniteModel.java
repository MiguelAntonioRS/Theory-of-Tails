public class MMCSFiniteModel extends Model {
    private int servers;
    private int capacity;

    public MMCSFiniteModel(double lambda, double mu, int servers, int capacity) {
        super(lambda, mu);
        this.servers = servers;
        this.capacity = capacity;
    }

    @Override
    public String calculate() {
        double rho = lambda / (servers * mu);
        double P0 = calculateP0(lambda, mu, servers, capacity);
        StringBuilder result = new StringBuilder();
        result.append(String.format("P0 (Probabilidad de 0 clientes en el sistema): %.4f\n", P0));

        for (int n = 0; n <= capacity; n++) { // Calcular Pn para n de 0 a capacidad
            double Pn;
            if (n < servers) {
                Pn = (Math.pow(lambda / mu, n) / factorial(n)) * P0;
            } else {
                Pn = (Math.pow(lambda / mu, n) / (factorial(servers) * Math.pow(servers, n - servers))) * P0;
            }
            result.append(String.format("P%d (Probabilidad de %d clientes en el sistema): %.4f\n", n, n, Pn));
        }

        double Lq = calculateLq(lambda, mu, servers, capacity);
        double L = Lq + (lambda / mu) * (1 - P0);
        double W = L / (lambda * (1 - P0));
        double Wq = Lq / (lambda * (1 - P0));

        result.append(String.format("ρ (Utilización): %.2f\n", rho));
        result.append(String.format("L (Número esperado de clientes en el sistema): %.2f\n", L));
        result.append(String.format("Lq (Número esperado de clientes en la cola): %.2f\n", Lq));
        result.append(String.format("W (Tiempo esperado en el sistema): %.2f\n", W));
        result.append(String.format("Wq (Tiempo esperado en la cola): %.2f\n", Wq));

        return result.toString();
    }

    private double calculateP0(double lambda, double mu, int servers, int capacity) {
        double sum = 0;
        for (int n = 0; n <= servers - 1; n++) {
            sum += (Math.pow(lambda / mu, n) / factorial(n));
        }
        double additionalTerm = (Math.pow(lambda / mu, servers) / factorial(servers)) *
                ((1 - Math.pow(lambda / (servers * mu), capacity - servers + 1)) / (1 - (lambda / (servers * mu))));
        return 1 / (sum + additionalTerm);
    }

    private double calculateLq(double lambda, double mu, int servers, int capacity) {
        double rho = lambda / (servers * mu);
        double P0 = calculateP0(lambda, mu, servers, capacity);
        double Lq = (P0 * Math.pow(lambda / mu, servers) * rho * (1 - Math.pow(rho, capacity - servers + 1))) /
                (factorial(servers) * Math.pow(1 - rho, 2));
        return Lq;
    }
}
