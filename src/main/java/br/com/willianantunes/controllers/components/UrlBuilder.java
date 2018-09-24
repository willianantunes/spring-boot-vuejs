package br.com.willianantunes.controllers.components;

public class UrlBuilder {

    public static final String REQUEST_PATH_API = "/api";

    public static final String REQUEST_PATH_USER_GET_ALL = "/users";
    public static final String REQUEST_PATH_USER_POST_OR_PUT = REQUEST_PATH_USER_GET_ALL;
    public static final String REQUEST_PATH_USER_GET_OR_DELETE = REQUEST_PATH_USER_GET_ALL + "/{id}";
}