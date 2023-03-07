import java.util.Arrays;

public class ValueCalculator implements Runnable {
    private final int arrayLength = 2000000;
    private final int halfArrayLength = arrayLength / 2;
    private float[] array = new float[arrayLength];

    ValueCalculator() {
    }

    ValueCalculator(float[] array) {
        this.array = array;
    }

    void doCalc(float el) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        float[] destArr1 = new float[halfArrayLength];
        float[] destArr2 = new float[halfArrayLength];
        Arrays.fill(array, el);
        System.arraycopy(array, 0, destArr1,
                0, halfArrayLength);
        System.arraycopy(array, halfArrayLength,
                destArr2, 0, halfArrayLength);
        final Thread thread1 = new Thread(new ValueCalculator(destArr1));
        final Thread thread2 = new Thread(new ValueCalculator(destArr2));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.arraycopy(destArr1, 0, array, 0, halfArrayLength);
        System.arraycopy(destArr2, 0, array, halfArrayLength, halfArrayLength);
        System.out.println("Time in msec: " + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void run() {
        for (int i = 0; i < halfArrayLength; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}