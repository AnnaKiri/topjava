package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.ConcurrentHashMapStorage;
import ru.javawebinar.topjava.storage.Storage;
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

    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = new ConcurrentHashMapStorage();
        MealsUtil.meals.forEach(meal -> storage.save(meal));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealTo> mealToList = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), MealsUtil.CALORIES_PER_DAY);

        request.setAttribute("meals", mealToList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
