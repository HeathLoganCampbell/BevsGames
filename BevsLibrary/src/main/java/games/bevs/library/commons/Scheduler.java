/*
 * MIT License
 *
 * Copyright (c) 2018 Andavin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package games.bevs.library.commons;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.lang.ref.WeakReference;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A class to make using the {@link BukkitScheduler} less
 * cumbersome and easier to use. Also, allows for easy
 * {@code do-while} loops and similar condition based looping
 * in timed loop tasks.
 *
 * @author Andavin
 * @since February 9, 2018
 */
@SuppressWarnings("UnusedReturnValue")
public final class Scheduler {

    private static final int SEARCH_ATTEMPTS = 10;
    private static final ScheduledExecutorService SCHEDULER_SERVICE =
            Executors.newScheduledThreadPool(5, new ThreadFactoryBuilder().setNameFormat("Savage Scheduler - %d")
                    .setUncaughtExceptionHandler((thread, error) -> error.printStackTrace()).build());

    /*
     * 1. Use a core pool size of 100 to allow threads to not have to be created most of the time
     * 2. Max pool size of 1,000 to avoid OutOfMemoryErrors - a RejectedExecutionException is preferable
     * 3. Keep threads alive for 30 seconds in case multiple spikes happen in short periods of time
     * 4. Use a ArrayBlockingQueue with capacity of 50 for better memory management with the GC.
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(100, 1000,
            30L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50), new ThreadFactoryBuilder()
            .setNameFormat("Bevs Executor - %d").setUncaughtExceptionHandler((thread, error) -> error.printStackTrace()).build());

    static {
        ScheduledThreadPoolExecutor schedulerService = (ScheduledThreadPoolExecutor) SCHEDULER_SERVICE;
        schedulerService.setRemoveOnCancelPolicy(true);
        schedulerService.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
    }

    /**
     * Get the dedicated {@link ExecutorService thread pool} that
     * is used for execution methods provided in this class.
     *
     * @return The executor service for the scheduler.
     */
    public static ScheduledExecutorService getExecutorService() {
        return SCHEDULER_SERVICE;
    }

    /**
     * Cleanup and shutdown the {@link Executor}s that manage
     * this scheduler.
     *
     * @see ExecutorService#shutdown()
     */
    public static void shutdown() {
        SCHEDULER_SERVICE.shutdown();
        MoreExecutors.shutdownAndAwaitTermination(EXECUTOR_SERVICE, 5, TimeUnit.SECONDS);
    }

    /**
     * Run a task synchronously on the main thread using
     * the {@link BukkitScheduler}.
     *
     * @param run The {@link Runnable} to execute.
     * @return The {@link BukkitTask} that is returned after registering the task.
     */
    public static BukkitTask sync(Runnable run) {
        return Bukkit.getScheduler().runTask(PluginRegistry.getPlugin(SEARCH_ATTEMPTS), run);
    }

    /**
     * Run a task asynchronously on a separate thread using
     * the {@link BukkitScheduler}.
     *
     * @param run The {@link Runnable} to execute.
     * @return The {@link BukkitTask} that is returned after registering the task.
     */
    public static BukkitTask async(Runnable run) {
        return Bukkit.getScheduler().runTaskAsynchronously(PluginRegistry.getPlugin(SEARCH_ATTEMPTS), run);
    }

    /**
     * Run a task synchronously after a specified amount of
     * ticks using the {@link BukkitScheduler}.
     *
     * @param run The {@link Runnable} task to execute.
     * @param delay The ticks (1 tick = 50 milliseconds, 20 ticks = 1 second) after which to run the task.
     * @return The {@link BukkitTask} that is returned after registering the task.
     */
    public static BukkitTask later(Runnable run, long delay) {
        return Bukkit.getScheduler().runTaskLater(PluginRegistry.getPlugin(SEARCH_ATTEMPTS), run, delay);
    }

