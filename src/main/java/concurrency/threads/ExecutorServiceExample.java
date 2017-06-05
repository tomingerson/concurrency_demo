package concurrency.threads;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExecutorServiceExample {

    public static void main(String... args) {
        System.out.println("===== runTask");
        runTask();

        System.out.println("\n===== submitTask");
        submitTask();

        System.out.println("\n===== submitCallable");
        submitCallable();

        System.out.println("\n===== invokeTasksAny");
        invokeTasksAny();

        System.out.println("\n===== invokeTasksAll");
        invokeTasksAll();
    }

    /**
     * simply run a task and ignore the result
     */
    private static void runTask() {
        // use just 1 thread to execute all tasks to be run by this ExecutorService
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Runnable runnable = () -> System.out.println("Asynchronous task");

        executorService.execute(runnable);
        executorService.shutdown();
    }

    /**
     * submit a task and be able to wait on completion
     */
    private static void submitTask() {
        // creates and releases threads on demand
        final ExecutorService executorService = Executors.newCachedThreadPool();

        final Runnable runnable = () -> System.out.println("Asynchronous task");

        // The Future's get method will return null upon successful completion
        Future<?> result = executorService.submit(runnable);
        try {
            boolean success = result.get(5, TimeUnit.SECONDS) == null;
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }


    /**
     * submit a task and return something from that task
     */
    private static void submitCallable() {
        // creates a ThreadPool with the given number of threads
        final ExecutorService executorService = Executors.newFixedThreadPool(5);

        final Callable<String> callable = () -> "result";

        // The Future's get method will return null upon successful completion
        Future<String> result = executorService.submit(callable);
        try {
            final String called = result.get(5, TimeUnit.SECONDS);
            System.out.println(called);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    /**
     * in case you want tasks to run asynchronously and need just the return
     * value of one of them
     */
    private static void invokeTasksAny() {
        // threads in this pool can be called following a schedule
        ExecutorService executorService = Executors.newScheduledThreadPool(3);

        Collection<Callable<String>> callables = new HashSet<>();

        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");
        callables.add(() -> "Task 3");


        try {
            final String result = executorService.invokeAny(callables);
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    /**
     * in case you want tasks to run asynchronously and need the return
     * values of all of them
     */
    private static void invokeTasksAll() {
        // this pool tries to keep threads running in parallel as much as possible
        ExecutorService executorService = Executors.newWorkStealingPool(5);

        Collection<Callable<String>> callables = new HashSet<>();

        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");
        callables.add(() -> "Task 3");

        try {
            final List<Future<String>> futures = executorService.invokeAll(callables);
            for (Future<String> f : futures) {
                try {
                    System.out.println("result = " + f.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
