/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rxJavaExample;

import java.io.IOException;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.*;

public class App {
    public static void main(String[] args) throws InterruptedException,IOException {

        
        // simple
        // -------------------//
        Flowable.just("Hello world").subscribe(System.out::println);

        
        // with consumer
        // -------------------//
        Flowable.just(new ModelKu("hello world")).subscribe(new Consumer<ModelKu>() {
            @Override public void accept(ModelKu s) {
                System.out.println(s.Data);
            }
        });

        
        // simulate thread
        // -------------------//
        Flowable.fromCallable(() -> {
            Thread.sleep(1000);
            return new ModelKu("Done");
        })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .subscribe(new Consumer<ModelKu>() {
            @Override public void accept(ModelKu s) {
                System.out.println(s.Data);
            }
        },new Consumer<Throwable>() {
            @Override public void accept(Throwable s) {
                System.out.println(s.getMessage());
            }
        });
        Thread.sleep(2000);

        
        // request with okhhtp
        // -------------------//
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://pariwisata-5a943.firebaseio.com/lokasi_wisata.json")
                .get()
                .addHeader("application/json", "charset=utf-8")
                .build();
        
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override 
            public void subscribe(ObservableEmitter<Response> subscriber) {
                try {
                    Response response = client.newCall(request).execute();
                    subscriber.onNext(response);
                    subscriber.onComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribe(new Consumer<Response>() {
            @Override 
            public void accept(Response s) throws IOException {
                System.out.println(s.body().string());
            }
        });  
    }


    public static class ModelKu {
        public String Data = "";
        public ModelKu(String data){
            this.Data = data;
        }
    }
}
