# RxJava Example

simple flow

```

    Flowable.just("Hello world").subscribe(System.out::println);

```

with consumer

```

    Flowable.just(new ModelKu("hello world")).subscribe(new Consumer<ModelKu>() {
        @Override public void accept(ModelKu s) {
            System.out.println(s.Data);
        }
    });

```

simulate thread

```

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

```

request with okhhtp

use Flowable

```
    final OkHttpClient client = new OkHttpClient();
    final Request request = new Request.Builder()
            .url("https://pariwisata-5a943.firebaseio.com/lokasi_wisata.json")
            .get()
            .addHeader("application/json", "charset=utf-8")
            .build();


    Flowable.create(new FlowableOnSubscribe<Response>() {
            @Override 
            public void subscribe(FlowableEmitter<Response> subscriber) {
                try {
                    Response response = client.newCall(request).execute();
                    subscriber.onNext(response);
                    subscriber.onComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER).subscribe(new Consumer<Response>() {
            @Override 
            public void accept(Response s) throws IOException {
                System.out.println(s.body().string());
            }
        }); 
```

use Observable

```

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
            
        }).subscribe(new Observer<Response>(){
            @Override 
            public void onSubscribe(Disposable d){
                System.out.println(d.isDisposed() ? "disposed" : "dispose");
            }

            @Override 
            public void onNext(Response s) {
                try {
                    System.out.println(s.body().string());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override 
            public void onError(Throwable e){
                System.out.println(e.getMessage());
            }

            @Override 
            public void onComplete(){
                System.out.println("request complete");
            }
            
        }); 

```