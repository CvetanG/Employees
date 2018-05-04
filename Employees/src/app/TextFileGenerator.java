package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TextFileGenerator {
	
	public static List<Integer> generateListEmploeeId(int listSize) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < listSize; i++) {
			result.add(i  + 100);
		}
		return result;
	}
	
	public static Integer randomEmploeeId(List<Integer> list) {
		int random = (int) (((list.size() - 1) * Math.random()) + 1);
		int result = list.get(random);
		list.remove(random);
		return result;
	}
	
	public static String randomDay(int time) {
		SimpleDateFormat dtf = new SimpleDateFormat("y-MM-dd");
		Calendar cal = Calendar.getInstance();
		Random gen = new Random();
		int range = Math.abs(time) * 365;
		int randInt = gen.nextInt(range);
		if (time > 0) {
			if ((randInt % 2) == 0) {
				cal.add(Calendar.DATE, randInt);
			} else {
				return "NULL";
			}
		} else {
			cal.add(Calendar.DATE, - randInt);
		}
		Date date = cal.getTime();
		return dtf.format(date).toString();
	}
	
	public static void generateFile(int genRows) {
		int emplIdStart = 100;
		
		List<Integer> list = generateListEmploeeId(emplIdStart);
		
		String fileName = "Employees.txt";
		File file = new File(fileName);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < genRows; i++) {
			
			sb.append((int) (randomEmploeeId(list)));
			sb.append(", ");
			sb.append((int) (((10) * Math.random()) + 10));
			sb.append(", ");
			sb.append(randomDay(-5));
			sb.append(", ");
			sb.append(randomDay(1));
			sb.append("\n");
		}
	
		FileWriter  fw;
	
		try {
			fw = new FileWriter(file, false);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
	public static void main(String[] args) {
		int fileRows = 10;
		generateFile(fileRows);
		System.out.println("Done Generating File!!!");
	}
}
