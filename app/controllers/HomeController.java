package controllers;

import com.helmes.form.UserForm;
import com.helmes.form.CategoryForm;
import com.helmes.db.CategoryDAO;
import com.helmes.db.Category;
import com.helmes.db.User;
import com.helmes.db.UserDAO;

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

    public Result getCategoriesFlat() {
        return ok(CategoryDAO.getCategoriesFlatAsJson()).as("application/json");
    }

    public Result saveCategory(Http.Request request) {
        CategoryForm categoryForm = formFactory.form(CategoryForm.class)
                .bindFromRequest(request).get();
        Category category = new Category(
            categoryForm.getCategoryId(),
            categoryForm.getParentId(),
            categoryForm.getName());
        CategoryDAO.saveOrUpdate(category);
        return ok();
    }

    public Result deleteCategory(Http.Request request) {
        CategoryForm categoryForm = formFactory.form(CategoryForm.class)
                .bindFromRequest(request).get();
        Category category = new Category(
            categoryForm.getCategoryId(),
            categoryForm.getParentId(),
            categoryForm.getName());
        if (CategoryDAO.delete(category)) {
            return ok();
        } else {
            return forbidden();
        }
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
