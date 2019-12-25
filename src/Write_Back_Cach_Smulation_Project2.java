
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Soukaina
 */
public class Write_Back_Cach_Smulation_Project2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Cach ccach = new Cach();
        ArrayList<Cach> cachArr = new ArrayList(16);
        int[] cData = new int[16];
        int[] blockArr = new int[16];
        char[] operationsArr = {'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R',
            'R', 'D', 'W', 'W', 'R', 'D','R','R','D','R','R','R','R','D'};
        int[] addressArr = {0x5, 0x6, 0x7, 0x14c, 0x14d, 0x14e, 0x14f, 0x150,
            0x151, 0x3a6, 0x4c3, 0x0, 0x14c, 0x63b, 0x582, 0x0,0x348,0x3f,0x0,0x14b,0x14c,0x63f,0x83,0x0};
        int[] valueArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x99, 0x7, 0, 0,0,0,0,0,0,0,0,0};

        for (int i = 0; i < 16; i++) {

            cachArr.add(new Cach(i, 0, 0, 0, cData));

        }
        
        int[] main_Mem = new int[2048];

        for (int i = 0, x = 0; i < 2048; i++, x++) {
            if (x == 256) {
                x = 0;
            }
            main_Mem[i] = x;
        }
        for (int op = 0; op < 24; op++) {
            System.out.println("\n(R)ead, (W)rite, or (D)isplay Cache?");
            System.out.println(Character.toUpperCase(operationsArr[op]));

            int address = addressArr[op];
            int Block_offset = address & 0x0000000F;
            int Block_beging_addr = address & 0x0000FFf0;
            int Tag = address >>> 8;
            int slot = (address & 0x000000f0) >>> 4;
            int hit = 0;

            switch (Character.toUpperCase(operationsArr[op])) {
                case 'D':
                    ccach.displayCach(cachArr);
                    break;

                case 'R':
                    System.out.println("What address Would you like to read?");
                    System.out.println(Integer.toHexString(addressArr[op]));

                    if (cachArr.get(slot).getTag() == Tag && cachArr.get(slot).getValidBit() == 1) {
                        hit = 1;
                    }
                    if (cachArr.get(slot).getTag() != Tag || cachArr.get(slot).getValidBit() != 1) {
                        hit = 0;
                        if (cachArr.get(slot).getDirtyBit() == 0) {
                            for (int i = 0; i < 16; i++) {
                                blockArr[i] = main_Mem[Block_beging_addr + i];
                            }

                            cachArr.set(slot, new Cach(slot, Tag, 1, 0, blockArr));
                        }
                     else {
                        // copy curent block in cach to main memory
                        int Beging_Addr_Cach = (cachArr.get(slot).getTag() << 8) + (slot << 4);
                        for (int i = 0; i < 16; i++) {
                            main_Mem[Beging_Addr_Cach + i] = cachArr.get(slot).getData()[i];
                        }
                        // get the new blolck from memory and save it to the cach
                        for (int i = 0; i < 16; i++) {
                            blockArr[i] = main_Mem[Block_beging_addr + i];
                        }
                        cachArr.set(slot, new Cach(slot, Tag, 1, 0, blockArr));

                    }
                    }

                    System.out.println("At that byte there is the value "
                            + Integer.toHexString(cachArr.get(slot).getData()[Block_offset])
                            + " (Cach " + ((hit == 1) ? "hit)" : "miss)"));
                    break;

                case 'W':
                    System.out.println("What value Would you like to write?");
                    System.out.println(Integer.toHexString(valueArr[op]));
                    System.out.println("What address Would you like to write to?");
                    System.out.println(Integer.toHexString(addressArr[op]));
                    if (cachArr.get(slot).getTag() == Tag && cachArr.get(slot).getValidBit() == 1) {
                        hit = 1;

                        cachArr.get(slot).setDirtyBit(1);
                        cachArr.get(slot).getData()[Block_offset] = valueArr[op];
                    }
                    System.out.println("The value " +Integer.toHexString(valueArr[op])
                            +" was written to the address " +Integer.toHexString(addressArr[op])+ " (Cache "
                            + ((hit == 1) ? "hit)" : "miss)"));
                    if (cachArr.get(slot).getTag() != Tag && cachArr.get(slot).getValidBit() != 1) {
                        hit = 0;

                        if (cachArr.get(slot).getDirtyBit() == 0) {
                            for (int i = 0; i < 16; i++) {
                                blockArr[i] = main_Mem[Block_beging_addr + i];
                            }

                            cachArr.set(slot, new Cach(slot, Tag, 1, 1, blockArr));
                            cachArr.get(slot).getData()[Block_offset] = valueArr[op];
                        } else {
                            // copy curent block in cach to main memory
                            int Beging_Addr_Cach = (cachArr.get(slot).getTag() << 8) + (slot << 4);
                            for (int i = 0; i < 16; i++) {
                                main_Mem[Beging_Addr_Cach + i] = cachArr.get(slot).getData()[i];
                            }
                            // get the new blolck from memory and save it to the cach
                            for (int i = 0; i < 16; i++) {
                                blockArr[i] = main_Mem[Block_beging_addr + i];
                            }
                            cachArr.set(slot, new Cach(slot, Tag, 1, 0, blockArr));

                        }

                    }

                    break;
            }
            
        }

    }
}
