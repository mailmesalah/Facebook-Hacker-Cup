import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ThePriceIsCorrectApp {

	public static void main(String[] args) {
		try {
			int NoOfShows = 0;
			int[] NoOfBoxes, TrycycleCost;
			int[][] BoxesOfPrice;
			int Counter[];

			Scanner ScannerObject = new Scanner(new File("input.txt"));
			FileWriter FileWriterObject = new FileWriter(new File("output.txt"));

			System.out.println("Started!");

			NoOfShows = ScannerObject.nextInt();
			TrycycleCost = new int[NoOfShows];
			NoOfBoxes = new int[NoOfShows];
			Counter = new int[NoOfShows];
			BoxesOfPrice = new int[NoOfShows][];
			for (int i = 0; i < NoOfShows; i++) {
				NoOfBoxes[i] = ScannerObject.nextInt();
				TrycycleCost[i] = ScannerObject.nextInt();
				BoxesOfPrice[i] = new int[NoOfBoxes[i]];
				for (int j = 0; j < NoOfBoxes[i]; j++) {
					BoxesOfPrice[i][j] = ScannerObject.nextInt();
				}
			}

			for (int i = 0; i < NoOfShows; i++) {
				int EachCounter = 0;
				int LastIndex = -1;
				for (int j = 0; j < NoOfBoxes[i]; j++) {
					int IsItDone = 1;
					int Temp = 0;
					int SumOfPrices = 0;

					for (int k = j; k < NoOfBoxes[i]; k++) {
						if (TrycycleCost[i] < (SumOfPrices + BoxesOfPrice[i][k])) {
							IsItDone = 0;
							break;
						} else if (k > LastIndex) {
							EachCounter = EachCounter + (k - j) + 1;
						}
						Temp = k;
						SumOfPrices += BoxesOfPrice[i][k];

					}

					LastIndex = Temp;

					if (IsItDone == 1) {
						break;
					}
				}
				Counter[i] = EachCounter;
			}

			// write the output to file
			for (int i = 0; i < NoOfShows; i++) {
				FileWriterObject.write("Case #" + (i + 1) + ": " + Counter[i]
						+ "\n");
			}
			FileWriterObject.close();
			ScannerObject.close();

			System.out.println("Done");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
