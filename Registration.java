import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Registration {

	public static void main(String[] args) {
		Student student = new Student("Josh");
		// load course into course
		try {
			// load course taken
			BufferedReader br = new BufferedReader(new FileReader("C:\\JavaLearning\\Registration\\src\\courseTaken.csv"));
			try {
				String line = br.readLine();

				while (line != null) {
					line = br.readLine();
					if (line == null) {
						continue;
					}
					String[] row = line.split(",");
					ClassTaken c = new ClassTaken(row[0], row[1], Integer.valueOf(row[2]), row[3].charAt(0));
					student.addClassTaken(c);
				}
				student.printClassTaken();
			} finally {
				br.close();
			}
			// load pre request into a map
			Map map = new Hashtable();
			br = new BufferedReader(new FileReader("C:\\JavaLearning\\Registration\\src\\preRequest.csv"));
			try {
				String line = br.readLine();

				while (line != null) {
					line = br.readLine();
					if (line == null) {
						continue;
					}
					String[] row = line.split(",");
					map.put(row[0], new PreRequest(row[1], row[2].charAt(0)));
				}
				student.setPrerequest(map);
				student.printPreRequest();
			} finally {
				br.close();
			}

			// load courses to try to register if the couse meet pre request
			br = new BufferedReader(new FileReader("C:\\JavaLearning\\Registration\\src\\course (1).csv"));
			try {
				String line = br.readLine();

				while (line != null) {
					line = br.readLine();
					if (line == null) {
						continue;
					}
					String[] row = line.split(",");
					Course c = new Course(row[0], row[1], Integer.valueOf(row[2]));
					student.addClass(c, map);
				}
				student.printRegistedCouses();
			} finally {
				br.close();
			}


	} catch (Exception e) {
		System.out.println("Error in main" + e.getMessage());
	}

	}

}

class PreRequest {

	@Override
	public String toString() {
		return "PreRequest [requiredCourse=" + requiredCourse + ", grade=" + grade + "]";
	}

	private String requiredCourse;
	private char grade;

	/**
	 * @return the grade
	 */
	public char getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(char grade) {
		this.grade = grade;
	}

	public PreRequest(String requiredCourse, char grade) {
		this.requiredCourse = requiredCourse;
		this.grade = grade;
	}

	/**
	 * @return the requiredCourse
	 */
	public String getRequiredCourse() {
		return requiredCourse;
	}

	/**
	 * @param requiredCourse
	 *            the requiredCourse to set
	 */
	public void setRequiredCourse(String requiredCourse) {
		this.requiredCourse = requiredCourse;
	}

}

class Student {

	private String name;
	private List<Course> classRegisted = new ArrayList();
	private List<ClassTaken> classTaken = new ArrayList();

	private Map<String, PreRequest> prerequest = new Hashtable();

	/**
	 * @return the prerequest
	 */
	public Map<String, PreRequest> getPrerequest() {
		return prerequest;
	}

	/**
	 * @param prerequest
	 *            the prerequest to set
	 */
	public void setPrerequest(Map<String, PreRequest> prerequest) {
		this.prerequest = prerequest;
	}

	public Student(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the classRegistered
	 */
	public List<Course> getClassRegisted() {
		return classRegisted;
	}

	public void printRegistedCouses() {
		for (Course course : this.classRegisted) {
			System.out.println(course);
		}
	}

	public void printClassTaken() {
		for (ClassTaken cls : this.classTaken) {
			System.out.println(cls);
		}
	}

	public void printPreRequest() {
		for (Map.Entry<String, PreRequest> pre : this.prerequest.entrySet()) {
			System.out.println(pre.getKey() + ": " + pre.getValue());
		}
	}

	/**
	 * @param course
	 * @param preRequest
	 *            AddClass if student meet has taken the pre request class and
	 *            the grade is >= required
	 */
	public void addClass(Course course, Map preRequest) {
		System.out.println("\ngoing to add course: " + course.getCourseName());
		if (preRequest.containsKey(course.getCourseName())) {
			// check for class taken if the student taken that course or not
			PreRequest request = (PreRequest)preRequest.get(course.getCourseName());
			ClassTaken ct = getClassTaken(request.getRequiredCourse());
			// class not taken or grade less than required, return
			if (ct == null || ct.getGrade() > request.getGrade()) {
				System.out.format("can not add couse due to pre request: %s", course.getCourseName());
				return;
			}
		}
		System.out.format("added couse: %s", course.getCourseName());
		classRegisted.add(course);
	}

	private ClassTaken getClassTaken(String course) {
		for (ClassTaken cls : classTaken) {
			if (course.equalsIgnoreCase(cls.getCourseName())) {
				return cls;
			}
		}
		return null;
	}
	/**
	 * @param classRegisted
	 *            the classRegisted to set
	 */
	public void setClassRegisted(List<Course> classRegisted) {
		this.classRegisted = classRegisted;
	}

	public void addClassTaken(ClassTaken classTaken) {
		this.classTaken.add(classTaken);
	}

}

class Course {

	@Override
	public String toString() {
		return "Course [courseName=" + courseName + ", desc=" + desc + ", creditHour=" + creditHour + "]";
	}

	private String courseName;
	private String desc;
	private int creditHour;

	/**
	 * @return the creditHour
	 */
	public int getCreditHour() {
		return creditHour;
	}

	/**
	 * @param creditHour
	 *            the creditHour to set
	 */
	public void setCreditHour(int creditHour) {
		this.creditHour = creditHour;
	}

	public Course(String courseName, String description, int creditHour) {
		this.courseName = courseName;
		this.desc = description;
		this.creditHour = creditHour;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName
	 *            the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}

class ClassTaken extends Course {

	@Override
	public String toString() {
		return "ClassTaken [grade=" + grade + ", toString()=" + super.toString() + ", getCreditHour()=" + getCreditHour() + ", getCourseName()="
				+ getCourseName() + ", getDesc()=" + getDesc() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

	char grade;

	/**
	 * @return the grade
	 */
	public char getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(char grade) {
		this.grade = grade;
	}

	public ClassTaken(String courseName, String description, int creditHour, char grade) {
		super(courseName, description, creditHour);
		this.grade = grade;
	}
}
