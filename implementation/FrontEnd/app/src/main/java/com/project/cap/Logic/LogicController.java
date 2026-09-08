package com.project.cap.Logic;

import com.project.cap.Backend.BackendController;
import com.project.cap.Logic.Util.FilterProvider;
import com.project.cap.Logic.Util.UserCache;

/**
 * Singleton controller
 *
 * Only way to access the logic module.
 *
 * */
public class LogicController {

    private static LogicController controller;

    private final FutureGateway futureGateway;
    private final UserCache userCache;
    private final FilterProvider filterProvider;

    public static LogicController getInstance() {
        if(controller != null) return controller;

        controller = new LogicController();
        return controller;
    }

    static BackendController getBackendController() {
        return BackendController.getInstance();
    }

    public FutureGateway getFutureGateway() {
        return futureGateway;
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public FilterProvider getFilterProvider() {
        return filterProvider;
    }

    /**
     * private Constructor (Singleton)
     * Instantiates FutureGateway & UserCache
     *
     */
    private LogicController() {
        userCache = new UserCache();
        futureGateway = new FutureGateway();
        filterProvider = new FilterProvider();
    }
}
