import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import  java.util.Scanner;
import  java.util.InputMismatchException;
import  java.time.LocalDateTime;
class LoginStatus
{
    public  static  final  int ADMIN=1;
    public  static  final  int STUDENT=2;
}
public class mainInterface implements  InterfaceManager{
    private int  loginType;
    boolean logoutStatus;
    private  Admin adminObj;
    ArrayList<Student> students;
    private int student_number;
    private  int currentStudentID;
    private  ArrayList<Department> dpList;
    private Department computerDp;
    private Department pharmacy;
    private Department arts;
    private  ArrayList<String> auditLog;

    // ctor
    public mainInterface()
    {
        adminObj= new Admin();
        students=new ArrayList<>();
        loginType=0;
        logoutStatus=false;
        student_number=0;
        currentStudentID=0;
        dpList=new ArrayList<>();
        computerDp= new Department("Computer Science");
        pharmacy=new Department("Pharmacy");
        arts=new Department("Arts and Design");
        dpList.add(computerDp);
        dpList.add(pharmacy);
        dpList.add(arts);
        auditLog=new ArrayList<>();
    }

    @Override
    public void launchInterface() {
        // retrieve  data from the file
        try {
            FileReader fr=new FileReader("auditLog.txt");
            BufferedReader bf=new BufferedReader(fr);
            String line="";
            int id=0;
            while ((line=bf.readLine())!=null) {
                auditLog.add(line);
                id++;
            }
            currentStudentID=id;
        }catch (IOException e)
        {
            System.out.println("Previous Audit-Log did'nt backed-up");
        }
        // login user
        // it will keep asking for input until correct value entered
        int choice;
        Scanner in=new Scanner(System.in);
        do {
            System.out.println("1.Login \n2. Exit");
            choice= in.nextInt();
            in.nextLine();
            if(choice==2)
            {
                // save all Students and admin details on disk and exit
                try {
                    saveAllDetailsOnDisk();
                }catch (IOException e)
                {
                    System.out.println("Failed to write data");
                }
                break;
            }
            if(choice!=1)
                continue;
            while(!promptUserForLogin())
            {
                // until ok value is entered
            }
            logoutStatus=true;
            if(loginType==LoginStatus.ADMIN)
                adminPanel();       // aslo manages all logic related to admin panel
            else if(loginType==LoginStatus.STUDENT)
                studentPanel();

        }while (choice!=2);

    }

    private void saveAllDetailsOnDisk() throws IOException {
        FileWriter fw=new FileWriter("auditLog.text",true);
        // saving Audit log only
        for(int i=0;i<auditLog.size();i++)
        {
            fw.write(auditLog.get(i));
            fw.write('\n');
        }
    }

    @Override
    protected void finalize() throws Throwable {
        saveAllDetailsOnDisk();
        super.finalize();
    }

    private void studentPanel() {
        // WHAT STUDENT PANEL DO IS
        // ACC SETTING // SEE THERE CGPA
        int choice;
        Scanner in=new Scanner(System.in);
        do {
            System.out.println("1.See CGPA \n2n. List my detail \n3.Account Setting");
            choice=in.nextInt();
            in.nextLine();
        }while ((choice==1|| choice==2 || choice==3 )&& logoutStatus);
        // now choice be 1 2 3
        // which student it is ? let's find by its ID with our dataBase arraylist of Student
        switch (choice)
        {
            case 1:
            {
                System.out.print("Your CGPA is :"+students.get(student_number).getCGPA());

            }
            case 2:
            {
                studentAccountSetting(students.get(student_number));
            }
        }
    }
    private void studentAccountSetting(Student student)
    {
        // what it does is actually is update || Name Update || Password ||  logout
        System.out.println("1. Update Name \n2. Update Password \n3.Logout");
        Scanner in=new Scanner(System.in);
        int choice=in.nextInt();

        switch (choice)
        {
            case 1:
            {
                // update name
                System.out.print("Enter Name: ");
                String testName= in.nextLine();
                student.setName(testName);
            }
            case 2:
            {
                System.out.print("Enter old Password: ");
                String oldEntered=in.nextLine();
                System.out.println("");
                if(student.matchPassword(oldEntered))
                {
                    System.out.print("Enter New Password: ");
                    String enterdNewPassword=in.nextLine();
                    student.updatePassword(oldEntered,enterdNewPassword);
                }
                else
                {
                    System.out.println("Wrong Password :(");
                }
                // Update Password
            }
            case 3:
            {
                // logout
                logoutStatus=false;
            }
        }
    }

