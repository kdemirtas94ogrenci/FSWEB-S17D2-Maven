package com.workintech.s17d2.model;

public class Developer {
    private final int id;
    private final String name;
    private final double salary;
    private final Experience experience;

    public Developer(int id, String name, double salary, Experience experience) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public Experience getExperience() {
        return experience;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }
}
