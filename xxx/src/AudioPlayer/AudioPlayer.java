package AudioPlayer;

import StructuralEntity.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AudioPlayer
{
    private AudioStatus status= AudioStatus.PAUSE;
    private Clip clip;
    private  Long currentFrame;
    private int audioNo=-1;
    private int noOfAudios;
    public Scanner ab;
    private List<Audio> musics;

    public AudioPlayer()
    {
        ab= new Scanner(System.in);
    }
//    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        String filename= "C:\\Users\\SOUMASIS\\Downloads\\S01-Episode-04-Inconsistencies.wav";
//        MyAudioPlayerTest obj= new MyAudioPlayerTest();
//        obj.controller(filename);
//    }
        public void APlayer(List<Audio> audioList) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        audioNo= -1;
        noOfAudios= audioList.size();
        musics= audioList;
        int ch=1;

        do {

            audioNo= (audioNo+1)%noOfAudios;

            Audio a= musics.get(audioNo);
            System.out.println("\n\nNow playing .......'"+a.getName()+"'"+" by "+a.getArtist().getName()+"\n\n");
            String url= a.getUrl();
            int outcome= controller(url);
            if(outcome== 6|| outcome==7 || outcome==8)
                continue;
            if(outcome==9)
                break;
            System.out.print("Enter 1 to continue playing , Enter 2 to exit audio player, Your choice: ");
            ch= ab.nextInt();
        }while (ch==1);

    }
    public int controller(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {

        File f= new File(filename);
        AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(f);
        clip= AudioSystem.getClip();
        clip.open(audioInputStream);

        int action=1;
//        play();
        while (action!=9 && action!=6 && action!=7 && action!=8)
        {
            System.out.println("Enter 1 to play, 2 to pause, 3 to resume, 4 to restart, 5 to jump , 6 for next, 7 for previous, 8 to play particular music, 9 to exit");
            System.out.print("Enter your choice: ");
            action= ab.nextInt();

            if((!clip.isRunning()) && (clip.getMicrosecondPosition()== clip.getMicrosecondLength()))
            {
                return 2;
            }
            switch (action)
            {
                case 1:
                    play();
                    break;
                case 2:
                    pause();
                    break;
                case 3:
                    resume();
                    break;
                case 4:
                    restart();
                    break;
                case 5:
                    jump();
                    break;
                case 6:
                    next();
                    break;
                case 7:
                    previous();
                    break;
                case 8:
                    particularAudio();
                    break;
                case 9:
                    exit();
                    break;
            }
        }
        return action;
    }
    public void play()
    {
        if (status== AudioStatus.PAUSE)
        {
            clip.start();
            status= AudioStatus.PLAY;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

    }
    public void pause()
    {
        if(status== AudioStatus.PAUSE)
        {
            System.out.println("Audio already paused");
            return;
        }
        currentFrame= clip.getMicrosecondPosition();
        clip.stop();
        status= AudioStatus.PAUSE;
    }
    public void resume()
    {
        if (status== AudioStatus.PLAY)
        {
            System.out.println("Audio is playing");
            return;
        }
        clip.setMicrosecondPosition(currentFrame);
        play();

    }
    public void restart()
    {
        if (status== AudioStatus.PLAY)
        {
            pause();
        }
        currentFrame= 0L;
        clip.setMicrosecondPosition(currentFrame);
        play();
    }
    public void jump()
    {
        System.out.print("Jump to position(mm:ss)(0 to "+musics.get(audioNo).getDuration()+")? ");
        String s= ab.next();
        int p= s.indexOf(':');
        long min= Long.parseLong(s.substring(0,p));
        long sec= Long.parseLong(s.substring(p+1,s.length()));
        long pos= (min*60+sec)*1000000L;

        if (pos<0L || pos>= clip.getMicrosecondLength())
            return;
        if (status== AudioStatus.PLAY)
        {
            pause();
        }
        currentFrame= pos;
        clip.setMicrosecondPosition(currentFrame);
        play();


    }
    public void exit()
    {
        if (status== AudioStatus.PLAY)
        {
            pause();
        }
        clip.close();
        currentFrame= 0L;
    }
    public void next()
    {
        exit();
    }
    public void previous()
    {
        if(audioNo==0)
            audioNo= noOfAudios-2;
        else
           audioNo= (audioNo-2)%noOfAudios;

        exit();
    }
    public void particularAudio()
    {
        System.out.print("Enter audio no: ");
        audioNo= ab.nextInt();
        audioNo--;
        exit();
    }
}
