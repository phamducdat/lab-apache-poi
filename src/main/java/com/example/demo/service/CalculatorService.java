package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorService {

    public static double calculateFormula(String formula, Map<String, Double> variables) {
        String postfixExpression = convertToPostfix(formula);
        return evaluatePostfixExpression(postfixExpression, variables);
    }

    private static String convertToPostfix(String formula) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : formula.toCharArray()) {
            if (Character.isWhitespace(c)) {
                continue;
            }

            if (Character.isDigit(c) || Character.isLetter(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                }
            } else {
                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    private static double evaluatePostfixExpression(String postfixExpression, Map<String, Double> variables) {
        Stack<Double> stack = new Stack<>();

        for (char c : postfixExpression.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push((double) (c - '0'));
            } else if (Character.isLetter(c)) {
                double value = variables.getOrDefault(String.valueOf(c), 0.0);
                stack.push(value);
            } else {
                double operand2 = stack.pop();
                double operand1 = stack.pop();

                double result = performOperation(c, operand1, operand2);
                stack.push(result);
            }
        }

        return stack.pop();
    }

    private static int getPrecedence(char operator) {
        if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '+' || operator == '-') {
            return 1;
        } else {
            return 0;
        }
    }

    private static double performOperation(char operator, double operand1, double operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

}



