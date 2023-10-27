package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var buttonA : Button
    lateinit var buttonBb : Button
    lateinit var buttonB : Button
    lateinit var buttonC : Button
    lateinit var buttonCSharp : Button
    lateinit var buttonD : Button
    lateinit var buttonDSharp : Button
    lateinit var buttonE : Button
    lateinit var buttonF : Button
    lateinit var buttonFSharp : Button
    lateinit var buttonG : Button
    lateinit var buttonGSharp : Button
    lateinit var buttonPlay : Button
    lateinit var soundPool : SoundPool
    var aNote = 0
    var bbNote = 0
    var bNote = 0
    var cNote = 0
    var cSharpNote = 0
    var dNote = 0
    var dSharpNote = 0
    var eNote = 0
    var fNote = 0
    var fSharpNote = 0
    var gNote = 0
    var gSharpNote = 0
    var lowGNote = 0
    var highANote = 0
    var highBNote = 0
    var highBBNote = 0
    var highCNote = 0
    var highCSharpNote = 0
    var highDNote = 0
    var highDSharpNote = 0
    var highENote = 0
    var highFNote = 0
    var highFSharpNote = 0
    var highGNote = 0
    var highGSharpNote = 0

    lateinit var notes: ArrayList<Note>
    var noteMap = HashMap<String, Int>()
//    val song: List<Note> = listOf(Note("highA", 200), Note("highA", 200), Note("highA", 200), Note("highA", 500), Note("highA", 200),
//    Note("G", 200), Note("E",200 ),Note("C",500 ),Note("C", 200 ), Note("highA",200 ), Note("highA",200 ),
//        Note("G", 150 ), Note("F", 200 ), Note("G", 1000 ),Note("F",150), Note("highBB",150),Note("highBB", 200),
//        Note("highBB", 400),Note("highBB",150),Note("highA",400), Note("G",200), Note("F", 300), Note("F",300),
//        Note("highA", 200),Note("highA", 100), Note("G",300),Note("F", 100), Note("highA", 1500),
//        Note("highA", 150), Note("highA",150),Note("highA", 150), Note("highA", 200), Note("highA" ,400),Note("highA",200), Note("G",200),Note("E",125), Note("C",2000),
//        Note("highA", 300), Note("highA", 300), Note("G", 150), Note("F", 200), Note("G",2000),
//        Note("F", 150), Note("highBB",150),Note("highBB",200),Note("highBB",300), Note("highBB",150),Note("highA",250),Note("G", 200)
//
//
//  )
 // val song: List<Note> = listOf(eNote, dNote,cNote,dNote,eNote, eNote, eNote, dNote, dNote,dNote,eNote,gNote, gNote,eNote,dNote, cNote, dNote,eNote, eNote, eNote, dNote,dNote, dNote, eNote, dNote, cNote)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wireWidgets()
        initializeSoundPool()
        setListeners()
        loadNotes()
        //playSong(song)
        buttonPlay.setOnClickListener {
            playSong(notes)
        }
    }

    private fun loadNotes() {

        val inputStream = resources.openRawResource(R.raw.song)
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        Log.d(TAG, "OnCreate: jsonString $jsonString")
        val gson = Gson()
        val qType = object: TypeToken<List<Note>>() { }.type
        notes = gson.fromJson(jsonString, qType)
        Log.d(TAG, "LoadNotes: $notes")
    }

    private fun setListeners() {
        val soundBoardListener = SoundBoardListener()
        buttonA.setOnClickListener(soundBoardListener)
        buttonBb.setOnClickListener(soundBoardListener)
        buttonB.setOnClickListener(soundBoardListener)
        buttonC.setOnClickListener(soundBoardListener)
        buttonCSharp.setOnClickListener(soundBoardListener)
        buttonD.setOnClickListener(soundBoardListener)
        buttonDSharp.setOnClickListener(soundBoardListener)
        buttonE.setOnClickListener(soundBoardListener)
        buttonF.setOnClickListener(soundBoardListener)
        buttonFSharp.setOnClickListener(soundBoardListener)
        buttonG.setOnClickListener(soundBoardListener)
        buttonGSharp.setOnClickListener(soundBoardListener)



    }


    private fun wireWidgets() {
        buttonA = findViewById(R.id.button_main_a)
        buttonBb = findViewById(R.id.button_main_bb)
        buttonB = findViewById(R.id.button_main_b)
        buttonC = findViewById(R.id.button_main_c)
        buttonCSharp = findViewById(R.id.button_main_cSharp)
        buttonD = findViewById(R.id.button_main_d)
        buttonDSharp = findViewById(R.id.button_main_dSharp)
        buttonE = findViewById(R.id.button_main_e)
        buttonF = findViewById(R.id.button_main_f)
        buttonFSharp = findViewById(R.id.button_main_fSharp)
        buttonG = findViewById(R.id.button_main_g)
        buttonGSharp = findViewById(R.id.button_main_gSharp)
        buttonPlay = findViewById(R.id.button_main_play)
    }
