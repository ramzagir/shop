package practic;

import practic.domain.UsersEntity;
import practic.service.LoginEJB;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
/**
 * Исправить логин и регистрацию
 */
public class LoginBean implements Serializable {

    private UsersEntity user = new UsersEntity();
    // для подтверждения пароля
    private String pass;

    private String requestedPage;

    private static final String VALIDATOR_MESSAGE = "Не правильное введены данные";
    private static final String EMPTY_LOGIN = "Введите значение login";
    private static final String EMPTY_PASSWORD = "Введите значение password";
    private static final String WRONG_PASSWORD = "Не верное подтверждение пароля";
    private static final String USER_IS_EXIST = "Пользователь с таким именем уже существует";
    private static final String USER_ISNOT_EXIST = "Пользователя с таким именем или паролемм не существует";

    public String getVALIDATOR_MESSAGE() {
        return VALIDATOR_MESSAGE;
    }

    public String getEMPTY_LOGIN() {
        return EMPTY_LOGIN;
    }

    public String getEMPTY_PASSWORD() {
        return EMPTY_PASSWORD;
    }

    @EJB
    private LoginEJB loginEJB;

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRequestedPage(String requestedPage) {
        this.requestedPage = requestedPage;
    }

    public String loginUser() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();

        // check password
        if (!user.getPassword().equals(pass)) {
            context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    WRONG_PASSWORD, WRONG_PASSWORD));
            return null;
        }

        //check login
        if (loginEJB.findUser(user) != null) {
            context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    USER_IS_EXIST, USER_IS_EXIST));
            return null;
        }
        loginEJB.addUser(user);
        //возвращаемся на исходную страницу
        //context.getExternalContext().redirect(requestedPage);

        return "catalog";
    }

    public String siginUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        UsersEntity u = loginEJB.findUser(user);

        if (u != null && u.getPassword().equals(user.getPassword())) {
            if (u.getRights().equals("adm"))
                return "admin/adminPage";
            return "catalog.xhtml?faces-redirect=true";
        } else {
            context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    USER_ISNOT_EXIST, USER_ISNOT_EXIST));
            return null;
        }
    }

    public boolean isLogged() {
        return  loginEJB.findUser(user) != null;
    }

    public boolean isAdmin() {
        return loginEJB.findUser(user).getRights().equals("adm");
    }

    public String isSignInUser(UsersEntity user) {
        if (user == null) {
            return "Войти";
        } else return user.getLogin();
    }

    public String signOut(){
        user = new UsersEntity();
        return "";
    }
}
