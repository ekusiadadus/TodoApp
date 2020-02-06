# ほめてくれるTodoアプリ
タスクを完了させるとアニメーションで祝福してくれるTodoアプリです。
![](https://github.com/nanaten/TodoApp/blob/develop/screenshot/lottie_animation.gif)

ダークモードにも対応しています。
![](https://github.com/nanaten/TodoApp/blob/develop/screenshot/dark_theme.png)

## アーキテクチャ
MVVM

## 構成

```
ProjectRoot/
    ├ adapter/      # RecyclerView Adapter
    ├ database/     # Entity
    ├ di/           # Dagger Component
    │  ├ ui/
    │  └ viewmodel/
    ├ domain/       # Repository
    ├ ui/           # View, ViewModel
    └ util/         # Utility

```

## 使用ライブラリ
- Android Architecture Components
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [Navigation Component](https://developer.android.com/guide/navigation)
- [Dagger2](https://github.com/google/dagger)
- [Realm](https://realm.io/docs/java/latest)
- [Material Dialogs](https://github.com/afollestad/material-dialogs)
- [Lottie](https://github.com/airbnb/lottie-android)

## 課題点
- Todoの数が多くなる（だいたい10個ぐらい）と、タブを切り替えた時などに若干リストがちらつくようになります。 

原因： [combine](https://github.com/nanaten/TodoApp/blob/develop/app/src/main/java/com/nanaten/todoapp/util/Util.kt) (from DroidKaigi 2020 App)という関数でリストとタブのポジションを監視して、リアルタイムにリストを差し替えているのがちらつきの原因になっているかと思います。今回は時間の関係上直していませんが、今後改善したいと思います。


## 追加したい機能
- 時間で通知する機能を追加したかったですが、工数が足りませんでした。今後追加したいと思います。