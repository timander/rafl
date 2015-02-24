package net.timandersen.enums;


public enum Gender {

    MALE("M", "Male"),
    FEMALE("F", "Female");

    private final String code;
    private final String description;

    Gender(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Gender findByCode(String code) {
        System.out.println("Gender.findByCode");
        System.out.println("code = " + code);
        for (Gender g : values()) {
            if (g.getCode().equals(code)) return g;
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