    /**
     * Run a task asynchronously after a specified amount of
     * ticks using the {@link BukkitScheduler}.
     *
     * @param run The {@link Runnable} task to execute.
     * @param delay The ticks (1 tick = 50 milliseconds, 20 ticks = 1 second) after which to run the task.
     * @return The {@link BukkitTask} that is returned after registering the task.
     */
    public static BukkitTask laterAsync(Runnable run, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(PluginRegistry.getPlugin(SEARCH_ATTEMPTS), run, delay);
    }

    /**
     * Run a task synchronously repeatedly after a specified amount
     * of ticks and repeated every period ticks until cancelled.
     *
     * @param run The {@link Runnable} task to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @return The {@link BukkitTask} that is returned after registering the task.
     */
    public static BukkitTask repeat(Runnable run, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(PluginRegistry.getPlugin(SEARCH_ATTEMPTS), run, delay, period);
    }

    /**
     * Run a task asynchronously repeatedly after a specified amount
     * of ticks and repeated every period ticks until canceled.
     *
     * @param run The {@link Runnable} task to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @return The {@link BukkitTask} that is returned after registering the task.
     */
    public static BukkitTask repeatAsync(Runnable run, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(PluginRegistry.getPlugin(SEARCH_ATTEMPTS), run, delay, period);
    }

    /**
     * Run a task synchronously repeatedly until the condition is met
     * at which point it will be cancelled.
     * <p>
     * In behavior towards the condition, this is identical to an inverted
     * {@code while} loop. If the condition is {@code true} to begin with
     * or by the time that {@code delay} has elapsed, then the task will
     * never be executed and will immediately be cancelled on the first run.
     *
     * @param run The {@link Runnable} task to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @param until The {@link Supplier} to test when to cancel. When this is
     *              {@code true} the task will be cancelled.
     * @return The {@link BukkitTask task} that was scheduled.
     */
    public static BukkitTask repeatUntil(Runnable run, long delay, long period, Supplier<Boolean> until) {
        return repeatWhile(run, delay, period, () -> !until.get());
    }

    /**
     * Run a task asynchronously repeatedly until the condition is met
     * at which point it will be cancelled.
     * <p>
     * In behavior towards the condition, this is identical to an inverted
     * {@code while} loop. If the condition is {@code true} to begin with
     * or by the time that {@code delay} has elapsed, then the task will
     * never be executed and will immediately be cancelled on the first run.
     *
     * @param run The {@link Runnable} task to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @param until The {@link Supplier} to test when to cancel.
     *         When this returns {@code true} the task will be cancelled.
     * @return The {@link BukkitTask task} that was scheduled.
     */
    public static BukkitTask repeatAsyncUntil(Runnable run, long delay, long period, Supplier<Boolean> until) {
        return repeatAsyncWhile(run, delay, period, () -> !until.get());
    }

    /**
     * Run a task repeatedly (every period of ticks) as long as the condition
     * met. Once the condition is no longer met (returns {@code false}) the
     * loop will end.
     * <p>
     * In behavior towards the condition, this is identical to a {@code while}
     * loop. If the condition is {@code false} to begin with or by the time
     * that {@code delay} has elapsed, then the task will never be executed and
     * will immediately be cancelled on the first run.
     *
     * @param run The {@link Runnable} task to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @param condition The {@link Supplier condition} that must be {@code true}
     *                  in order for the task to continue to run.
     * @return The {@link BukkitTask task} that was scheduled.
     */
    public static BukkitTask repeatWhile(Runnable run, long delay, long period, Supplier<Boolean> condition) {
        Task task = new Task(run, condition);
        BukkitTask bukkitTask = repeat(task, delay, period);
        task.setTask(bukkitTask);
        return bukkitTask;
    }

