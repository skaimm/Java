package Common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


import Realestate.ApartmentBlock;


public class Landlord {

	static void maintain(String filename,ApartmentBlock block) {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = null;
		try {
			String currentLine;
			br = new BufferedReader(new FileReader(filename));
			while((currentLine = br.readLine()) != null)
			{
				String[] strArr = currentLine.split("\\;");
				for(String str:strArr) {
				 	if(str!=null)
					list.add(str);	
				}
				block.addProperty(list);
				list.clear();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(br != null)
					br.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	

	public static void main(String[] args) {
		
		ApartmentBlock apartBlock = new ApartmentBlock(20, 10);
		maintain("file.txt",apartBlock);
		System.out.println("The Number of Tenants in the Apartment Block   :" + apartBlock.numberOfTenants());
		System.out.println("The Total Costs of Rented Property :" + apartBlock.usedPropertyValue());
		
		apartBlock.printblocks();

	}

}
