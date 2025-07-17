package io.nawatech.erp.domain.audit.entitychange;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackEntityChange {}
