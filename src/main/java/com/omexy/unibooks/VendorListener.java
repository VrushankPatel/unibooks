package com.omexy.unibooks;

import java.util.List;

/**
 * Listener interface for vendor updates.
 * 
 * @purpose Notify panels of vendor changes.
 */
public interface VendorListener {
    void vendorsUpdated(List<String> vendorNames);
} 