//    private fun getNoteId(key : String): Int {
//        return when(key) {
//            "A" -> aNote
//            "B" -> bNote
//            "C" -> cNote
//            "D" -> dNote
//            "E" -> eNote
//            "F" -> fNote
//            "G" -> gNote
//            else -> {
//                -1
//            }
//        }
//
//    }
    private fun initializeSoundPool() {

        this.volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
//        soundPool.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
//           // isSoundPoolLoaded = true
//        })
        aNote = soundPool.load(this, R.raw.scalea, 1)
        bbNote = soundPool.load(this, R.raw.scalebb, 1)
        bNote = soundPool.load(this, R.raw.scaleb, 1)
        cNote =  soundPool.load(this, R.raw.scalec, 1)
        cSharpNote = soundPool.load(this, R.raw.scalecs, 1)
        dNote =  soundPool.load(this, R.raw.scaled, 1)
        dSharpNote =  soundPool.load(this, R.raw.scaleds, 1)
        eNote =  soundPool.load(this, R.raw.scalee, 1)
        fNote =  soundPool.load(this, R.raw.scalef, 1)
        fSharpNote =  soundPool.load(this, R.raw.scalefs, 1)
        gNote =  soundPool.load(this, R.raw.scaleg, 1)
        gSharpNote =  soundPool.load(this, R.raw.scalegs, 1)
        lowGNote = soundPool.load(this, R.raw.scalelowg, 1)
        highANote = soundPool.load(this,R.raw.scalehigha,1)
        highBNote = soundPool.load(this,R.raw.scalehighb,1)
        highBBNote = soundPool.load(this,R.raw.scalehighbb,1)
        highCNote = soundPool.load(this,R.raw.scalehighc,1)
        highCSharpNote = soundPool.load(this,R.raw.scalehighcs,1)
        highDNote = soundPool.load(this,R.raw.scalehighd,1)
        highDSharpNote = soundPool.load(this,R.raw.scalehighds,1)
        highENote = soundPool.load(this,R.raw.scalehighe,1)
        highFNote = soundPool.load(this,R.raw.scalehighf,1)
        highFSharpNote = soundPool.load(this,R.raw.scalehighfs,1)
        highGNote = soundPool.load(this,R.raw.scalehighg,1)
        highGSharpNote = soundPool.load(this,R.raw.scalehighgs,1)
        // Maps use key-value pairs ( just like the bundle)
        noteMap.put("A", aNote)
        noteMap["Bb"] = bbNote
        noteMap["B"] = bNote
        noteMap["C"] = cNote
        noteMap["cSharp"] = cSharpNote
        noteMap["D"] = dNote
        noteMap["dSharp"] = dSharpNote
        noteMap["E"] = eNote
        noteMap["F"] = fNote
        noteMap["fSharp"] = fSharpNote
        noteMap["G"] = gNote
        noteMap["gSharp"] = gSharpNote
        noteMap["highA"] = highANote
        noteMap["highB"] = highBNote
        noteMap["highBB"] = highBBNote
        noteMap["highC"] = highCNote
        noteMap["highCSharp"] = highCSharpNote
        noteMap["highD"] = highDNote
        noteMap["highDSharp"] = highDSharpNote
        noteMap["highE"] = highENote
        noteMap["highF"] = highFNote
        noteMap["highFSharp"] = highFSharpNote
        noteMap["highG"] = highGNote
        noteMap["highGSharp"] = highGSharpNote





}

    private fun playNote(note: String) {
        // ?: elvis operator provides default value if value is null
        playNote(noteMap[note] ?: 0)
      //  soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }
    private fun playNote(noteId: Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }


    private fun playSong(song: List<Note>){
//        for(i in song.indices)
//        {
//            playNote(getNoteId(song[i].note))
//            delay(song[i].delay)
//        }
        GlobalScope.launch {


            for (item in song.indices) {
                playNote(song[item].note)
                delay(song[item].duration)
            }
        }
        //loop through list of notes
        //play the note
        //delay for the delay
        //note you get is a String
        // to play the note, you need the corresponding soundPool objec
    }
    private fun delay(time: Long) {
        try {
            Thread.sleep(time)
        } catch(e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private inner class SoundBoardListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when(v?.id) {
                R.id.button_main_a -> playNote(aNote)
                R.id.button_main_bb -> playNote(bbNote)
                R.id.button_main_b -> playNote(bNote)
                R.id.button_main_c -> playNote(cNote)
                R.id.button_main_cSharp -> playNote(cSharpNote)
                R.id.button_main_d -> playNote(dNote)
                R.id.button_main_dSharp -> playNote(dSharpNote)
                R.id.button_main_e -> playNote(eNote)
                R.id.button_main_f -> playNote(fNote)
                R.id.button_main_fSharp -> playNote(fSharpNote)
                R.id.button_main_g -> playNote(gNote)
                R.id.button_main_gSharp -> playNote(gSharpNote)

            }
        }
    }
}