package com.sstanislavsky.acts.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by stanislav on 2/28/18.
 */

// Now is not used
public class ToUpperCaseFirstLetterTextWatcher implements TextWatcher {

    private EditText editText;
    private String enteredText = "";
    private int editTextCursorPosition = 0;
    private boolean isCursorMoveFromEnd = false;
    //private boolean isCursorMoveToEnd = false;

    public ToUpperCaseFirstLetterTextWatcher(EditText editText) {
        super();
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
        enteredText = charSequence.toString();
        //editTextCursorPosition = editText.getSelectionStart() - 1;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String logMethodParam = charSequence.toString() + " " + start + " " + before + " " + count;
        Log.d("TextWatcher_on()", logMethodParam);
        String logLastWord = String.valueOf(getNumberOfFirstCharacterInLastWord(charSequence.toString()));
        Log.d("TextWatcher_getSpace", logLastWord);

        int firstCharAtLastWord = getNumberOfFirstCharacterInLastWord(charSequence.toString());
        // костыль, ибо .getSelectionStart() иногда возвращает 0
        if (editText.getSelectionStart() != 0)
            editTextCursorPosition = editText.getSelectionStart();
        else if (charSequence.length() == 0)
            editTextCursorPosition = 0;
        //int enteredTextLength = charSequence.length();

        Log.d("CursorPosition_on()", String.valueOf(editTextCursorPosition));

        if (count >= 1 && (count - before) > 0 &&
                Character.isLowerCase(charSequence.charAt(/*start*/firstCharAtLastWord)) &&
                editTextCursorPosition == charSequence.length()) {
            Character ch = charSequence.charAt(start);
            String firstLetter = ch.toString();
            Log.d("TextWatcher_in_if()", firstLetter);

            String result = firstLetter.toUpperCase() + charSequence.subSequence(start + 1, count + start);
            enteredText += result;
            editText.setText(enteredText);
            //editTextCursorPosition = enteredText.length();
            editText.setSelection(editTextCursorPosition);
            Log.d("CursorPosition_in_if", String.valueOf(editTextCursorPosition));
        }

        /* Следующий код из двух операторлов if служит для фиксации перемещения курсора EditText
         * из его конца в другую позицию и обратно. Этим осуществляется проверка изменения
         * пользователем введенных данных не в конце EditText и переведение подстроки в
         * верхний регистр. */
        if (/*editTextCursorPosition*/editTextCursorPosition < enteredText.length()) {
            isCursorMoveFromEnd = true;
            Log.d("isCursorMoveFromEnd", "true");
        }

        if (isCursorMoveFromEnd && editTextCursorPosition == enteredText.length()) {
            isCursorMoveFromEnd = false;
            String[] enteredTextArray = charSequence.toString().split(" ");
            StringBuilder builder = new StringBuilder();

            for(String s : enteredTextArray) {
                if (s.length() > 0) {
                    Log.d("enteredTextArray", s);
                    String temp = s.substring(0, 1).toUpperCase() + s.substring(1) + " ";
                    Log.d("split", temp);
                    builder.append(temp);
                    editText.setText(builder.toString());
                    editText.setSelection(editText.length());
                } else Log.d("String.split() is < 1", s);
            }
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
