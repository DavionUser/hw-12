public class Exercise1 {
    private static int time;
    private static int period = 10;

    public static void main(String[] args) {

        Thread secondsCounter = new Thread(() -> {
            while (!timeIsOut()) {
                time++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    ///NOP
                }
                print("Від запуску програми - " + time + " сек");
            }
        });

        Thread fiveSecondsCounter = new Thread(() -> {
            while (!timeIsOut()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    ///NOP
                }
                print("Минуло 5 cекунд");
            }
        });

        secondsCounter.start();
        fiveSecondsCounter.start();
    }

    private static void print(String message) {
        System.out.println(message);
    }

    private static boolean timeIsOut() {
        return time == period;
    }
}