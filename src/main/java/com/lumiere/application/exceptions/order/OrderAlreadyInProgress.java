package com.lumiere.application.exceptions.order;

import com.lumiere.application.exceptions.base.ApplicationException;

public class OrderAlreadyInProgress extends ApplicationException {

    public OrderAlreadyInProgress() {
        super("There is already an order in progress in this account");

    }

}
