import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] fractions;
    private int timesOfTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("given grid size and times of trials should be positive");
        }
        timesOfTrials = trials;
        fractions = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            fractions[t] = (double) percolation.numberOfOpenSites() / (n * n);
            System.out.println(fractions[t]);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (double f : fractions) {
            sum += f;
        }
        return (double) sum / timesOfTrials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(timesOfTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(timesOfTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats prs = new PercolationStats(200, 100);
        System.out.println("mean = " + prs.mean());
        System.out.println("stddev = " + prs.stddev());
        System.out.println("95% confidence interval = [" + prs.confidenceLo() + ", " + prs.confidenceHi() + "]");
    }

}
