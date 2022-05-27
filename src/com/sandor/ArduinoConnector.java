package com.sandor;

import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;

public class ArduinoConnector {
    private SerialPort comPorts[] = SerialPort.getCommPorts();
    public static int port = 0;

    public ArduinoConnector() {
        if(comPorts.length > 0) {
            System.out.println("List COM ports");
            for (int i = 0; i < comPorts.length; i++)
                System.out.println("comPorts[" + i + "] = " + comPorts[i].getDescriptivePortName());

            comPorts[port].openPort();
            System.out.println("connected to " + comPorts[port].getDescriptivePortName());
            comPorts[port].setBaudRate(9600);
        }
        else{
            System.out.println("Geen arduino aangesloten");
        }
    }

    public void sendSerialString(String str){
        if(comPorts.length > 0) {
            try {
                byte[] writeBuffer = str.getBytes();
                comPorts[port].writeBytes(writeBuffer, writeBuffer.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String readString() {
        try {
            String nummer =   "1\n";
            byte[] writeBuffer = nummer.getBytes();
            comPorts[port].writeBytes(writeBuffer, writeBuffer.length);
            byte[] readBuffer = new byte[comPorts[port].bytesAvailable()];
            int numRead = comPorts[port].readBytes(readBuffer, readBuffer.length);
            String s = new String(readBuffer, StandardCharsets.UTF_8);
            System.out.println(s);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
