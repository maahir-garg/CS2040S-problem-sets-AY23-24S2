import java.util.Random;

public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        Random rnd = new Random();
        if (size == 1) {
            return true;
        } else if (size < 1) {
            return false;
        }

        KeyValuePair[] reverse = new KeyValuePair[size];
        KeyValuePair[] only_3_4 = new KeyValuePair[size];
        KeyValuePair[] rand = new KeyValuePair[size];
        KeyValuePair[] all_but_1 = new KeyValuePair[size];

        for (int i = 0; i < size; i++) {
            reverse[i] = new KeyValuePair(size-i, 20);
            if (i%2 == 0) {
                only_3_4[i] = new KeyValuePair(3, 20);
            } else {
                only_3_4[i] = new KeyValuePair(4, 20);
            }
            rand[i] = new KeyValuePair(rnd.nextInt(100), 20);
            if (i == size/2) {
                all_but_1[i] =  new KeyValuePair(2, 20);;
            } else {
                all_but_1[i] =  new KeyValuePair(1, 20);;
            }
        }
        long sortCost_1 = sorter.sort(reverse);
        long sortCost_2 = sorter.sort(only_3_4);
        long sortCost_3 = sorter.sort(rand);
        long sortCost_4 = sorter.sort(all_but_1);

        for (int j = 0; j < size - 1; j++) {
            if (reverse[j].compareTo(reverse[j+1]) == 1) {
                return false;
            }
            if (only_3_4[j].compareTo(only_3_4[j+1]) == 1) {
                return false;
            }
            if (rand[j].compareTo(rand[j+1]) == 1) {
                return false;
            }
            if (all_but_1[j].compareTo(all_but_1[j+1]) == 1) {
                return false;
            }
        }

        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        Random rnd = new Random();
        if (size == 1) {
            return true;
        } else if (size < 1) {
            return false;
        }

        KeyValuePair[] reverse = new KeyValuePair[size];
        KeyValuePair[] only_3_4 = new KeyValuePair[size];
        KeyValuePair[] rand = new KeyValuePair[size];
        KeyValuePair[] all_but_1 = new KeyValuePair[size];
        KeyValuePair[] same_val = new KeyValuePair[size];

        for (int i = 0; i < size; i++) {
            reverse[i] = new KeyValuePair(size-i, i);
            if (i%2 == 0) {
                only_3_4[i] = new KeyValuePair(3, i);
            } else {
                only_3_4[i] = new KeyValuePair(4, i);
            }
            rand[i] = new KeyValuePair(rnd.nextInt(1000), i);
            if (i == size/2) {
                all_but_1[i] =  new KeyValuePair(2, 20);;
            } else {
                all_but_1[i] =  new KeyValuePair(1, 20);;
            }
            same_val[i] = new KeyValuePair(1, i);
        }

        long sortCost_1 = sorter.sort(reverse);
        long sortCost_2 = sorter.sort(only_3_4);
        long sortCost_3 = sorter.sort(rand);
        long sortCost_4 = sorter.sort(all_but_1);
        long sortCost_5 = sorter.sort(same_val);

        for (int j = 0; j < size - 1; j++) {
            if (reverse[j].compareTo(reverse[j+1]) == 1) {
                return false;
            }
            if (only_3_4[j].compareTo(only_3_4[j+1]) == 1) {
                return false;
            }
            if (rand[j].compareTo(rand[j+1]) == 1) {
                return false;
            }
            if (all_but_1[j].compareTo(all_but_1[j+1]) == 1) {
                return false;
            }
            if (same_val[j].compareTo(same_val[j+1]) == 1) {
                return false;
            }
            if (same_val[j].getValue() > same_val[j+1].getValue()) {
                return false;
            }
            if ((only_3_4[j].getKey() == only_3_4[j+1].getKey()) && (only_3_4[j].getValue() > only_3_4[j+1].getValue())) {
                return false;
            }
            if ((rand[j].getKey() == rand[j+1].getKey()) && (rand[j].getValue() > rand[j+1].getValue())) {
                return false;
            }
            if ((all_but_1[j].getKey() == all_but_1[j+1].getKey()) && (all_but_1[j].getValue() > all_but_1[j+1].getValue())) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        testing_check();
    }



    public static void testing_stable () {
        boolean sor_A = isStable(new SorterA(), 1000);
        boolean sor_B = isStable(new SorterB(), 1000);
        boolean sor_C = isStable(new SorterC(), 1000);
        boolean sor_D = isStable(new SorterD(), 1000);
        boolean sor_E = isStable(new SorterE(), 1000);
        boolean sor_F = isStable(new SorterF(), 1000);

        System.out.println(sor_A);
        System.out.println(sor_B);
        System.out.println(sor_C);
        System.out.println(sor_D);
        System.out.println(sor_E);
        System.out.println(sor_F);
    }

    public static void testing_check() {

        boolean sor_A = checkSort(new SorterA(), 30000);
        boolean sor_B = checkSort(new SorterB(), 30000);
        boolean sor_C = checkSort(new SorterC(), 30000);
        boolean sor_D = checkSort(new SorterD(), 30000);
        boolean sor_E = checkSort(new SorterE(), 30000);
        boolean sor_F = checkSort(new SorterF(), 30000);

        System.out.println(sor_A);
        System.out.println(sor_B);
        System.out.println(sor_C);
        System.out.println(sor_D);
        System.out.println(sor_E);
        System.out.println(sor_F);

    }

    public static void D_or_E() {
        long sum = 0;
        for (int i = 0; i < 50; i++) {
            sum += is_selection();
        }
        System.out.println(sum/50);
        // for Sorter D (value | n)
        // 38432230 | 1000
        // 380464 | 100
        // 4120 | 10
        // each is a factor of 100 to the other,so it is selection O(n^2)

        // for Sorter E (value | n)
        // 2285610 | 1000
        // 206017 | 100
        // 12318 | 10
        // each is a factor of 10 to the other,so it is quick O(nlog(n))
    }


    public static long is_selection() {
        Random rnd = new Random();
        KeyValuePair[] rand = new KeyValuePair[10];


        for (int i = 0; i < 10; i++) {
            rand[i] = new KeyValuePair(rnd.nextInt(1000), i);
        }

        //Create a Sorter
        ISort sortingObject_1 = new SorterD();
        ISort sortingObject_2 = new SorterE();

        // Do the sorting
        long random_1 = sortingObject_1.sort(rand);
        long random_2 = sortingObject_2.sort(rand);

//        System.out.println("Sort Cost Random 1: " + random_1);
        System.out.println("Sort Cost Random 2: " + random_2);

//        return random_1;
        return random_2;
    }

    public static void is_merge() {
        Random rnd = new Random();
        int size = 100;

        ISort sortingObject_A = new SorterA();
        ISort sortingObject_C = new SorterC();
        ISort sortingObject_F = new SorterF();

        long avgA_rand = 0;
        long avgC_rand = 0;
        long avgF_rand = 0;
        long avgA_inc = 0;
        long avgC_inc = 0;
        long avgF_inc = 0;
        long avgA_dec = 0;
        long avgC_dec = 0;
        long avgF_dec = 0;


        for (int j = 0 ; j < 5000; j++) {
            KeyValuePair[] rand_A = new KeyValuePair[size];
            KeyValuePair[] rand_C = new KeyValuePair[size];
            KeyValuePair[] rand_F = new KeyValuePair[size];
            KeyValuePair[] inc_A = new KeyValuePair[size];
            KeyValuePair[] inc_B = new KeyValuePair[size];
            KeyValuePair[] inc_C = new KeyValuePair[size];
            KeyValuePair[] dec_A = new KeyValuePair[size];
            KeyValuePair[] dec_C = new KeyValuePair[size];
            KeyValuePair[] dec_F = new KeyValuePair[size];
            for (int i = 0; i < size; i++) {
                rand_A[i] = new KeyValuePair(rnd.nextInt(1000), i);
                rand_C[i] = new KeyValuePair(rnd.nextInt(1000), i);
                rand_F[i] = new KeyValuePair(rnd.nextInt(1000), i);
                inc_A[i] = new KeyValuePair(i, i);
                inc_B[i] = new KeyValuePair(i, i);
                inc_C[i] = new KeyValuePair(i, i);
                dec_A[i] = new KeyValuePair(size-i , i);
                dec_C[i] = new KeyValuePair(size-i , i);
                dec_F[i] = new KeyValuePair(size-i , i);
            }
            inc_A[size-1] = new KeyValuePair(-1, 1);
            inc_B[size-1] = new KeyValuePair(-1, 1);
            inc_C[size-1] = new KeyValuePair(-1, 1);
            long random_1 = sortingObject_A.sort(rand_A);
            avgA_rand += random_1;
            long random_2 = sortingObject_C.sort(rand_C);
            avgC_rand += random_2;
            long random_3 = sortingObject_F.sort(rand_F);
            avgF_rand += random_3;
            long inc_1 = sortingObject_A.sort(inc_A);
            avgA_inc += inc_1;
            long inc_2 = sortingObject_C.sort(inc_B);
            avgC_inc += inc_2;
            long inc_3 = sortingObject_F.sort(inc_C);
            avgF_inc += inc_3;
            long dec_1 = sortingObject_A.sort(dec_A);
            avgA_dec += dec_1;
            long dec_2 = sortingObject_C.sort(dec_C);
            avgC_dec += dec_2;
            long dec_3 = sortingObject_F.sort(dec_F);
            avgF_dec += dec_3;
        }

        System.out.println("Random");
        System.out.println("Avg for A: " + avgA_rand/5000);
        System.out.println("Avg for C: " + avgC_rand/5000);
        System.out.println("Avg for F: " + avgF_rand/5000);
        System.out.println("Increasing");
        System.out.println("Avg for A: " + avgA_inc/5000);
        System.out.println("Avg for C: " + avgC_inc/5000);
        System.out.println("Avg for F: " + avgF_inc/5000);
        System.out.println("Decreasing");
        System.out.println("Avg for A: " + avgA_dec/5000);
        System.out.println("Avg for C: " + avgC_dec/5000);
        System.out.println("Avg for F: " + avgF_dec/5000);

//        for n = 100
//        Random
//        Avg for A: 68444
//        Avg for C: 578596
//        Avg for F: 83862
//        Increasing
//        Avg for A: 68300
//        Avg for C: 7087
//        Avg for C almost sorted: 642396
//        Avg for F: 3511
//        Avg for F almost sorted: 6703
//        Decreasing
//        Avg for A: 68389
//        Avg for C: 641673
//        Avg for F: 161093
//
//
//        for n = 10
//        Random
//        Avg for A: 4652
//        Avg for C: 4994
//        Avg for F: 1261
//        Increasing
//        Avg for A: 4655
//        Avg for C: 1188
//        Avg for F: 592
//        Decreasing
//        Avg for A: 4643
//        Avg for C: 5913
//        Avg for F: 1760
    }


}
