package com.kanto.gestiondestock.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private Pattern pattern;
    private Matcher matcher;
   // private static final String EMAIL_PATTERN = "\t^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
   private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
           + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public boolean isValid(final String mail, final ConstraintValidatorContext context) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        if (mail == null) {
            return false;
        }
        matcher = pattern.matcher(mail);
        return matcher.matches();
    }
}
