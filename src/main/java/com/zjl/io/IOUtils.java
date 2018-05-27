package com.zjl.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
	public static List<String> file2List(String path , String reg) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
	    List<String> values = new ArrayList<String>();
	    while(true){
	        String line = reader.readLine();
	        if(line == null){
	            break;
	        }
	        String[] vStrs = line.split(reg);
	        for(String str : vStrs){
	            values.add(str);
	        }
	    }
		return values;
	}
}
