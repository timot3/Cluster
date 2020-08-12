package com.example.cluster;

public class ClusterSortContainer {
    private String clusterName;
    private String clusterID;

    public ClusterSortContainer(String clusterName, String clusterID) {
        this.clusterName = clusterName;
        this.clusterID = clusterID;
    }

    public ClusterSortContainer() {
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterID() {
        return clusterID;
    }

    public void setClusterID(String clusterID) {
        this.clusterID = clusterID;
    }
}
