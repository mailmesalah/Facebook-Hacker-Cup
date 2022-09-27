import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class BoomerangApp {

	public class PlaceHolder{
		int X;
		int Y;
		double[] Distance;
		
		public PlaceHolder(int X, int Y, double[] Distance) {
			super();
			this.X = X;
			this.Y = Y;
			this.Distance = Distance;
		}
			
	}
	
	public static void main(String[] args){
		try{
		int NoOfNights=0;
		int[] NoOfStarsInEachNights;
		int[] Counter;
		
		
		Scanner ScannerObject = new Scanner(new File("input.txt"));
		FileWriter FileWriterObject = new FileWriter( new File("output.txt"));

		System.out.println("Started!");
		
		NoOfNights=ScannerObject.nextInt();
		NoOfStarsInEachNights=new int[NoOfNights];
		Counter=new int[NoOfNights];
		BoomerangApp BoomerangAppObject = new BoomerangApp();
		
		//Reading
		for(int i=0;i<NoOfNights;i++){
			Counter[i]=0;			
			NoOfStarsInEachNights[i]=ScannerObject.nextInt();
			ArrayList<PlaceHolder> ListOfStars = new ArrayList<>();
			for(int j=0;j<NoOfStarsInEachNights[i];j++){
				int X=ScannerObject.nextInt();
				int Y=ScannerObject.nextInt();
				ListOfStars.add(BoomerangAppObject.new PlaceHolder(X, Y, new double[NoOfStarsInEachNights[i]]));									
			}
			//Processing
			BoomerangAppObject.CreateDistancesOfAStarWithOthers(ListOfStars);
			Counter[i]=BoomerangAppObject.CountBoomerang(ListOfStars);
			FileWriterObject.write("Case #"+(i+1)+": "+Counter[i] +"\n");
		}		
		FileWriterObject.close();							
		System.out.println("Completed");

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private int CountBoomerang(ArrayList<PlaceHolder> ListOfStars){
		int LocalCounter=0;
		for(int i=0;i<ListOfStars.size();i++){
			for(int j=0;j<ListOfStars.size();j++){
				for(int k=j+1;k<ListOfStars.size();k++){
					if(i!=j && j!=k && k!=i && (ListOfStars.get(i).Distance[j]==ListOfStars.get(i).Distance[k])){
						++LocalCounter;
					}
				}				
			}
		}
		
		return LocalCounter;
	}
	
		
	private void CreateDistancesOfAStarWithOthers(ArrayList<PlaceHolder> ListOfStars){
		for(int i=0;i<ListOfStars.size();i++){
			for(int j=0;j<ListOfStars.size();j++){
				if(i!=j){
					int FirstProduct=ListOfStars.get(i).X-ListOfStars.get(j).X;
					FirstProduct=FirstProduct*FirstProduct;
					int SecondProduct=ListOfStars.get(i).Y-ListOfStars.get(j).Y;
					SecondProduct=SecondProduct*SecondProduct;					
					ListOfStars.get(i).Distance[j]=Math.sqrt(FirstProduct+SecondProduct);
				}else{
					ListOfStars.get(i).Distance[j]=0;
				}
			}
		}
	}
	
}
