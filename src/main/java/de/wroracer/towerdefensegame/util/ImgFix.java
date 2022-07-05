package de.wroracer.towerdefensegame.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImgFix {
    //Rotate
    public static BufferedImage rotate(BufferedImage img,int angle){
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage newImg = new BufferedImage(w,h,img.getType());
        Graphics2D g2d = newImg.createGraphics();

        g2d.rotate(Math.toRadians(angle),w/2.0,h/2.0);
        g2d.drawImage(img,0,0,null);
        g2d.dispose();

        return newImg;
    }

    //Img layer build

    public static BufferedImage buildImg(BufferedImage ... imgs){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage newImg = new BufferedImage(w,h,imgs[0].getType());
        Graphics2D g2d = newImg.createGraphics();

        for (BufferedImage img : imgs) {
            g2d.drawImage(img,0,0,null);
        }
        g2d.dispose();
        return newImg;
    }

    //Rotate Second img only
    public static BufferedImage buildRotateimg(int angle,int rotAtIndex,BufferedImage ... imgs){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage newImg = new BufferedImage(w,h,imgs[0].getType());
        Graphics2D g2d = newImg.createGraphics();

        for (int i = 0; i < imgs.length; i++){
            if (rotAtIndex == i)
                g2d.rotate(Math.toRadians(angle),w/2.0,h/2.0);
            g2d.drawImage(imgs[i],0,0,null);
            if (rotAtIndex == i)
                g2d.rotate(Math.toRadians(-angle),w/2.0,h/2.0);
        }
        g2d.dispose();
        return newImg;
    }

    //Rotate Second img only + animaton
    public static BufferedImage[] buildRotateimg(BufferedImage[] imgsAni,BufferedImage secondImg,int angle){
        int w = imgsAni[0].getWidth();
        int h = imgsAni[0].getHeight();

        BufferedImage[] arr = new BufferedImage[imgsAni.length];

        for (int i = 0;i < imgsAni.length;i++){
            BufferedImage newImg = new BufferedImage(w,h,imgsAni[0].getType());
            Graphics2D g2d = newImg.createGraphics();
            g2d.drawImage(imgsAni[i],0,0,null);
            g2d.rotate(Math.toRadians(angle),w/2.0,h/2.0);
            g2d.drawImage(secondImg,0,0,null);
            g2d.dispose();
            arr[i] = newImg;
        }

        return arr;
    }

}
