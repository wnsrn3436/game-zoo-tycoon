# Zoo Tycoon

[한국어](README.ko.md)

An Android game where you build a zoo by dragging animals and zookeepers onto a grassland. The placed animals are saved in SQLite, so you can load the zoo and keep building it.

<p>
  <img src="docs/screenshots/screenshot-1.jpg" width="354" alt="Load screen">
</p>


## How to play

Open `source/` in Android Studio and build it. The project uses Android Gradle plugin 3.1.3, Gradle 4.4, and `compileSdkVersion 28`, with a `minSdkVersion` of 22 (Android 5.1).

On the loading screen, touch the screen once "계속하려면 화면을 터치하세요." (touch the screen to continue) appears after 3 seconds. On the main screen, "불러오기" (load) shows the number of saved animals and then resumes the game, and "도움말" (help) describes the animals and zookeepers. On the "새로 만들기" (new) screen, choose whether to hire zookeepers and set the game speed on the 3-star scale, then press "새로 만들기" on that screen to empty the saved zoo and start. The faster the game speed, the faster the animals bob in place.

| Control | Action |
|---|---|
| Long press an animal or zookeeper in the bottom bar and drag it | Places it where it is dropped |
| Long press a placed animal | Sends it back to the wild |
| Drag the grassland | Looks around the map |

The animals are a penguin, a crocodile, a deer, a fox, a koala, a squirrel, and a turtle, and the 3 zookeepers handle training, feeding, and cleaning. Zookeepers can be placed in games where "사육사 고용" (hire zookeepers) is turned on.


## How it works

**Placement uses Android drag and drop, and each drop is written to SQLite right away.** A long press on a picture in the bottom bar starts a drag with a `ClipData` that carries the ID of that view. On `ACTION_DROP`, the map layout creates a new `ImageView` showing the picture for that ID at the drop position, and in the same moment inserts a row with the view ID and position into the `animals` table. The loading screen reads this table into a list in memory, and when the game starts it places everything from that list back on the map.

```java
case DragEvent.ACTION_DROP:
    int viewId = event.getClipData().getItemAt(0).getIntent().getIntExtra("Id", -1);
    createAnimal(viewId, event.getX(), event.getY());
    ZooTycoonDB.addAnimal(new ZooTycoonDB.Animal(viewId, (int) event.getX(), (int) event.getY()));
    return true;
```

**The map moves two scroll views together.** The grassland, a 3x3 tiling of a 512dp grass texture, sits in a `HorizontalScrollView` inside a `ScrollView`. `SyncScroll` receives touches from both views and calls `scrollBy` on both axes at once by the distance the finger moved, so the map can be dragged diagonally too.


## Files

| Path | Content |
|---|---|
| `source/app/src/main/java/com/jungu/zootycoon/GameActivity.java` | Game screen and drag and drop placement |
| `source/app/src/main/java/com/jungu/zootycoon/ZooTycoonDB.java`, `ZooTycoonHelper.java` | Saving to and loading from SQLite |
| `source/app/src/main/java/com/jungu/zootycoon/SyncScroll.java` | Map scrolling |
| Other activities in `source/app/src/main/java/com/jungu/zootycoon/` | Loading, main, new game, load, and help screens |
| `source/app/src/main/res/` | Layouts, pictures, and animations |
| `docs/screenshots/` | Screenshots |


## License

CC BY-NC-ND 4.0. Unmodified copies may be shared for noncommercial purposes with attribution. Distributing modified versions and commercial use are not allowed. Bundled libraries, graphics, sounds, and maps made by other people keep their own rights. See [LICENSE](LICENSE).
