package com.devpatil;
/*
 * CorsFilter.java: Defines a filter to add CORS headers to every response.
Main.java: Applies the CORS filter to all routes using before("*", CorsFilter.applyCors);. 
This ensures that all requests, regardless of the route, will have the CORS headers applied.

*/

import spark.Filter;
import spark.Request;
import spark.Response;

public class CorsFilter {
    public static Filter applyCors = (Request request, Response response) -> {
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.header("Access-Control-Max-Age", "3600");
    };
}
