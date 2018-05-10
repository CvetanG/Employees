package app.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Result {
	
	private int emplIdA;
	private int emplIdB;
	private List<Integer> projectsTogether = new ArrayList<>();
	private long timeTogether = Long.MIN_VALUE;
	
	public Result(int emplIdA, int emplIdB, List<Integer> projectsTogether, long timeTogether) {
		this.emplIdA = emplIdA;
		this.emplIdB = emplIdB;
		this.projectsTogether = projectsTogether;
		this.timeTogether = timeTogether;
	}

	public Result(int emplIdA, int emplIdB, int projectId, long timeTogether) {
		this.emplIdA = emplIdA;
		this.emplIdB = emplIdB;
		List<Integer> list = new ArrayList<>();
		list.add(projectId);
		this.projectsTogether = list;
		this.timeTogether = timeTogether;
	}

	public void addProject(List<Integer> list) {
		this.projectsTogether.addAll(list);
	}
	
	public void addTime(long temp) {
		this.timeTogether += temp;
	}
	
	public int getEmplIdA() {
		return emplIdA;
	}

	public void setEmplIdA(int emplIdA) {
		this.emplIdA = emplIdA;
	}

	public int getEmplIdB() {
		return emplIdB;
	}

	public void setEmplIdB(int emplIdB) {
		this.emplIdB = emplIdB;
	}

	public List<Integer> getProjectsTogether() {
		return projectsTogether;
	}

	public void setProjectsTogether(List<Integer> projectsTogether) {
		this.projectsTogether = projectsTogether;
	}

	public long getTimeTogether() {
		return timeTogether;
	}

	public void setTimeTogether(long timeTogether) {
		this.timeTogether = timeTogether;
	}

	@Override
	public String toString() {
		return "Employee with ID:" + emplIdA + " and employee with ID:"
				+ emplIdB + " worked longest time together " 
				+ timeTogether + " days on projectsID:" 
				+ Arrays.toString(projectsTogether.toArray());
	}
	
}
