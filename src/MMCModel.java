public class MMCModel extends Model {
    private int servers;

    public MMCModel(double lambda, double mu, int servers) {
        super(lambda, mu);
        this.servers = servers;
    }

    @Override
    public String calculate() {
        double rho = lambda / (servers * mu);
        double P0 = calculateP0(lambda, mu, servers);
        StringBuilder result = new StringBuilder();
        result.append(String.format("P0 (Probabilidad de 0 clientes en el sistema): %.4f\n", P0));

        for (int n = 0; n <= 10; n++) { // Calcular Pn para n de 0 a 10
            double Pn;
            if (n < servers) {
                Pn = (Math.pow(lambda / mu, n) / factorial(n)) * P0;
            } else {
                Pn = (Math.pow(lambda / mu, n) / (factorial(servers) * Math.pow(servers, n - servers))) * P0;
            }
            result.append(String.format("P%d (Probabilidad de %d clientes en el sistema): %.4f\n", n, n, Pn));
        }

        double Lq = calculateLq(lambda, mu, servers);
        double L = Lq + (lambda / mu);
        double W = L / lambda;
        double Wq = Lq / lambda;

        result.append(String.format("ρ (Utilización): %.2f\n", rho));
        result.append(String.format("L (Número esperado de clientes en el sistema): %.2f\n", L));
        result.append(String.format("Lq (Número esperado de clientes en la cola): %.2f\n", Lq));
        result.append(String.format("W (Tiempo esperado en el sistema): %.2f\n", W));
        result.append(String.format("Wq (Tiempo esperado en la cola): %.2f\n", Wq));

        return result.toString();
    }

    private double calculateP0(double lambda, double mu, int servers) {
        double sum = 0;
        for (int n = 0; n < servers; n++) {
            sum += (Math.pow(lambda / mu, n) / factorial(n));
        }
        double additionalTerm = (Math.pow(lambda / mu, servers) / factorial(servers)) * (1 / (1 - (lambda / (servers * mu))));
        return 1 / (sum + additionalTerm);
    }

    private double calculateLq(double lambda, double mu, int servers) {
        double rho = lambda / (servers * mu);
        double P0 = calculateP0(lambda, mu, servers);
        double Lq = (P0 * Math.pow(lambda / mu, servers) * rho) / (factorial(servers) * Math.pow(1 - rho, 2));
        return Lq;
    }
}

