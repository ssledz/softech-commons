/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.collection;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class BitArray {

    private interface Command {
        void execute(int index, int bit);
    }
    
    private static final int INT_SIZE = 32;
    private static final int MASK_MOST_SIG_BIT_SET = 1 << (INT_SIZE - 1);
    private static final int MASK_NEXT_MOST_SIG_BIT_SET = 1 << (INT_SIZE - 2);
    private int[] bits;
    private int size;
    
    private Command bitSetCommand = new Command() {

        @Override
        public void execute(int index, int bit) {
            bits[index] |= bit;
        }
    };
    
    private Command bitUnSetCommand = new Command() {

        @Override
        public void execute(int index, int bit) {
             bits[index] &= ~bit;
        }
    };

    public BitArray(int size) {
        bits = new int[1 + (size - 1) / INT_SIZE];
        this.size = size;
    }

    public final long size() {
        return size;
    }

    private int bit2Set(int bitNumber) {
        int shift = bitNumber % INT_SIZE;
        if (shift == 0) {
            return MASK_MOST_SIG_BIT_SET;
        }

        return MASK_NEXT_MOST_SIG_BIT_SET >> (shift - 1);
    }

    private boolean executeBitCommand(int bitNumber, Command command) {
        int index = (int) (bitNumber / INT_SIZE);
        int bit = bit2Set(bitNumber);
        boolean last = (bits[index] & bit) > 0;
        command.execute(index, bit);
        return last;
        
    }
    
    public boolean set(int bitNumber) {
        return executeBitCommand(bitNumber, bitSetCommand);
    }
    
    public boolean unset(int bitNumber) {
        return executeBitCommand(bitNumber, bitUnSetCommand);
    }
    
    private boolean isSet(int bitNumber, int index, int bit) {
        return (bits[index] & bit) != 0;
    }

    public boolean isSet(int bitNumber) {
        int index = (int) (bitNumber / INT_SIZE);
        int bit = bit2Set(bitNumber);
        return isSet(bitNumber, index, bit);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            builder.append(isSet(i) ? "1" : "0");
        }
        return builder.toString();
    }

    public static void main(String[] args) {

        BitArray ba = new BitArray(125);

        System.out.println(ba);
        for (int i = 0; i < 100; i += 2) {
            ba.set(i);
            System.out.println(ba);
        }
        ba.set(124);
        System.out.println(ba);
        
        
        for (int i = 0; i < 100; i += 2) {
            ba.unset(i);
            System.out.println(ba);
        }
        
        ba.unset(124);
        System.out.println(ba);
    }
}
