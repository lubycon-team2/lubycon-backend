package com.rubycon.rubyconteam2.global.common.anotation;

import com.rubycon.rubyconteam2.global.common.validator.ListOfEnumValidator;
import com.rubycon.rubyconteam2.global.common.validator.ValueOfEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ListOfEnumValidator.class)
public @interface ListOfEnum {

    Class<? extends Enum<?>> enumClass();

    String message() default "Contains inappropriate types, must be any of enum {enumClass} ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
