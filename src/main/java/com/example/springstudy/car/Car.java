package com.example.springstudy.car;


public class Car {

  private long carId;
  private String brand;
  private String model;
  private String color;

  public Car(long carId, String brand, String model, String color) {
    this.carId = carId;
    this.brand = brand;
    this.model = model;
    this.color = color;
  }

  public long getCarId() {
    return carId;
  }

  public void setCarId(long carId) {
    this.carId = carId;
  }


  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }


  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }


  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return "Car{" +
            "carId=" + carId +
            ", brand='" + brand + '\'' +
            ", model='" + model + '\'' +
            ", color='" + color + '\'' +
            '}';
  }
}
