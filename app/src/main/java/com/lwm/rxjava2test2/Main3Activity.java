package com.lwm.rxjava2test2;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/*
    ConcatMap（）
        作用：类似FlatMap（）操作符

        与FlatMap（）的 区别在于：拆分 & 重新合并生成的事件序列 的顺序 = 被观察者旧序列生产的顺序

    应用场景
        有序的将被观察者发送的整个事件序列进行变换

    注：新合并生成的事件序列顺序是有序的，即 严格按照旧序列发送事件的顺序
 */
public class Main3Activity extends AppCompatActivity {

    private static final String TAG = Main3Activity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        // 采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
            }

            // 采用concatMap（）变换操作符
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                int delay = 0;
                if(integer == 3){
                    delay = 500;//延迟500ms发射
                }
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过concatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的,发送三个String事件
                    // 最终合并，再发送给被观察者
                }

                return Observable.fromIterable(list).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }
}
