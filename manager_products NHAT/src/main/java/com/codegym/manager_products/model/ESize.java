package com.codegym.manager_products.model;

public enum ESize {
    M(1, "M"), L(2, "L"), XL(3, "XL");
    private int id;
    private String name;

    ESize(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static ESize findById(int id) {
        for (ESize e : ESize.values()) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
    public static ESize find(String type) {
        for (ESize e : ESize.values()) {
            if (e.toString().equals(type)) {
                return e;
            }
        }
        return null;
    }
}
