package com.jungu.zootycoon;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class HelpActivity extends AppCompatActivity {
    //View
    private ListView listHelp;
    private IconTitleContentsAdapter listHelpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.anime_fox);
            bar.setDisplayUseLogoEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }
        setTitle(getResources().getString(R.string.app_name) + " <Help>");

        //View
        listHelp = findViewById(R.id.listHelp);

        //Adapter
        listHelpAdapter = new IconTitleContentsAdapter();
        listHelp.setAdapter(listHelpAdapter);

        //Animals
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.anime_penguin),
                "펭귄", "남극의 귀염둥이, 상징과도 같은 새입니다. 날지는 못하기에 걸어다니며, 먹이를 잡을 때는 물 속을 헤엄쳐서 물고기를 사냥합니다.");
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.anime_crocodile),
                "악어", "생김새는 도마뱀과 비슷하지만 몸길이가 평균적으로 4m를 넘는 종도 있을 정도로 매우 큽니다. 각질의 비늘로 덮여 있고, 주둥이는 넓고 길며 튼튼하며 눈이 머리 꼭대기에 있고 발가락 사이에는 물갈퀴가 있습니다.");
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.anime_deer),
                "사슴", "소목 사슴과에 속하는 중대형의 초식동물이며, 갈색 털과 수컷의 크고 아름다운 뿔이 특징입니다. 유명한 사슴 캐릭터로는 디즈니의 애니메이션으로 나온 밤비가 있습니다.");
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.anime_fox),
                "여우", "한국을 비롯해서 전세계적으로 가장 널리 퍼졌고 여우 무리를 대표하는 종류는 붉은여우입니다. 세계적으로 많이 분포하고, 그 영리함이 교활함으로 여겨졌는지 동서양에서 민담 등에 트릭스터로서 많이 등장하며, 불여우라고도 불립니다.");
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.anime_koala),
                "코알라", "오스트레일리아 동부에 사는 동물입니다. 게으른 동물의 대표주자격이자 어떠한 해도 끼치지 않는 온순한 동물이며, 사람을 별로 무서워하지 않습니다.");
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.anime_squirrel),
                "다람쥐", "다람쥐는 먹이를 저장해 두는 것으로 유명하며, 특히 볼주머니는 탄력이 좋아 땅콩 7~8개 정도는 쉽게 들어갑니다. 이렇게 저장한 먹이는 둥지로 나르거나 땅에 묻어 보관합니다.");
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.anime_turtle),
                "거북이", "거북은 파충류에 속하는 동물입니다. 알을 낳는 난생이며 거북의 몸은 단단한 등딱지 안에 갇혀 있고 배딱지와 등딱지는 각각 연결되어 있어 상자와 같은 인상을 준다.");

        //ZooKeepers
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.zookeeper1),
                "훈련 담당 사육사", "동물을 훈련하여 공연을 진행합니다.");
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.zookeeper2),
                "먹이 담당 사육사", "동물에게 먹이 활동을 진행합니다.");
        listHelpAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.zookeeper3),
                "청소 담당 사육사", "동물원 및 동물의 배설물 청소를 진행합니다.");
    }
}