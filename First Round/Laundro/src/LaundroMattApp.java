import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class LaundroMattApp {
	
	private int FindSmallestTimeIndex(long[] TimeRequiredForDryers){
		int SmallestNo=0;
		for(int i=1;i<TimeRequiredForDryers.length;i++){
			if(TimeRequiredForDryers[SmallestNo]>TimeRequiredForDryers[i]){
				SmallestNo=i;
			}
		}
		return SmallestNo;
	}
	
	private int FindBiggestTimeIndex(long[] TimeRequiredByDryers){
		int BiggestNo=0;
		for(int i=1;i<TimeRequiredByDryers.length;i++){
			if(TimeRequiredByDryers[BiggestNo]<TimeRequiredByDryers[i]){
				BiggestNo=i;
			}
		}
		return BiggestNo;
	}
	
	private long FindSmallestTimeRequired(int NoOLaundries,long NoOfDryers,long TimeRequiredForDrying,long[] TimeRequiredForWashingMachines){

		for(int i=0;i<TimeRequiredForWashingMachines.length-1;i++){
			for(int j=i+1;j<TimeRequiredForWashingMachines.length;j++){
				if(TimeRequiredForWashingMachines[i]>TimeRequiredForWashingMachines[j]){
					long t=TimeRequiredForWashingMachines[i];
					TimeRequiredForWashingMachines[i]=TimeRequiredForWashingMachines[j];
					TimeRequiredForWashingMachines[j]=t;
				}
			}
		}
		
		long[] SumOfTimeRequiredByWashingMachine= new long[TimeRequiredForWashingMachines.length];
		int Index=0;
		SumOfTimeRequiredByWashingMachine[Index]=TimeRequiredForWashingMachines[Index];
		
		long[] TimeRequiredByDryers= new long[(int)NoOfDryers];
		int IndexOfSmallestRequiredTime=0;			
		TimeRequiredByDryers[IndexOfSmallestRequiredTime]=SumOfTimeRequiredByWashingMachine[Index]+TimeRequiredForDrying;
		
		for(int i=1;i<NoOLaundries;i++){
			boolean Found=false;
			for(int j=0;j<TimeRequiredForWashingMachines.length-1;j++){
				if((SumOfTimeRequiredByWashingMachine[j]+TimeRequiredForWashingMachines[j])<=(SumOfTimeRequiredByWashingMachine[j+1]+TimeRequiredForWashingMachines[j+1])){
					SumOfTimeRequiredByWashingMachine[j]+=TimeRequiredForWashingMachines[j];
					Index=j;
					Found=true;
					
					IndexOfSmallestRequiredTime=FindSmallestTimeIndex(TimeRequiredByDryers);
					TimeRequiredByDryers[IndexOfSmallestRequiredTime]=(TimeRequiredByDryers[IndexOfSmallestRequiredTime]>=SumOfTimeRequiredByWashingMachine[j]?TimeRequiredByDryers[IndexOfSmallestRequiredTime]+TimeRequiredForDrying:SumOfTimeRequiredByWashingMachine[j]+TimeRequiredForDrying);
					break;
				}
			}
			if(!Found){
				SumOfTimeRequiredByWashingMachine[TimeRequiredForWashingMachines.length-1]+=TimeRequiredForWashingMachines[TimeRequiredForWashingMachines.length-1];
				Index=TimeRequiredForWashingMachines.length-1;
				
				IndexOfSmallestRequiredTime=FindSmallestTimeIndex(TimeRequiredByDryers);
				TimeRequiredByDryers[IndexOfSmallestRequiredTime]=(TimeRequiredByDryers[IndexOfSmallestRequiredTime]>=SumOfTimeRequiredByWashingMachine[TimeRequiredForWashingMachines.length-1]?TimeRequiredByDryers[IndexOfSmallestRequiredTime]+TimeRequiredForDrying:SumOfTimeRequiredByWashingMachine[TimeRequiredForWashingMachines.length-1]+TimeRequiredForDrying);
			}
		}
		return TimeRequiredByDryers[FindBiggestTimeIndex(TimeRequiredByDryers)];
	}
	
	public static void main(String[] args) throws IOException{
		
		Scanner ScannerObject = new Scanner(new File("input.txt"));
		FileWriter FileWriterObject = new FileWriter( new File("output.txt"));
		
		int NoOfLaundries=ScannerObject.nextInt();
		int[] EachLaundry=new int[NoOfLaundries];
		int[] NoOfWashingMachine=new int[NoOfLaundries];
		long[] NoOfDryers=new long[NoOfLaundries];
		long[] DryerTimeRequired=new long[NoOfLaundries];
		
		long[] Counter=new long[NoOfLaundries];
		LaundroMattApp LaundroMattObject = new LaundroMattApp();
	
		for(int i=0;i<NoOfLaundries;i++){
			Counter[i]=0;			
			EachLaundry[i]=ScannerObject.nextInt();
			NoOfWashingMachine[i]=ScannerObject.nextInt();
			NoOfDryers[i]=ScannerObject.nextInt();
			DryerTimeRequired[i]=ScannerObject.nextInt();
			long[]WMachines=new long[NoOfWashingMachine[i]];
			for(int j=0;j<NoOfWashingMachine[i];j++){
				WMachines[j]=ScannerObject.nextInt();												
			}
			Counter[i]=LaundroMattObject.FindSmallestTimeRequired(EachLaundry[i],NoOfDryers[i],DryerTimeRequired[i],WMachines);
			
			FileWriterObject.write("Case #"+(i+1)+": "+Counter[i] +"\n");
			
		}		
		FileWriterObject.close();
		ScannerObject.close();
		System.out.println("Done!");

	
	}
		
}
