package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;


@SpringBootApplication
@Controller
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    private static final int N = 7;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        logger.info("Application started");
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name, Model model) {
        logger.debug("Request to /hello with name: {}", name);
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/multiply")
    public String showMultiplyForm() {
        logger.debug("Request to /multiply");
        return "multiply";
    }

    @GetMapping("/showDetails")
    public String multiplyNumber(@RequestParam(value = "number", required = true) String numberStr, Model model) {
        logger.debug("Request to /showDetails with number: {}", numberStr);
        try {
            int number = Integer.parseInt(numberStr);
            int result = number * N;
            logger.info("Multiplication result: {}", result);
            model.addAttribute("result", "Результат: " + result);
        } catch (NumberFormatException ex) {
            logger.error("Number format exception for input: {}", numberStr, ex);
            model.addAttribute("error", "Неверный формат числа. Пожалуйста, укажите целое число.");
        }
        return "multiply";
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormatException(NumberFormatException ex, Model model) {
        logger.error("Handling NumberFormatException", ex);
        model.addAttribute("error", "Неверный формат числа. Пожалуйста, укажите целое число для параметра 'number'.");
        return "multiply";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        logger.error("Handling IllegalArgumentException", ex);
        model.addAttribute("error", "Произошла ошибка: " + ex.getMessage());
        return "multiply";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        logger.error("Handling Exception", ex);
        model.addAttribute("error", "Произошла ошибка: " + ex.getMessage());
        return "multiply";
    }
}



