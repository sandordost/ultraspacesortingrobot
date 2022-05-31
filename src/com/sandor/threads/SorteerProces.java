package com.sandor.threads;

import com.sandor.ArduinoConnector;
import com.sandor.UltraSpaceSortingMachine;
import com.sandor.hardware.InpakRobot;
import com.sandor.hardware.SorteerRobot;
import com.sandor.models.Container;
import com.sandor.models.Order;
import com.sandor.models.Product;

import java.util.ArrayList;

public class SorteerProces extends Thread{
    SerialReader serialReader = UltraSpaceSortingMachine.serialReader;
    SorteerRobot sorteerRobot = UltraSpaceSortingMachine.sorteerRobot;
    InpakRobot inpakRobot = UltraSpaceSortingMachine.inpakRobot;

    private Container[] objective;
    private Container[] currentContainers;

    @Override
    public void run() {
        currentContainers = new Container[objective.length];
        for(int i = 0; i < currentContainers.length; i++){
            currentContainers[i] = new Container(objective[i].getMaxSize());
            currentContainers[i].setProducts(new ArrayList<>());
        }

        while(checkForProducts(objective)) {
            //Sluit alle poorten
            UltraSpaceSortingMachine.inpakRobot.sendBallTo(-1);

            //Zet positie op gate_load
            System.out.println("Open poort voor bal");
            UltraSpaceSortingMachine.sorteerRobot.moveGate(1);

            //Wacht tot bal binnen komt (positie gate_load)
            while (!serialReader.lastMessage.equals("ball_arrived")) {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Bal in poort, bal scannen ...");

            //TODO: Bal is er -> scan bal (positie gate_scanpos)
            sorteerRobot.moveGate(2);
            String colorScanPrefix = "color:";
            while (!serialReader.lastMessage.startsWith(colorScanPrefix)) {
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String ballColor = serialReader.lastMessage.substring(colorScanPrefix.length());
            System.out.println("Kleur van bal gevonden: " + ballColor);

            //TODO bal naar juiste bakje sturen & release (positie gate_release)
            //Bekijk welke container de kleur nog nodig heeft en stuur open poort van die container, verwijder product uit container
            int gotoContainer = getContainerWithProduct(objective, ballColor);
            if (!removeFirstProductWithColor(objective, ballColor)) {
                System.out.println("Er is geen bal meer over");
            }
            System.out.println("Bal moet naar container: " + gotoContainer);

            UltraSpaceSortingMachine.inpakRobot.sendBallTo(gotoContainer);

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Bal doorlaten zodat bal naar container kan ...");
            sorteerRobot.moveGate(3);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Bal is gearriveerd");
            UltraSpaceSortingMachine.status.refreshScreen(objective, currentContainers);
            System.out.println("Geef de huidige gewichtsitatue weer op de segmentdisplays");
            inpakRobot.ShowContainersOnDisplays(currentContainers);
        }
    }

    public void setObjective(Container[] objective) {
        this.objective = objective;
    }

    public Container[] getCurrentContainers() {
        return currentContainers;
    }

    public int getContainerWithProduct(Container[] containers, String color){
        for(int i = 0; i < containers.length; i ++){
            ArrayList<Product> products = containers[i].getProducts();
            for(Product p : products){
                if(p.getColor().equals(color)){
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean removeFirstProductWithColor(Container[] containers, String color){
        for (int i = 0; i < containers.length; i++) {
            ArrayList<Product> products = containers[i].getProducts();
            for (Product p : products) {
                if (p.getColor().equals(color)) {
                    addProductToCurrentContainers(i, p);
                    containers[i].getProducts().remove(p);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkForProducts(Container[] containers){
        for(Container c : containers){
            if(c.getProducts().size() > 0){
                return true;
            }
        }
        return false;
    }

    public void addProductToCurrentContainers(int containerIndex, Product product){
        currentContainers[containerIndex].addProduct(product);
    }
}
