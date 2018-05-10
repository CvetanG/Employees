package app;

import java.io.File;
import java.text.ParseException;

import app.entities.Employees;

public class RunMe {
	public static void main(String[] args) throws ParseException {
		String fileName = "EmployeesMixFormat.txt";
		File file = new File(fileName);
		Employees emp = new Employees(file);
		emp.printTopResult();
	}
}
