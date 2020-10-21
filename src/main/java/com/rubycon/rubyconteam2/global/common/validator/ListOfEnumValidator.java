package com.rubycon.rubyconteam2.global.common.validator;

import com.rubycon.rubyconteam2.global.common.anotation.ListOfEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListOfEnumValidator implements ConstraintValidator<ListOfEnum, List<String>> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ListOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) return true;

        return values.stream()
                .allMatch(e -> acceptedValues.contains(e));
    }
}