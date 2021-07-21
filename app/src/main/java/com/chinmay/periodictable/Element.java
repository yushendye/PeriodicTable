package com.chinmay.periodictable;

public class Element {
    int atomic_mass;
    String atomic_name;

    public int getAtomic_mass() {
        return atomic_mass;
    }

    public void setAtomic_mass(int atomic_mass) {
        this.atomic_mass = atomic_mass;
    }

    public String getAtomic_name() {
        return atomic_name;
    }

    public void setAtomic_name(String atomic_name) {
        this.atomic_name = atomic_name;
    }

    public Element(int atomic_mass, String atomic_name) {
        this.atomic_mass = atomic_mass;
        this.atomic_name = atomic_name;
    }
}
