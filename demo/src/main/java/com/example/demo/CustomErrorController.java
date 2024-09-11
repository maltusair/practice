package com.example.demo;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorMessage", "Страница не найдена");
                return "error";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorCode", "500");
                model.addAttribute("errorMessage", "Внутренняя ошибка сервера");
                return "error";
            }
        }
        model.addAttribute("errorCode", status.toString());
        model.addAttribute("errorMessage", "Неизвестная ошибка");
        return "error";
    }
}
