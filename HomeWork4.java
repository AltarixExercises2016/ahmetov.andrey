import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 1 on 30.11.2016.
 */
public class HomeWork4 {

    static ArrayList<String> ListWork = new ArrayList<>();

    public static void main(String[] args) {

        Thread WriteThread = new Thread(new Runnable() {
            @Override
            public void run() {

                int bar = 0;
                int[] timeArray = {7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
                Random randTime = new Random();
                long timeRead = System.currentTimeMillis();
                long diff = 0;
                while (true) {
//                    System.out.println("Пищущий поток "+timeArray[bar]);
                    bar = randTime.nextInt(11);
                    diff = System.currentTimeMillis() - timeRead;
                    ListWork.add(timeArray[bar] + "  " + diff);
                    try {
                        Thread.sleep(timeArray[bar] * 100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread ReadThread = new Thread(new Runnable() {
            @Override
            public void run() {

                long timeRead = System.currentTimeMillis();
                long diff = 0;
                while (true) {
                    diff = System.currentTimeMillis() - timeRead;
                    try {
                        Thread.sleep(1000);
                        System.out.println(ListWork.get(0) + "  " + ListWork.size() + "  " + diff);
                        ListWork.remove(0);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Список пуст");
                    }
                }
            }
        });

        WriteThread.start();
        ReadThread.start();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WriteThread.stop();
        ReadThread.stop();
    }
}