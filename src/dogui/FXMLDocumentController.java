/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dogui;

import app.FileBot;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;

/**
 *
 * @author github.com/polysource
 */
public class FXMLDocumentController implements Initializable {
        
    @FXML
    private Button createFolderBtn;
    @FXML
    private TextField newFolderName;
    @FXML
    private Label statusLB;
    @FXML
    private TreeView<String> allFiles = new TreeView<>();
    
    private TreeItem<String> root = new TreeItem<String>("Desktop ('All Files')");
    
    String[] filesReturned;
    
    
    @FXML
    private TreeView<String> allFolders = new TreeView<>();
    
    private TreeItem<String> folders = new TreeItem<String>("Desktop ('Only folders')");
    
    
    
    @FXML
    private ComboBox<String> extensionsCB = new ComboBox<>();
    @FXML
    private ComboBox<String> destinationCB = new ComboBox<>();
    @FXML
    private Button applyBTN;
    
        
   /* @FXML
    private void sayHello(ActionEvent event) {
        System.out.println("You clicked me!");
        statusLB.setText("Folder created succesfully!");
        
        
        TreeItem<String> item = new TreeItem<String> ("test1");            
            root.getChildren().add(item);
        
        
    }*/
    
    
    @FXML
    private void createFolder(ActionEvent event) 
    {
        String folderName = newFolderName.getText();
        
        File f = new File(System.getProperty("user.home") + "/Desktop/"+folderName);
        if(!f.exists())
        {
            f.mkdir(); 
            
            TreeItem<String> item = new TreeItem<String> (folderName);            
            folders.getChildren().add(item);
            
            TreeItem<String> item2 = new TreeItem<String> (folderName);            
            root.getChildren().add(item2);
            
            
            destinationCB.getItems().add(folderName);
            
            
            statusLB.setTextFill(Color.web("#14a333"));
            statusLB.setText("Folder created succesfully!");
            
            
            
        }
        else
        {
            
            statusLB.setTextFill(Color.web("#d71919"));
            statusLB.setText("Folder already exists!");
            

        }
        
    }
    
    
    
    @FXML
    private void moveFiles(ActionEvent event) throws IOException 
    {
        String opt = extensionsCB.getValue();
        
        String destFolder = destinationCB.getValue();
        
        FileBot fb = new FileBot();
        
        String[] f = fb.getAllFiles();
        
        ArrayList<String> newListFiles = new ArrayList<>();
        
        for(String item : f)
        {
            if(fb.isSameExtension(item, opt))
            {
                newListFiles.add(item);
            }
        }
        
        for(String k: newListFiles)
        {
            
            removeItemFrom(k, root);
            
            Files.move(Paths.get(System.getProperty("user.home") + "/Desktop/" + k), Paths.get(System.getProperty("user.home") + "/Desktop/" + destFolder + "/" + k), REPLACE_EXISTING);
            /*System.out.println(k);
            System.out.println(destFolder);
            Files.delete(Paths.get(System.getProperty("user.home") + "/Desktop/" + k));*/
            
            allFiles.setRoot(null);
            //allFolders.setRoot(null);
            root.getChildren().clear();
            updateFileTreeView(fb, allFiles, root);
            
            
            
        }
        
        //Files.move(Paths.get(System.getProperty("user.home") + "/Desktop/TEXT3000.txt"), Paths.get(System.getProperty("user.home") + "/Desktop/test3000/TEXT3000.txt"), REPLACE_EXISTING);
        
        statusLB.setTextFill(Color.web("#14a333"));
        statusLB.setText("Files moved");
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FileBot fb = new FileBot();
        
        newFolderName.setPromptText("Folder name");
        
        //Initializes Desktop file treeView
        root.setExpanded(true);
        
        /*filesReturned = fb.getAllFiles();
        
        for(int i = 0; i < filesReturned.length; i++)
        {
            TreeItem<String> item = new TreeItem<String> (filesReturned[i]);            
            root.getChildren().add(item);
        }
        allFiles.setRoot(root);*/
        
        
        updateFileTreeView(fb,allFiles,root);
        
        
        //Display TreeView Folders
        folders.setExpanded(true);
        
        
        updateFolderTreeView(fb,allFolders,folders);
        
        
        
        //Load extensions combobox
        
        loadExtensionsComboBox(fb);
        
        
        loadFoldersComboBox(fb);
        
        
    }    

    private void updateFolderTreeView(FileBot fb, TreeView t, TreeItem i) {
        ArrayList<String> allFoldersArray = fb.getAllFolders();
        
        for(String f : allFoldersArray)
        {
            TreeItem<String> item2 = new TreeItem<String>(f);
            i.getChildren().add(item2);
            
        }
        
        t.setRoot(i);
    }

    private void updateFileTreeView(FileBot fb, TreeView t, TreeItem m) {
        filesReturned = fb.getAllFiles();
        
        for(int i = 0; i < filesReturned.length; i++)
        {
            TreeItem<String> item = new TreeItem<String> (filesReturned[i]);
            m.getChildren().add(item);
        }
        /*root.setExpanded(true);*/
        t.setRoot(m);
    }
    
    
    
    public void loadExtensionsComboBox(FileBot f)
    {
        ArrayList<String> list = f.listExtensions();
        
        for(String s : list)
        {
            extensionsCB.getItems().add(s);
        }
        
    }
    
    public void loadFoldersComboBox(FileBot f)
    {
        ArrayList<String> list = f.getAllFolders();
        
        for(String s : list)
        {
            destinationCB.getItems().add(s);
        }
        
    }
    
    
    public void removeItemFrom(String k, TreeItem i)
    {
        int s = i.getChildren().indexOf(i);
        System.out.println(s);
    }
    
    
}
