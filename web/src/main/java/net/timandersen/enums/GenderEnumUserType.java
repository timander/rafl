package net.timandersen.enums;

import net.timandersen.util.GenericEnumUserType;

public class GenderEnumUserType extends GenericEnumUserType<Gender> {

    public GenderEnumUserType() {
        super(Gender.class, "findByCode", "getCode");
    }

}
