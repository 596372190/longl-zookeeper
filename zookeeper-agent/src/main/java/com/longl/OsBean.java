package com.longl;

public class OsBean {

    public long lastUpdateTime;
    public String ip;
    public double cpu;

    public long usableMemorySize;
    public long maxMemorySize;

    public OsBean() {
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public long getUsableMemorySize() {
        return usableMemorySize;
    }

    public void setUsableMemorySize(long usableMemorySize) {
        this.usableMemorySize = usableMemorySize;
    }

    public long getMaxMemorySize() {
        return maxMemorySize;
    }

    public void setMaxMemorySize(long maxMemorySize) {
        this.maxMemorySize = maxMemorySize;
    }
}
