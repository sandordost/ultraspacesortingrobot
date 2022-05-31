package com.sandor;

import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;

public class ArduinoConnector {
    private SerialPort comPorts[] = SerialPort.getCommPorts();
    public static int sorteerPort = 1;
    public static int inpakPort = 0;

    public ArduinoConnector() {
        for(int i = 0; i < 2; i ++) {
            System.out.println("List COM ports");
            for (int i2 = 0; i2 < comPorts.length; i2++)
                System.out.println("comPorts[" + i2 + "] = " + comPorts[i2].getDescriptivePortName());

            if (arduinoIsAvailable()) {
                comPorts[i].openPort();
                System.out.println("connected to " + comPorts[i].getDescriptivePortName());
                comPorts[i].setBaudRate(1200);
                comPorts[i].setNumStopBits(SerialPort.ONE_STOP_BIT);
                comPorts[i].setParity(SerialPort.NO_PARITY);
                comPorts[i].setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
            }
        }
    }

    public void sendSerialString(String str, int port){
        try {
            byte[] writeBuffer = str.getBytes();
            comPorts[port].writeBytes(writeBuffer, writeBuffer.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean arduinoIsAvailable(){
        return comPorts.length > 0;
    }

    public String readString() {
        if(arduinoIsAvailable()) {
            try {
                String nummer = "";
                byte[] writeBuffer = nummer.getBytes();
                comPorts[sorteerPort].writeBytes(writeBuffer, writeBuffer.length);
                byte[] readBuffer = new byte[comPorts[sorteerPort].bytesAvailable()];
                int numRead = comPorts[sorteerPort].readBytes(readBuffer, readBuffer.length);
                return new String(readBuffer, StandardCharsets.UTF_8)
                        .replace("\n", "").replace("\r", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
