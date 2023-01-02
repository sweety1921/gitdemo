package com.umtest.testframe.capability;

import org.openqa.grid.internal.utils.DefaultCapabilityMatcher;

import java.util.Map;

public class CapabilityMatcher extends DefaultCapabilityMatcher {

    private final String deviceName = "deviceName";
    @Override
    public boolean matches(Map nodeCapability, Map requestedCapability) {
        boolean basicChecks = super.matches(nodeCapability, requestedCapability);
        if (! requestedCapability.containsKey(deviceName)){
            //If the user didnt set the custom capability lets just return what the DefaultCapabilityMatcher
            //would return. That way we are backward compatibility and arent breaking the default behavior of the
            //grid
            return basicChecks;
        }
        return (basicChecks && nodeCapability.get(deviceName).equals(requestedCapability.get(deviceName)));
    }
}
