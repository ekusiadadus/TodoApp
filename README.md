# ほめてくれるTodoアプリ
タスクを完了させるとアニメーションで祝福してくれるTodoアプリです。

![](https://github.com/nanaten/TodoApp/blob/develop/screenshot/lottie_animation.gif)

ダークモードにも対応しています。

![](https://github.com/nanaten/TodoApp/blob/develop/screenshot/dark_theme.png)

## アーキテクチャ
MVVM

![](https://github.com/nanaten/TodoApp/blob/develop/screenshot/structure.png)

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
- [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Dagger2](https://github.com/google/dagger)
- [Realm](https://realm.io/docs/java/latest)
- [Material Dialogs](https://github.com/afollestad/material-dialogs)
- [Lottie](https://github.com/airbnb/lottie-android)


## 追加したい機能
- 時間で通知する機能
- タスクの内容メモ機能 -> 実装済み
