package com.rabbit.dayfilm.common;

public class EndPoint {
    public static final String DELETE = "/delete";

    public static final String USER = "/user";
    public static final String ADDRESS = "/address";
    public static final String CHECK = "/check";
    public static final String CHECK_NICKNAME = CHECK + "/nickname";
    public static final String ADDRESS_DELETE = ADDRESS+DELETE;

    public static final String ITEM = "/items";
    public static final String LIKE = "/likes";
    public static final String REVIEW = "/reviews";
    public static final String PRODUCT = "/products";

    public static final String BASKET = "/basket";
    public static final String ALL = "/all";

    public static final String ORDER = "/order";
    public static final String RESERVE = "/reserve";
    public static final String INFO = "/info";
    public static final String ORDER_INFO = ORDER + INFO;

    public static final String TOSS = "/toss";
    public static final String CREATE = "/create";
    public static final String REDIRECT = "/redirect";
    public static final String CANCEL = "/cancel";
    public static final String REDIRECT_SUCCESS = REDIRECT + "/success";
    public static final String REDIRECT_FAIL = REDIRECT + "/fail";

    public static final String STORE = "/store";
    public static final String DELIVERY = "/delivery";
    public static final String ORDER_COUNT = ORDER +"/count";
    public static final String ORDER_LIST = ORDER + "/list";
    public static final String ORDER_CHECK = ORDER + "/check";
    public static final String ORDER_DELIVERY_UPDATE = ORDER + DELIVERY + "/update";
    public static final String ORDER_DONE = ORDER + "/done";

    public static final String TRACKING = "/track";

    public static final String ITEM_CANCEL = ITEM + "/cancel";
    public static final String ITEM_CANCEL_PROCESS = ITEM_CANCEL + "/process";
}
