package TheRealMcrafter.LunaGamesUtils.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;

public class DataIO {
	
	public DataIO() {
        
    }
	
	
    public boolean createGame(String gameName) {
    	File f = new File("plugins/LunaGamesUtils/" + gameName);
        if (!f.exists()) {
            f.mkdir();
            return true;
        }
        return false;
    }
    
    public boolean removeGame(String gameName){
    	File f = new File("plugins/LunaGamesUtils/" + gameName);
        if (!f.exists()) {
            f.delete();
            return true;
        }
        return false;
    }
    
    public boolean createMap(String gameName, String mapName) {
    	try {
    		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/LunaGamesUtils/" + gameName + "/" + mapName + ".yml", true)));
		    out.println("##LunaGamesUtils Data File");
		    out.println("");
		    out.println("## These coordinates are for the map - " + mapName);
		    out.close();
		    
		    return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return false;
    }
    
    public boolean removeMap(String gameName, String mapName){
    	
    	return false;
    }
    
    
    
    public ArrayList<String> listGames(){
    	File file = new File("plugins/LunaGamesUtils/");
    	
    	String[] directories = file.list(new FilenameFilter() {
    	@Override
    	public boolean accept(File current, String name){return new File(current, name).isDirectory();}});
    	
    	ArrayList<String> games = new ArrayList<String>();
    	
    	for (int i = 0; i < directories.length; i++){
    		games.add(directories[i]);
    	}
    	    	
    	return games;
    }
    
    
    
    
    
    public ArrayList<String> listMaps(String gameName){
    	
    	
    	
    	
    	
    	
		return null;
    }
    
    public boolean addCoord(String gameName, String mapName, Location loc) {
    	try {
    		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/LunaGamesUtils/" + gameName + "/" + mapName + ".yml", true)));
		    out.println(loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ());
		    out.close();
		    return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    
    public boolean removeCoord(String gameName, String mapName, int index) {
    	// Load the entire file, store in String Array, remove line in question
    	try {
    		final FileInputStream fstream = new FileInputStream("plugins/LunaGamesUtils/" + gameName + "/" + mapName + ".yml");
            final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            
            int fileIndex = 0;
            ArrayList<String> lines = new ArrayList<String>();
            
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains(":")) {
                	// This is a data line, continue
                	if (fileIndex == index){
                		// This is the line to remove, dont copy it
                    	index++;
                	} else {
                		lines.add(strLine);
                    	index++;
                	}
                } else {
                	lines.add(strLine);
                }
                
            }
            
    		
            File f = new File("plugins/LunaGamesUtils/" + gameName + "/" + mapName + ".yml");
            if (!f.exists()) {
                f.delete();
            }
            
    		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/LunaGamesUtils/" + gameName + "/" + mapName + ".yml", true)));
		    
    		for (int i = 0; i < lines.size(); i++){
    			out.println(lines.get(i));
    		}
		    out.close();
		    return true;
		    
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public void loadMap(String gameName, String mapName) {
        try {
            final FileInputStream fstream = new FileInputStream("plugins/LunaGamesUtils/" + gameName + "/" + mapName + ".yml");
            final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains(":")) {
                    // this is a data line, process it
                	
                	String playerName = strLine.substring(0,strLine.indexOf(":"));
                	strLine = strLine.substring(strLine.indexOf(":") + 1);
                	UUID uuid = UUID.fromString(strLine.substring(0, strLine.indexOf(":")));
                	strLine = strLine.substring(strLine.indexOf(":") + 1);
                	String worldName = strLine.substring(0, strLine.indexOf(":"));
                	strLine = strLine.substring(strLine.indexOf(":") + 1);
                	int x = Integer.valueOf(strLine.substring(0, strLine.indexOf(":")));
                	strLine = strLine.substring(strLine.indexOf(":") + 1);
                	int y = Integer.valueOf(strLine.substring(0, strLine.indexOf(":")));
                	strLine = strLine.substring(strLine.indexOf(":") + 1);
                	int z = Integer.valueOf(strLine);
                	

                }
            }
            br.close();
        }
        catch (IOException ex) {}
    }
    
}