    /**
     * Run a task asynchronously repeatedly (every period of ticks) as long
     * as the condition met. Once the condition is no longer met (returns
     * {@code false}) the loop will end.
     * <p>
     * In behavior towards the condition, this is identical to a {@code while}
     * loop. If the condition is {@code false} to begin with or by the time
     * that {@code delay} has elapsed, then the task will never be executed and
     * will immediately be cancelled on the first run.
     *
     * @param run The {@link Runnable} task to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @param condition The {@link Supplier condition} that must be {@code true}
     *                  in order for the task to continue to run.
     * @return The {@link BukkitTask task} that was scheduled.
     */
    public static BukkitTask repeatAsyncWhile(Runnable run, long delay, long period, Supplier<Boolean> condition) {
        Task task = new Task(run, condition);
        BukkitTask bukkitTask = repeatAsync(task, delay, period);
        task.setTask(bukkitTask);
        return bukkitTask;
    }

    /**
     * Run a task repeatedly (every period of ticks) until the specified
     * amount of executions have taken place.
     * <p>
     * This is, conditionally, almost identical to a {@code fori} loop:
     * <pre>
     *     for (int i = 0; i &lt; count; i++) {
     *         // The runnable contents
     *     }
     * </pre>
     *
     * @param consumer The {@link Consumer task} to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @param count The amount of executions to allow before cancelling.
     * @return The {@link BukkitTask task} that was scheduled.
     */
    public static BukkitTask repeatFor(Consumer<Integer> consumer, long delay, long period, int count) {
        Task task = new IncrementTask(consumer, count);
        BukkitTask bukkitTask = repeat(task, delay, period);
        task.setTask(bukkitTask);
        return bukkitTask;
    }

    /**
     * Run a task repeatedly (every period of ticks) until the specified
     * amount of executions have taken place.
     * <p>
     * This is, conditionally, almost identical to a {@code fori} loop:
     * <pre>
     *     for (int i = 0; i &lt; count; i++) {
     *         // The runnable contents
     *     }
     * </pre>
     *
     * @param consumer The {@link Consumer task} to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @param count The amount of executions to allow before cancelling.
     * @return The {@link BukkitTask task} that was scheduled.
     */
    public static BukkitTask repeatAsyncFor(Consumer<Integer> consumer, long delay, long period, int count) {
        Task task = new IncrementTask(consumer, count);
        BukkitTask bukkitTask = repeatAsync(task, delay, period);
        task.setTask(bukkitTask);
        return bukkitTask;
    }

    /**
     * Run a task repeatedly (every period of ticks) until the specified
     * amount of time has elapsed.
     * <p>
     * Timing precision is in milliseconds, however, the task is not guaranteed
     * to run a specific amount of times due to server lag.<pre>1000ms ≈ 20 ticks</pre>
     * If the duration given was 4 seconds and the task is supposed to run every
     * 5 ticks, then it should run about 16 times ({@code 20 / 5 * 4}), however,
     * this is not guaranteed and it can be any amount from 0 to 16 times
     * depending on server lag.
     * <br>
     * This would be guaranteed not to exceed 16 executions, though, since TPS
     * will never be above 20.
     *
     * @param run The {@link Runnable} task to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @param duration The amount of {@link TimeUnit units} to run for.
     * @param unit The {@link TimeUnit} to multiply the duration by.
     * @return The {@link BukkitTask task} that was scheduled.
     */
    public static BukkitTask repeatFor(Runnable run, long delay, long period, long duration, TimeUnit unit) {
        long until = unit.toMillis(duration);
        Task task = new Task(run, () -> System.currentTimeMillis() < until);
        BukkitTask bukkitTask = repeat(task, delay, period);
        task.setTask(bukkitTask);
        return bukkitTask;
    }

