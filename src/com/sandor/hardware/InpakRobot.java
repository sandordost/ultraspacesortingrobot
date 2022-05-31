package com.sandor.hardware;

import com.sandor.ArduinoConnector;
import com.sandor.UltraSpaceSortingMachine;
import com.sandor.models.Container;

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

    public void ShowContainersOnDisplays(Container[] containers){
        int[] currentWeights = new int[containers.length];
        for(int i = 0; i < containers.length; i++){
            currentWeights[i] = containers[i].getTotalWeight();
        }
        sendContainerWeights(currentWeights);
    }

    private void sendContainerWeights(int[] weights){
        connector.sendSerialString(String.format("weight:%s,%s,%s", weights[0], weights[1], weights[2]), ArduinoConnector.inpakPort);
    }
}
