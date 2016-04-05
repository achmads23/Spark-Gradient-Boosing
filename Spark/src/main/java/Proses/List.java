/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.util.ArrayList;

/**
 *
 * @author achma
 */
public class List {
    public static void main(String[] args) throws Exception{
        ArrayList<ArrayList<Integer>> array = new ArrayList<ArrayList<Integer>>();
        array.add(new ArrayList<Integer>()); 
        System.out.println(array.size());
        array.get(array.size()-1).add(0,1);
        array.get(0).add(2);
        array.get(0).add(3); 
        array.get(0).add(4); 
        array.get(0).set(3,5); 
        array.add(new ArrayList<Integer>()); 
        System.out.println(array.size());
        array.get(1).add(2); 
        array.get(1).add(3);
        array.get(1).add(4); 
        array.get(1).add(5); 
        System.out.println(array);
        for( int a = 0; a < array.size() ; a++){
            for( int b = 0; b < array.get(a).size() ; b++){
                System.out.println(array.get(a).get(b));
            }
        }
        System.out.println(array.get(0).get(0));
    }
        
}
