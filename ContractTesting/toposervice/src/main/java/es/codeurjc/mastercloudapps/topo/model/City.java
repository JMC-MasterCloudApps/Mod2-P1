package es.codeurjc.mastercloudapps.topo.model;

public record City (String id, String landscape) {

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", landscape='" + landscape + '\'' +
                '}';
    }
}