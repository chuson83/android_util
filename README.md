android_util
============

アンドロイドアプリ開発用便利クラス

・HTTP通信（GET、POST、画像取得）を行うクラス(HttpIF.java)
・Edittext入力後エンターキーを押すことでキーボードを閉じるクラス(KeyBoardHidden.java)
・preferenceを作成、編集ができるクラス(Preference.java)

を収納。

-HttpIF.java-
クラスをインスタンス化して利用してください。
細かい点はコードのコメントを参照していただけると幸いです。
基本的にはメソッドのパラメータにURLやクエリを指定して実行すると、
レスポンスが戻り値として戻ってくる形になっています。

-KeyBoardHidden.java-
任意のエディットテキストのリスナーに追加することで利用できます。
例：Edittext et.setOnEditorActionListener(new KeyBoardHidden(this));

-Preference.java-
クラスをインスタンス化して利用してください。
コンストラクタでファイル名を指定しています。
ここで既存のファイルを指定しなかった場合は、新規作成になります。
MODE_PRIVATE固定なので、変える場合はコンストラクタのパラメータを変更して利用してください。
