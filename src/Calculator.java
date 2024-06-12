public class Calculator {
    public String calculateAllModels(double lambda, double mu, int servers, int capacity) {
        StringBuilder results = new StringBuilder();

        results.append("Modelo M/M/s con cola infinita:\n");
        MMCModel mmc = new MMCModel(lambda, mu, servers);
        results.append(mmc.calculate()).append("\n\n");

        results.append("Modelo M/M/s con cola finita (K=10):\n");
        MMCSFiniteModel mmcFinite = new MMCSFiniteModel(lambda, mu, servers, 10);
        results.append(mmcFinite.calculate()).append("\n\n");

        results.append("Modelo M/M/s con Fuente Limitada:\n");
        MMFLFIFOModel mmcFLFifo = new MMFLFIFOModel(lambda, mu, servers, 10);
        results.append(mmcFLFifo.calculate()).append("\n\n");

        return results.toString();
    }
}
