import java.util.ArrayList;

public class Department
{
    // it should store its Name Assigned to it
    // all the students which are enrolled in it
    private  String departName;
    ArrayList<Student> ls;
    public Department(String name)
    {
        this.departName=name;
        ls=new ArrayList<>();
    }
    public String getDepartName()
    {
        return  departName;
    }
    public ArrayList<Student> getStudentsOfDepartment()
    {
        return ls;
    }

    @Override
    public String toString() {
        return this.departName;
    }
}
