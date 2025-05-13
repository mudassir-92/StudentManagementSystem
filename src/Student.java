public class Student implements  IStudent{
    private int loginID;
    private  String password;
    private  String name;
    private  double CGPA;
    private  Department depart;


    public Student (String studentName,int studentNumber,Department departName)
    {
        name=studentName;
        this.password=String.valueOf(studentNumber);
        loginID=studentNumber;
        depart=departName; // does it shallow copy ?
    }

    public Student(Student student) {
        this.password=student.password;
        this.name=student.name;
        this.depart=student.depart;
        this.loginID=student.loginID;
        this.CGPA=student.CGPA;
    }

    public int getLoginID() {
        return loginID;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean updatePassword(String oldPassword, String newPassword) {
        // if old password matches new password then it will updte it to new
        if(oldPassword.equals(this.password))
        {
            this.password=newPassword;
            return  true;
        }
        return  false;
    }

    @Override
    public Boolean matchPassword(String test) {
        return test.equals(password);
    }

    @Override
    public String toString() {
        return String.valueOf(loginID)+"ID, "+name+" from Department of "+depart+" with CGPA "+String.valueOf(CGPA);
    }

    public void setDepart(Department depart) {
        this.depart = depart;
    }

    public void setCGPA(double CGPA) {
        this.CGPA = CGPA;
    }

    public String getName() {
        return name;
    }

    public double getCGPA() {
        return CGPA;
    }

}
