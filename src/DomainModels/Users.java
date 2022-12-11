
package DomainModels;

public class Users {
    private String username;
    private String passwordS;
    private String vaitro;

    public Users( String username, String passwordS, String vaitro, int TrangThai) {
        this.username = username;
        this.passwordS = passwordS;
        this.vaitro = vaitro;
    }

    public Users() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordS() {
        return passwordS;
    }

    public void setPasswordS(String passwordS) {
        this.passwordS = passwordS;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

   
}
