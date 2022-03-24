package controllers;

import com.helmes.CategoryDAO;

import com.helmes.UserForm;
import com.helmes.User;
import com.helmes.UserDAO;

import play.data.Form;
import play.mvc.*;

import javax.inject.Inject;
import java.util.Optional;

public class HomeController extends Controller {
    @Inject
    play.data.FormFactory formFactory;

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result getCategories() {
        return ok(CategoryDAO.getCategoriesAsJson()).as("application/json");
    }

    private Result addNewUser(
            UserForm userForm,
            Http.Request request) {
        User user = new User(
                userForm.getName(),
                CategoryDAO.get(userForm.getCategoryId()),
                userForm.getAgreeToTerms());
        UserDAO.saveOrUpdate(user);
        return ok().addingToSession(request, "userName", userForm.getName());
    }

    private Result updateExistingUser(
            UserForm userForm,
            User user,
            Http.Request request) {
        user.setName(userForm.getName());
        user.setCategory(CategoryDAO.get(userForm.getCategoryId()));
        user.setAgreeToTerms(userForm.getAgreeToTerms());
        UserDAO.saveOrUpdate(user);
        return ok(user.toJson()).as("application/json")
                .addingToSession(request, "userName", userForm.getName());
    }

    public Result saveUserData(Http.Request request) {
        UserForm userForm = formFactory.form(UserForm.class)
                .bindFromRequest(request).get();
        return request
                .session()
                .get("userName")
                .map(userName -> {
                    Optional<User> user = UserDAO.getByName(userName);
                    return user
                            .map(u -> updateExistingUser(userForm, u, request))
                            .orElseGet(() -> addNewUser(userForm, request));
                })
                .orElseGet(() -> addNewUser(userForm, request));
    }

    public Result getUserData(Http.Request request) {
        return request
                .session()
                .get("userName")
                .map(userName -> {
                    Optional<User> user = UserDAO.getByName(userName);
                    return user
                            .map(u -> ok(u.toJson()).as("application/json"))
                            .orElseGet(() -> ok());
                })
                .orElseGet(() -> ok());
    }
}
