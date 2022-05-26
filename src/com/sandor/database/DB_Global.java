package com.sandor.database;

public class DB_Global {

    public static int getIdFromString(String stockItemString, char idSeperator){
        String idStr = "";
        for (int i = 0; i < stockItemString.length(); i++){
            if(stockItemString.charAt(i) != idSeperator){
                idStr = idStr + stockItemString.charAt(i);
            }
            else{
                break;
            }
        }
        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
