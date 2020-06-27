import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    public static void main(String[] args) {
        float[] arr = new float[SIZE];
        Arrays.fill(arr,1);
        method1(arr);
        Arrays.fill(arr,1);
        method2(arr);
    }

    private static void method1(float[] arr){
        long start = System.currentTimeMillis();
        long stop;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        stop = System.currentTimeMillis();
        System.out.printf("Type 1: %d ms\n",stop-start);
    }

    private static void method2(float[] arr){
        float[] a1 = new float[SIZE];
        float[] a2 = new float[SIZE];
        long start = System.currentTimeMillis();
        long stop;
        System.arraycopy(arr,0,a1,0,HALF);
        System.arraycopy(arr,HALF,a2,0,HALF);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF ; i++) {
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF ; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1,0,arr,0,HALF);
        System.arraycopy(a2,0,arr,HALF,HALF);
        stop = System.currentTimeMillis();
        System.out.printf("Type 2: %d ms\n",stop-start);
    }
}
