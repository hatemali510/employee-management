package com.stc.employee_management.helper;




import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class InputValidationHelper {


    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final List<String> DEPARTMENT_LIST= List.of("Engineering","Accounting");


    public static final Predicate<String> isValidEmail = email ->
            email!=null &&
                    VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches();

    public static final Predicate<String> isValidDepartment = department ->
            department!=null &&
                    DEPARTMENT_LIST.contains(department);


}
