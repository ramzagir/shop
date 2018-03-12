package practic;

import practic.domain.UsersEntity;
import practic.service.LoginEJB;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable{

    private UsersEntity user = new UsersEntity();
    // для подтверждения пароля
    private String pass;
    // простое сообщение
    private String message;
    // сообщение ошибки
    private String messageError;

    private final String VALIDATOR_MESSAGE = "Не правильное введены данные";
    private final String EMPTY_LOGIN = "Введите значение login";
    private final String EMPTY_PASSWORD = "Введите значение password";
    private final String WRONG_PASSWORD = "Не верное подтверждение пароля";
    private final String USER_IS_EXIST = "Пользователь с таким именем уже существует";
    private final String USER_ISNOT_EXIST = "Пользователя с таким именем не существует";

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

    public String getMessage() {
        return message;
    }

    public String getMessageError() {
        return messageError;
    }

    public String loginUser(){

        // check password
        if(user.getPassword().equals(pass))
            message = "";
        else {
            message = WRONG_PASSWORD;
            return "logIn";
        }

        //check login
        for(UsersEntity u : loginEJB.showUsers()) {
            if (user.equals(u)){
                messageError = USER_IS_EXIST;
                return "loginError";
            }
        }
        loginEJB.addUser(user);
        return "catalog";
    }

    public String siginUser(){
        //если есть пользователь с таким имененм переходим на него
        for(UsersEntity u : loginEJB.showUsers()){
            if(user.equals(u)){
                if(u.getRights().equals("adm"))
                    return "admin/adminPage";
                return "user/catalog";
            }
        }
        // если нет такого пользователя, сообщаем об ошибке
        messageError = USER_ISNOT_EXIST;
        return "loginError";
    }

    public boolean isLogged(){
        for(UsersEntity u : loginEJB.showUsers()){
            if(user.equals(u))
                return true;
        }
        return false;
    }

    public boolean isAdmin(){
        for(UsersEntity u : loginEJB.showUsers()){
            if(user.equals(u)){
                if(u.getRights().equals("adm"))
                    return true;
            }
        }
        return false;
    }
}
