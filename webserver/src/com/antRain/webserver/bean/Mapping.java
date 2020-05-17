package com.antRain.webserver.bean;

import java.util.HashSet;
import java.util.Set;

public class Mapping {
    private String name;
    private Set<String> patterns;

    @Override
    public String toString() {
        return "Mapping [name=" + name + ", patterns=" + patterns + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(Set<String> patterns) {
        this.patterns = patterns;
    }

    public Mapping() {
        this.patterns = new HashSet<>();
    }

    public Mapping(String name, Set<String> patterns) {
        this.name = name;
        this.patterns = patterns;
    }

    public void addPattern(String str) {
        this.patterns.add(str);
    }

}