    // only be accessed when authenticated that
    private void adminPanel() {
        // it would handle all admin panel things
        // now asking foe to E select your option

        // do while !logout
        do
        {
            printAdminPanelOptions();
            Scanner obj=new Scanner(System.in);
            int choice=obj.nextInt();
            obj.nextLine();

            switch (choice)
            {
                // "1. Add a student"
                case 1:
                {
                    addStudent();
                    break;
                }
//                2. Remove a student
                case 2:
                {
                    // returns boolean if student is really removed
                    removeStudent();
                    break;
                }
//                Update a student records
                case 3:
                {
                    // returns boolean if student is really updated
                    updateStudent();
                    break;
                }
//                Search a student
                case  4:
                {
                    searchStudent();
                    break;
                }
//                List all Students
                case 5:
                {
                    listAllStudents();
                    break;
                }
//                Sort students
                case 6:
                {
                    sortStudents();
                    break;
                }
//                print Audit-Log
                case 7:
                {
                    printAuditLog();
                    break;
                }
//                Account Settings
                case 8:{
                    manageAccountSettings(); // will also log out
                }
                default:{
                    System.out.println("Invalid try Again");
                }
            }
        }while (logoutStatus);

    }

    private void manageAccountSettings() {
        // what should be account Setting ?
        // changeName and change Password logOut
        System.out.println("1. Change Name \n2. Update Password \n3. Logout");
        Scanner in=new Scanner(System.in);
        int choice=in.nextInt();
        in.nextLine();
        switch (choice)
        {
            case 1:
            {
                System.out.print("Enter Your Name: ");
                String testName=in.nextLine();
                adminObj.setName(testName);
            }
            case 2:
            {
                System.out.println("Enter Old Password");
                String oldPass=in.nextLine();
                System.out.print("Enter New Password: ");
                String enteredPassword=in.nextLine();
                adminObj.updatePassword(oldPass,enteredPassword);
            }
            case 3:
            {
                logoutStatus=false;
            }
        }
    }

    private void printAuditLog() {
        // what should be stored here is wrong ID credentials entered when sorted ?
        System.out.println("Here Are the Audit Log");
        for(int i=0;i<auditLog.size();i++)
        {
            System.out.println(auditLog.get(i));
        }
    }

    private void sortStudents() {
        // before what lets see how :)
        // implement Bubble Sort to sort
        System.out.println("Which Sorting You Need");
        System.out.println("1. Name \n2. CGPA");
        Scanner in=new Scanner(System.in);
        int choice;
        do {
            choice=in.nextInt();
            in.nextLine();
        }while (choice!=1 && choice!=2);
        // Bubble Sort
        boolean flag;
        do {
            flag=true;
            for(int i=0;i<students.size()-1;i++)
            {
                if(choice==1 && students.get(i).getName().compareTo(students.get(i+1).getName())>0)
                {
                        Student temp=new Student(students.get(i));
                        students.set(i,students.get(i+1));
                        students.set(i+1,temp);
                        flag=false;
                }
                if(choice==2 && students.get(i).getCGPA()>students.get(i+1).getCGPA())
                {
                    Student temp=new Student(students.get(i));
                    students.set(i,students.get(i+1));
                    students.set(i+1,temp);
                    flag=false;
                }
            }
        }while (!flag);
    }

    private void listAllStudents() {
        for (Student i:students)
        {
            System.out.println(i);
        }
    }

    private void searchStudent() {
        System.out.println("Enter Student ID: ");
        Scanner in=new Scanner(System.in);
        int enteredID;
        try
        {
            enteredID=in.nextInt();  // note if it fails then all the -> input remains on buffer
            in.nextLine();
        }catch (InputMismatchException E)
        {
            System.out.println("Integral Input is expected ");
            in.nextLine(); // if there was an exception so there should be \n ?
            return ;
        }
        for(int i=0;i<students.size();i++)
        {
            if(students.get(i).getLoginID()==enteredID)
            {
                System.out.println(students.get(i));
            }
        }
        System.out.println("No Such Student Exist! ");
    }

