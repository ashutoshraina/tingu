package io.opentracing.extensions.faultinjection;

public class FaultFields {
    private FaultFields() {
    }


    public static String Target = "fault.target";
    public static final String FaultType = "fault.type";
    public static String NoFault = "fault.none";
}

