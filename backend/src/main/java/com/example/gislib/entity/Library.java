package com.example.gislib.entity;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "LIBRARIES")
public class Library {
    @Id
    @Column(name = "lib_id")
    private Long libId;

    @Column(name = "lib_name")
    private String libName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "open_hours")
    private String openHours;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "lat")
    private Double lat;

    @JdbcTypeCode(SqlTypes.GEOMETRY)
    @Column(name = "location", columnDefinition = "MDSYS.SDO_GEOMETRY")
    private Point location;

    public Long getLibId() { return libId; }
    public void setLibId(Long libId) { this.libId = libId; }
    public String getLibName() { return libName; }
    public void setLibName(String libName) { this.libName = libName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getOpenHours() { return openHours; }
    public void setOpenHours(String openHours) { this.openHours = openHours; }
    public Double getLon() { return lon; }
    public void setLon(Double lon) { this.lon = lon; }
    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }
    public Point getLocation() { return location; }
    public void setLocation(Point location) { this.location = location; }
}
