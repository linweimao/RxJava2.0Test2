package com.lwm.rxjava2test2;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import android.os.Bundle;
import android.util.Log;

import java.util.List;


/*
    Buffer（）
    作用
        定期从 被观察者（Obervable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送

    应用场景
        缓存被观察者发送的事件
 */
public class Main4Activity extends AppCompatActivity {

    private static final String TAG = Main4Activity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        // 被观察者 需要发送5个数字
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1) // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(List<Integer> stringList) {
                        Log.d(TAG, " 缓存区里的事件数量 = " +  stringList.size());
                        for (Integer value : stringList) {
                            Log.d(TAG, " 事件 = " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应" );
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