    private boolean updateStudent() {
        // what it will do is update the Student NAME CANT BE / DEPARTMENT YES :) / CGPA YES ALS0
         // ask user to enter Id of Student who's gonna be updated
        System.out.println("Enter Student ID: ");
        Scanner in=new Scanner(System.in);
        int enteredID;
        try
        {
            enteredID=in.nextInt();  // note if it fails then all the -> input remains on buffer
            in.nextLine();
        }catch (InputMismatchException E)
        {
            System.out.println("Integral Input is expected ");
            in.nextLine(); // if there was an exception so there should be \n ?
            return  false;
        }
        // now we have ID of that students who's gonna update
        // why checking in loop bcz if we check by NOS then after removing those with higher ID already Assigned
        for(int i=0;i<students.size();i++)
        {
            if(students.get(i).getLoginID()==enteredID)
            {
                System.out.println(students.get(i));
                // updating {} first showing what actual his depart was and cgpa
                System.out.println("Select what to update ");
                System.out.println("1. Department \n2.CGPA");
                int choice;
                do {
                    choice=in.nextInt();
                    in.nextLine();
                }while (choice!=1 && choice!=2);
                if(choice==1)
                {
                    System.out.println("Select Department\n 1. Computer Science");
                    System.out.println("2. Pharmacy 3. Arts and Design");
                    int sele;
                    do{
                        sele =in.nextInt();
                        in.nextLine();

                    }while(sele !=1 && sele ==2 && sele ==3);
                    students.get(i).setDepart(dpList.get(sele-1));
                }
                else
                {
                    System.out.print("Enter CGPA:");
                    double cp=in.nextFloat();
                    in.nextLine();
                    students.get(i).setCGPA(cp);
                }
            }
        }
        System.out.println("No Such Student Exist! ");
        return  false;
    }

    private boolean removeStudent() {
        // what it will do is it will remove student based on their ID only :)
        System.out.print("Enter Student ID: ");
        Scanner obj= new Scanner(System.in);
        int enteredID;
        try{
            enteredID=obj.nextInt();
            obj.nextLine();

        }catch (InputMismatchException e)
        {
            System.out.println("Only Integral input expected :(");
            obj.nextLine();
            return  false;
        }
        // not implementing any heavy logic but iterating through full ArrayList<Student> students and...
        // cheking id of all if matches then remove
        for(int i=0;i<students.size();i++)
        {
            if(enteredID==students.get(i).getLoginID())
            {
                // that ith is the person has to be removed
                students.remove(i);
                return  true; // actually removed boss
            }
        }
        return false;
    }

    private void addStudent() {
        // it will add students …
        // it will auto provide an ID to student unique
        System.out.print("Enter Student Name");
        Scanner obj=new Scanner(System.in);

        String name= obj.nextLine(); // as left hand side is string so need to implement it on heap as done implicitly
        // which depart?
        System.out.println("Select Department\n 1. Computer Science");
        System.out.println("2. Pharmacy 3. Arts and Design");
        int choice;
        do{
            choice=obj.nextInt();
            obj.nextLine();

        }while(choice!=1 && choice==2 && choice==3);
        Student st=new Student(name,currentStudentID++,dpList.get(choice-1));

        boolean addStatus=students.add(st);
    }

    private void printAdminPanelOptions() {
        System.out.println("1. Add a student");             // Name || depart || password will be default organization provided
        System.out.println("2. Remove a student");
        System.out.println("3. Update a student records");  // like cgpa
        System.out.println("4. Search a student");          // by ID or course
        System.out.println("5. List all Students");         // with ID || Name || cgpa -> ( with departs status showing)
        System.out.println("6. Sort students");             // by Name(lexo ordered) || by ID assigned || by cgpa
        System.out.println("7. print Audit-Log");           // all the information like what student logged in what moment
        System.out.println("8. Account Settings");             // update password && change name && logout
    }

    private Boolean promptUserForLogin() {
        // ask for password
        System.out.println("Enter User ID: ");
        Scanner obj=new Scanner(System.in);
        int enteredID=obj.nextInt();
        obj.nextLine();
        if(enteredID==adminObj.getLoginID())
        {
            System.out.print("Enter Password: ");
            String enteredPassword=obj.nextLine();
            if(adminObj.matchPassword(enteredPassword))
            {
                setLoginType(LoginStatus.ADMIN);
                return true;
            }
        }
        else
        {
            // checking all students is this matches
            // search for if matches with any of existing
            // if yes then set loginType for further proc
            for(int i=0;i<students.size();i++)
            {
                if(students.get(i).getLoginID()==enteredID) {
                    System.out.print("Enter Password: ");
                    obj.nextLine();
                    String enteredPassword = obj.nextLine();
                    if (students.get(i).matchPassword(enteredPassword))
                    {
                        setLoginType(LoginStatus.STUDENT);
                        student_number = i;
                        return true;
                    }
                }
            }
        }

        System.out.println("Invalid Credentials! Try Again");
        LocalDateTime ldt=LocalDateTime.now();

        auditLog.add(ldt.toLocalDate().toString()+" : "+ldt.toLocalTime().toString()+"WRONG CREDENTAILS ADDED WITH ID: "+enteredID);
        return false;
    }
    private void setLoginType(int loginType) {
        this.loginType = loginType;
    }
}
