import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StreamAPI {

public static void main(String[] args) throws IOException, SalesException {
		StreamAPI streamDemo=new StreamAPI();
		List<Sales> ls=new ArrayList();
		String filePath="C:\\Users\\Kajal\\Desktop\\Sales_Record.csv";
		List<Sales> list=readFile(filePath);
		ls=list.stream().filter(x->x.units_Sold>6000).collect(Collectors.toList());
		streamDemo.printData(ls);
		out.println("Total="+ls.stream().count());
}
		

	private static void printData(List<Sales> list, int k) throws SalesException {
		if(k<0 || k>list.size())
			throw new SalesException("NO_DATA_FOUND",SalesException.SalesExceptionType.NO_DATA_FOUND);
		else 
			out.println(list.get(k).toString());
	}

	private static void printData(List<Sales> list) {
		for(int i=0;i<list.size();i++) {
			out.print(i+1+ " ");
			out.print("Region:"+list.get(i).getRegion()+" ");
			out.print("Country:"+list.get(i).getCountry()+" ");
			out.print("Item Type:"+list.get(i).getItem_Type()+" ");
			out.print("Sales Channel:"+list.get(i).getSales_Channel()+" ");
			out.print("Order Priority:"+list.get(i).getOrder_Priority()+" ");
			out.print("Order Date:"+list.get(i).getOrder_Date()+" ");
			out.print("Order ID:"+list.get(i).getOrder_ID()+" ");
			out.print("Ship Date:"+list.get(i).getShip_Date()+" ");
			out.print("Unit Sold:"+list.get(i).getUnits_Sold()+" ");
			out.print("Unit Price:"+list.get(i).getUnit_Price()+" ");
			out.print("Unit Cost:"+list.get(i).getUnit_Cost()+" ");
			out.print("Total Revenue:"+list.get(i).getTotal_Revenue()+" ");
			out.print("Total Cost:"+list.get(i).getTotal_Cost()+" ");
			out.print("Total Profit:"+list.get(i).getTotal_Profit()+" ");
			out.println();
		}	
		
	}

	private static List<Sales> readFile(String filePath) throws SalesException {
		List<Sales> list=new ArrayList<Sales>();
		try (Reader reader=Files.newBufferedReader(Paths.get(filePath));){
			CsvToBean<Sales> csvReader=new CsvToBeanBuilder(reader)
					.withType(Sales.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			Iterator<Sales> it=csvReader.iterator();
			while(it.hasNext()) {
				Sales s=it.next();
				list.add(s);
			}
		}catch (FileNotFoundException e) {
			throw new SalesException("FILE_NOT_FOUND",SalesException.SalesExceptionType.FILE_NOT_FOUND);
		} catch (IOException e) {
			throw new SalesException("INPUT_OUTPUT_ERROR",SalesException.SalesExceptionType.INPUT_OUTPUT_ERROR);
		}
		return list;
	}
}