    /**
     * Run a task asynchronously repeatedly (every period of ticks) until
     * the specified amount of time has elapsed.
     * <p>
     * Timing precision is in milliseconds, however, the task is not guaranteed
     * to run a specific amount of time due to server lag.<pre>1000ms ≈ 20 ticks</pre>
     * If the duration given was 4 seconds and the task is supposed to run every
     * 5 ticks, then it should run about 16 times ({@code 20 / 5 * 4}), however,
     * this is not guaranteed and it can be any amount from 0 to 16 times
     * depending on server lag.
     * <br>
     * This would be guaranteed not to exceed 16 executions, though, since TPS
     * will never be above 20.
     *
     * @param run The {@link Runnable} task to execute every period.
     * @param delay The delay in ticks before the first run of the task.
     * @param period The period in ticks to wait until running again after each run.
     * @param duration The amount of {@link TimeUnit units} to run for.
     * @param unit The {@link TimeUnit} to multiply the duration by.
     * @return The {@link BukkitTask task} that was scheduled.
     */
    public static BukkitTask repeatAsyncFor(Runnable run, long delay, long period, long duration, TimeUnit unit) {
        long until = unit.toMillis(duration);
        Task task = new Task(run, () -> System.currentTimeMillis() < until);
        BukkitTask bukkitTask = repeatAsync(task, delay, period);
        task.setTask(bukkitTask);
        return bukkitTask;
    }

    /**
     * Execute the given {@link Runnable} asynchronously using
     * an {@link ExecutorService}.
     * <p>
     * The returned {@link CompletionStage} can be used to listen
     * in on when the execution completes. This can be achieved by
     * using the {@link CompletionStage#whenComplete(BiConsumer)}
     * method or other similar methods.
     * <p>
     * If an error is thrown during the execution of the runnable,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param runnable The runnable to execute.
     * @return A {@link CompletionStage} in order to listen
     *         in on when the execution completes.
     * @see CompletionStage
     */
    public static CompletionStage<Void> execute(Runnable runnable) {
        return execute(runnable, null);
    }

    /**
     * Execute the given {@link Runnable} asynchronously using
     * an {@link ExecutorService}.
     * <p>
     * The returned {@link CompletionStage} can be used to listen
     * in on when the execution completes. This can be achieved by
     * using the {@link CompletionStage#whenComplete(BiConsumer)}
     * method or other similar methods.
     * <p>
     * If an error is thrown during the execution of the runnable,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param runnable The runnable to execute.
     * @param handler The {@link CommandSender} that will handle player
     *                oriented exception messages.
     * @return A {@link CompletionStage} in order to listen
     *         in on when the execution completes.
     * @see CompletionStage
     */
    public static CompletionStage<Void> execute(Runnable runnable, CommandSender handler) {
        return CompletableFuture.runAsync(runnable, EXECUTOR_SERVICE)
                .whenCompleteAsync(new ExceptionHandler<>(handler), EXECUTOR_SERVICE);
    }

    /**
     * Execute the given {@link Supplier} asynchronously using
     * an {@link ExecutorService}.
     * <p>
     * The returned {@link CompletionStage} can be used to listen
     * in on when the execution completes. This can be achieved by
     * using the {@link CompletionStage#whenComplete(BiConsumer)}
     * method or other similar methods.
     * <p>
     * If an error is thrown during the execution of the supplier,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param supplier The supplier to execute.
     * @return A {@link CompletionStage} in order to listen
     *         in on when the execution completes.
     * @param <T> The type of object that is returned by the supplier.
     * @see CompletionStage
     */
    public static <T> CompletionStage<T> execute(Supplier<T> supplier) {
        return execute(supplier, null);
    }

    /**
     * Execute the given {@link Supplier} asynchronously using
     * an {@link ExecutorService}.
     * <p>
     * The returned {@link CompletionStage} can be used to listen
     * in on when the execution completes. This can be achieved by
     * using the {@link CompletionStage#whenComplete(BiConsumer)}
     * method or other similar methods.
     * <p>
     * If an error is thrown during the execution of the supplier,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param supplier The supplier to execute.
     * @param handler The {@link CommandSender} that will handle player
     *                oriented exception messages.
     * @return A {@link CompletionStage} in order to listen
     *         in on when the execution completes.
     * @param <T> The type of object that is returned by the supplier.
     * @see CompletionStage
     */
    public static <T> CompletionStage<T> execute(Supplier<T> supplier, CommandSender handler) {
        return CompletableFuture.supplyAsync(supplier, EXECUTOR_SERVICE)
                .whenCompleteAsync(new ExceptionHandler<>(handler), EXECUTOR_SERVICE);
    }

