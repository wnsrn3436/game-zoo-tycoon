# Zoo Tycoon

초원 위에 동물과 사육사를 끌어다 놓아 동물원을 꾸미는 안드로이드 게임이다. 놓은 동물은 SQLite 에 저장되어 불러오기로 이어서 꾸밀 수 있다.

<p>
  <img src="docs/screenshots/screenshot-1.jpg" width="354" alt="불러오기 화면">
</p>


## 플레이 방법

Android Studio 로 `source/` 를 열어 빌드한다. Android Gradle 플러그인 3.1.3, Gradle 4.4, `compileSdkVersion 28` 프로젝트이고 `minSdkVersion` 은 22(Android 5.1)다.

로딩 화면에서 3초가 지나 "계속하려면 화면을 터치하세요." 가 뜨면 화면을 터치한다. 메인 화면의 "불러오기" 는 저장된 동물 수를 보여 준 뒤 이어서 시작하고, "도움말" 에는 동물과 사육사 설명이 있다. "새로 만들기" 화면에서는 사육사 고용 여부와 별 세 개짜리 게임 속도를 고르고, 그 화면의 "새로 만들기" 를 누르면 저장된 동물원을 비우고 시작한다. 게임 속도가 빠를수록 동물이 제자리에서 흔들리는 애니메이션이 빨라진다.

| 조작 | 동작 |
|---|---|
| 아래 막대의 동물이나 사육사를 길게 눌러 끌기 | 놓은 자리에 배치 |
| 배치된 동물을 길게 누르기 | 야생으로 돌려보내기 |
| 초원을 끌기 | 맵 둘러보기 |

동물은 펭귄, 악어, 사슴, 여우, 코알라, 다람쥐, 거북이이고 사육사는 훈련, 먹이, 청소 담당 셋이다. 사육사는 "사육사 고용" 을 켠 게임에서 놓을 수 있다.


## 구현 원리

**배치는 안드로이드 드래그 앤 드롭으로 하고 바로 SQLite 에 쓴다.** 아래 막대의 그림을 길게 누르면 그 뷰의 ID 를 담은 `ClipData` 로 드래그를 시작한다. 맵 레이아웃은 `ACTION_DROP` 에서 그 ID 의 그림으로 새 `ImageView` 를 만들어 놓은 좌표에 두고, 같은 순간 `animals` 테이블에 뷰 ID 와 좌표를 한 줄 넣는다. 로딩 화면에서 이 테이블을 메모리 목록으로 읽어 두었다가 게임을 시작할 때 목록대로 다시 놓는다.

```java
case DragEvent.ACTION_DROP:
    int viewId = event.getClipData().getItemAt(0).getIntent().getIntExtra("Id", -1);
    createAnimal(viewId, event.getX(), event.getY());
    ZooTycoonDB.addAnimal(new ZooTycoonDB.Animal(viewId, (int) event.getX(), (int) event.getY()));
    return true;
```

**맵은 스크롤뷰 두 개를 함께 민다.** 512dp 풀 텍스처를 3x3 으로 깐 초원은 `ScrollView` 안에 `HorizontalScrollView` 를 넣은 구조다. `SyncScroll` 이 두 뷰의 터치를 받아 손가락이 움직인 거리만큼 가로와 세로를 동시에 `scrollBy` 해서 대각선으로도 끌 수 있다.


## 파일

| 경로 | 내용 |
|---|---|
| `source/app/src/main/java/com/jungu/zootycoon/GameActivity.java` | 게임 화면과 드래그 앤 드롭 배치 |
| `source/app/src/main/java/com/jungu/zootycoon/ZooTycoonDB.java`, `ZooTycoonHelper.java` | SQLite 저장과 불러오기 |
| `source/app/src/main/java/com/jungu/zootycoon/SyncScroll.java` | 맵 스크롤 |
| `source/app/src/main/java/com/jungu/zootycoon/` 의 나머지 Activity | 로딩, 메인, 새로 만들기, 불러오기, 도움말 화면 |
| `source/app/src/main/res/` | 레이아웃, 그림, 애니메이션 |
| `docs/screenshots/` | 스크린샷 |


## 라이선스

CC BY-NC-ND 4.0 라이선스다. 출처를 밝히면 비상업 용도로 그대로 공유할 수 있고, 수정본 배포와 상업적 이용은 금지한다. 함께 들어 있는 것 중 다른 사람이 만든 라이브러리와 그림·소리·맵은 각자의 권리를 따른다. 자세한 내용은 [LICENSE](LICENSE) 에 있다.
