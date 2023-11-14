package ru.javawebinar.topjava.web.meal;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealService;

@Controller
public class MealRestController extends AbstractMealController {
    public MealRestController(MealService service) {
        super(service, LoggerFactory.getLogger(MealRestController.class));
    }
}