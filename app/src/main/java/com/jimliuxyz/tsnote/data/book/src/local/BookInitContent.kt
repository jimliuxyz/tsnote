package com.jimliuxyz.tsnote.data.book.src.local

import com.jimliuxyz.tsnote.data.book.BookContent
import com.jimliuxyz.tsnote.data.book.BookInfo
import com.jimliuxyz.tsnote.data.book.KeyNote

/**
 * Created by jimliu on 2018/3/22.
 */
object BookInitContent {

    private var dao: BookDao? = null

    private fun add(title: String, content: String, vararg keyNotes: KeyNote) {
        val info = BookInfo(title = title)
        val content = BookContent(info.id, content, arrayListOf(*keyNotes))

        dao?.insertBookInfo(info)
        dao?.insertBookContent(content)
    }

    fun init(dao: BookDao) {
        if (this.dao != null) return
        this.dao = dao


        add("The Fox and the Grapes",
                """One hot summer's day a Fox was strolling through an orchard till he came to a bunch of Grapes just ripening on a vine which had been trained over a lofty branch.

"Just the thing to quench my thirst," quoth he.

Drawing back a few paces, he took a run and a jump, and just missed the bunch.

Turning round again with a One, Two, Three, he jumped up, but with no greater success.

Again and again he tried after the tempting morsel, but at last had to give it up, and walked away with his nose in the air, saying:"I am sure they are sour."
----------
The moral is that：It is easy to despise what you cannot get.
""",
                KeyNote("strolling through an orchard", "漫步通過果園"),
                KeyNote("bunch of Grapes", "一串葡萄"),
                KeyNote("ripening on a vine", "在藤蔓上成熟"),
                KeyNote("lofty", "高遠"),
                KeyNote("quench my thirst", "解渴"),
                KeyNote("Drawing back", "退後一步"),
                KeyNote("tempting morsel", "誘人的食物"),
                KeyNote("sour", "酸"),
                KeyNote("nose in the air", "鼻朝天,高傲的"),
                KeyNote("moral", "寓意,道德"),
                KeyNote("It is easy to despise what you cannot get", "輕視你無法得到的東西可真容易")
        )



        add("Last Christmas",
                """Last Christmas, I gave you my heart
But the very next day you gave it away
This year, to save me from tears
I'll give it to someone special

Once bitten and twice shy
I keep my distance
But you still catch my eye
Tell me, baby, Do you recognize me?
Well, it's been a year, It doesn't surprise me

(Merry Christmas!) I wrapped it up and sent it
With a note saying, "I love you", I meant it
Now, I know what a fool I've been
But if you kissed me now
I know you'd fool me again

Last Christmas, I gave you my heart
But the very next day you gave it away
This year, to save me from tears
I'll give it to someone special

A crowded room, friends with tired eyes
I'm hiding from you, and your soul of ice
My god, I thought you were someone to rely on
Me? I guess I was a shoulder to cry on

A face on a lover with a fire in his heart
A man under cover but you tore me apart
Now, I've found a real love you'll never fool me again

Last Christmas, I gave you my heart
But the very next day you gave it away
This year, to save me from tears
I'll give it to someone special
""",
                KeyNote("special", "特別"),
                KeyNote("distance", "距離"),
                KeyNote("recognize", "辨識,認識"),
                KeyNote("catch my eye", "吸引我的目光"),
                KeyNote("surprise me", "讓我吃驚"),
                KeyNote("wrapped it up", "把它包起來"),

                KeyNote("I meant it", "我是認真的"),
                KeyNote("crowded room", "擁擠的房間"),
                KeyNote("rely on", "依靠"),
                KeyNote("shoulder", "肩膀"),
                KeyNote("tore me apart", "撕裂我"),
                KeyNote("under cover", "在掩護下"),
                KeyNote("gave it away", "丟棄了"),
                KeyNote("Once bitten and twice shy", "被咬一次,之後就膽怯")
        )

        add("Yesterday Once More",
                """When I was young I'd listen to the radio
Waiting for my favorite songs
When they played I'd sing along, it made me smile

Those were such happy times and not so long ago
How I wondered where they'd gone
But they're back again just like a long lost friend
All the songs I loved so well

Every sha-la-la-la
Every wo-o-wo-o, still shines
Every shing-a-ling-a-ling, that they're starting to sing's, so fine
When they get to the part
Where he's breaking her heart
It can really make me cry, just like before
It's yesterday once more

Looking back on how it was in years gone by
And the good times that I had
Makes today seem rather sad, so much has changed

It was songs of love that I would sing to then
And I'd memorize each word
Those old melodies still sound so good to me
As they melt the years away

Every sha-la-la-la
Every wo-o-wo-o, still shines
Every shing-a-ling-a-ling, that they're starting to sing's so fine

All my best memories come back clearly to me
Some can even make me cry, just like before
It's yesterday once more
"""
                ,
                KeyNote("listen", "聴く"),
                KeyNote("favorite", "특히 잘하는"),
                KeyNote("sing", "петь"),
                KeyNote("happy", "มีความสุข"),
                KeyNote("When I was young", "내가 어렸을 때"),
                KeyNote("friend", "người bạn"),
                KeyNote("yesterday", "어제"),
                KeyNote("shines", "輝く"),
                KeyNote("song", "노래들"),
                KeyNote("word", "워드"),
                KeyNote("love", "amour"),
                KeyNote("cry", "泣く"),
                KeyNote("it made me smile", "它讓我微笑"),
                KeyNote("How I wondered where they'd gone", "彼らはどこに行ったのだろうか？")
        )


        add("readme",
                """這是一個翻譯用的筆記app，
文字內容可經由右上方按鈕貼上，
或在其他app中選字後在長按選單中直接開啟。

支持多國語言，選字後會自動翻譯與發音。

Q:如何刪除筆記?
A:在列表中長按欲刪除的筆記即可。
""",
                KeyNote("翻譯", "translation"),
                KeyNote("筆記", "note"),
                KeyNote("文字內容", "text content"),
                KeyNote("右上方按鈕貼上", "paste by press top right icon"),
                KeyNote("長按選單", "long press menu"),
                KeyNote("多國語言", "multi-lingual"),
                KeyNote("發音", "pronunciation"),
                KeyNote("如何刪除筆記", "How to delete notes?")
        )
    }
}