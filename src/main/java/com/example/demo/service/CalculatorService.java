package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorService {

    public static List<String> extractVariables(String formula) {
        List<String> variables = new ArrayList<>();

        // Regular expression pattern to match variables (alphabetic characters)
        String pattern = "[a-zA-Z]+";
        Pattern variablePattern = Pattern.compile(pattern);
        Matcher matcher = variablePattern.matcher(formula);

        while (matcher.find()) {
            String variable = matcher.group();
            variables.add(variable);
        }

        return variables;
    }

}



