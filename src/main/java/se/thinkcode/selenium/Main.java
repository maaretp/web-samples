package se.thinkcode.selenium;

import se.thinkcode.selenium.actions.buy.currency.Action;
import se.thinkcode.selenium.actions.buy.currency.BuyCurrency;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static spark.Spark.*;
import static spark.SparkBase.stop;

public class Main {
    public static void main(String[] args) {
        int port = getPort();
        port(port);
        staticFileLocation("/public");
        requestNewPassword();
        exchangeRate();
        calculateCurrencyCost();
        awaitInitialization();
    }

    private static int getPort() {
        String port = System.getProperty("port", "8080");
        return Integer.parseInt(port);
    }

    public static void shutdown() {
        stop();
    }

    private static void requestNewPassword() {
        post("/requestPassword", (request, response) -> {
            String account = request.queryParams("account");

            Map<String, String> map = new HashMap<>();
            map.put("account", account);

            return new ModelAndView(map, "password_confirmation.mustache");
        }, new MustacheTemplateEngine());
    }

    private static void exchangeRate() {
        post("/exchangeRate", (request, response) -> {
            String from = request.queryParams("from");
            String to = request.queryParams("to");

            String sleep = slowResponse(10);

            Map<String, String> map = new HashMap<>();
            map.put("sleep", sleep);
            map.put("from", from);
            map.put("to", to);
            map.put("exchangeRate", "2.07");


            return new ModelAndView(map, "exchange_rate.mustache");
        }, new MustacheTemplateEngine());
    }

    private static String slowResponse(int maxSeconds) {
        Random random = new Random();
        return "" + random.nextInt(maxSeconds * 1000);
    }

    private static void calculateCurrencyCost() {
        post("/calculateCurrencyCost", (request, response) -> {
            String actionStr = request.queryParams("action");
            String toCurrencyStr = request.queryParams("toCurrency");
            String amountStr = request.queryParams("amount");
            String fromCurrencyStr = request.queryParams("fromCurrency");

            Action action = new Action(actionStr);
            int amount = Integer.parseInt(amountStr);
            Currency to = Currency.getInstance(toCurrencyStr);
            Currency from = Currency.getInstance(fromCurrencyStr);

            BuyCurrency buyCurrency = new BuyCurrency(action, amount, to, from);
            int cost = buyCurrency.getCost();

            Map<String, String> map = new HashMap<>();
            map.put("amount", "" + amountStr);
            map.put("toCurrency", "" + toCurrencyStr);
            map.put("cost", "" + cost);
            map.put("fromCurrency", fromCurrencyStr);

            return new ModelAndView(map, "buy_currency.mustache");
        }, new MustacheTemplateEngine());
    }
}
