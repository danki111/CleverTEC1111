package org.example;

//directory to simple writing all console output into file
import org.apache.commons.io.output.TeeOutputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CheckRunner {
    public static void main(String[] args) {

        //safe information from console to buffer
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        OutputStream teeStream = new TeeOutputStream(System.out, buffer);
        System.setOut(new PrintStream(teeStream));

        //create regex expression
        String regex = "Card\\d+";

        String discCard = "0";

        //create checklist from file
        Map<Integer, Integer> goodId = new HashMap<>();
        Scanner sc = null;
        //connecting with file
        try {
            sc = new Scanner(new FileReader("goods.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("NO FILE FOUND");
        }
        //take information from file
        if (sc != null) {
            while (sc.hasNext()) {
                String good = sc.next();
                //searching for discount card in file
                if (good.matches(regex)) {
                    discCard = good;
                } else {
                    String[] tmp = good.split("-");
                    Integer id = Integer.parseInt(tmp[0]);
                    Integer amount = Integer.parseInt(tmp[1]);
                    goodId.put(id, amount);
                }
            }
        }
        //make checklist with discount card or without
        if (!discCard.equals("0")) {
            new CheckMaker(goodId, discCard);
        } else new CheckMaker(goodId);

        //create checklist from commandline
        Map<Integer, Integer> goodId1 = new HashMap<>();
        for (String str : args) {
            //found disccard
            if (str.matches(regex)) {
                discCard = str;
            } else {
                String[] tmp = str.split("-");
                Integer id = Integer.parseInt(tmp[0]);
                Integer amount = Integer.parseInt(tmp[1]);
                goodId1.put(id, amount);
            }
        }
        //make checklist with discount card or without
        if (!discCard.equals("0")) {
            new CheckMaker(goodId1, discCard);
        } else new CheckMaker(goodId);


        //write buffer into file
        try (OutputStream fileStream = new FileOutputStream("output.txt")) {
            buffer.writeTo(fileStream);
        } catch (IOException e) {
            System.err.println("ERROR WRITING INTO FILE");
        }
    }

}
