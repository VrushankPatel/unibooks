package com.omexy.unibooks.eventhandler;

import java.util.List;

public interface CustomerListener {
    void customersUpdated(List<String> customerNames);
} 