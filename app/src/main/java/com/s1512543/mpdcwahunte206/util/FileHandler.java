//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHandler {

    public static void saveFile(String data){
        FileOutputStream fileOutputStream;
        try{
            fileOutputStream = ApplicationContext.getContext().openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(){
        if(!fileExists()){
            return null;
        }
        FileInputStream fileInputStream;
        StringBuilder contents = new StringBuilder();
        try{
            fileInputStream = ApplicationContext.getContext().openFileInput("data.txt");
            InputStreamReader streamReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(streamReader);
            String line;
            while((line = br.readLine()) != null){
                contents.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents.toString();
    }

    public static void deleteFile(){
        File file = new File(ApplicationContext.getContext().getFilesDir(), "data.txt");
        if(file.exists()){
            file.delete();
        }
    }



    public static boolean fileExists(){
        File file = new File(ApplicationContext.getContext().getFilesDir(), "data.txt");
        return file.exists();
    }

}
