package app.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TextFileGenerator {

	public static SimpleDateFormat dtf = new SimpleDateFormat("y-MM-dd");

	public static Date randomDay(int time) {
		Calendar cal = Calendar.getInstance();
		Random gen = new Random();
		int range = Math.abs(time) * 365;
		int randInt = gen.nextInt(range);
		cal.add(Calendar.DATE, -randInt);
		Date date = cal.getTime();
		return date;
	}
	
	public static Date addDays(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		Date today = new Date();
		Random gen = new Random();
		long diff = today.getTime()- startDate.getTime();
		int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		int randInt = gen.nextInt(days);
		if ((randInt % 2) == 0) {
			cal.add(Calendar.DATE, randInt);
		} else {
			return null;
		}
		return cal.getTime();
	}

	public static void generateFile(int genRows) {
		int emplIdStart = 100;
		int projectIdStart = 10;
		
		int numEmpl = 20;
		int numProjects = 10;
		
		int dateFromPeriod = 3;
		
		List<int[]> listEmplProject = new ArrayList<>();
		for (int i = 0; i < numEmpl; i++) {
			int emplId = emplIdStart + i;
			for (int j = 0; j < numProjects; j++) {
				int projectId = projectIdStart + j;
				int[] arr = {emplId, projectId};
				listEmplProject.add(arr);
			}
		}
		
		String fileName = "Employees.txt";
		File file = new File(fileName);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < genRows; i++) {
			int rand = (int) (((listEmplProject.size()) * Math.random()));
			int emplID = listEmplProject.get(rand)[0];
			int projectID = listEmplProject.get(rand)[1];
			listEmplProject.remove(rand);
			Date dateFrom = randomDay(-dateFromPeriod);
			Date dateTo = addDays(dateFrom);
			sb.append(emplID);
			sb.append(", ");
			sb.append(projectID);
			sb.append(", ");
			sb.append(dtf.format(dateFrom).toString());
			sb.append(", ");
			if (dateTo != null) {
				sb.append(dtf.format(dateTo).toString());
			} else {
				sb.append("NULL");
			}
			sb.append("\n");
		}

		FileWriter fw;

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
		int fileRows = 50;
		generateFile(fileRows);
		System.out.println("Done Generating File!!!");
	}
}
