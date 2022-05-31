package com.sandor.hardware;

import com.sandor.ArduinoConnector;
import com.sandor.UltraSpaceSortingMachine;

public class InpakRobot {
    ArduinoConnector connector;

    public InpakRobot(){
        connector = UltraSpaceSortingMachine.arduinoConnector;
    }

    //poort -1 = afal bak
    public void sendBallTo(int gate){
        openGate(gate);
    }

    private void openGate(int gate) {
        if(gate == -1){
            connector.sendSerialString("close_all", ArduinoConnector.inpakPort);
        }
        else {
            connector.sendSerialString("gate" + (gate + 1) + "_open", ArduinoConnector.inpakPort);
        }
    }
}
