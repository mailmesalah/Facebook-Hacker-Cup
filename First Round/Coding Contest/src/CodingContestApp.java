import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class CodingContestApp {
	
	private int FindNoOfAddedProblems(int[] D){
		int Counter=0;
		int CountIndex=0;
		
		int i=0;
		while(i<D.length-1){
			++CountIndex;
			CountIndex=CountIndex%4;
			
			if(CountIndex==0){
				++i;
				continue;
			}
			
			if(D[i]>=D[i+1]){
				Counter+=4-CountIndex;
				CountIndex=0;
			}else if((D[i+1]-D[i])>10){
				if((((D[i+1]-D[i]-1)/10))+CountIndex>=4){
					Counter+=4-CountIndex;
					CountIndex=0;
				}else{
					Counter+=(((D[i+1]-D[i])-1)/10);
					CountIndex+=(((D[i+1]-D[i])-1)/10);
				}
			}								
			++i;
		}
		++CountIndex;
		Counter+=4-CountIndex;
		return Counter;
	}
	
	public static void main(String[] args) throws IOException{
		Scanner ScannerObject = new Scanner(new File("input.txt"));
		FileWriter FileWriterObject = new FileWriter( new File("output.txt"));
		
		int NoOfTests=ScannerObject.nextInt();
		int[] NoOfProblems=new int[NoOfTests];
		int[] Counter=new int[NoOfTests];
		CodingContestApp CodingContestObject = new CodingContestApp();
		int[] ProblemRatings;
		for(int i=0;i<NoOfTests;i++){
			Counter[i]=0;			
			NoOfProblems[i]=ScannerObject.nextInt();
			ProblemRatings=new int[NoOfProblems[i]];
			for(int j=0;j<NoOfProblems[i];j++){
				ProblemRatings[j]=ScannerObject.nextInt();												
			}
			Counter[i]=CodingContestObject.FindNoOfAddedProblems(ProblemRatings);
			FileWriterObject.write("Case #"+(i+1)+": "+Counter[i] +"\n");
			
		}		
		FileWriterObject.close();
		ScannerObject.close();
		System.out.println("Done");
		
	}
	
	
	
}
