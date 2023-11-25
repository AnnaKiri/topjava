package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.formatter.CustomDateTimeFormat;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MealTo>> getBetweenDateTime(
            @RequestParam(value = "startDate", required = false) @CustomDateTimeFormat(type = CustomDateTimeFormat.Type.START_DATE) LocalDate startDate,
            @RequestParam(value = "startTime", required = false) @CustomDateTimeFormat(type = CustomDateTimeFormat.Type.START_TIME) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) @CustomDateTimeFormat(type = CustomDateTimeFormat.Type.END_DATE) LocalDate endDate,
            @RequestParam(value = "endTime", required = false) @CustomDateTimeFormat(type = CustomDateTimeFormat.Type.END_TIME) LocalTime endTime) {

        List<MealTo> meals = super.getBetween(startDate, startTime, endDate, endTime);
        return ResponseEntity.ok(meals);
    }

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }
}