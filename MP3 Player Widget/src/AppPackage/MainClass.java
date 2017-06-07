package AppPackage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MainClass {
    FileInputStream fis;
    BufferedInputStream bis;
    public Player player;
    public long pauseLocation,songTotal;
    public String song;
    
    public void Stop(){
        if(player!=null){
            player.close();
            pauseLocation=songTotal=0;
            MP3PlayerGUI.Display.setText("ANKS wants you to select a song");
        }
    }
    
    public void Pause(){
        if(player!=null){
            try {
                pauseLocation= fis.available();
                player.close();
                MP3PlayerGUI.Display.setText("Don't stop the fun!!");
            }
            catch (IOException ex) {
                
            }
            
        }
    }
    
    public void Play(String path) {
        try {
            fis= new FileInputStream(path);
            bis= new BufferedInputStream(fis);
            player= new Player(bis);
            try {
                songTotal=fis.available();
                song=path+"";
            }
            catch (IOException ex) {
                
            }
        }
        catch (FileNotFoundException | JavaLayerException ex) {
            
        }
        new Thread(){
            @Override
            public void run(){
                try {
                    player.play();
                    if(player.isComplete() && MP3PlayerGUI.loop){
                        Play(song);
                    }
                    if(player.isComplete()){
                        MP3PlayerGUI.Display.setText("ANKS wants you to select a song");
                    }
                } 
                catch (JavaLayerException ex) {
                    
                }
            }
        }.start();
    }
    
    public void Resume() {
        try {
            fis= new FileInputStream(song);
            bis= new BufferedInputStream(fis);
            player= new Player(bis);
            try {
                fis.skip(songTotal-pauseLocation);
            }
            catch (IOException ex) {
               
            }
        }
        catch (FileNotFoundException | JavaLayerException ex) {
            
        }
        new Thread(){
            @Override
            public void run(){
                try {
                    MP3PlayerGUI.Display.setText(MP3PlayerGUI.name);
                    player.play();
                } 
                catch (JavaLayerException ex) {
                    
                }
            }
        }.start();
    }
}
