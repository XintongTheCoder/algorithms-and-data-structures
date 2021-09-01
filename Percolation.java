import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF wqu;
    private int gridSize;
    private int numberOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        validate(n);
        gridSize = n;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 0; j++) {
                grid[i][j] = false;
            }
        }
        wqu = new WeightedQuickUnionUF(n * n + 2);
    }

    // get the index of current site in the weightedQuickUnion
    private int indexOfSite(int row, int col) {
        return gridSize * row + col + 1;
    }

    // validate given n is a valid indice
    private void validate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(n + " is not a valid number of grid size!");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        grid[row][col] = true;
        int indexOfCurrentSite = indexOfSite(row, col);
        // union current open site with all neighboring open sites
        if ((row > 0) && grid[row - 1][col]) {
            int indexOfUpSite = gridSize * (row - 1) + col + 1;
            wqu.union(indexOfUpSite, indexOfCurrentSite);
        }
        if ((row < gridSize - 1) && grid[row + 1][col]) {
            int indexOfDownSite = gridSize * (row + 1) + col + 1;
            wqu.union(indexOfDownSite, indexOfCurrentSite);
        }
        if ((col > 0) && grid[row][col - 1]) {
            int indexOfLeftSite = gridSize * row + col;
            wqu.union(indexOfLeftSite, indexOfCurrentSite);
        }
        if ((col < gridSize - 1) && grid[row][col + 1]) {
            int indexOfRightSite = gridSize * row + col + 2;
            wqu.union(indexOfRightSite, indexOfCurrentSite);
        }
        // if current open site is at top/bottom row, union it with top/bottom sentinel
        // site
        if (row == 0) {
            wqu.union(0, indexOfCurrentSite);
        }
        if (row == gridSize - 1) {
            wqu.union(gridSize * gridSize + 1, indexOfCurrentSite);
        }

        numberOfOpenSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    // // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (wqu.find(0) == wqu.find(indexOfSite(row, col))) {
            return true;
        }
        return false;
    }

    // validate given row and col are valid index
    private void validate(int row, int col) {
        if (row < 0 || row >= gridSize) {
            throw new IllegalArgumentException(row + " is not a valid number of row");
        } else if (col < 0 || col >= gridSize) {
            throw new IllegalArgumentException(col + " is not a valid number of col");
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (wqu.find(0) == wqu.find(gridSize * gridSize + 1)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(0, 1);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        System.out.println(percolation.isFull(4, 1));
        System.out.println(percolation.percolates());
    }
}