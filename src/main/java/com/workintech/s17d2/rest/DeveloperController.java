package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private final Taxable taxable;
    public  Map<Integer, Developer> developers;

    public Map<Integer, Developer> getDevelopers() {
        return developers;
    }

    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @PostConstruct
    public void init() {
        developers = new HashMap<>();

    }

    @GetMapping
    public List<Developer> getAllDevelopers() {
        return new ArrayList<>(developers.values());
    }

    @GetMapping("/{id}")
    public Developer getDeveloperById(@PathVariable int id) {
        return developers.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Developer addDeveloper(@RequestBody Developer developer) {
        int id = developer.getId();
        String name = developer.getName();
        double salary = developer.getSalary();
        Experience experience = developer.getExperience();

        Developer dev = null;

        switch (experience) {
            case JUNIOR:
                salary = salary - (salary * taxable.getSimpleTaxRate() / 100);
                dev = new JuniorDeveloper(id, name, salary);
                break;
            case MID:
                salary = salary - (salary * taxable.getMiddleTaxRate() / 100);
                dev = new MidDeveloper(id, name, salary);
                break;
            case SENIOR:
                salary = salary - (salary * taxable.getUpperTaxRate() / 100);
                dev = new SeniorDeveloper(id, name, salary);
                break;
            default:
                break;
        }

        developers.put(id, dev);
        return dev;
    }

    @PutMapping("/{id}")
    public Developer updateDeveloper(@PathVariable int id, @RequestBody Developer updatedDeveloper) {
        developers.put(id, updatedDeveloper);
        return updatedDeveloper;
    }

    @DeleteMapping("/{id}")
    public String deleteDeveloper(@PathVariable int id) {
        Developer removed = developers.remove(id);
        if (removed != null) {
            return "Developer with ID " + id + " deleted.";
        } else {
            return "Developer with ID " + id + " not found.";
        }
    }


}

