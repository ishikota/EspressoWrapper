package com.ikota.espressowrapper.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface TargetActivity {
    int value();
}
