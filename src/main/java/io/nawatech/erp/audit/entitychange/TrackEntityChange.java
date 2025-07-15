package io.nawatech.erp.audit.entitychange;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackEntityChange {}
