/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 *
 * @author github.com/polysource
 */
public class FileBot {
    private String currentDir;
    
    //Uses desktop folder by default
    public FileBot()
    {
        this.currentDir = System.getProperty("user.home") + "/Desktop" ;
    }
    
    public FileBot(String currentDir) {
        this.currentDir = currentDir;
    }

    public String getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }
    
    public String[] getAllFiles()
    {
        File dir = new File(this.currentDir);
       
        String[] files = dir.list();
                 
        return files;
    }
    
    public ArrayList getAllFolders()
    {
        ArrayList<String> allFolders = new ArrayList<>();
        
        File dir = new File(this.currentDir);
       
        String[] files = dir.list();
        
        for(int i = 0; i < files.length; i++)
        {
            String fullPath = this.currentDir + "/" + files[i];
            if(Files.isDirectory(Paths.get(fullPath)))
            {
                allFolders.add(files[i]);
            }
        }
        
        
        
        return allFolders;
    }
    
    public ArrayList<String> listExtensions()
    {
        String[] files = this.getAllFiles();
        
        ArrayList<String> allExtensions = new ArrayList<>();
        
        String extension;
        
        for(int i = 0; i < files.length; i++)
        {
            extension = "";
            int j = files[i].lastIndexOf(".");
            //if(j > 0)
            if(j > 0)
            {
                extension = files[i].substring(j+1);
            }
            
            allExtensions.add(extension);
            
        }
        
        //deletes duplicates
        ArrayList<String> newList = new ArrayList<String>(new LinkedHashSet<String>(allExtensions));
        
        //removes empty strings
        newList.removeAll(Collections.singleton(""));
        
        
        return newList;
    }
    
    
    
    public String getExtension(String f)
    {
        String extension = "";
        int j = f.lastIndexOf(".");
        //if(j > 0)
        if(j > 0)
        {
            extension = f.substring(j+1);
        }
        return extension;
    }
    
    public boolean isSameExtension(String file, String e)
    {
        String ext = this.getExtension(file);
        
        boolean result = new String(ext).equals(e);
        return result;
    }
    
}
