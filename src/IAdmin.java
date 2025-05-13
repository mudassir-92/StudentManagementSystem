public interface IAdmin {

    public  Boolean updatePassword(String oldpassword,String newPassword);
    public  Boolean matchPassword(String test);

}
