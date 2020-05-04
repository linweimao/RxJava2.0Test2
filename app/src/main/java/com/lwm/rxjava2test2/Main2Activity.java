package com.lwm.rxjava2test2;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/*
    FlatMap（）
        作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送

    原理
        1、为事件序列中每个事件都创建一个 Observable 对象；
        2、将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
        3、将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
        4、新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）

    应用场景
        无序的将被观察者发送的整个事件序列进行变换

    注：新合并生成的事件序列顺序是无序的，即 与旧序列发送事件的顺序无关
 */
public class Main2Activity extends AppCompatActivity {

    private static final String TAG = Main2Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


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

            // 采用flatMap（）变换操作符
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                int delay = 0;
                if(integer == 3){
                    delay = 500;//延迟500ms发射
                }
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的,发送三个String事件
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
