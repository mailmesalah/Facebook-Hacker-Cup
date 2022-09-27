import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HightSecurityApp {

	public static void main(String[] args) {
		try {
			char[][][] Grids;
			int[] GridColumnLength;
			int Counter[];
			int NoOfFacilities;

			Scanner ScannerObject = new Scanner(new File("input.txt"));
			FileWriter FileWriterObject = new FileWriter(new File("output.txt"));

			NoOfFacilities = ScannerObject.nextInt();
			Counter = new int[NoOfFacilities];
			GridColumnLength = new int[NoOfFacilities];
			Grids = new char[NoOfFacilities][][];
			for (int i = 0; i < NoOfFacilities; i++) {
				GridColumnLength[i] = ScannerObject.nextInt();
				Grids[i] = new char[2][GridColumnLength[i]];
				Grids[i][0] = ScannerObject.next().toCharArray();
				Grids[i][1] = ScannerObject.next().toCharArray();
			}

			HightSecurityApp HightSecurityAppObject = new HightSecurityApp();
			for (int i = 0; i < NoOfFacilities; i++) {
				Counter[i] = HightSecurityAppObject
						.FindNumberOfMinimumRequiredGuards(Grids[i]);
			}

			for (int i = 0; i < NoOfFacilities; i++) {
				FileWriterObject.write("Case #" + (i + 1) + ": " + Counter[i]
						+ "\n");
			}
			FileWriterObject.close();
			ScannerObject.close();

			System.out.println("Done!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private int FindNumberOfMinimumRequiredGuards(char[][] FacilityGrid) {
		int LocalCounter = 0;
		ArrayList<PlaceHolder> FirstRow = new ArrayList<PlaceHolder>();
		ArrayList<PlaceHolder> SecondRow = new ArrayList<PlaceHolder>();

		int IndexLimitOfFirstRow = 0, IndexOfFirstRow = 0, IndexLimitOfSecondRow = 0, IndexOfSecondRow = 0;

		for (int i = 0; i < FacilityGrid[0].length; i++) {
			if (FacilityGrid[0][i] == '.') {
				if (IndexLimitOfFirstRow == 0) {
					IndexOfFirstRow = i;
				}
				IndexLimitOfFirstRow++;

			} else {
				if (IndexLimitOfFirstRow > 0) {
					PlaceHolder d = new PlaceHolder(IndexOfFirstRow,
							IndexLimitOfFirstRow);
					FirstRow.add(d);

					IndexLimitOfFirstRow = 0;
				}
			}

			if (FacilityGrid[1][i] == '.') {
				if (IndexLimitOfSecondRow == 0) {
					IndexOfSecondRow = i;
				}
				IndexLimitOfSecondRow++;

			} else {
				if (IndexLimitOfSecondRow > 0) {
					PlaceHolder d = new PlaceHolder(IndexOfSecondRow,
							IndexLimitOfSecondRow);
					SecondRow.add(d);

					IndexLimitOfSecondRow = 0;
				}
			}
		}
		if (IndexLimitOfFirstRow > 0) {
			PlaceHolder PlaceHolderObject = new PlaceHolder(IndexOfFirstRow,
					IndexLimitOfFirstRow);
			FirstRow.add(PlaceHolderObject);
		}

		if (IndexLimitOfSecondRow > 0) {
			PlaceHolder PlaceHolderObject = new PlaceHolder(IndexOfSecondRow,
					IndexLimitOfSecondRow);
			SecondRow.add(PlaceHolderObject);
		}

		FirstRow = SortPlaceHolderList(FirstRow);
		SecondRow = SortPlaceHolderList(SecondRow);

		while (!FirstRow.isEmpty() || !SecondRow.isEmpty()) {
			if (!FirstRow.isEmpty() && !SecondRow.isEmpty()) {
				if (FirstRow.get(0).Limit > SecondRow.get(0).Limit) {
					for (int i = SecondRow.size() - 1; i >= 0; i--) {

						if ((FirstRow.get(0).Index + (FirstRow.get(0).Limit - 1)) >= (SecondRow
								.get(i).Index + (SecondRow.get(i).Limit - 1))
								&& (FirstRow.get(0).Index <= (SecondRow.get(i).Index + (SecondRow
										.get(i).Limit - 1)))) {
							SecondRow.get(i).Limit = SecondRow.get(i).Limit - 1;
							FirstRow.remove(0);
							if (SecondRow.get(i).Limit == 0) {
								SecondRow.remove(i);
							} else {
								SecondRow = SortPlaceHolderList(SecondRow);
							}
							LocalCounter++;

							break;
						} else if ((SecondRow.get(i).Index >= FirstRow.get(0).Index)
								&& (SecondRow.get(i).Index <= (FirstRow.get(0).Index + (FirstRow
										.get(0).Limit - 1)))) {
							SecondRow.get(i).Index = SecondRow.get(i).Index + 1;
							SecondRow.get(i).Limit = SecondRow.get(i).Limit - 1;
							FirstRow.remove(0);
							if (SecondRow.get(i).Limit == 0) {
								SecondRow.remove(i);
							} else {
								SecondRow = SortPlaceHolderList(SecondRow);
							}
							LocalCounter++;

							break;
						}

						if (i == 0) {
							FirstRow.remove(0);
							LocalCounter++;
						}
					}

				} else {
					for (int i = FirstRow.size() - 1; i >= 0; i--) {

						if ((SecondRow.get(0).Index + (SecondRow.get(0).Limit - 1)) >= (FirstRow
								.get(i).Index + (FirstRow.get(i).Limit - 1))
								&& (SecondRow.get(0).Index <= (FirstRow.get(i).Index + (FirstRow
										.get(i).Limit - 1)))) {
							FirstRow.get(i).Limit = FirstRow.get(i).Limit - 1;
							SecondRow.remove(0);
							if (FirstRow.get(i).Limit == 0) {
								FirstRow.remove(i);
							} else {
								FirstRow = SortPlaceHolderList(FirstRow);
							}
							LocalCounter++;

							break;
						} else if ((FirstRow.get(i).Index >= SecondRow.get(0).Index)
								&& (FirstRow.get(i).Index <= (SecondRow.get(0).Index + (SecondRow
										.get(0).Limit - 1)))) {
							FirstRow.get(i).Index = FirstRow.get(i).Index + 1;
							FirstRow.get(i).Limit = FirstRow.get(i).Limit - 1;
							SecondRow.remove(0);
							if (FirstRow.get(i).Limit == 0) {
								FirstRow.remove(i);
							} else {
								FirstRow = SortPlaceHolderList(FirstRow);
							}
							LocalCounter++;

							break;
						}

						if (i == 0) {
							SecondRow.remove(0);
							LocalCounter++;
						}
					}
				}
			} else if (!FirstRow.isEmpty()) {
				LocalCounter = LocalCounter + FirstRow.size();
				FirstRow.clear();
			} else if (!SecondRow.isEmpty()) {
				LocalCounter = LocalCounter + SecondRow.size();
				SecondRow.clear();
			}
		}

		return LocalCounter;
	}

	private ArrayList<PlaceHolder> SortPlaceHolderList(
			ArrayList<PlaceHolder> ListOfPlaceHolderObjects) {
		for (int i = 0; i < ListOfPlaceHolderObjects.size() - 1; i++) {
			for (int j = i + 1; j < ListOfPlaceHolderObjects.size(); j++) {
				if (ListOfPlaceHolderObjects.get(i).Limit < ListOfPlaceHolderObjects
						.get(j).Limit) {
					PlaceHolder temp = ListOfPlaceHolderObjects.remove(i);
					ListOfPlaceHolderObjects.add(i,
							ListOfPlaceHolderObjects.get(j - 1));
					ListOfPlaceHolderObjects.remove(j);
					ListOfPlaceHolderObjects.add(j, temp);
				}
			}

		}
		return ListOfPlaceHolderObjects;
	}

	private class PlaceHolder {
		public PlaceHolder(int Index, int Limit) {
			this.Index = Index;
			this.Limit = Limit;
		}

		public int Index;
		public int Limit;
	}
}
