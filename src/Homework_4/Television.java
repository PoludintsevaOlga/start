package Homework_4;

public class Television {
    private String manufacturer; //Имя бренда
    private double screenSize; //размер экрана телевизора
    private int channel; //канал
    private int volume; //громкость
    private boolean powerOn; //включен true, выкелючен - false

    public Television ( String manufacturer,double screenSize,int channel, int volume) {
        this.manufacturer= manufacturer;
        this.screenSize=screenSize;
        this.channel = channel;
        this.volume = volume;
        this.powerOn=false; //телевизор выключен
    }

    //проверяем включение/выключение телевизора
    public void turnOn() {
        powerOn = true;
        System.out.println("Телевизор включен");
    }

    public void turnOff() {
        powerOn = false;
        System.out.println("Телевизор выключен");
    }

    @Override
    public String toString() {
        return "Имя бренда: "+ manufacturer +", размер экрана: "+ screenSize+ ", канал:" +channel
                + ", громкость: "+ volume + ", Состояние телевизора: " + powerOn;
    }
}
