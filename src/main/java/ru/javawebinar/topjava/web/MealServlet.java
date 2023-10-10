package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.CollectionMealStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private Storage<Meal> storage;

    @Override
    public void init() throws ServletException {
        storage = new CollectionMealStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        Meal meal = new Meal(DateUtil.parse(dateTime), description, Integer.parseInt(calories));

        final boolean isCreate = id == null || id.isEmpty();
        if (isCreate) {
            storage.create(meal);
            log.debug("New meal with id {} was created", meal.getId());
        } else {
            meal.setId(Integer.parseInt(id));
            storage.update(meal);
            log.debug("Meal with id {} was updated", meal.getId());
        }
        response.sendRedirect("meals");
        log.debug("Redirect to meals from doPost");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idValue = request.getParameter("id");
        String action = request.getParameter("action");

        if (action == null) {
            action = "view";
        }

        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(idValue));
                log.debug("Meal with id {} was deleted", idValue);
                response.sendRedirect("meals");
                log.debug("Redirect to meals from delete case");
                return;
            case "add":
                meal = new Meal(LocalDateTime.now().withSecond(0).withNano(0), "", 0);
                log.debug("Creation new meal");
                break;
            case "edit":
                meal = storage.get(Integer.parseInt(idValue));
                log.debug("Meal with id {} in updating stage", idValue);
                break;
            default:
                List<MealTo> mealToList = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
                request.setAttribute("meals", mealToList);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                log.debug("Redirect to meals from default case");
                return;
        }

        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        log.debug("Redirect to mealEdit");
    }
}
