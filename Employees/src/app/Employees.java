package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.entities.Result;

public class Employees {
	
	private static SimpleDateFormat dtfDefault = new SimpleDateFormat("y-MM-dd");
	
	private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
	    put("^\\d{8}$", "yyyyMMdd");
	    put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
	    put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
	    put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
	    put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
	    put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
	    put("^\\d{12}$", "yyyyMMddHHmm");
	    put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
	    put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
	    put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
	    put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
	    put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
	    put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
	    put("^\\d{14}$", "yyyyMMddHHmmss");
	    put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
	    put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
	    put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
	    put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
	    put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
	    put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
	}};

	public static String determineDateFormat(String dateString) {
	    for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
	        if (dateString.toLowerCase().matches(regexp)) {
	            return DATE_FORMAT_REGEXPS.get(regexp);
	        }
	    }
	    return null;
	}
	
	public static List<String[]> parseFile(File file){
		List<String[]> result = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        String lineSplitBy = ", ";

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] row = line.split(lineSplitBy);
//                System.out.println("EmplId= " + row[0] + " , ProjectId=" + row[1] + " , DateFrom=" + row[2] + " , DateTo=" + row[3]);
                result.add(row);
            }
				} catch (FileNotFoundException e) {
		            System.out.println("The file \"" + file + "\" does not exist! Unable to read it.");
		            e.printStackTrace();
		        } catch (IOException e) {
		            // Exception handler for IOException
		            e.printStackTrace();
		        } catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return result;
	}
	
	public static long calcTogetherTime(Date dateFromA, Date dateToA,
			Date dateFromB, Date dateToB) {
		if (dateFromB.after(dateToA) || dateFromA.after(dateToB)) {
			return 0;
		}
		Date tempFrom;
		Date tempTo;
		
		if (dateFromA.after(dateFromB)) {
			tempFrom = dateFromA;
		} else {
			tempFrom = dateFromB;
		}
		
		if (dateToA.before(dateToB)) {
			tempTo = dateToA;
		} else {
			tempTo = dateToB;
		}
		long diff = tempTo.getTime() - tempFrom.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public static void mergeResult(List<Result> list) {
		List<Integer> removeList = new ArrayList<>();
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i).getEmplIdA() == list.get(i + 1).getEmplIdA()
					&& list.get(i).getEmplIdB() == list.get(i + 1).getEmplIdB()) {
				list.get(i).addProject(list.get(i + 1).getProjectsTogether());
				list.get(i).addTime(list.get(i + 1).getTimeTogether());
				removeList.add(i + 1);
			}
		}
		for (int num : removeList) {
			list.remove(num);
		}
	}
	
	static Comparator<Result> getResultTimeComparator() {
		return new Comparator<Result>() {
			@Override
			public int compare(Result r1, Result r2) {
				return Long.compare(r2.getTimeTogether(), r1.getTimeTogether());
			}
		};
	}
	
	public static Date parseStringToDate(String dateStr) throws ParseException {
		String format = determineDateFormat(dateStr);
		SimpleDateFormat dtf = null;
		if (format != null) {
			dtf = new SimpleDateFormat(format);
		} else {
			dtf = dtfDefault;
		}
        Date result;
        if (!"NULL".equalsIgnoreCase(dateStr)) {
        	result = dtf.parse(dateStr);
		} else {
			result = new Date();
		}
        return result;
		
	}
	
	public static List<Result> compareEmployees(List<String[]> rowData) throws ParseException {
		List<Result> result = new ArrayList<>();
		for (int i = 0; i < rowData.size() - 1; i++) {
			int emplIdA = Integer.parseInt(rowData.get(i)[0]);
			int projectIdA = Integer.parseInt(rowData.get(i)[1]);
			for (int j = i + 1; j < rowData.size(); j++) {
				if (projectIdA == Integer.parseInt(rowData.get(j)[1])) {
					int emplIdB = Integer.parseInt(rowData.get(j)[0]);
					Date dateFromA = parseStringToDate(rowData.get(i)[2]);
					Date dateToA = parseStringToDate(rowData.get(i)[3]);
					Date dateFromB = parseStringToDate(rowData.get(j)[2]);
					Date dateToB = parseStringToDate(rowData.get(j)[3]);
	                long timeTogether = calcTogetherTime(dateFromA, dateToA, dateFromB, dateToB);
	                if (timeTogether != 0) {
	                	Result res = new Result(emplIdA, emplIdB, projectIdA, timeTogether);
//	                	System.out.println(res);
	                	result.add(res);
					}
				}
			}
		}
		mergeResult(result);
		Collections.sort(result, getResultTimeComparator());
		return result;
	}

	public static void main(String[] args) throws ParseException {
		String fileName = "EmployeesMixFormat.txt";
		File file = new File(fileName);
		List<String[]> rData = parseFile(file);
		List<Result> resultList = compareEmployees(rData);
		System.out.println(resultList.get(0));
	}
}
