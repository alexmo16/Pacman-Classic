package com.pacman.utils;

import java.awt.Image;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class SpriteUtils
{
    public static Image getSpritesSheetImage(String spritesSheetFilePath)
    {
        Image spritesSheet = null;
        try
        {
            if (Files.exists(Paths.get(spritesSheetFilePath)))
            {
            	spritesSheet = new ImageIcon(spritesSheetFilePath).getImage();
                if (spritesSheet.getHeight(null) == -1)
                {
                    throw new Exception("Empty image");
                }
            } else
            {
                throw new Exception("Couldn't find file: " + spritesSheetFilePath);
            }
        } catch (Exception e)
        {
            spritesSheet = null;
            System.out.println("Exception in getSpritesSheetImage : " + e.toString());
        }

        return spritesSheet;
    }

    public static int[][] getSpritesCoordsFromSheet(Image spritesSheet, int blockSize)
    {
        int[][] spritesCoords = null;
        try
        {
            if (spritesSheet == null)
            {
                throw new Exception("Image is null");
            } else if (spritesSheet.getHeight(null) % blockSize != 0)
            {
                throw new Exception("Block size " + blockSize + " is not compatible with sprites sheet height "
                        + spritesSheet.getHeight(null));
            } else if (spritesSheet.getWidth(null) % blockSize != 0)
            {
                throw new Exception("Block size " + blockSize + " is not compatible with sprites sheet width "
                        + spritesSheet.getWidth(null));
            }

            int xSize = spritesSheet.getWidth(null) / blockSize;
            int ySize = spritesSheet.getHeight(null) / blockSize;
            spritesCoords = new int[xSize * ySize][4];

            for (int y = 0; y < ySize; y++)
            {
                for (int x = 0; x < xSize; x++)
                {
                    spritesCoords[x + y * xSize][0] = x * blockSize;
                    spritesCoords[x + y * xSize][1] = y * blockSize;
                    spritesCoords[x + y * xSize][2] = x * blockSize + blockSize;
                    spritesCoords[x + y * xSize][3] = y * blockSize + blockSize;
                    // System.out.println(spritesCoords[x + y * xSize][0] + " " + spritesCoords[x +
                    // y * xSize][1] + " " + spritesCoords[x + y * xSize][2] + " " + spritesCoords[x
                    // + y * xSize][3]);
                }
            }
        } catch (Exception e)
        {
            spritesCoords = null;
            System.out.println("Exception in getSpritesCoords : " + e.toString());
        }

        return spritesCoords;
    }
}
