public class Admin  implements  IAdmin{
    private int loginID;
    private  String password;
    private  String name;
    public Admin()
    {
        this.name="admin";
        this.password="admin123";
        loginID=786;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin(String name1)
    {
        this.name=name;
        this.password="admin123";
    }

    @Override
    public Boolean updatePassword(String oldpassword, String newPassword) {
        // if old password matches new password then it will updte it to new
        if(oldpassword.equals(this.password))
        {
            this.password=newPassword;
            return  true;
        }
        return  false;
    }

    public int getLoginID() {
        return loginID;
    }

    @Override
    public Boolean matchPassword(String test) {
//        System.out.println(test.equals(this.password));
       return  test.equals(this.password);
    }
}
