import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextEditorApp {
	public static void main(String[] args) {
		Scanner ScannerObject;
		FileWriter FileWriterObject;
		try {
			ArrayList<ArrayList<String>> ListOfListOfStrings = new ArrayList<ArrayList<String>>();
			int[] NoOfWordsInEachEdition, NoOfWordsToBePrinted;
			int NoOfEditions;
			
			int Counter[];

			File InputFile = new File("input.txt");
			File OutputFile = new File("output.txt");
			ScannerObject = new Scanner(InputFile);
			FileWriterObject = new FileWriter(OutputFile);

			NoOfEditions = ScannerObject.nextInt();
			NoOfWordsInEachEdition = new int[NoOfEditions];
			NoOfWordsToBePrinted = new int[NoOfEditions];
			Counter = new int[NoOfEditions];

			for (int i = 0; i < NoOfEditions; i++) {
				NoOfWordsInEachEdition[i] = ScannerObject.nextInt();
				NoOfWordsToBePrinted[i] = ScannerObject.nextInt();

				ArrayList<String> ListOfStrings = new ArrayList<String>();
				for (int j = 0; j < NoOfWordsInEachEdition[i]; j++) {
					ListOfStrings.add(ScannerObject.next());
				}
				ListOfListOfStrings.add(ListOfStrings);
			}

			TextEditorApp TextEditorAppObject = new TextEditorApp();
			for (int i = 0; i < NoOfEditions; i++) {
				TextEditorAppObject.SortListOfStringBasedOnItsSize(ListOfListOfStrings.get(i));
			}

			ArrayList<ArrayList<PlaceHolder>> ListOfListOfPlaceHolder = new ArrayList<ArrayList<PlaceHolder>>();
			for (int i = 0; i < ListOfListOfStrings.size(); i++) {
				ArrayList<PlaceHolder> ListOfPlaceHolder = new ArrayList<PlaceHolder>();
				for (int j = 0; j < ListOfListOfStrings.get(i).size(); j++) {
					ListOfPlaceHolder.add(TextEditorAppObject.CreateStringAssociationRow(ListOfListOfStrings.get(i).get(j), j, ListOfListOfStrings.get(i)));
				}
				ListOfListOfPlaceHolder.add(ListOfPlaceHolder);
			}

			for (int i = 0; i < ListOfListOfPlaceHolder.size(); i++) {
				Counter[i] = TextEditorAppObject.FindShortestActionCountsForStrings(ListOfListOfPlaceHolder.get(i), NoOfWordsToBePrinted[i]);
			}

			for (int i = 0; i < NoOfEditions; i++) {
				FileWriterObject.write("Case #" + (i + 1) + ": " + Counter[i] + "\n");
			}
			FileWriterObject.close();

			System.out.println("Done!");

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	private int FindShortestActionCountsForStrings(ArrayList<PlaceHolder> ListOfStrings, int StringLimit) {		
		int NoOfStringToPrint = StringLimit;
		int Counter,Index = 0;		
		boolean[] IsStringAlreadyChecked = new boolean[ListOfStrings.size()];
		int l = ListOfStrings.get(Index).StringAssociationValues[Index];
		for (int i = 0; i < ListOfStrings.size(); i++) {
			for (int j = 0; j < ListOfStrings.get(i).StringAssociationValues.length; j++) {
				if (i != j && (ListOfStrings.get(i).StringAssociationValues[j] < l || l == 0)) {
					Index = i;
					l = ListOfStrings.get(i).StringAssociationValues[j];
				}
			}
		}

		Counter = ListOfStrings.get(Index).StringValue.length();
		--StringLimit;
		IsStringAlreadyChecked[Index] = true;

		while (StringLimit > 0) {
			int IndexOfNextString = Index;
			l = ListOfStrings.get(Index).StringAssociationValues[Index];
			for (int j = 0; j < ListOfStrings.get(Index).StringAssociationValues.length; j++) {
				if (Index != j && (ListOfStrings.get(Index).StringAssociationValues[j] < l) && !IsStringAlreadyChecked[j]) {
					IndexOfNextString = j;
					l = ListOfStrings.get(Index).StringAssociationValues[j];
				}
			}

			Counter += ListOfStrings.get(Index).StringAssociationValues[IndexOfNextString];

			Index = IndexOfNextString;

			--StringLimit;
			IsStringAlreadyChecked[Index] = true;
		}

		Counter += ListOfStrings.get(Index).StringValue.length() + NoOfStringToPrint;

		return Counter;
	}

	private PlaceHolder CreateStringAssociationRow(String CurrentString, int CurrentStringIndex, ArrayList<String> AssociatedStrings) {
		int BiggestAssociationCost = 0;

		PlaceHolder PlaceHolderObjectOfCurrentString = new PlaceHolder(CurrentString, new int[AssociatedStrings.size()]);
		for (int i = 0; i < AssociatedStrings.size(); i++) {
			if (i == CurrentStringIndex) {
				PlaceHolderObjectOfCurrentString.StringAssociationValues[i] = 0;
				continue;
			}

			boolean IsBothStringsAreEqual = true;
			if (CurrentString.length() <= AssociatedStrings.get(i).length()) {
				for (int j = 0; j < CurrentString.length(); j++) {
					if (CurrentString.charAt(j) != AssociatedStrings.get(i).charAt(j)) {
						PlaceHolderObjectOfCurrentString.StringAssociationValues[i] = (CurrentString.length() - j) + (AssociatedStrings.get(i).length() - j);

						BiggestAssociationCost = BiggestAssociationCost + PlaceHolderObjectOfCurrentString.StringAssociationValues[i];
						IsBothStringsAreEqual = false;
						break;
					}
				}
			} else {
				for (int j = 0; j < AssociatedStrings.get(i).length(); j++) {
					if (CurrentString.charAt(j) != AssociatedStrings.get(i).charAt(j)) {
						PlaceHolderObjectOfCurrentString.StringAssociationValues[i] = (CurrentString.length() - j) + (AssociatedStrings.get(i).length() - j);

						BiggestAssociationCost = BiggestAssociationCost + PlaceHolderObjectOfCurrentString.StringAssociationValues[i];
						IsBothStringsAreEqual = false;
						break;
					}
				}
			}

			if (IsBothStringsAreEqual) {
				PlaceHolderObjectOfCurrentString.StringAssociationValues[i] = Math.abs((AssociatedStrings.get(i).length() - CurrentString.length()));
				BiggestAssociationCost = BiggestAssociationCost + PlaceHolderObjectOfCurrentString.StringAssociationValues[i];
			}
		}
		PlaceHolderObjectOfCurrentString.StringAssociationValues[CurrentStringIndex] = BiggestAssociationCost;

		return PlaceHolderObjectOfCurrentString;
	}

	private ArrayList<String> SortListOfStringBasedOnItsSize(ArrayList<String> ListOfStrings) {
		for (int i = 0; i < ListOfStrings.size() - 1; i++) {
			for (int j = i + 1; j < ListOfStrings.size(); j++) {
				if (ListOfStrings.get(i).length() > ListOfStrings.get(j).length()) {
					String KeepString = ListOfStrings.remove(i);
					ListOfStrings.add(i, ListOfStrings.get(j - 1));
					ListOfStrings.remove(j);
					ListOfStrings.add(j, KeepString);
				}
			}
		}
		return ListOfStrings;
	}

	private class PlaceHolder {		
		public int[] StringAssociationValues;
		public String StringValue;

		public PlaceHolder(String StringValue, int[] StringAssociationValues) {
			this.StringValue = StringValue;
			this.StringAssociationValues = StringAssociationValues;
		}

	}

}
