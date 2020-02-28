import java.io.*; 
import java.util.*; 
import java.util.Scanner;
import java.util.ArrayList;
class MegaSort { 
    public static void sort(int[] array) {
       MegaSort.sort(array, 10);
    }

    public static void sort(int[] array, int radix) {
        if (array.length == 0) {
            return;
        }

        // Determine minimum and maximum values
        int minValue = array[0];
        int maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            } else if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }

        // Perform counting sort on each exponent/digit, starting at the least
        // significant digit
        int exponent = 1;
        while ((maxValue - minValue) / exponent >= 1) {
            MegaSort.countingSortByDigit(array, radix, exponent, minValue);
            exponent *= radix;
        }
    }

    private static void countingSortByDigit(int[] array, int radix, int exponent, int minValue) {
        int bucketIndex;
        int[] buckets = new int[radix];
        int[] output = new int[array.length];

        // Initialize bucket
        for (int i = 0; i < radix; i++) {
            buckets[i] = 0;
        }

        // Count frequencies
        for (int i = 0; i < array.length; i++) {
            bucketIndex = (int)(((array[i] - minValue) / exponent) % radix);
            buckets[bucketIndex]++;
        }

        // Compute cumulates
        for (int i = 1; i < radix; i++) {
            buckets[i] += buckets[i - 1];
        }

        // Move records
        for (int i = array.length - 1; i >= 0; i--) {
            bucketIndex = (int)(((array[i] - minValue) / exponent) % radix);
            output[--buckets[bucketIndex]] = array[i];
        }

        // Copy back
        for (int i = 0; i < array.length; i++) {
            array[i] = output[i];
        }
    }
    /* Driver function to check for above function sorted was founded from w3resource */
    public static void main(String args[]) throws java.io.IOException {
        int max = 1000000;
        BufferedReader reader;
        int[] arr = new int[1000000];
        try {
            reader = new BufferedReader(new FileReader("1m-ints.txt"));
            String line = reader.readLine();
            int count = 0;
            while (line != null) {
                arr[count] = Integer.parseInt(line);// Stores current line in array
                if (arr[count] > max)// Checks max
                    max = arr[count];
                line = reader.readLine();// Goes to new line
                count++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file!");
            e.printStackTrace();
        }
        sort(arr);
        
        System.out.println(Arrays.toString(arr));
}
}
