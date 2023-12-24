package com.kozhemyakin.lab2;

import java.util.List;

/**
 * WordBuff implementation
 * @author n.s.kozhemyakin
 */
public class WordBuff {
    private int position;
    public List<Word> words;

    public WordBuff(List<Word> words) {
        this.words = words;
    }

    public Word next(){
        return words.get(position++);
    }

    public void back(){
        position--;
    }

    public int getPosition(){
        return position;
    }
}

