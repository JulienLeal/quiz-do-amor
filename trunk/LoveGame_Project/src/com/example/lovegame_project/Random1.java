package com.example.lovegame_project;

import java.util.Random;
 
public class Random1 {
 private  Random gerador;
    
    public int randomInt()
    {
    	gerador = new Random();
    	return gerador.nextInt(45);
    }
}