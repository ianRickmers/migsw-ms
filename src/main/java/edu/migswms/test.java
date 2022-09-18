package edu.migswms;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
public class test {

    public static void main(String[] args) throws IOException {
        FileReader fr=new FileReader("archives/data/data.txt");
        BufferedReader br=new BufferedReader(fr);
        String data = "";
        String i;    
        while((i=br.readLine())!=null){  
            data += i+'\n';
        }
        br.close();    
        fr.close(); 
        System.out.println(data);
        }
    }
