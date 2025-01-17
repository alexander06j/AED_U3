package modelo;

import java.util.*;

public class InventarioMaximizarBeneficios {

	// Método Recursivo
	public static double knapsackRecursive(int n, double[] valores, double[] pesos, double capacidad) {
		if (n == 0 || capacidad == 0) {
			return 0;
		}
		if (pesos[n - 1] > capacidad) {
			return knapsackRecursive(n - 1, valores, pesos, capacidad);
		}
		double incluir = valores[n - 1] + knapsackRecursive(n - 1, valores, pesos, capacidad - pesos[n - 1]);
		double excluir = knapsackRecursive(n - 1, valores, pesos, capacidad);
		return Math.max(incluir, excluir);
	}

	// Método Bottom-Up
	public static double knapsackBottomUp(int n, double[] valores, double[] pesos, double capacidad) {
		double[][] dp = new double[n + 1][(int) capacidad + 1];

		for (int i = 1; i <= n; i++) {
			for (int w = 0; w <= capacidad; w++) {
				if (pesos[i - 1] <= w) {
					dp[i][w] = Math.max(dp[i - 1][w], valores[i - 1] + dp[i - 1][(int) (w - pesos[i - 1])]);
				} else {
					dp[i][w] = dp[i - 1][w];
				}
			}
		}
		return dp[n][(int) capacidad];
	}

	// Método Top-Down
	public static double knapsackTopDown(int n, double[] valores, double[] pesos, double capacidad, double[][] memo) {
		if (n == 0 || capacidad == 0) {
			return 0;
		}
		if (memo[n][(int) capacidad] != -1) {
			return memo[n][(int) capacidad];
		}
		if (pesos[n - 1] > capacidad) {
			memo[n][(int) capacidad] = knapsackTopDown(n - 1, valores, pesos, capacidad, memo);
		} else {
			double incluir = valores[n - 1] + knapsackTopDown(n - 1, valores, pesos, capacidad - pesos[n - 1], memo);
			double excluir = knapsackTopDown(n - 1, valores, pesos, capacidad, memo);
			memo[n][(int) capacidad] = Math.max(incluir, excluir);
		}
		return memo[n][(int) capacidad];
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Entrada por consola
		System.out.println("Ingrese el número de productos: ");
		int n = scanner.nextInt();

		double[] valores = new double[n];
		double[] pesos = new double[n];

		System.out.println("Ingrese los valores (beneficios) de los productos: ");
		for (int i = 0; i < n; i++) {
			valores[i] = scanner.nextDouble();
		}

		System.out.println("Ingrese los pesos de los productos: ");
		for (int i = 0; i < n; i++) {
			pesos[i] = scanner.nextDouble();
		}

		System.out.println("Ingrese la capacidad máxima del inventario: ");
		double capacidad = scanner.nextDouble();

		// Enfoque recursivo
		long startRec = System.nanoTime();
		double maxRec = knapsackRecursive(n, valores, pesos, capacidad);
		long endRec = System.nanoTime();

		// Enfoque bottom-up
		long startBottom = System.nanoTime();
		double maxBottom = knapsackBottomUp(n, valores, pesos, capacidad);
		long endBottom = System.nanoTime();

		// Enfoque top-down
		double[][] memo = new double[n + 1][(int) capacidad + 1];
		for (double[] row : memo) {
			Arrays.fill(row, -1);
		}
		long startTop = System.nanoTime();
		double maxTop = knapsackTopDown(n, valores, pesos, capacidad, memo);
		long endTop = System.nanoTime();

		// Resultados
		System.out.println("\nResultados:");
		System.out.println("Máximo beneficio (Recursivo): " + maxRec);
		System.out.println("Tiempo de ejecución (Recursivo): " + (endRec - startRec) + " ns");

		System.out.println("Máximo beneficio (Bottom-Up): " + maxBottom);
		System.out.println("Tiempo de ejecución (Bottom-Up): " + (endBottom - startBottom) + " ns");

		System.out.println("Máximo beneficio (Top-Down): " + maxTop);
		System.out.println("Tiempo de ejecución (Top-Down): " + (endTop - startTop) + " ns");

		scanner.close();
	}

}
