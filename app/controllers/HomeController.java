package controllers;

import com.helmes.CategoryDAO;

import com.helmes.UserForm;
import com.helmes.User;
import com.helmes.UserDAO;

import play.data.Form;
import play.mvc.*;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    @Inject
    play.data.FormFactory formFactory;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result getCategories() {
        return ok(CategoryDAO.getCategoriesAsJson()).as("application/json");
    }

    public Result saveUserData(Http.Request request) {
        UserForm userForm = formFactory.form(UserForm.class)
            .bindFromRequest(request).get();
        User user = new User(
            userForm.getName(),
            CategoryDAO.get(userForm.getCategoryId()),
            userForm.getAgreeToTerms());
        UserDAO.add(user);
        return ok();
    }

    public Result getUserData(Http.Request request) {
        User user = UserDAO.get(1);
        UserDAO.add(user);
        return ok(user.toJson()).as("application/json");
    }    
}
