package Lecture;

public class ClassDTO {
	private int classID;
	private String classThema;
	private String classTitle;
	private String classProfessor;
	private String classContent;
	private String classImg;
	private int classVideoNum;
	private int classPersonnel;
	
	public ClassDTO(int classID, String classThema, String classTitle, String classProfessor, String classContent, String classImg, int classVideoNum, int classPersonnel) {
		super();
		this.classID = classID;
		this.classThema = classThema;
		this.classTitle = classTitle;
		this.classProfessor = classProfessor;
		this.classContent = classContent;
		this.classImg = classImg;
		this.classVideoNum = classVideoNum;
		this.classPersonnel = classPersonnel;
	}
	public ClassDTO(int classID, String classImg, String classTitle, String classProfessor, int classPersonnel) {
        this.classID = classID;
        this.classImg = classImg;
        this.classTitle = classTitle;
        this.classProfessor = classProfessor;
        this.classPersonnel = classPersonnel;
    }
	public ClassDTO(String classImg, String classTitle, String classProfessor, int classVideoNum) {
        this.classImg = classImg;
        this.classTitle = classTitle;
        this.classProfessor = classProfessor;
        this.classVideoNum = classVideoNum;
    }
	public ClassDTO(int classID, String classImg, String classTitle, String classThema,String classProfessor) {
		this.classID = classID;
		this.classImg = classImg;
        this.classTitle = classTitle;
        this.classProfessor = classProfessor;
        this.classThema = classThema;
    }
	public ClassDTO() {
    }
	public int getClassID() {
		return classID;
	}
	public void setClassID(int classID) {
		this.classID = classID;
	}
	public String getClassThema() {
		return classThema;
	}
	public void setClassThema(String classThema) {
		this.classThema = classThema;
	}
	public String getClassTitle() {
		return classTitle;
	}
	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}
	public String getClassImg() {
		return classImg;
	}
	public void setClassImg(String classImg) {
		this.classImg = classImg;
	}
	public String getClassProfessor() {
		return classProfessor;
	}
	public void setClassProfessor(String classProfessor) {
		this.classProfessor = classProfessor;
	}
	public String getClassContent() {
		return classContent;
	}
	public void setClassContent(String classContent) {
		this.classContent = classContent;
	}
	public int getClassVideoNum() {
		return classVideoNum;
	}
	public void setClassVideoNum(int classVideoNum) {
		this.classVideoNum = classVideoNum;
	}
	public int getClassPersonnel() {
		return classPersonnel;
	}
	public void setClassPersonnel(int classPersonnel) {
		this.classPersonnel = classPersonnel;
	}
	
	
}
