package org.geeksword.xwy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeekswordXwyApplicationTests.class)
public class GeekswordXwyApplicationTests {

    @Test
    public void contextLoads() {

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        };
    }


    public static void main(String[] args) {
        System.out.println("主线程开始:" + System.currentTimeMillis());
        Callable callable = new Callable() {
            int sun = 0;

            @Override
            public Object call() throws Exception {
                System.out.println("子线程开始：" + System.currentTimeMillis());
                for (int i = 0; i < 200; i++) {
                    sun = sun + i;
                }

                Thread.sleep(1000);
                System.out.println("子线程结束：" + System.currentTimeMillis());
                return sun;
            }
        };
        Thread thread = new Thread(String.valueOf(callable));
        thread.start();
        System.out.println("主线程结束:" + System.currentTimeMillis());
    }
}