    /**
     * Schedule the given {@link Runnable} to be run asynchronously
     * after a given delay.
     * <p>
     * If an error is thrown during the execution of the runnable,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param runnable The runnable to execute asynchronously.
     * @param delay The delay in {@link TimeUnit units} to delay the execution for.
     * @param unit The {@link TimeUnit unit} of delay to delay the execution for.
     * @return The {@link CompletionStage} in order to listen
     *         in on when the execution completes.
     * @see CompletionStage
     */
    public static CompletionStage<?> later(Runnable runnable, long delay, TimeUnit unit) {
        return handle(SCHEDULER_SERVICE.schedule(runnable, delay, unit), null);
    }

    /**
     * Schedule the given {@link Runnable} to be run asynchronously
     * after a given delay.
     * <p>
     * If an error is thrown during the execution of the runnable,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param runnable The runnable to execute asynchronously.
     * @param delay The delay in {@link TimeUnit units} to delay the execution for.
     * @param unit The {@link TimeUnit unit} of delay to delay the execution for.
     * @param handler The {@link CommandSender} that will handle player oriented exception messages.
     * @return The {@link CompletionStage} in order to listen
     *         in on when the execution completes.
     * @see CompletionStage
     */
    public static CompletionStage<?> later(Runnable runnable, long delay, TimeUnit unit, CommandSender handler) {
        return handle(SCHEDULER_SERVICE.schedule(runnable, delay, unit), handler);
    }

    /**
     * Schedule the given {@link Runnable} to be run asynchronously
     * at a fixed rate of execution until cancelled.
     * <p>
     * If an error is thrown during the execution of the runnable,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param runnable The runnable to execute asynchronously.
     * @param period The period of {@link TimeUnit units} to wait between executions.
     * @param unit The {@link TimeUnit unit} of delay to measure the period in.
     * @return The {@link ScheduledFuture} in order to cancel
     *         the task in the future.
     */
    public static ScheduledFuture<?> repeat(Runnable runnable, long period, TimeUnit unit) {
        return repeat(runnable, 0, period, unit, null);
    }

    /**
     * Schedule the given {@link Runnable} to be run asynchronously
     * after a given delay.
     * <p>
     * If an error is thrown during the execution of the runnable,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param runnable The runnable to execute asynchronously.
     * @param period The period of {@link TimeUnit units} to wait between executions.
     * @param unit The {@link TimeUnit unit} of delay to measure the period in.
     * @param handler The {@link CommandSender} that will handle player oriented exception messages.
     * @return The {@link ScheduledFuture} in order to cancel
     *         the task in the future.
     */
    public static ScheduledFuture<?> repeat(Runnable runnable, long period, TimeUnit unit, CommandSender handler) {
        return repeat(runnable, 0, period, unit, handler);
    }

    /**
     * Schedule the given {@link Runnable} to be run asynchronously
     * at a fixed rate of execution until cancelled.
     * <p>
     * If an error is thrown during the execution of the runnable,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param runnable The runnable to execute asynchronously.
     * @param delay The delay of {@link TimeUnit units} to wait before beginning executions.
     * @param period The period of {@link TimeUnit units} to wait between executions.
     * @param unit The {@link TimeUnit unit} of delay to measure the period in.
     * @return The {@link ScheduledFuture} in order to cancel
     *         the task in the future.
     */
    public static ScheduledFuture<?> repeat(Runnable runnable, long delay, long period, TimeUnit unit) {
        ScheduledFuture<?> future = SCHEDULER_SERVICE.scheduleAtFixedRate(runnable, delay, period, unit);
        handle(future, null);
        return future;
    }

