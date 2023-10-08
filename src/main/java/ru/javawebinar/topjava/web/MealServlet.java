package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.ConcurrentHashMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private Storage<Meal> storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = new ConcurrentHashMapStorage();
        MealsUtil.meals.forEach(meal -> storage.save(meal));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        Meal meal = new Meal(DateUtil.parse(dateTime), description, Integer.parseInt(calories));

        final boolean isCreate = id == null || id.length() == 0 || id.equals(String.valueOf(MealsUtil.UNKNOWN_ID));
        if (isCreate) {
            storage.save(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            storage.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealTo> mealToList = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), MealsUtil.CALORIES_PER_DAY);

        String idValue = request.getParameter("id");
        int id = MealsUtil.UNKNOWN_ID;
        if (idValue != null && idValue.length() != 0) {
            id = Integer.parseInt(idValue);
        }
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", mealToList);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case "delete" -> {
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            }
            case "add" -> meal = Meal.EMPTY;
            case "edit" -> meal = storage.get(id);
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }
}
