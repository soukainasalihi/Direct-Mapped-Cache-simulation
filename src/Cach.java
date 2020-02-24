

/*
 * Name : Soukaina salihi 
 * Class: CS472 EX Computer Architecture fall 2017 
 * Date: 10/3/2017
 * project 1
 */
/**
 *
 * @author Soukaina
 */
public class MIPS_DISASSEMBLER {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        // int array that holdes instuctions that needs to be disassambled
        int[] instr = {0xa1020000, 0x810afffc, 0x00831820, 0x01263820, 0x01224820,
            0x81180000, 0x81510010, 0x00624022, 0x00000000, 0x00000000,0x00000000,0x00000000 };
        // assumed starting address 
        int address = 0x9a040;
        // for loop to loop through the 11 instructions and do the apropriate opereration 
        for (int i = 0; i < 11; i++) {
            int Rtype_opCode_bitmask = 0xFC000000;
            // do the tow's complement and shift the instruction by 26 position
            int opCode = (instr[i] & Rtype_opCode_bitmask) >>> 26;

            if (opCode == 0) {
                System.out.print("\n" + Integer.toHexString(address));
                int funct_bitmask = 0x0000003F;
                // do the tow's complement for the function
                int funct = (instr[i] & funct_bitmask);
                if (funct == 0x20) {
                    System.out.print("   add");

                } else if (funct == 0) {
                    System.out.print("   nop");


                } else if (funct == 0x22) {
                    System.out.print("   sub");

                }
                
                

                int Rtype_source_reg_bitmask = 0x03E00000;
                // do the tow's complement and shift the instruction by 21 position 
                int Rtype_source_reg = (instr[i] & Rtype_source_reg_bitmask) >>> 21;

                int Rtype_source_reg2_bitmask = 0x001F0000;
                // do the tow's complement and shift the instruction by 16 position 
                int Rtype_source_reg2 = (instr[i] & Rtype_source_reg2_bitmask) >>> 16;

                int Rtype_dest_reg_bitmask = 0x0000F800;
                //do the tow's complement and shift the instruction by 11 position
                int Rtype_dest_reg = (instr[i] & Rtype_dest_reg_bitmask) >>> 11;

                System.out.print("   $" + Rtype_dest_reg);
                System.out.print(", $" + Rtype_source_reg);
                System.out.print(", $" + Rtype_source_reg2);
                // increamrent the address by 4 
                address += 4;

            } else {

                System.out.print("\n" + Integer.toHexString(address));
                int offSet_bitmask = 0x0000FFFF;
                //do the tow's complement for the instruction to get the offset
                short offSet = (short) (instr[i] & offSet_bitmask);
                int Itype_source_reg_bitmask = 0x03E00000;
                //do the tow's complement and shift the instruction by 21 position
                int Itype_source_reg = (instr[i] & Itype_source_reg_bitmask) >>> 21;
                int Itype_dest_reg_bitmask = 0x001F0000;
                ////do the tow's complement and shift the instruction by 16 position
                int Itype_dest_reg = (instr[i] & Itype_dest_reg_bitmask) >>> 16;
                // check if it is branch equal or branch not equal
                if (opCode == 0x4 || opCode == 0x5) {
                    if (opCode == 0x4) {
                        System.out.print("   beq");

                    } else if (opCode == 0x5) {
                        System.out.print("   bne");

                    }
                    int branch_to_address;
                    branch_to_address = address + 4 + (offSet << 2);
                    System.out.print("   $" + Itype_dest_reg);
                    System.out.print(", $" + Itype_source_reg);
                    System.out.print(", address  " + Integer.toHexString(branch_to_address));
                    // increamrent the address by 4
                    address += 4;
                } else {
                    if (opCode == 0x20) {
                        System.out.print("   lb ");

                    } else if (opCode == 0x28) {
                        System.out.print("   sb ");

                    }
                    System.out.print("   $" + Itype_dest_reg);
                    System.out.print(", " + offSet + "($" + Itype_source_reg + ")");
                    address += 4;

                }
            }
        }
        System.out.println("\n");
    }
}
