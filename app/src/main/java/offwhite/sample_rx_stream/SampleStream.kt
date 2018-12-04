package offwhite.sample_rx_stream

import io.reactivex.*

/**
 * ストリーム作成のサンプル(基本)
 *
 * ・ストリームの作成
 *     ・Observable
 *     ・Single
 *     ・Maybe
 *     ・Completable
 * ・データソースの設定
 *     ・fromArray
 *     ・create
 *     ・just
 */
class SampleStream {

    /**
     * サンプル１：リストのストリームを生成する
     */
    fun getStringListStreamFromObservable() : Observable<Array<String>>{

        // Observable はストリームを配置することができます。
        // fromArrayはこのストリームにデータソースを設定しています。
        //  データソースは、ストリームに流れるデータの元になルものです。
        //  APIのレスポンスやDBに保存されているデータなどが設定される事が多いかと思います。
        // データソースの設定方法は複数あるが、配列をデータソースとしたい場合はfromArrayを用います。
        // 注：サンプルの多くはfromメソッドを利用しているが、
        // 2018/12/04時点ではfrom関数がJavadocに記載されていない
        return Observable.fromArray(arrayOf("a","b","c"))
    }

    /**
     * サンプル２：リストのストリームを生成する(Single)
     */
    fun getStringListStreamFromSingle() : Single<String> {

        // SingleもObservableと同様にストリームを配置する事ができますが、
        // Observableと以下の点が異なります。
        // 1.単一の値しか流せない(配列は流せない)
        // 2.一度イベントが発生した後は、購読されない。(onSuccess, onErrorどちらか一つ発生したら終了)
        // そのため、基本的にはAPIのレスポンスクラス等の”クラス一つだけを一回取りに行く”ようなHttp通信時に用いると思います。
        // justは単一の値(item)をSingleのストリームに流します。(流す事をemitと呼んだりします。)
        return Single.just("Hello")
    }


    /**
     * サンプル３：リストのストリームを生成する(Maybe)
     */
    fun getStringListStreamFromMaybe() : Maybe<String> {

        // MaybeはSingleとほぼ同じです。
        // しかしSingleと異なり、"値を返さずに終了しても良い"点が異なります。
        // createは公式の説明だと、”リアクティブの世界とコールバックの世界を橋渡しするAPI” とあります。
        // つまりストリームの情報から判断して適切なコールバックを呼び出したい時に使うと思っています。
        // リアクティブプログラミングをしたい場合は基本的にこのcreateを使う事が多いかなと思います。
        return Maybe.create { emitter->
            val result = "no data"
            if ("no data" == result) {
                // Singleだとできないが、ここで値を流さずに終了ができる
                emitter.onComplete()
            } else if ("has data" == result) {
                // Singleと同じように値を返す
                emitter.onSuccess(result)
            } else {
                // Singleと同じようにエラーを返す
                emitter.onError(Throwable())
            }
        }
    }

    /**
     * サンプル４：リストのストリームを生成する(Completable)
     */
    fun getStringListStreamFromCompletable() : Completable {

        //
        return Completable.create{
            emitter ->

            // CompletableはonCompleteのみ実行できます。
            // つまり、値を返さないストリームです。
            // 利用用途としては"このタイミングでAPIからデータは取得するけど、特に画面に反映させない"
            // ような場合に使うかなと思います。
            emitter.onComplete()
        }
    }


}