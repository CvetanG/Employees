package app.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.utils.Utils;

public class Employees {
	
	List<Result> data;

	public Employees(File file) {
		List<String[]> rData = parseFile(file);
		try {
			this.data = compareEmployees(rData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String[]> parseFile(File file){
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
	
	public long calcTogetherTime(String dateFromAStr, String dateToAStr,
			String dateFromBStr, String dateToBStr) throws ParseException {
		Date dateFromA = Utils.parseStringToDate(dateFromAStr);
		Date dateToA = Utils.parseStringToDate(dateToAStr);
		Date dateFromB = Utils.parseStringToDate(dateFromBStr);
		Date dateToB = Utils.parseStringToDate(dateToBStr);
		
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
	
	public void mergeResult(List<Result> list) {
		List<Integer> removeList = new ArrayList<>();
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getEmplIdA() == list.get(j).getEmplIdA()
						&& list.get(i).getEmplIdB() == list.get(j).getEmplIdB()) {
					list.get(i).addProject(list.get(j).getProjectsTogether());
					list.get(i).addTime(list.get(j).getTimeTogether());
					removeList.add(i + 1);
				}
			}
		}
		list.removeAll(removeList);
	}
	
	static Comparator<Result> getResultTimeComparator() {
		return new Comparator<Result>() {
			@Override
			public int compare(Result r1, Result r2) {
				return Long.compare(r2.getTimeTogether(), r1.getTimeTogether());
			}
		};
	}
	
	public List<Result> compareEmployees(List<String[]> rowData) throws ParseException {
		List<Result> result = new ArrayList<>();
		for (int i = 0; i < rowData.size() - 1; i++) {
			int emplIdA = Integer.parseInt(rowData.get(i)[0]);
			int projectIdA = Integer.parseInt(rowData.get(i)[1]);
			for (int j = i + 1; j < rowData.size(); j++) {
				if (projectIdA == Integer.parseInt(rowData.get(j)[1])) {
					int emplIdB = Integer.parseInt(rowData.get(j)[0]);
	                long timeTogether = calcTogetherTime(rowData.get(i)[2], rowData.get(i)[3], rowData.get(j)[2], rowData.get(j)[3]);
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
	
	public void printTopResult() {
		System.out.println(this.data.get(0));
	}

	public List<Result> getData() {
		return data;
	}
	
}