    /**
     * Schedule the given {@link Runnable} to be run asynchronously
     * after a given delay.
     * <p>
     * If an error is thrown during the execution of the runnable,
     * then it will be logged appropriately, but still passed along
     * to and stages that were added after this method call.
     *
     * @param runnable The runnable to execute asynchronously.
     * @param delay The delay of {@link TimeUnit units} to wait before beginning executions.
     * @param period The period of {@link TimeUnit units} to wait between executions.
     * @param unit The {@link TimeUnit unit} of delay to measure the period in.
     * @param handler The {@link CommandSender} that will handle player oriented exception messages.
     * @return The {@link ScheduledFuture} in order to cancel
     *         the task in the future.
     */
    public static ScheduledFuture<?> repeat(Runnable runnable, long delay, long period, TimeUnit unit, CommandSender handler) {
        ScheduledFuture<?> future = SCHEDULER_SERVICE.scheduleAtFixedRate(runnable, delay, period, unit);
        handle(future, handler);
        return future;
    }

    private static <T> CompletableFuture<T> handle(ScheduledFuture<T> future, CommandSender handler) {

        return CompletableFuture.supplyAsync(() -> {

            try {
                return future.get();
            } catch (CancellationException e) {
                return null; // Ignore cancellations
            } catch (Exception e) {
                // Delegate exceptions to the Handler
                throw new RuntimeException(e);
            }
        }, EXECUTOR_SERVICE).whenCompleteAsync(new ExceptionHandler<>(handler), EXECUTOR_SERVICE);
    }

    private static class ExceptionHandler<T> implements BiConsumer<T, Throwable> {

        private final WeakReference<CommandSender> handler;

        ExceptionHandler(CommandSender handler) {
            this.handler = handler != null ? new WeakReference<>(handler) : null;
        }

        @Override
        public void accept(T t, Throwable throwable) {

            if (throwable != null) {

                if (this.handler != null) {

                    CommandSender handler = this.handler.get();
                    if (handler != null) {
//                        Logger.handle(throwable, handler, true);
                        return;
                    }
                }

                if (throwable instanceof CancellationException) {
//                    Logger.debug(throwable); // Just debug for log files
                } else {
//                    Logger.severe(throwable);
                }
            }
        }
    }

    private static class Task implements Runnable {

        BukkitTask task;
        boolean cancelled;
        private final Runnable runnable;
        private final Supplier<Boolean> condition;

        Task(Runnable runnable, Supplier<Boolean> condition) {
            this.runnable = runnable;
            this.condition = condition;
        }

        /**
         * The {@link BukkitTask} that represents the
         * task that is being run.
         *
         * @param task The task.
         */
        final void setTask(BukkitTask task) {
            this.task = task;
        }

        @Override
        public void run() {

            if (this.cancelled) {

                if (this.task != null) {
                    this.task.cancel();
                }

                return;
            }

            if (this.condition.get()) {
                this.runnable.run();
            } else {

                if (this.task != null) {
                    this.task.cancel();
                } else {
                    this.cancelled = true;
                }
            }
        }
    }

    private static class IncrementTask extends Task implements Runnable {

        private final int maxCount;
        private final Consumer<Integer> consumer;
        private final AtomicInteger count = new AtomicInteger();

        IncrementTask(Consumer<Integer> consumer, int maxCount) {
            super(null, null);
            this.maxCount = maxCount;
            this.consumer = consumer;
        }

        @Override
        public void run() {

            if (this.cancelled) {

                if (this.task != null) {
                    this.task.cancel();
                }

                return;
            }

            int count = this.count.getAndIncrement();
            if (count < this.maxCount) {
                this.consumer.accept(count);
            } else {

                if (this.task != null) {
                    this.task.cancel();
                } else {
                    this.cancelled = true;
                }
            }
        }
    }
}