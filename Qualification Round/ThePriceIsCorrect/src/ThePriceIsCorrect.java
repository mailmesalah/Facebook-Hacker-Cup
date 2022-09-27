import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ThePriceIsCorrect {

	public static void main(String[] args) throws IOException {
		int T = 0;
		long[] N;
		long[] P;
		long[][] B;
		long count[];

		Scanner fr = new Scanner(new File("input.txt"));
		FileWriter fw = new FileWriter(new File("output.txt"));

		System.out.println("Started!");

		T = fr.nextInt();
		// System.out.println(T);
		N = new long[T];
		P = new long[T];
		count = new long[T];
		B = new long[T][];
		for (int i = 0; i < T; i++) {
			N[i] = fr.nextInt();
			P[i] = fr.nextInt();
			// System.out.println("\n"+N[i]+" "+P[i]);
			B[i] = new long[(int)N[i]];
			for (int j = 0; j < N[i]; j++) {
				B[i][j] = fr.nextInt();
				// System.out.print(B[i][j]+" ");
			}
		}

		ThePriceIsCorrect tp = new ThePriceIsCorrect();
		// Processing
		for (int i = 0; i < T; i++) {
			int lastIndex = -1;
			long x = 0;
			for (int j = 0; j < N[i]; j++) {
				long sum = 0;
				int tempk = 0;
				int endReached = 1;

				for (int k = j; k < N[i]; k++) {
					if (P[i] < (sum + B[i][k])) {						
						endReached = 0;
						break;
					} else if (k > lastIndex) {
						x = x + (k - j) + 1;
					}
					sum += B[i][k];
					tempk = k;
				}

				lastIndex = tempk;

				if (endReached == 1) {
					break;
				}
			}
			count[i] = x;
		}

		// write the output to file
		for (int i = 0; i < T; i++) {
			fw.write("Case #" + (i + 1) + ": " + count[i] + "\n");
		}
		fw.close();

		System.out.println("Completed");

	}
}
