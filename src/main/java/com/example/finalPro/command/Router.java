package com.example.finalPro.command;

public class Router {
    private final DispatchType dispatchType;
    private final String targetPath;


    public Router(DispatchType routeType, String targetPath) {
        this.dispatchType = routeType;
        this.targetPath = targetPath;
    }

    public DispatchType getDispatchType() {
        return dispatchType;
    }

    public String getTargetPath() {
        return targetPath;
    }


    public enum DispatchType {

        FORWARD,

        REDIRECT
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Router router = (Router) o;

        if (dispatchType != router.dispatchType) return false;
        return targetPath.equals(router.targetPath);
    }


    @Override
    public int hashCode() {
        int result = dispatchType.hashCode();
        result = 31 * result + targetPath.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "Router{" +
                "routeType=" + dispatchType +
                ", targetPath='" + targetPath + '\'' +
                '}';
    }
}