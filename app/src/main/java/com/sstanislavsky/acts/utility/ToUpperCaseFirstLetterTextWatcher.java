package com.sstanislavsky.acts.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by stanislav on 2/28/18.
 */

public class ToUpperCaseFirstLetterTextWatcher implements TextWatcher {

    private EditText editText;

    public ToUpperCaseFirstLetterTextWatcher(EditText editText) {
        super();
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String log = charSequence.toString() + " " + start + " " + before + " " + count;
        Log.d("TextWatcher_on()", log);
        //Log.d("TextWatcher_indexOf()", String.valueOf(charSequence.toString().indexOf(" ")));
        Log.d("TextWatcher_getSpace", String.valueOf(getNumberOfFirstCharacterInLastWord(charSequence.toString())));

        String enteredText = charSequence.toString();
        int firstCharAtLastWord = getNumberOfFirstCharacterInLastWord(charSequence.toString());

        if (count >= 1 && (count - before) > 0 &&
                Character.isLowerCase(charSequence.charAt(/*start*/firstCharAtLastWord))) {
            Character ch = charSequence.charAt(start);
            String firstLetter = ch.toString();
            Log.d("TextWatcher_in_if()", firstLetter);

            String result = firstLetter.toUpperCase() + charSequence.subSequence(start + 1, count + start);
            enteredText += result;
            editText.setText(enteredText);
            editText.setSelection(enteredText.length());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {}

    private int getNumberOfFirstCharacterInLastWord(String string) {
         int result = string.lastIndexOf(" ");
         if (string.endsWith(" ")) return 0;
         else return result + 1;
    }
}
