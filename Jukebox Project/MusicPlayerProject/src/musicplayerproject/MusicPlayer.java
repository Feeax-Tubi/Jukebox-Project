/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayerproject;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.FloatControl;
import javax.swing.*;

/**
 *
 * @author Feeax
 */
public class MusicPlayer {
    
    long currentFrame; 
    Clip clip; 
    boolean playResume=false;
    
    String status; 
    File file;
    AudioInputStream audioInputStream; 
    String filePath, name; 
    FloatControl gainControl;

    public MusicPlayer()
    {
    }    
   
    public void setStruct() throws Exception
    {
        file = new File(filePath);
        name = getFile().getName();
        
        audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile()); 
        
        clip = AudioSystem.getClip(); 
        
        clip.open(audioInputStream); 
        
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
          
        gainControl.setValue(-20.0f);
        
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
    
    public void VolumeUp(){
        if(gainControl.getValue() > 0.9f && gainControl.getValue() < 5f){
            gainControl.setValue(gainControl.getValue() + 1f);
        }else gainControl.setValue(gainControl.getValue()+5f);
    }
    
    public void VolumeDown(){
        if(gainControl.getValue() < -74.9f && gainControl.getValue() > -78.9f){
            gainControl.setValue(gainControl.getValue() -1f);
        }else gainControl.setValue(gainControl.getValue()-5f);
    }
    
    public void VolumeSet(Float dB){
        gainControl.setValue(dB);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
 
    public void play() throws Exception{  
        clip.start(); 
        status = "play"; 
    } 
      

    public void pause(){ 
        this.currentFrame =  this.clip.getMicrosecondPosition(); 
        clip.stop(); 
        status = "paused"; 
    } 

    public String getStatus() {
        return status;
    }
      

    public void resume() throws Exception{ 
        clip.setMicrosecondPosition(currentFrame); 
        play(); 
    } 
      
    public void restart() throws Exception{ 
        clip.stop(); 
        clip.close(); 
        resetAudioStream(); 
        currentFrame = 0L; 
        clip.setMicrosecondPosition(0); 
        this.play(); 
    } 
    
    public void stop() throws Exception{ 
        currentFrame = 0L; 
        clip.setMicrosecondPosition(currentFrame);
        clip.stop(); 
    }
      

    public void resetAudioStream() throws Exception  
    { 
        audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile()); 
        clip.open(audioInputStream); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    } 
}
