import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class Test {
    public static void main(String[] args) {

        FizzBuzz fizzBuzz = new FizzBuzz(30);
        Runnable printFizz = () -> System.out.print("fizz, ");
        Runnable printBuzz = () -> System.out.print("buzz, ");
        Runnable printFizzBuzz = () -> System.out.print("fizzbuzz, ");
        IntConsumer printNumber = number -> System.out.print(number + ", ");

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz(printFizz);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz(printBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadC = new Thread(()-> {
            try {
                fizzBuzz.fizzbuzz(printFizzBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number(printNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }
}

class FizzBuzz {
    int number;

    private final Semaphore semFizz;
    private final Semaphore semBuzz;
    private final Semaphore semFizzBuzz;
    private final Semaphore semNumber;

    public FizzBuzz(int number) {
        this.number = number;

        semFizz = new Semaphore(0);
        semBuzz = new Semaphore(0);
        semFizzBuzz = new Semaphore(0);
        semNumber = new Semaphore(1);
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= number ; i+= 3) {
            if (i % 15 == 0) {
                continue;
            }
            semFizz.acquire();
            printFizz.run();
            semNumber.release();

        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= number ; i += 5) {
            if (i % 15 == 0) {
                continue;
            }
            semBuzz.acquire();
            printBuzz.run();
            semNumber.release();

        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= number; i += 15) {
            semFizzBuzz.acquire();
            printFizzBuzz.run();
            semNumber.release();
        }
    }

    public void number(IntConsumer numb) throws InterruptedException {
        for (int i = 1; i <= number; i++) {
            semNumber.acquire();
            if(i % 15 == 0){
                semFizzBuzz.release();
            }
            else if(i % 5 == 0){
                semBuzz.release();
            }
            else if(i % 3 == 0){
                semFizz.release();
            }
            else{
                numb.accept(i);
                semNumber.release();
            }
        }
    }
}



