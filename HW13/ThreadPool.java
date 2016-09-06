/**
 * Created by Yaroslav on 31.08.16.
 */
public interface ThreadPool {
    void execute(Runnable runnable);

    void start();
}
