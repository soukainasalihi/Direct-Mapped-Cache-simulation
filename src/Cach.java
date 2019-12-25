
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Soukaina
 */
public class Cach {
  private int slot;
  private int tag;
  private int validBit;
  private int dirtyBit;
  private int [] data;

 
 public Cach(){
  data= new int [16];
 }

    public Cach( int cSlot,int cTag, int cValidBit, int cDirtyBit, int[] cData) {
        
        slot = cSlot;
        tag = cTag;
        validBit = cValidBit;
        dirtyBit = cDirtyBit;
       data = Arrays.copyOf(cData, cData.length);;
    }

    public void setData(int[] cData) {
          
  //data=cData;
      data = Arrays.copyOf(cData, cData.length);

  
    }

    public int[] getData() {
        return data;
    }
  

    public int getTag() {
        return tag;
    }

    public int getSlot() {
        return slot;
    }

    public int getValidBit() {
        return validBit;
    }

    public int getDirtyBit() {
        return dirtyBit;
    }


    public void setTag(int cTag) {
        tag = cTag;
    }

    public void setSlot(int cSlot) {
        slot = cSlot;
    }

    public void setValidBit(int cValidBit) {
        validBit = cValidBit;
    }

    public void setDirtyBit(int cDirtyBit) {
        dirtyBit = cDirtyBit;
    }
 
public void displayCach(ArrayList<Cach> Arr){
    System.out.println("Slot\tTag\tVBit\tDBit\tData");
for(int i=0; i<16; i++){
System.out.print(Integer.toHexString(Arr.get(i).getSlot())+"\t"+
Arr.get(i).getTag()+"\t"+
Arr.get(i).getValidBit()+"\t"+
Arr.get(i).getDirtyBit()+"\t");
for(int x=0; x<16; x++){
System.out.print((Arr.get(i).getData()[x]< 16 ? "0" : "") 
        + Integer.toHexString(Arr.get(i).getData()[x])+" ");
        }
System.out.println();
}



}
  
   
}
