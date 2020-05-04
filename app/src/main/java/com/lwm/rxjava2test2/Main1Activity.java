package com.lwm.rxjava2test2;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import android.os.Bundle;
import android.util.Log;

/*
    作用
        对被观察者发送的每1个事件都通过 指定的函数 处理，从而变换成另外一种事件

        即， 将被观察者发送的事件转换为任意的类型事件。

    应用场景
        数据类型转换

    具体使用
        下面以将 使用Map（） 将事件的参数从 整型 变换成 字符串类型 为例子说明
 */
public class Main1Activity extends AppCompatActivity {

    private static final String TAG = Main1Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        // 采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {

            // 1. 被观察者发送事件 : 参数为整型 = 1、2、3
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

            }
            // 2. 使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer +"的参数从 整型"+integer + " 变换成 字符串类型" + integer ;
            }
        }).subscribe(new Consumer<String>() {

            // 3. 观察者接收事件时，是接收到变换后的事件 : 字符串类型
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });

    }
}
